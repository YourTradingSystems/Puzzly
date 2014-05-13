package com.mobilez365.puzzly.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mobilez365.puzzly.R;

public class Menu extends Activity implements View.OnClickListener {

    private ImageView ivGameSimpleFill_MS;
    private ImageView ivGameSimpleReveal_MS;
    private Button btnGameSettings_MS;
    private Button btnGameAchievement_MS;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_screen);
        findViews();
        setListeners();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ivGameSimpleFill_MS:
                gameSimpleFill();
                break;

            case R.id.ivGameSimpleReveal_MS:
                gameSimpleReveal();
                break;

            case R.id.btnGameSettings_MS:
                settings();
                break;

            case R.id.btnGameAchievement_MS:
                achievement();
                break;
        }
    }

    private final void findViews() {
        ivGameSimpleFill_MS = (ImageView)findViewById(R.id.ivGameSimpleFill_MS);
        ivGameSimpleReveal_MS = (ImageView)findViewById(R.id.ivGameSimpleReveal_MS);
        btnGameSettings_MS = (Button)findViewById(R.id.btnGameSettings_MS);
        btnGameAchievement_MS = (Button)findViewById(R.id.btnGameAchievement_MS);
    }

    private final void setListeners() {
        ivGameSimpleFill_MS.setOnClickListener(this);
        ivGameSimpleReveal_MS.setOnClickListener(this);
        btnGameSettings_MS.setOnClickListener(this);
        btnGameAchievement_MS.setOnClickListener(this);
    }

    private final void gameSimpleFill() {

    }

    private final void gameSimpleReveal() {

    }

    private final void settings() {
        startActivity(new Intent(this, Settings.class));
    }

    private final void achievement() {

    }
}
