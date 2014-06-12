package com.mobilez365.puzzly.screens;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.*;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.Constans;
import com.mobilez365.puzzly.puzzles.PuzzlesDB;
import com.mobilez365.puzzly.util.BackgroundSound;
import com.startad.lib.SADView;

import java.util.Locale;

/**
 * Created by Denis on 12.05.14.
 */
public class SettingsActivity extends RestartActivty implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageButton btnBack_SS;
    private CheckBox ccbPlayBackgroundMusic_SS;
    private CheckBox ccbPlaySound_SS;
    private CheckBox ccbVibrate_SS;
    private CheckBox ccbDisplayInnerBorders_SS;
    private Spinner spinnerChooseAppCountry_SS;
    private Spinner spinnerChooseStudyCountry_SS;
    private ScrollView swMain;

    private int displayInit = 0;

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        AppHelper.changeLanguage(this, AppHelper.getLocaleLanguage(this, Constans.APP_LANGUAGE).name());

        super.onCreate(_savedInstanceState);
        setContentView(R.layout.settings_screen);

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

                if (ccbPlayBackgroundMusic_SS.isChecked())
                    AppHelper.startBackgroundSound(this, Constans.MENU_BACKGROUND_MUSIC);
                else
                    AppHelper.getBackgroundSound().stop();

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
        if (displayInit > 1) {
            if(_parent.getId() == R.id.spinnerChooseAppCountry_SS) {
                AppHelper.setLocalizeAppLanguage(this, _position);
                AppHelper.changeLanguageRefresh(this, AppHelper.getLocaleLanguage(this, Constans.APP_LANGUAGE).name(), swMain.getScrollY());
            }
            else {
                AppHelper.setLocalizeStudyLanguage(this, _position);
               // AppHelper.changeLanguageRefresh(this, AppHelper.getLocaleLanguage(this, Constans.GAME_LANGUAGE).name(), swMain.getScrollY());

                //update word in table
                //PuzzlesDB.updateTableGameWord(this);
            }
        }
        else
            displayInit ++;
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

        if (!AppHelper.isAppInBackground(this))
            AppHelper.getBackgroundSound().pause(false);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (AppHelper.isAppInBackground(this) || AppHelper.isScreenOff(this))
            AppHelper.getBackgroundSound().pause(true);
    }

    private final void findViews() {
        btnBack_SS = (ImageButton)findViewById(R.id.btnBack_SS);
        ccbPlayBackgroundMusic_SS = (CheckBox)findViewById(R.id.ccbPlayBackgroundMusic_SS);
        ccbPlaySound_SS = (CheckBox)findViewById(R.id.ccbPlaySound_SS);
        ccbVibrate_SS = (CheckBox)findViewById(R.id.ccbVibrate_SS);
        ccbDisplayInnerBorders_SS = (CheckBox)findViewById(R.id.ccbDisplayInnerBorders_SS);
        spinnerChooseAppCountry_SS = (Spinner)findViewById(R.id.spinnerChooseAppCountry_SS);
        spinnerChooseStudyCountry_SS = (Spinner)findViewById(R.id.spinnerChooseStudyCountry_SS);
        swMain = (ScrollView) findViewById(R.id.swMain_SS);
    }

    private final void setListener() {
        btnBack_SS.setOnClickListener(this);
        ccbPlayBackgroundMusic_SS.setOnClickListener(this);
        ccbPlaySound_SS.setOnClickListener(this);
        ccbVibrate_SS.setOnClickListener(this);
        ccbDisplayInnerBorders_SS.setOnClickListener(this);
        spinnerChooseAppCountry_SS.setOnItemSelectedListener(this);
        spinnerChooseStudyCountry_SS.setOnItemSelectedListener(this);
    }

    private final void setValues() {
        ccbPlayBackgroundMusic_SS.setChecked(AppHelper.getPlayBackgroundMusic(this));
        ccbPlaySound_SS.setChecked(AppHelper.getPlaySound(this));

        Vibrator mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if(mVibrator.hasVibrator())
            ccbVibrate_SS.setChecked(AppHelper.getVibrate(this));
        else
            ccbVibrate_SS.setVisibility(View.GONE);

        ccbDisplayInnerBorders_SS.setChecked(AppHelper.getShowImageBorder(this));
        spinnerChooseAppCountry_SS.setSelection(AppHelper.getLocalizeAppLanguage(this));
        spinnerChooseStudyCountry_SS.setSelection(AppHelper.getLocalizeStudyLanguage(this));
        swMain.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                swMain.scrollTo(0, getIntent().getIntExtra("scrollPos", 0));
                swMain.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    private void showBanner() {
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.llBanner_SS);
        layout.removeAllViews();
        switch (AppHelper.adware % 2){
            case 0:
                AdView adView = new AdView(this);
                adView.setAdUnitId(getString(R.string.adUnitId));
                adView.setAdSize(AdSize.BANNER);
                AdRequest adRequest = new AdRequest.Builder().build();
                adView.loadAd(adRequest);
                layout.addView(adView);
                break;
            case 1:
                SADView sadView = new SADView(this, getResources().getString(R.string.startADId));
                if(Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("uk")){
                    sadView.loadAd(SADView.LANGUAGE_RU);
                }else {
                    sadView.loadAd(SADView.LANGUAGE_EN);
                }
                layout.addView(sadView);
                break;
        }
        AppHelper.adware +=1;
    }
}
