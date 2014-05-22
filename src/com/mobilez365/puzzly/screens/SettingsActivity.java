package com.mobilez365.puzzly.screens;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.util.BackgroundSound;

/**
 * Created by Denis on 12.05.14.
 */
public class SettingsActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Button btnBack_SS;
    private CheckBox ccbPlayBackgroundMusic_SS;
    private CheckBox ccbDisplayInnerBorders_SS;
    private CheckBox ccbPlaySoundImageAppear_SS;
    private CheckBox ccbDisplayWords_SS;
    private CheckBox ccbVoiceDisplayWords_SS;
    private CheckBox ccbVibrateDragPuzzles_SS;
    private CheckBox ccbVibratePieceInPlace_SS;
    private Spinner spinnerChooseCountry_SS;

    private boolean displayInit = true;
    private BackgroundSound mBackgroundSound;

    @Override
    public void onCreate(Bundle _savedInstanceState) {
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
        _v.setClickable(false);
        switch (_v.getId()) {
            case R.id.btnBack_SS:
                finish();
                break;

            case R.id.ccbPlayBackgroundMusic_SS:
                AppHelper.setPlayBackgroundMusic(this, ccbPlayBackgroundMusic_SS.isChecked());

                if (ccbPlayBackgroundMusic_SS.isChecked()) {
                    AppHelper.startBackgroundSound(this);
                    mBackgroundSound = AppHelper.getBackgroundSound();
                }
                else {
                    if (mBackgroundSound.getBackgroundPlayer() != null) {
                        mBackgroundSound.getBackgroundPlayer().stop();
                        mBackgroundSound.getBackgroundPlayer().release();
                    }
                }

                break;

            case R.id.ccbDisplayInnerBorders_SS:
                AppHelper.setShowImageBorder(this, ccbDisplayInnerBorders_SS.isChecked());
                break;

            case R.id.ccbPlaySoundImageAppear_SS:
                AppHelper.setPlaySoundImageAppear(this, ccbPlaySoundImageAppear_SS.isChecked());
                break;

            case R.id.ccbDisplayWords_SS:
                AppHelper.setDisplayWords(this, ccbDisplayWords_SS.isChecked());
                break;

            case R.id.ccbVoiceDisplayWords_SS:
                AppHelper.setVoiceForDisplayWords(this, ccbVoiceDisplayWords_SS.isChecked());
                break;

            case R.id.ccbVibrateDragPuzzles_SS:
                AppHelper.setVibrateDragPuzzles(this, ccbVibrateDragPuzzles_SS.isChecked());
                break;

            case R.id.ccbVibratePieceInPlace_SS:
                AppHelper.setVibratePieceInPlace(this, ccbVibratePieceInPlace_SS.isChecked());
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
        if (AppHelper.getPlayBackgroundMusic(this))
            mBackgroundSound.pause(false);
        if(btnBack_SS != null) btnBack_SS.setClickable(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (AppHelper.getPlayBackgroundMusic(this))
            mBackgroundSound.pause(true);
    }

    private final void findViews() {
        btnBack_SS = (Button)findViewById(R.id.btnBack_SS);
        ccbPlayBackgroundMusic_SS = (CheckBox)findViewById(R.id.ccbPlayBackgroundMusic_SS);
        ccbDisplayInnerBorders_SS = (CheckBox)findViewById(R.id.ccbDisplayInnerBorders_SS);
        ccbPlaySoundImageAppear_SS = (CheckBox)findViewById(R.id.ccbPlaySoundImageAppear_SS);
        ccbDisplayWords_SS = (CheckBox)findViewById(R.id.ccbDisplayWords_SS);
        ccbVoiceDisplayWords_SS = (CheckBox)findViewById(R.id.ccbVoiceDisplayWords_SS);
        ccbVibrateDragPuzzles_SS = (CheckBox)findViewById(R.id.ccbVibrateDragPuzzles_SS);
        ccbVibratePieceInPlace_SS = (CheckBox)findViewById(R.id.ccbVibratePieceInPlace_SS);
        spinnerChooseCountry_SS = (Spinner)findViewById(R.id.spinnerChooseCountry_SS);
    }

    private final void setListener() {
        btnBack_SS.setOnClickListener(this);
        ccbPlayBackgroundMusic_SS.setOnClickListener(this);
        ccbDisplayInnerBorders_SS.setOnClickListener(this);
        ccbPlaySoundImageAppear_SS.setOnClickListener(this);
        ccbDisplayWords_SS.setOnClickListener(this);
        ccbVoiceDisplayWords_SS.setOnClickListener(this);
        ccbVibrateDragPuzzles_SS.setOnClickListener(this);
        ccbVibratePieceInPlace_SS.setOnClickListener(this);
        spinnerChooseCountry_SS.setOnItemSelectedListener(this);
    }

    private final void setValues() {
        ccbPlayBackgroundMusic_SS.setChecked(AppHelper.getPlayBackgroundMusic(this));
        ccbDisplayInnerBorders_SS.setChecked(AppHelper.getShowImageBorder(this));
        ccbPlaySoundImageAppear_SS.setChecked(AppHelper.getPlaySoundImageAppear(this));
        ccbDisplayWords_SS.setChecked(AppHelper.getDisplayWords(this));
        ccbVoiceDisplayWords_SS.setChecked(AppHelper.getVoiceForDisplayWords(this));
        ccbVibrateDragPuzzles_SS.setChecked(AppHelper.getVibrateDragPuzzles(this));
        ccbVibratePieceInPlace_SS.setChecked(AppHelper.getVibratePieceInPlace(this));
        spinnerChooseCountry_SS.setSelection(AppHelper.getLocalizeLanguage(this));
    }

    private void showBanner() {
        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}
