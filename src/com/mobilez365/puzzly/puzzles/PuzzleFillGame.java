package com.mobilez365.puzzly.puzzles;

import android.graphics.Point;

import java.util.List;

/**
 * Created by andrewtivodar on 12.05.2014.
 */
public class PuzzleFillGame {

	private int gameType;
	private String word;
    private String itemName;
    private List<PuzzlesPart> parts;
    private String image;
    private String resultImage;
    private Point figurePos;

    public PuzzleFillGame(int gameType, String word, String itemName, List<PuzzlesPart> parts, String image, String resultImage, Point figurePos)  {
        this.gameType = gameType;
    	this.word = word;
        this.itemName = itemName;
        this.parts = parts;
        this.image = image;
        this.resultImage = resultImage;
        this.figurePos = figurePos;
    }


    public String getWord() {
        return word;
    }

    public String getItemName() {
        return itemName;
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
    public int getGameType() {
		return gameType;
	}

}
