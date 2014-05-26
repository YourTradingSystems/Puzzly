package com.mobilez365.puzzly.screens;

import android.app.Activity;
import android.content.Intent;

public class RestartActivty extends Activity {

    @Override
    protected void onRestart() {
        super.onRestart();
        PreMainActivity.ENABLE_RESTART = true;
        Intent i = new Intent(this,PreMainActivity.class);
        i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }
}
