package com.mobilez365.puzzly.customViews;


import android.content.Context;
import android.graphics.*;
import android.util.DisplayMetrics;
import android.view.*;
import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.mobilez365.puzzly.R;
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
    private int figurePosX;
    private int figurePosY;
    private int svgBackgroundWidth = 800;
    private int svgBackgroundHeight = 480;
    private Bitmap shape;
    private GameSprite sprite;
    private GameCallBacks listener;
    private boolean showOnlyPicture = false;
    private static final int FILL_GAME = 0;
    private static final int REVEAL_GAME = 1;
    private int gameType;
    private List<PuzzlesPart> parts;

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
        this.parts = puzzleFillGame.getParts();
        gameLoopThread = new GameLoopThread(this);
        SurfaceHolder holder = getHolder();
        setFocusable(true);
        holder.addCallback(this);
        holder.setFormat(PixelFormat.TRANSPARENT);

        getDensity();
        gameType = puzzleFillGame.getGameType();
        createFigure(puzzleFillGame.getImage());
    }

    private int getResIdFromString(String name) {
        return getResources().getIdentifier(name, "raw", context.getPackageName());
    }

    private int getScaledXByShape(int origX, int svgWidth) {
        return Math.round(((origX + svgWidth / 2) * displayWidth / svgBackgroundWidth - svgWidth * displayHeight / svgBackgroundHeight / 2));
    }

    private int getScaledX(int orig) {
        return Math.round(orig * displayWidth / svgBackgroundWidth);
    }

    private int getScaledY(int orig) {
        return Math.round(orig * displayHeight / svgBackgroundHeight);
    }

    private void getDensity() {
        final WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Display d = w.getDefaultDisplay();
        d.getMetrics(metrics);
        displayWidth = metrics.widthPixels;
        displayHeight = metrics.heightPixels;
    }

    private void createFigure(String shapeName) {
        SVG svg = SVGParser.getSVGFromResource(getResources(), getResIdFromString(shapeName));
        Picture picture = svg.getPicture();
        int svgWidth = picture.getWidth();
        int svgHeight = picture.getHeight();
        int spriteWidth = getScaledY(svgWidth);
        int spriteHeight = getScaledY(svgHeight);
        Bitmap bmp = Bitmap.createBitmap(spriteWidth, spriteHeight, Bitmap.Config.ARGB_8888);
        Canvas cnv = new Canvas(bmp);
        cnv.setDensity((int) (metrics.xdpi));
        cnv.drawPicture(picture, new Rect(0, 0, spriteWidth, spriteHeight));
        figurePosX = getScaledXByShape(puzzleFillGame.getFigurePos().x, svgWidth);
        figurePosY = getScaledY(puzzleFillGame.getFigurePos().y);
        shape = bmp;
        if (showOnlyPicture) return;
        for (PuzzlesPart part : parts) {
            sprites.add(new GameSprite(this, createSpriteBitmap(getResIdFromString(part.partImage)),
                    figurePosX + part.finalPartLocation.x * spriteWidth / svgWidth,
                    getScaledY(puzzleFillGame.getFigurePos().y + part.finalPartLocation.y),
                    getScaledX(part.currentPartLocation.x), getScaledY(part.currentPartLocation.y)));
        }
    }


    private Bitmap createSpriteBitmap(int svgResourceId) {
        SVG svg = SVGParser.getSVGFromResource(getResources(), svgResourceId);
        Picture picture = svg.getPicture();
        int spriteWidth = getScaledY(picture.getWidth());
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
        canvas.drawColor(getResources().getColor(R.color.background_game));
        canvas.drawBitmap(shape, figurePosX, figurePosY, null);

        if (!showOnlyPicture)
            synchronized (this) {
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
                        sprite = spr;
                        //move touched sprite to end of sprites sorted set
                        synchronized (this) {
                            if (sprites.contains(sprite)) sprites.remove(sprite);
                            sprites.add(sprite);
                        }
                        listener.onPartMove();
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (sprite == null) break;
                if (!sprite.isPieceLocked()) {
                    sprite.update(xPos, yPos);
                    if (sprite.checkPieceLocked()) {
                        sprite.setPieceLocked(true);
                        listener.onPartsLock();
                    }
                    break;
                } else {//check end of game
                    boolean tempEnd = true;
                    switch (gameType) {
                        case FILL_GAME:
                            synchronized (this) {
                                for (GameSprite spr : sprites) {
                                    if (!spr.isPieceLocked()) {
                                        tempEnd = false;
                                        break;
                                    }
                                }
                            }
                            break;
                        case REVEAL_GAME:
                            tempEnd = true;
                            break;
                    }

                    end = tempEnd;
                }
                break;
            case MotionEvent.ACTION_UP:
                sprite = null;
                if (end) {
                    listener.onGameFinish();
                    createFigure(puzzleFillGame.getResultImage());

                    synchronized (this) {
                        for (GameSprite spr : sprites) {
                            spr = null;
                        }
                        sprites.clear();
                    }

                    showOnlyPicture = true;
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

    public void release() {
        getHolder().getSurface().release();
    }
}
 