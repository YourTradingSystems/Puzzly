package com.mobilez365.puzzly.puzzles;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;

import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.Constans;

import java.util.List;

/**
 * Created by andrewtivodar on 12.05.2014.
 */
public class PuzzleFillGame {

	private int gameType;
    private String itemName;
    private List<PuzzlesPart> parts;
    private String image;
    private String resultImage;
    private Point figurePos;

    public PuzzleFillGame(int gameType, String itemName, List<PuzzlesPart> parts, String image, String resultImage, Point figurePos)  {
        this.gameType = gameType;
        this.itemName = itemName;
        this.parts = parts;
        this.image = image;
        this.resultImage = resultImage;
        this.figurePos = figurePos;
    }

    public String getWord(Activity _activity) {
        AppHelper.changeLanguage(_activity, AppHelper.getLocaleLanguage(_activity, Constans.GAME_LANGUAGE).name());
        return _activity.getResources().getString(_activity.getResources().getIdentifier(itemName, "string", _activity.getPackageName()));
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
