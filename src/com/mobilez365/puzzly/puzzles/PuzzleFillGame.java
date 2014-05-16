package com.mobilez365.puzzly.puzzles;

import android.graphics.Point;

import java.util.List;

/**
 * Created by andrewtivodar on 12.05.2014.
 */
public class PuzzleFillGame {

    private int puzzleId;
    private String word;
    private List<PuzzlesPart> parts;
    private String image;
    private String borderedImage;
    private String resultImage;

    public PuzzleFillGame(int puzzleId, String word, List<PuzzlesPart> parts, String image, String borderedImage, String resultImage) {
        this.puzzleId = puzzleId;
        this.word = word;
        this.parts = parts;
        this.image = image;
        this.borderedImage = borderedImage;
        this.resultImage = resultImage;
    }

    public int getPuzzleId() {
        return puzzleId;
    }

    public String getWord() {
        return word;
    }

    public List<PuzzlesPart> getParts() {
        return parts;
    }

    public String getImage() {
        return image;
    }

    public String getBorderedImage() {
        return borderedImage;
    }

    public String getResultImage() {
        return resultImage;
    }
}
