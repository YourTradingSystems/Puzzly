package com.mobilez365.puzzly.util;

import android.app.Activity;
import android.graphics.Canvas;
import com.mobilez365.puzzly.customViews.GameView;

public class GameLoopThread extends Thread {
    public static final long FPS = 50;
    private GameView view;
    private volatile boolean running = false;
    private GameView.GameCallBacks listener;

    public GameLoopThread(GameView view, GameView.GameCallBacks listener) {
        this.view = view;
        this.listener = listener;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;

        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.onDraw(c);
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
                if (view.isEnd()) {
                    setRunning(false);
                    ((Activity)listener).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onGameFinish();
                        }
                    });
                }
            }
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {
            }
        } ;

    }
}  