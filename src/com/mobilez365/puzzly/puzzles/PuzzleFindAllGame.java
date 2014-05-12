package com.mobilez365.puzzly.puzzles;

import android.graphics.Point;

import java.util.List;

/**
 * Created by andrewtivodar on 12.05.2014.
 */
public class PuzzleFindAllGame {

    private int puzzleId;
    private String word;
    private String sound;
    private List<PuzzlesPart> parts;
    private String image;
    private String borderedImage;
    private String resultImage;

    public PuzzleFindAllGame(int puzzleId, String word, String sound, List<PuzzlesPart> parts, String image, String borderedImage, String resultImage) {
        this.puzzleId = puzzleId;
        this.word = word;
        this.sound = sound;
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

    public String getSound() {
        return sound;
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
