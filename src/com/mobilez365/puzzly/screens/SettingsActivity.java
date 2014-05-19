package com.mobilez365.puzzly.screens;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Contacts;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.Constans;

import java.util.HashMap;

/**
 * Created by Denis on 12.05.14.
 */
public class SettingsActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Button btnBack_SS;
    private CheckBox cbDisplayInnerBorders_SS;
    private CheckBox cbPlaySoundImageAppear_SS;
    private CheckBox cbDisplayWords_SS;
    private CheckBox cbVoiceDisplayWords_SS;
    private CheckBox cbVibrateDragPuzzles_SS;
    private CheckBox cbVibratePieceInPlace_SS;
    private Spinner spinnerChooseCountry_SS;

    private boolean displayInit = true;

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.settings_screen);

        findViews();
        setListener();
        setValues();
    }

    @Override
    public void onClick(View _v) {
        switch (_v.getId()) {
            case R.id.btnBack_SS:
                finish();
                break;

            case R.id.cbDisplayInnerBorders_SS:
                AppHelper.setShowImageBorder(this, cbDisplayInnerBorders_SS.isChecked());
                break;

            case R.id.cbPlaySoundImageAppear_SS:
                AppHelper.setPlaySoundImageAppear(this, cbPlaySoundImageAppear_SS.isChecked());
                break;

            case R.id.cbDisplayWords_SS:
                AppHelper.setDisplayWords(this, cbDisplayWords_SS.isChecked());
                break;

            case R.id.cbVoiceDisplayWords_SS:
                AppHelper.setVoiceForDisplayWords(this, cbVoiceDisplayWords_SS.isChecked());
                break;

            case R.id.cbVibrateDragPuzzles_SS:
                AppHelper.setVibrateDragPuzzles(this, cbVibrateDragPuzzles_SS.isChecked());
                break;

            case R.id.cbVibratePieceInPlace_SS:
                AppHelper.setVibratePieceInPlace(this, cbVibratePieceInPlace_SS.isChecked());
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

    private final void findViews() {
        btnBack_SS = (Button)findViewById(R.id.btnBack_SS);
        cbDisplayInnerBorders_SS = (CheckBox)findViewById(R.id.cbDisplayInnerBorders_SS);
        cbPlaySoundImageAppear_SS  = (CheckBox)findViewById(R.id.cbPlaySoundImageAppear_SS);
        cbDisplayWords_SS  = (CheckBox)findViewById(R.id.cbDisplayWords_SS);
        cbVoiceDisplayWords_SS  = (CheckBox)findViewById(R.id.cbVoiceDisplayWords_SS);
        cbVibrateDragPuzzles_SS  = (CheckBox)findViewById(R.id.cbVibrateDragPuzzles_SS);
        cbVibratePieceInPlace_SS  = (CheckBox)findViewById(R.id.cbVibratePieceInPlace_SS);
        spinnerChooseCountry_SS = (Spinner)findViewById(R.id.spinnerChooseCountry_SS);
    }

    private final void setListener() {
        btnBack_SS.setOnClickListener(this);
        cbDisplayInnerBorders_SS.setOnClickListener(this);
        cbPlaySoundImageAppear_SS.setOnClickListener(this);
        cbDisplayWords_SS.setOnClickListener(this);
        cbVoiceDisplayWords_SS.setOnClickListener(this);
        cbVibrateDragPuzzles_SS.setOnClickListener(this);
        cbVibratePieceInPlace_SS.setOnClickListener(this);
        spinnerChooseCountry_SS.setOnItemSelectedListener(this);
    }

    private final void setValues() {
        cbDisplayInnerBorders_SS.setChecked(AppHelper.getShowImageBorder(this));
        cbPlaySoundImageAppear_SS.setChecked(AppHelper.getPlaySoundImageAppear(this));
        cbDisplayWords_SS.setChecked(AppHelper.getDisplayWords(this));
        cbVoiceDisplayWords_SS.setChecked(AppHelper.getVoiceForDisplayWords(this));
        cbVibrateDragPuzzles_SS.setChecked(AppHelper.getVibrateDragPuzzles(this));
        cbVibratePieceInPlace_SS.setChecked(AppHelper.getVibratePieceInPlace(this));
        spinnerChooseCountry_SS.setSelection(AppHelper.getLocalizeLanguage(this));
    }
}
