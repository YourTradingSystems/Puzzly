package com.mobilez365.puzzly.puzzles;

import android.graphics.Point;

import java.util.List;

/**
 * Created by andrewtivodar on 12.05.2014.
 */
public class PuzzlesPart {
    public int partId;
    public String partImage;
    public Point currentPartLocation;
    public Point finalPartLocation;

    public PuzzlesPart(int partId, String partImage, Point currentPartLocation, Point finalPartLocation) {
        this.partId = partId;
        this.partImage = partImage;
        this.currentPartLocation = currentPartLocation;
        this.finalPartLocation = finalPartLocation;
    }
}
