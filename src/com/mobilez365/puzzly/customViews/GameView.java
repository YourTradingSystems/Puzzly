package com.mobilez365.puzzly.customViews;


import android.content.Context;
import android.graphics.*;
import android.util.DisplayMetrics;
import android.view.*;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;
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
    private volatile boolean gameOver = false;
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
    private static final int FILL_GAME = 0;
    private static final int REVEAL_GAME = 1;
    private int gameType;
    private List<PuzzlesPart> parts;
    private Paint mCharacterPaint = null;
    private Animation mFadeIn;
    private Transformation mTransformation;

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
        gameLoopThread = new GameLoopThread(this, listener);
        SurfaceHolder holder = getHolder();
        setFocusable(true);
        holder.addCallback(this);
        holder.setFormat(PixelFormat.OPAQUE);

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
        if (gameOver) return;
        if (gameType == REVEAL_GAME) {
            GameSprite spr = (new GameSprite(this, createSpriteBitmap(getResIdFromString(shapeName)), figurePosX, figurePosY, figurePosX, figurePosY));
            spr.setPieceLocked(true);
            sprites.add(spr);
        }
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
        if (!gameOver && gameType == FILL_GAME) canvas.drawBitmap(shape, figurePosX, figurePosY, null);

        if ((mFadeIn == null || !mFadeIn.hasEnded()) && (gameType == FILL_GAME || !gameOver)) {
            synchronized (this) {
                for (GameSprite spt : sprites) {
                    spt.onDraw(canvas);
                }
            }
        }
        if (gameOver) {
           if(gameType == REVEAL_GAME){
               synchronized (this) {
               for (GameSprite spr : sprites) {
                   if (spr.isPieceLocked()) canvas.drawBitmap(spr.bmp, spr.lockedX, spr.lockedY, mCharacterPaint);
               }
           }   }
                    if (mFadeIn != null && mFadeIn.hasStarted() && !mFadeIn.hasEnded()) {
                        mFadeIn.getTransformation(System.currentTimeMillis(), mTransformation);
                        mCharacterPaint.setAlpha((int) (255 * mTransformation.getAlpha()));
                    } else if (mFadeIn != null && mFadeIn.hasEnded()) {
                        for (GameSprite spr : sprites) {
                            spr = null;
                        }
                        sprites.clear();
                        end = gameOver;
                    }
                    canvas.drawBitmap(shape, figurePosX, figurePosY, mCharacterPaint);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (gameOver) return true;
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
                        //check end of game
                            synchronized (this) {
                                gameOver = true;
                                switch (gameType) {
                                    case FILL_GAME:
                                        for (GameSprite spr : sprites) {
                                            if (!spr.isPieceLocked()) {
                                                gameOver = false;
                                                break;
                                            }
                                        }
                                        break;
                                    case REVEAL_GAME:
                                        gameOver = true;
                                        break;
                                }
                                if (gameOver) {
                                    createFigure(puzzleFillGame.getResultImage());
                                    fade();
                                }
                            }
                    }
                    break;
                }
                break;
            case MotionEvent.ACTION_UP:
                sprite = null;
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
            gameLoopThread = new GameLoopThread(GameView.this, listener);
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

    private void fade() {
        mCharacterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFadeIn = new AlphaAnimation((gameType == FILL_GAME) ? 0f : 0.35f, 1f);
        mTransformation = new Transformation();
        mFadeIn.setDuration(1000);
        mFadeIn.start();
        mFadeIn.getTransformation(System.currentTimeMillis(), mTransformation);
    }
}
 