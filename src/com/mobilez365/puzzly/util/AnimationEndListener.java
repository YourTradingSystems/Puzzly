package com.mobilez365.puzzly.util;

import android.animation.Animator;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * Created by andrewtivodar on 13.05.2014.
 */
public class AnimationEndListener implements Animator.AnimatorListener {

    View view;
    AnimEndListener listener;

    public AnimationEndListener(View view, AnimEndListener listener) {
        this.view = view;
        this.listener = listener;
    }


    public interface AnimEndListener{
        public void OnAnimEnd(View v);
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        listener.OnAnimEnd(view);
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

}
