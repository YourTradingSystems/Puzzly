package com.mobilez365.puzzly.screens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.*;
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
public class SettingsActivity extends RestartActivty{

    private ImageButton btnBack_SS;
    private CheckBox ccbPlayBackgroundMusic_SS;
    private CheckBox ccbPlaySound_SS;
    private CheckBox ccbVibrate_SS;
    private CheckBox ccbDisplayInnerBorders_SS;
    private Spinner spinnerChooseAppCountry_SS;
    private Spinner spinnerChooseStudyCountry_SS;
    private ScrollView swMain;
    private AdView adView;
    private SADView sadView;

    private int displayInit = 0;

    private final AdapterView.OnItemSelectedListener mItemSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (displayInit > 1) {
                if(parent.getId() == R.id.spinnerChooseAppCountry_SS) {
                    AppHelper.setLocalizeAppLanguage(getApplicationContext(), position);
                    AppHelper.changeLanguage(getApplicationContext(), AppHelper.getLocaleLanguage(getApplicationContext(), Constans.APP_LANGUAGE).name());

                    Intent mIntent = new Intent(SettingsActivity.
                            this, SettingsActivity.class);
                    mIntent.putExtra("scrollPos" , swMain.getScrollY());
                    startActivity(mIntent);
                    overridePendingTransition(0, 0);
                    finish();
                }
                else
                    AppHelper.setLocalizeStudyLanguage(getApplicationContext(), position);
            }
            else
                displayInit ++;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnBack_SS:
                    finish();
                    break;

                case R.id.ccbPlayBackgroundMusic_SS:
                    AppHelper.setPlayBackgroundMusic(getApplicationContext(), ccbPlayBackgroundMusic_SS.isChecked());

                    if (ccbPlayBackgroundMusic_SS.isChecked())
                        AppHelper.startBackgroundSound(getApplicationContext(), Constans.MENU_BACKGROUND_MUSIC);
                    else
                        AppHelper.getBackgroundSound().stop();

                    break;

                case R.id.ccbPlaySound_SS:
                    AppHelper.setPlaySound(getApplicationContext(), ccbPlaySound_SS.isChecked());
                    break;

                case R.id.ccbVibrate_SS:
                    AppHelper.setVibrate(getApplicationContext(), ccbVibrate_SS.isChecked());
                    break;

                case R.id.ccbDisplayInnerBorders_SS:
                    AppHelper.setShowImageBorder(getApplicationContext(), ccbDisplayInnerBorders_SS.isChecked());
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        AppHelper.changeLanguage(getApplicationContext(), AppHelper.getLocaleLanguage(getApplicationContext(), Constans.APP_LANGUAGE).name());

        super.onCreate(_savedInstanceState);
        setContentView(R.layout.settings_screen);
        findViews();
        showBanner();
        setListener();
        setValues();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!AppHelper.isAppInBackground(this))
            AppHelper.getBackgroundSound().pause(false);

        if(adView != null)
            adView.resume();

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (AppHelper.isAppInBackground(this) || AppHelper.isScreenOff(this))
            AppHelper.getBackgroundSound().pause(true);

        if(adView != null)
            adView.pause();

    }

    @Override
    public void onDestroy() {
        if(adView != null)
            adView.destroy();
        if(sadView != null)
            sadView.destroy();
        super.onDestroy();
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
        btnBack_SS.setOnClickListener(mOnClickListener);
        ccbPlayBackgroundMusic_SS.setOnClickListener(mOnClickListener);
        ccbPlaySound_SS.setOnClickListener(mOnClickListener);
        ccbVibrate_SS.setOnClickListener(mOnClickListener);
        ccbDisplayInnerBorders_SS.setOnClickListener(mOnClickListener);
        spinnerChooseAppCountry_SS.setOnItemSelectedListener(mItemSelectListener);
        spinnerChooseStudyCountry_SS.setOnItemSelectedListener(mItemSelectListener);
    }

    private final void setValues() {
        ccbPlayBackgroundMusic_SS.setChecked(AppHelper.getPlayBackgroundMusic(getApplicationContext()));
        ccbPlaySound_SS.setChecked(AppHelper.getPlaySound(getApplicationContext()));

        Vibrator mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if(mVibrator.hasVibrator())
            ccbVibrate_SS.setChecked(AppHelper.getVibrate(getApplicationContext()));
        else
            ccbVibrate_SS.setVisibility(View.GONE);

        ccbDisplayInnerBorders_SS.setChecked(AppHelper.getShowImageBorder(getApplicationContext()));
        spinnerChooseAppCountry_SS.setSelection(AppHelper.getLocalizeAppLanguage(getApplicationContext()));
        spinnerChooseStudyCountry_SS.setSelection(AppHelper.getLocalizeStudyLanguage(getApplicationContext()));
        swMain.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                swMain.scrollTo(0, getIntent().getIntExtra("scrollPos", 0));
                swMain.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }
    private void showBanner() {
        LinearLayout layout = (LinearLayout)findViewById(R.id.llBanner_SS);
        layout.removeAllViews();
        adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.adUnitId));
        adView.setAdSize(AdSize.BANNER);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        layout.addView(adView);
    }
}
