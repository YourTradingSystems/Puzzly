package com.mobilez365.puzzly.customViews;


import android.content.Context;
import android.graphics.*;
import android.util.DisplayMetrics;
import android.view.*;
import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.mobilez365.puzzly.puzzles.GameSprite;
import com.mobilez365.puzzly.puzzles.PuzzleFillGame;
import com.mobilez365.puzzly.puzzles.PuzzlesPart;
import com.mobilez365.puzzly.util.GameLoopThread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameLoopThread gameLoopThread;
    private PuzzleFillGame puzzleFillGame;
    private volatile boolean end = false;
    private LinkedHashSet<GameSprite> sprites = new LinkedHashSet<GameSprite>();
    private Context context;
    private final DisplayMetrics metrics = new DisplayMetrics();
    private int displayWidth;
    private int displayHeight;
    private int svgBackgroundWidth = 0;
    private int svgBackgroundHeight = 0;
    private Bitmap background;
    private GameSprite sprite;
    private GameCallBacks listener;

    public interface GameCallBacks {
        public void onGameFinish();

        public void onPartMove();

        public void onPartsLock();
    }

    ;

    public GameView(Context context, PuzzleFillGame puzzleFillGame, GameCallBacks listener) {
        super(context);
        this.context = context;
        this.puzzleFillGame = puzzleFillGame;
        this.listener = listener;

        gameLoopThread = new GameLoopThread(this);
        //setZOrderOnTop(true);
        SurfaceHolder holder = getHolder();
        setFocusable(true);
        holder.addCallback(this);
        holder.setFormat(PixelFormat.TRANSPARENT);
        creteBackground(puzzleFillGame.getImage());
        createSprites(puzzleFillGame.getParts());
    }

    private int getResIdFromString(String name) {
        return getResources().getIdentifier(name, "raw", context.getPackageName());
    }

    private void creteBackground(String backgroundName) {
        final WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Display d = w.getDefaultDisplay();
        d.getMetrics(metrics);
        displayWidth = metrics.widthPixels;
        displayHeight = metrics.heightPixels;
        SVG svg = SVGParser.getSVGFromResource(getResources(), getResIdFromString(backgroundName));
        Bitmap bmp = Bitmap.createBitmap(displayWidth, displayHeight, Bitmap.Config.ARGB_8888);
        Canvas cnv = new Canvas(bmp);
        cnv.setDensity((int) (metrics.xdpi));
        Picture picture = svg.getPicture();
        svgBackgroundWidth = picture.getWidth();
        svgBackgroundHeight = picture.getHeight();
        cnv.drawPicture(picture, new Rect(0, 0, displayWidth, displayHeight));
        background = bmp;
    }

    private void createSprites(List<PuzzlesPart> parts) {
        for (PuzzlesPart part : parts) {
            sprites.add(new GameSprite(this, createSpriteBitmap(getResIdFromString(part.partImage)),
                    getScaledX(part.finalPartLocation.x), getScaledY(part.finalPartLocation.y),
                    getScaledX(part.currentPartLocation.x), getScaledY(part.currentPartLocation.y)));
        }
    }

    private int getScaledX(int orig) {
        return Math.round(orig * displayWidth / svgBackgroundWidth);
    }

    private int getScaledY(int orig) {
        return Math.round(orig * displayHeight / svgBackgroundHeight);
    }

    private Bitmap createSpriteBitmap(int svgResourceId) {
        SVG svg = SVGParser.getSVGFromResource(getResources(), svgResourceId);
        Picture picture = svg.getPicture();
        int spriteWidth = getScaledX(picture.getWidth());
        int spriteHeight = getScaledY(picture.getHeight());
        Bitmap bmp = Bitmap.createBitmap(spriteWidth, spriteHeight, Bitmap.Config.ARGB_8888);
        Canvas cnv = new Canvas(bmp);
        cnv.setDensity((int) (metrics.xdpi));
        cnv.drawPicture(picture, new Rect(0, 0, spriteWidth, spriteHeight));
        return bmp;
    }

    public void onDraw(Canvas canvas) {
        if (canvas == null) return;
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawBitmap(background, 0, 0, null);

        synchronized (this) {
            if (!isEnd())
                for (GameSprite spt : sprites) {
                    spt.onDraw(canvas);
                }
        }

    }

    public boolean onTouchEvent(MotionEvent event) {
        int xPos = (int) event.getX();
        int yPos = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //select last touched sprite
                List<GameSprite> list;
                synchronized (this) {
                    list = new ArrayList<GameSprite>(sprites);
                }
                Collections.reverse(list);
                for (GameSprite spr : list) {
                    if (spr.isCollision(xPos, yPos)) {
                        listener.onPartMove();
                        sprite = spr;
                        //move touched sprite to end of sprites sorted set
                        synchronized (this) {
                            if (sprites.contains(sprite)) sprites.remove(sprite);
                            sprites.add(sprite);
                        }
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (sprite != null && !sprite.isPieceLocked()) {
                    sprite.update(xPos, yPos);
                    if (sprite.checkPieceLocked()) {
                        sprite.setPieceLocked(true);
                        listener.onPartsLock();
                    }
                    break;
                }
                break;
            case MotionEvent.ACTION_UP:
                sprite = null;
                //check end of game
                boolean tempEnd = true;
                synchronized (this) {
                    for (GameSprite spr : sprites) {
                        if (!spr.isPieceLocked()) {
                            tempEnd = false;
                            break;
                        }
                    }
                }
                end = tempEnd;
                if (end) {
                    listener.onGameFinish();
                    creteBackground(puzzleFillGame.getResultImage());

                    Canvas c = getHolder().lockCanvas();
                    synchronized (getHolder()) {
                        onDraw(c);
                    }
                    getHolder().unlockCanvasAndPost(c);
                }
                break;
        }
        return true;
    }

    public boolean isEnd() {
        return end;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        gameLoopThread.setRunning(false);
        while (retry) {
            try {
                gameLoopThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (gameLoopThread.getState() == Thread.State.TERMINATED) {
            gameLoopThread = new GameLoopThread(GameView.this);
        }
        gameLoopThread.setRunning(true);
        gameLoopThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }
}
 