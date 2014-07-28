package com.mobilez365.puzzly.puzzles;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import com.mobilez365.puzzly.customViews.AutoResizeImageView;

public class GameSprite {
    //piece locked coordinates
    public int lockedX = 0;
    public int lockedY = 0;
    //distance for precision moving
    public int deltaX = 0;
    public int deltaY = 0;

    private AutoResizeImageView puzzlePart;

    private boolean pieceLocked = false;

    public GameSprite(AutoResizeImageView _puzzlePart, int lockedX, int lockedY) {
        this.lockedX = lockedX;
        this.lockedY = lockedY;
        this.puzzlePart =_puzzlePart;
    }

    public boolean isPieceLocked() {
        return pieceLocked;
    }

    public void setPieceLocked(boolean pieceLocked) {
        this.pieceLocked = pieceLocked;
    }

    public AutoResizeImageView getPuzzlePart() {
        return puzzlePart;
    }

    public void onPartLock(int finalPosX, int finalPosY){
        ObjectAnimator moveXAnimator = ObjectAnimator.ofFloat(puzzlePart, "translationX", puzzlePart.getX(), finalPosX);
        ObjectAnimator moveYAnimator = ObjectAnimator.ofFloat(puzzlePart, "translationY", puzzlePart.getY(), finalPosY);

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(puzzlePart, "scaleX", 1f, 1.2f);
        scaleXAnimator.setRepeatCount(1);
        scaleXAnimator.setRepeatMode(ValueAnimator.REVERSE);

        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(puzzlePart, "scaleY", 1f, 1.2f);
        scaleYAnimator.setRepeatCount(1);
        scaleYAnimator.setRepeatMode(ValueAnimator.REVERSE);

        AnimatorSet moveSet = new AnimatorSet();
        moveSet.play(moveXAnimator).with(moveYAnimator);
        moveSet.setDuration(250);
        moveSet.start();

        AnimatorSet scaleInSet = new AnimatorSet();
        scaleInSet.play(scaleXAnimator).with(scaleYAnimator);
        scaleInSet.setDuration(125);
        scaleInSet.start();

        setPieceLocked(true);
        puzzlePart.setOnTouchListener(null);
    }

}