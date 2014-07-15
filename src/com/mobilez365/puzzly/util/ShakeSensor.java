package com.mobilez365.puzzly.util;

import android.content.Context;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.util.Log;
import com.mobilez365.puzzly.global.Constans;

/**
 * Created by andrewtivodar on 12.05.2014.
 */
public class ShakeSensor implements SensorListener {

    private OnShakeListener mShakeListener;
    private OnMoveListener mMoveListener;
    private SensorManager mSensorMgr;

    private int index = 0;
    private float valuesX[] = new float[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    private long mLastUpdate = 0;
    private float mLastX = -1.0f;
    private float mLastY = -1.0f;
    private float mLastZ = -1.0f;

    public interface OnShakeListener {
        public void onShake();
    }

    public interface OnMoveListener {
        public void onMove(float moveDiff);
    }

    public void resume(Context context, OnShakeListener _shakelistener, OnMoveListener _moveListener) {
        mSensorMgr = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        if (mSensorMgr == null) {
            return;
        }

        boolean supported = mSensorMgr.registerListener(this, SensorManager.SENSOR_ACCELEROMETER, SensorManager.SENSOR_DELAY_GAME);
        if (!supported) {
            mSensorMgr.unregisterListener(this, SensorManager.SENSOR_ACCELEROMETER);
            return;
        }

        mShakeListener = _shakelistener;
        mMoveListener = _moveListener;
    }

    public void pause() {
        if (mSensorMgr != null) {
            mSensorMgr.unregisterListener(this, SensorManager.SENSOR_ACCELEROMETER);
            mSensorMgr = null;
        }
        mShakeListener = null;
        mMoveListener = null;
    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {
        if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            if ((curTime - mLastUpdate) > 100) {
                long diffTime = (curTime - mLastUpdate);
                mLastUpdate = curTime;

                float x = values[SensorManager.DATA_X];
                float y = values[SensorManager.DATA_Y];
                float z = values[SensorManager.DATA_Z];

                float speed = Math.abs(x + y + z - mLastX - mLastY - mLastZ) / diffTime * 10000;

                if (speed > Constans.SHAKE_THRESHOLD) {
                    if (mShakeListener != null) {
                        mShakeListener.onShake();
                    }
                }

                mLastX = x;
                mLastY = y;
                mLastZ = z;
            }
            else{
                valuesX[index] = values[SensorManager.DATA_X];
                index++;
                if (index >= 10)
                    index = 0;
                if (mMoveListener != null) {
                    float forceX = 0.0f;
                    for (int i = 0; i < 10; i++)
                        forceX += valuesX[i];
                    forceX = forceX / 10;
                    mMoveListener.onMove(forceX);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {

    }
}
