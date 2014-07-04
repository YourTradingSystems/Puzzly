package com.mobilez365.puzzly.screens;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.*;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.*;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.plus.PlusShare;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.Constans;
import com.mobilez365.puzzly.util.Purchase;
import com.mobilez365.puzzly.util.PurchaseHelper;
import com.mobilez365.puzzly.util.SocialShare;
import com.startad.lib.SADView;
import org.brickred.socialauth.android.SocialAuthAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private ProgressDialog loadingDialog;

    private final SocialShare.ShareListener mShareListener = new SocialShare.ShareListener() {
        @Override
        public void onShareResult(final int result) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (result) {
                        case Constans.SHARE_LOGIN_ERROR:
                            Toast.makeText(SettingsActivity.this, getString(R.string.share_login_error), Toast.LENGTH_SHORT).show();
                            break;
                        case Constans.SHARE_POST_ERROR:
                            Toast.makeText(SettingsActivity.this, getString(R.string.share_post_error), Toast.LENGTH_SHORT).show();
                            break;
                        case Constans.SHARE_POST_DONE:
                            Toast.makeText(SettingsActivity.this, getString(R.string.share_post_done), Toast.LENGTH_SHORT).show();
                            break;
                        case Constans.SHARE_INTERNET_ERROR:
                            Toast.makeText(SettingsActivity.this, getString(R.string.share_internet_error), Toast.LENGTH_SHORT).show();
                            break;
                        case Constans.SHARE_POST_DUPLICATE:
                            Toast.makeText(SettingsActivity.this, getString(R.string.share_post_duplicate), Toast.LENGTH_SHORT).show();
                            break;
                    }
                    loadingDialog.dismiss();
                }
            });
        }
    };

    private final AdapterView.OnItemSelectedListener mItemSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(parent.getId() == R.id.spinnerChooseAppCountry_SS) {
                AppHelper.setLocalizeAppLanguage(getApplicationContext(), getLanguageLocale(((TextView)view).getText().toString()));
                AppHelper.changeLanguage(getApplicationContext(), AppHelper.getLocaleLanguage(getApplicationContext(), Constans.APP_LANGUAGE).name());

                Intent mIntent = new Intent(SettingsActivity.
                        this, SettingsActivity.class);
                mIntent.putExtra("scrollPos" , swMain.getScrollY());
                startActivity(mIntent);
                overridePendingTransition(0, 0);
                finish();
            }
            else if(parent.getId() == R.id.spinnerChooseStudyCountry_SS) AppHelper.setLocalizeStudyLanguage(getApplicationContext(), getLanguageLocale(((TextView)view).getText().toString()));
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

                case R.id.rlPlayBackgroundMusic_SS:
                    ccbPlayBackgroundMusic_SS.setChecked(!ccbPlayBackgroundMusic_SS.isChecked());

                    AppHelper.setPlayBackgroundMusic(getApplicationContext(), ccbPlayBackgroundMusic_SS.isChecked());

                    if (ccbPlayBackgroundMusic_SS.isChecked())
                        AppHelper.startBackgroundSound(getApplicationContext(), Constans.MENU_BACKGROUND_MUSIC);
                    else
                        AppHelper.getBackgroundSound().stop();

                    break;

                case R.id.rlPlaySound_SS:
                    ccbPlaySound_SS.setChecked(!ccbPlaySound_SS.isChecked());
                    AppHelper.setPlaySound(getApplicationContext(), ccbPlaySound_SS.isChecked());
                    break;

                case R.id.rlVibrate_SS:
                    ccbVibrate_SS.setChecked(!ccbVibrate_SS.isChecked());
                    AppHelper.setVibrate(getApplicationContext(), ccbVibrate_SS.isChecked());
                    break;

                case R.id.rlDisplayInnerBorders_SS:
                    ccbDisplayInnerBorders_SS.setChecked(!ccbDisplayInnerBorders_SS.isChecked());
                    AppHelper.setShowImageBorder(getApplicationContext(), ccbDisplayInnerBorders_SS.isChecked());
                    break;
                case R.id.btnTwitter_SS:
                    loadingDialog.show();
                    SocialShare shareTwitter = new SocialShare();
                    shareTwitter.shareToSocial(SettingsActivity.this,
                            SocialAuthAdapter.Provider.TWITTER, mShareListener, getString(R.string.share_message));
                    break;

                case R.id.btnGoogle_SS:
                    Intent shareIntent = new PlusShare.Builder(SettingsActivity.this)
                            .setType("text/plain")
                            .setText(getString(R.string.share_message))
                            .setContentUrl(Uri.parse("https://developers.google.com/+/"))
                            .getIntent();

                    startActivityForResult(shareIntent, 0);

                    break;

                case R.id.btnFacebook_SS:
                    loadingDialog.show();
                    SocialShare shareFacebook = new SocialShare();
                    shareFacebook.shareToSocial(SettingsActivity.this,
                            SocialAuthAdapter.Provider.FACEBOOK, mShareListener, getString(R.string.share_message));
                    break;

                case R.id.btnPurchase_SS:
                    PurchaseHelper.purchaseAdSubscription(SettingsActivity.this);
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        AppHelper.changeLanguage(getApplicationContext(), AppHelper.getLocaleLanguage(getApplicationContext(), Constans.APP_LANGUAGE).name());

        super.onCreate(_savedInstanceState);
        setContentView(R.layout.settings_screen);

        PurchaseHelper.initPurchaseWorker(this);

        findViews();
        showBanner();
        setValues();
        setListener();
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

        PurchaseHelper.DestroyHelper();

        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!PurchaseHelper.isHelperInit()) return;

        if (!PurchaseHelper.handleResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
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
        findViewById(R.id.rlPlayBackgroundMusic_SS).setOnClickListener(mOnClickListener);
        findViewById(R.id.rlPlaySound_SS).setOnClickListener(mOnClickListener);
        findViewById(R.id.rlVibrate_SS).setOnClickListener(mOnClickListener);
        findViewById(R.id.rlDisplayInnerBorders_SS).setOnClickListener(mOnClickListener);
        findViewById(R.id.btnTwitter_SS).setOnClickListener(mOnClickListener);
        findViewById(R.id.btnGoogle_SS).setOnClickListener(mOnClickListener);
        findViewById(R.id.btnFacebook_SS).setOnClickListener(mOnClickListener);
        findViewById(R.id.btnPurchase_SS).setOnClickListener(mOnClickListener);
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

        CharSequence[] languageArray = getResources().getTextArray(R.array.settings_languages);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,
                R.layout.item_settings_spiner, languageArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerChooseAppCountry_SS.setAdapter(adapter);
        spinnerChooseStudyCountry_SS.setAdapter(adapter);
        spinnerChooseAppCountry_SS.setSelection(getLanguagePosition(adapter, AppHelper.getLocalizeAppLanguage(getApplicationContext())), false);
        spinnerChooseStudyCountry_SS.setSelection(getLanguagePosition(adapter, AppHelper.getLocalizeStudyLanguage(getApplicationContext())), false);

        swMain.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                swMain.scrollTo(0, getIntent().getIntExtra("scrollPos", 0));
                swMain.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage("Loading...");
        loadingDialog.setCancelable(false);
    }

    private int getLanguagePosition(ArrayAdapter adapter, String language){
        if(language.equals("en"))
            return adapter.getPosition("English");
        else if(language.equals("uk"))
            return adapter.getPosition("Українська");
        else if(language.equals("ru"))
            return adapter.getPosition("Русский");
        else if(language.equals("hu"))
            return adapter.getPosition("Magyar");
        else if(language.equals("de"))
            return adapter.getPosition("Deutsch");
        else if(language.equals("fr"))
            return adapter.getPosition("la France");
        else if(language.equals("es"))
            return adapter.getPosition("España");
        else if(language.equals("zh"))
            return adapter.getPosition("中文");
        else if(language.equals("ar"))
            return adapter.getPosition("العربية");
        else if(language.equals("hi"))
            return adapter.getPosition("हिंदी");

        return adapter.getPosition("English");
    }

    private String getLanguageLocale(String language){
        if(language.equals("English"))
            return "en";
        else if(language.equals("Українська"))
            return "uk";
        else if(language.equals("Русский"))
            return "ru";
        else if(language.equals("Magyar"))
            return "hu";
        else if(language.equals("Deutsch"))
            return "de";
        else if(language.equals("la France"))
            return "fr";
        else if(language.equals("España"))
            return "es";
        else if(language.equals("中文"))
            return "zh";
        else if(language.equals("العربية"))
            return "ar";
        else if(language.equals("हिंदी"))
            return "hi";

        return "en";
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
