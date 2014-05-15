package com.mobilez365.puzzly;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class SpriteObject {

	private Bitmap bitmap;
	private int x;		
	private int y;	
	private int x_move = 0;
	private int y_move = 0;
	

	public SpriteObject(Bitmap bitmap, int x, int y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
	}

	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	
	public void setMoveX(int movex){
		x_move = movex;
	}
	public void setMoveY(int movey){
		y_move = movey;
	}
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	
	

	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	}

	public void update(int adj_mov) {
			x += (adj_mov * x_move);
			y += (adj_mov * y_move);
		
	}

}
