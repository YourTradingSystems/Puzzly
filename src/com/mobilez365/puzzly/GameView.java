package com.mobilez365.puzzly;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.mobilez365.puzzly.GameLogic;
import com.mobilez365.puzzly.InputObject;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.SpriteObject;

import java.util.concurrent.ArrayBlockingQueue;


public class GameView extends SurfaceView implements
		SurfaceHolder.Callback {

	private SpriteObject sprite;
	private GameLogic mGameLogic;
	private ArrayBlockingQueue<InputObject> inputObjectPool;
	
	public GameView(Context context) {
		super(context);
		getHolder().addCallback(this);
		sprite = new SpriteObject(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher), 50, 50);
		mGameLogic = new GameLogic(getHolder(), this);
		createInputObjectPool();
		setFocusable(true);
	}

	private void createInputObjectPool() {
		inputObjectPool = new ArrayBlockingQueue<InputObject>(20);
		for (int i = 0; i < 20; i++) {
			inputObjectPool.add(new InputObject(inputObjectPool));
		}
	}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			try {
				int hist = event.getHistorySize();
				if (hist > 0) {
					for (int i = 0; i < hist; i++) {
						InputObject input = inputObjectPool.take();
						input.useEventHistory(event, i);
						mGameLogic.feedInput(input);
					}
				}
				InputObject input = inputObjectPool.take();
				input.useEvent(event);
				mGameLogic.feedInput(input);
			} catch (InterruptedException e) {
			}
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
			}
			return true;
		}
		
		
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mGameLogic.setGameState(mGameLogic.RUNNING);
		mGameLogic.start();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}
	
	
	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		sprite.draw(canvas);
	}

	public void update(int adj_mov) {
		if (sprite.getX() >= getWidth()){ 
			//sprite.setMoveX(0);
		}
		if (sprite.getX() <= 0){
			//sprite.setMoveX(0);
		}
		sprite.update(adj_mov);

	}
	
	public void processMotionEvent(InputObject input){
		sprite.setX(input.x);
		sprite.setY(input.y);
	}
	
	public void processKeyEvent(InputObject input){
	}

	public void processOrientationEvent(float orientation[]){
		
		float roll = orientation[2];
		if (roll < -40) {
			sprite.setMoveX(2);
		} else if (roll > 40) {
			sprite.setMoveX(-2);
		}
		
	}
	
	

	
	

	
}
