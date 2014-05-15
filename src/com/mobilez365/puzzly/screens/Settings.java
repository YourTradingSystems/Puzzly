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
public class Settings extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

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
        SharedPreferences.Editor editor = getSharedPreferences(Constans.PREFERENCES_NAME, MODE_PRIVATE).edit();
        switch (_v.getId()) {
            case R.id.btnBack_SS:
                startActivity(new Intent(this, Menu.class));
                finish();
                break;

            case R.id.cbDisplayInnerBorders_SS:
                editor.putBoolean(Constans.DISPLAY_INNER_BORDERS, cbDisplayInnerBorders_SS.isChecked());
                break;

            case R.id.cbPlaySoundImageAppear_SS:
                editor.putBoolean(Constans.PLAY_SOUND_WHEN_IMAGE_APPEAR, cbPlaySoundImageAppear_SS.isChecked());
                break;

            case R.id.cbDisplayWords_SS:
                editor.putBoolean(Constans.DISPLAY_WORDS, cbDisplayWords_SS.isChecked());
                break;

            case R.id.cbVoiceDisplayWords_SS:
                editor.putBoolean(Constans.VOICE_FOR_DISPLAY_WORDS, cbVoiceDisplayWords_SS.isChecked());
                break;

            case R.id.cbVibrateDragPuzzles_SS:
                editor.putBoolean(Constans.VIBRATE_WHEN_DRAG_PUZZLES, cbVibrateDragPuzzles_SS.isChecked());
                break;

            case R.id.cbVibratePieceInPlace_SS:
                editor.putBoolean(Constans.VIBRATE_WHEN_A_PIECE_IN_PLACE, cbVibratePieceInPlace_SS.isChecked());
                break;
        }
        editor.commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> _parent, View _view, int _position, long _id) {
        if (!displayInit) {
            SharedPreferences.Editor editor = getSharedPreferences(Constans.PREFERENCES_NAME, MODE_PRIVATE).edit();
            editor.putInt(Constans.LOCALIZE_LANGUAGE, _position);
            editor.commit();

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
    public boolean onKeyDown(int _keyCode, KeyEvent _event) {
        if (_keyCode == KeyEvent.KEYCODE_BACK ) {
            startActivity(new Intent(this, Menu.class));
        }

        return super.onKeyDown(_keyCode, _event);
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
        SharedPreferences prefs = getSharedPreferences(Constans.PREFERENCES_NAME, MODE_PRIVATE);
        cbDisplayInnerBorders_SS.setChecked(prefs.getBoolean(Constans.DISPLAY_INNER_BORDERS, false));
        cbPlaySoundImageAppear_SS.setChecked(prefs.getBoolean(Constans.PLAY_SOUND_WHEN_IMAGE_APPEAR, false));
        cbDisplayWords_SS.setChecked(prefs.getBoolean(Constans.DISPLAY_WORDS, false));
        cbVibrateDragPuzzles_SS.setChecked(prefs.getBoolean(Constans.VIBRATE_WHEN_DRAG_PUZZLES, false));
        cbVibratePieceInPlace_SS.setChecked(prefs.getBoolean(Constans.VIBRATE_WHEN_A_PIECE_IN_PLACE, false));
        spinnerChooseCountry_SS.setSelection(prefs.getInt(Constans.LOCALIZE_LANGUAGE, 0));
    }
}
