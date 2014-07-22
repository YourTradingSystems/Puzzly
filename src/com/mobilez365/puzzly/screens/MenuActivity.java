package com.mobilez365.puzzly.screens;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.*;
import com.google.android.gms.ads.AdView;
import com.mobilez365.puzzly.R;
import com.mobilez365.puzzly.global.AnalyticsGoogle;
import com.mobilez365.puzzly.global.AppHelper;
import com.mobilez365.puzzly.global.Constans;
import com.mobilez365.puzzly.global.SoundManager;
import com.mobilez365.puzzly.puzzles.PuzzlesDB;
import com.startad.lib.SADView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MenuActivity extends Activity {

    private TextView tvGameAchievement_MS;
    private RelativeLayout rlLeftBalloon_MS;
    private RelativeLayout rlRightBalloon_MS;
    private ImageView ivLeftHandTutorial_MS;
    private ImageView ivRightHandTutorial_MS;
    private SADView sadView;

    private List<ObjectAnimator> mCloudsAnimations = new ArrayList<ObjectAnimator>();
    private Animation leftBalonAnim;
    private Animation rightBalonAnim;
    private Animation leftHandAnim;
    private Animation rightHandAnim;

    private final View.OnLongClickListener settingsLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            startActivity(new Intent(MenuActivity.this, SettingsActivity.class));
            return false;
        }
    };

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivGameSimpleFill_MS:
                    Intent gameFillIntent = new Intent(MenuActivity.this, ChoosePuzzleActivity.class);
                    gameFillIntent.putExtra("type", 0);
                    startActivity(gameFillIntent);
                    AppHelper.setLeftHandTutorial(getApplicationContext(), true);
                    break;

                case R.id.ivGameSimpleReveal_MS:
                    Intent gameIntent = new Intent(MenuActivity.this, ChoosePuzzleActivity.class);
                    gameIntent.putExtra("type", 1);
                    startActivity(gameIntent);
                    AppHelper.setRightHandTutorial(getApplicationContext(), true);
                    break;

                case R.id.btnGameSettings_MS:
                    Toast.makeText(MenuActivity.this, getString(R.string.menu_settings_long_click), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.menu_screen);

        findViews();
        setListeners();
        showReminderDialog();
        startAnimation();
        showBanner();

        PuzzlesDB.addBasePuzzlesToDB(getApplicationContext());
        AnalyticsGoogle.fireScreenEvent(this, getString(R.string.activity_main_menu));
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvGameAchievement_MS.setText("" + AppHelper.getGameAchievement(getApplicationContext()));

        if (AppHelper.getLeftHandTutorial(getApplicationContext())) {
            ivLeftHandTutorial_MS.clearAnimation();
            ivLeftHandTutorial_MS.setVisibility(View.GONE);
        }

        if (AppHelper.getRightHandTutorial(getApplicationContext())) {
            ivRightHandTutorial_MS.clearAnimation();
            ivRightHandTutorial_MS.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        if (sadView != null)
            sadView.destroy();

        for (ObjectAnimator mCloudsAnimation : mCloudsAnimations) {
            mCloudsAnimation.cancel();
        }

        if (leftBalonAnim != null)
            leftBalonAnim.cancel();

        if (rightBalonAnim != null)
            rightBalonAnim.cancel();

        if (leftHandAnim != null)
            leftHandAnim.cancel();

        if (rightHandAnim != null)
            rightHandAnim.cancel();

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SoundManager.stopBackgroundMusic();
    }

    private void findViews() {
        tvGameAchievement_MS = (TextView) findViewById(R.id.tvGameAchievement_MS);
        rlLeftBalloon_MS = (RelativeLayout) findViewById(R.id.rlLeftBalloon_MS);
        rlRightBalloon_MS = (RelativeLayout) findViewById(R.id.rlRightBalloon_MS);
        ivLeftHandTutorial_MS = (ImageView) findViewById(R.id.ivLeftHandTutorial_MS);
        ivRightHandTutorial_MS = (ImageView) findViewById(R.id.ivRightHandTutorial_MS);
    }

    private void setListeners() {
        findViewById(R.id.ivGameSimpleFill_MS).setOnClickListener(mOnClickListener);
        findViewById(R.id.ivGameSimpleReveal_MS).setOnClickListener(mOnClickListener);
        findViewById(R.id.btnGameSettings_MS).setOnClickListener(mOnClickListener);
        findViewById(R.id.btnGameSettings_MS).setOnLongClickListener(settingsLongClickListener);
    }

    private void startAnimation() {
        startCloudAnimation(0);
        startCloudAnimation(10000);

        if (!AppHelper.getLeftHandTutorial(getApplicationContext())) {
            leftHandAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.menu_hand_left);
            ivLeftHandTutorial_MS.startAnimation(leftHandAnim);
        } else
            ivLeftHandTutorial_MS.setVisibility(View.GONE);

        if (!AppHelper.getRightHandTutorial(getApplicationContext())) {
            rightHandAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.menu_hand_right);
            ivRightHandTutorial_MS.startAnimation(rightHandAnim);
        } else
            ivRightHandTutorial_MS.setVisibility(View.GONE);

        leftBalonAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.menu_balloon_rotate_left);
        rlLeftBalloon_MS.startAnimation(leftBalonAnim);

        rightBalonAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.menu_balloon_rotate_right);
        rlRightBalloon_MS.startAnimation(rightBalonAnim);

    }

    private void startCloudAnimation(long _startOffset) {
        int cloudCount = 4;
        int cloud[] = new int[]{R.drawable.clouds1_icon, R.drawable.clouds2_icon, R.drawable.clouds3_icon, R.drawable.clouds4_icon};
        Random rand = new Random();
        float y = 50;

        RelativeLayout rlMenuMainLayout_MS = (RelativeLayout) findViewById(R.id.rlMenuMainLayout_MS);

        for (int i = 0; i < cloudCount; i++) {
            y += rand.nextInt(50);

            ImageView animCloud = new ImageView(this);
            animCloud.setImageResource(cloud[rand.nextInt(cloud.length)]);
            animCloud.setY(y);
            animCloud.setX(-250);
            rlMenuMainLayout_MS.addView(animCloud, 0);
            y += 50;

            ObjectAnimator animation = ObjectAnimator.ofFloat(animCloud, "translationX", -250, getResources().getDisplayMetrics().widthPixels);
            animation.setDuration(rand.nextInt(20000) + 30000);
            animation.setInterpolator(new LinearInterpolator());
            animation.setRepeatCount(Animation.INFINITE);
            animation.start();
            if (_startOffset == 0)
                animation.setCurrentPlayTime(rand.nextInt((int) animation.getDuration()));
            else
                animation.setStartDelay(_startOffset + rand.nextInt((10000)));
            mCloudsAnimations.add(animation);
        }
    }

    private void showBanner() {
        if (!AppHelper.isAdsDisabled(getApplicationContext())) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.llBanner_SS);
            layout.removeAllViews();
            sadView = new SADView(this, getResources().getString(R.string.startADId));
            if (Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("uk")) {
                sadView.loadAd(SADView.LANGUAGE_RU);
            } else {
                sadView.loadAd(SADView.LANGUAGE_EN);
            }
            layout.addView(sadView);
        }
    }

    private void showReminderDialog() {
        if (AppHelper.getStartCount(getApplicationContext()) <= 3)
            AppHelper.increaseStartCount(getApplicationContext());
        if (AppHelper.getStartCount(getApplicationContext()) != 3)
            return;
        LayoutInflater factory = LayoutInflater.from(getApplicationContext());
        final View reminderDialogView = factory.inflate(R.layout.reminder_dialog, null);
        final AlertDialog reminderDialog = new AlertDialog.Builder(this).create();
        reminderDialog.setView(reminderDialogView);
        reminderDialogView.findViewById(R.id.menu_dialog_yes).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String url = Constans.REVIEW_URL;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                reminderDialog.dismiss();
            }
        });
        reminderDialogView.findViewById(R.id.menu_dialog_later).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                reminderDialog.dismiss();
            }
        });
        reminderDialogView.findViewById(R.id.menu_dialog_no).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                reminderDialog.dismiss();
            }
        });
        reminderDialog.show();
        AppHelper.decreaseStartCount(getApplicationContext());
        Typeface robotoTypeface = Typeface.createFromAsset(this.getAssets(), "Roboto-Regular.ttf");
        final int alertTitle = getApplicationContext().getResources().getIdentifier("alertTitle", "id", "android");
        TextView tvAlertTitle = ((TextView) reminderDialog.findViewById(alertTitle));
        tvAlertTitle.setTypeface(robotoTypeface);
        TextView tvAlertMessage = ((TextView) reminderDialog.findViewById(R.id.message));
        tvAlertMessage.setTypeface(robotoTypeface);
        Button btnYes = (Button) reminderDialogView.findViewById(R.id.menu_dialog_yes);
        btnYes.setTypeface(robotoTypeface);
        Button btnLater = (Button) reminderDialogView.findViewById(R.id.menu_dialog_later);
        btnLater.setTypeface(robotoTypeface);
        Button btnNo = (Button) reminderDialogView.findViewById(R.id.menu_dialog_no);
        btnNo.setTypeface(robotoTypeface);
    }
}
