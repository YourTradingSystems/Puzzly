package com.mobilez365.puzzly.puzzles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import com.mobilez365.puzzly.customViews.GameView;

public class GameSprite {
    private static final int DISTANCE = 15;
    //current coordinates
    private int x = 0;
    private int y = 0;
    //piece locked coordinates
    private int lockedX = 0;
    private int lockedY = 0;
    //distance for precision moving
    private int dX = 0;
    private int dY = 0;

    private int width = 0;
    private int height = 0;

    private GameView gameView;
    private Bitmap bmp;

    private boolean pieceLocked = false;

    public GameSprite(GameView gameView, Bitmap bmp, int lockedX, int lockedY, int startX, int startY) {

        this.x = startX;
        this.y = startY;
        this.gameView = gameView;
        this.bmp = bmp;
        this.lockedX = lockedX;
        this.lockedY = lockedY;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
    }

    public void update(int xPos, int yPos) {
        //check display limits
        if ((0 < xPos - dX) && (xPos - dX < gameView.getWidth() - width)) {
            this.x = xPos - dX;
        }
        if ((0 < yPos - dY) && (yPos - dY < gameView.getHeight() - height)) {
            this.y = yPos - dY;
        }
    }

    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(bmp, x, y, null);
    }

    public boolean isCollision(float x1, float y1) {
        int x2 = (int) x1;
        int y2 = (int) y1;
        boolean temp = false;
        if ((x2 > x && x2 < x + width && y2 > y && y2 < y + height) && !pieceLocked && bmp.getPixel(x2 - x, y2 - y) != Color.TRANSPARENT) {
            temp = true;
            dX = x2 - x;
            dY = y2 - y;
        }
        ;
        return temp;
    }

    public boolean isPieceLocked() {
        return pieceLocked;
    }

    public void setPieceLocked(boolean pieceLocked) {
        this.pieceLocked = pieceLocked;
        if (pieceLocked) {
            this.x = lockedX;
            this.y = lockedY;
        }
    }

    public boolean checkPieceLocked() {
        return (lockedX > x - DISTANCE && lockedX < x + DISTANCE && lockedY > y - DISTANCE && lockedY < y + DISTANCE);
    }


}