package com.mobilez365.puzzly.puzzles;

import android.graphics.Point;

import java.util.List;

/**
 * Created by andrewtivodar on 12.05.2014.
 */
public class PuzzleFillGame {

    private String wordEng;
    private String wordRus;
    private List<PuzzlesPart> parts;
    private String image;
    private String resultImage;
    private Point figurePos;

    public PuzzleFillGame(String wordEng, String wordRus, List<PuzzlesPart> parts, String image, String resultImage, Point figurePos)  {
        this.wordEng = wordEng;
        this.wordRus = wordRus;
        this.parts = parts;
        this.image = image;
        this.resultImage = resultImage;
        this.figurePos = figurePos;
    }


    public String getWordEng() {
        return wordEng;
    }

    public String getWordRus() {
        return wordRus;
    }

    public List<PuzzlesPart> getParts() {
        return parts;
    }

    public String getImage() {
        return image;
    }

    public String getResultImage() {
        return resultImage;
    }

    public Point getFigurePos() {
        return figurePos;
    }
}
