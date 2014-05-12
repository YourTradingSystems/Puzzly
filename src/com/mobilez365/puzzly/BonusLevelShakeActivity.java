package com.mobilez365.puzzly;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.mobilez365.puzzly.util.ShakeSensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by andrewtivodar on 12.05.2014.
 */
public class BonusLevelShakeActivity extends Activity implements ShakeSensor.OnShakeListener, View.OnClickListener{

    private final int candiesCount = 10;
    private ShakeSensor mShaker;
    private Vibrator vibe;
    private List<ImageView> candiesList;
    private int candiesDroppedCount = 0;
    private int dropHeight;
    private int storeWidth;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_level_shake);

        mShaker = new ShakeSensor(this);
        mShaker.setOnShakeListener(this);

        final RelativeLayout candiesLayout = (RelativeLayout) findViewById(R.id.rlCandiesAS);

        candiesLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                initCandies(candiesLayout);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
                    candiesLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                else
                    candiesLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });


        vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

    }

    private void initCandies(RelativeLayout candiesLayout){

        int width = candiesLayout.getWidth();
        dropHeight = candiesLayout.getHeight();
        storeWidth = width;
        int step = width/candiesCount;

        candiesList = new ArrayList<ImageView>();
        for(int i = 0; i < candiesCount; i++) {
            ImageView candy = new ImageView(this);
            candy.setImageResource(R.drawable.ic_launcher);
            candy.setX(i * step);
            candy.setTag(false);
            candy.setOnClickListener(this);
            candiesList.add(candy);
            candiesLayout.addView(candy);

        }
    }

    private void dropCandy(){
        Random r = new Random();
        int candyNumber = r.nextInt(candiesCount);
        ImageView candy = candiesList.get(candyNumber);
        if(!(Boolean)candy.getTag()) {
            candy.setTag(true);
            ObjectAnimator fallAnimator = ObjectAnimator.ofFloat(candy, "translationY", 0f, dropHeight - candy.getHeight());
            fallAnimator.setDuration(300);
            fallAnimator.start();
            candiesDroppedCount ++;
        }
        else dropCandy();
    }

    @Override
    public void onResume()
    {
        mShaker.resume();
        super.onResume();
    }

    @Override
    public void onPause()
    {
        mShaker.pause();
        super.onPause();
    }

    @Override
    public void onShake() {
        if(candiesDroppedCount != candiesCount) {
            vibe.vibrate(100);
            dropCandy();
        }
    }

    @Override
    public void onClick(View v) {
        if((Boolean)v.getTag()) {
            ObjectAnimator storeAnimator = ObjectAnimator.ofFloat(v, "translationX", v.getX(), storeWidth);
            storeAnimator.setDuration(300);
            storeAnimator.start();
        }
    }
}