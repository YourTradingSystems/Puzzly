package com.mobilez365.puzzly.puzzles;

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

}