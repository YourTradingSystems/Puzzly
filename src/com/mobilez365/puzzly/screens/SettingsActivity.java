package com.mobilez365.puzzly.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.Constans;
import com.mobilez365.puzzly.util.BackgroundSound;

/**
 * Created by Denis on 12.05.14.
 */
public class SettingsActivity extends RestartActivty implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageButton btnBack_SS;
    private CheckBox ccbPlayBackgroundMusic_SS;
    private CheckBox ccbPlaySound_SS;
    private CheckBox ccbVibrate_SS;
    private CheckBox ccbDisplayInnerBorders_SS;
    private Spinner spinnerChooseCountry_SS;

    private boolean displayInit = true;
    private BackgroundSound mBackgroundSound;

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        AppHelper.changeLanguage(this, AppHelper.getLocaleLanguage(this).name());

        super.onCreate(_savedInstanceState);
        setContentView(R.layout.settings_screen);
        mBackgroundSound = AppHelper.getBackgroundSound();

        findViews();
        showBanner();
        setListener();
        setValues();
    }

    @Override
    public void onClick(View _v) {
        //_v.setClickable(false);
        switch (_v.getId()) {
            case R.id.btnBack_SS:
                finish();
                break;

            case R.id.ccbPlayBackgroundMusic_SS:
                AppHelper.setPlayBackgroundMusic(this, ccbPlayBackgroundMusic_SS.isChecked());

                if (ccbPlayBackgroundMusic_SS.isChecked()) {
                    AppHelper.startBackgroundSound(this, Constans.MENU_BACKGROUND_MUSIC);
                    mBackgroundSound = AppHelper.getBackgroundSound();
                }
                else {
                    AppHelper.stopBackgroundSound();
                }

                break;

            case R.id.ccbPlaySound_SS:
                AppHelper.setPlaySound(this, ccbPlaySound_SS.isChecked());
                break;

            case R.id.ccbVibrate_SS:
                AppHelper.setVibrate(this, ccbVibrate_SS.isChecked());
                break;

            case R.id.ccbDisplayInnerBorders_SS:
                AppHelper.setShowImageBorder(this, ccbDisplayInnerBorders_SS.isChecked());
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> _parent, View _view, int _position, long _id) {
        if (!displayInit) {
            AppHelper.setLocalizeLanguage(this, _position);
            AppHelper.changeLanguageRefresh(this, AppHelper.getLocaleLanguage(this).name());
        }
        else
            displayInit = false;
    }

    @Override
    public void onNothingSelected(AdapterView<?> _parent) {
        /**
         * Nothing
         */
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!AppHelper.isAppInBackground(this)) {
            if (mBackgroundSound != null && !mBackgroundSound.isPlay())
                mBackgroundSound.pause(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (AppHelper.isAppInBackground(this) || AppHelper.isScreenOff(this)) {
            if (mBackgroundSound != null && mBackgroundSound.isPlay())
                mBackgroundSound.pause(true);
        }
    }

    private final void findViews() {
        btnBack_SS = (ImageButton)findViewById(R.id.btnBack_SS);
        ccbPlayBackgroundMusic_SS = (CheckBox)findViewById(R.id.ccbPlayBackgroundMusic_SS);
        ccbPlaySound_SS = (CheckBox)findViewById(R.id.ccbPlaySound_SS);
        ccbVibrate_SS = (CheckBox)findViewById(R.id.ccbVibrate_SS);
        ccbDisplayInnerBorders_SS = (CheckBox)findViewById(R.id.ccbDisplayInnerBorders_SS);
        spinnerChooseCountry_SS = (Spinner)findViewById(R.id.spinnerChooseCountry_SS);
    }

    private final void setListener() {
        btnBack_SS.setOnClickListener(this);
        ccbPlayBackgroundMusic_SS.setOnClickListener(this);
        ccbPlaySound_SS.setOnClickListener(this);
        ccbVibrate_SS.setOnClickListener(this);
        ccbDisplayInnerBorders_SS.setOnClickListener(this);
        spinnerChooseCountry_SS.setOnItemSelectedListener(this);
    }

    private final void setValues() {
        ccbPlayBackgroundMusic_SS.setChecked(AppHelper.getPlayBackgroundMusic(this));
        ccbPlaySound_SS.setChecked(AppHelper.getPlaySound(this));
        ccbVibrate_SS.setChecked(AppHelper.getVibrate(this));
        ccbDisplayInnerBorders_SS.setChecked(AppHelper.getShowImageBorder(this));
        spinnerChooseCountry_SS.setSelection(AppHelper.getLocalizeLanguage(this));
    }

    private void showBanner() {
        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}
