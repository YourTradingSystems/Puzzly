package com.mobilez365.puzzly.util;

import android.content.Context;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import com.mobilez365.puzzly.global.Constans;

/**
 * Created by andrewtivodar on 12.05.2014.
 */
public class ShakeSensor implements SensorListener {

    private Context mContext;
    private OnShakeListener mShakeListener;
    private SensorManager mSensorMgr;

    private long mLastUpdate = 0;
    private float mLastX = -1.0f;
    private float mLastY = -1.0f;
    private float mLastZ = -1.0f;

    public interface OnShakeListener {
        public void onShake();
    }

    public ShakeSensor(Context context) {
        mContext = context;
        resume();
    }

    public void resume() {
        mSensorMgr = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);

        if (mSensorMgr == null) {
            return;
        }

        boolean supported = mSensorMgr.registerListener(this, SensorManager.SENSOR_ACCELEROMETER, SensorManager.SENSOR_DELAY_GAME);
        if (!supported) {
            mSensorMgr.unregisterListener(this, SensorManager.SENSOR_ACCELEROMETER);
            return;
        }
    }

    public void pause() {
        if (mSensorMgr != null) {
            mSensorMgr.unregisterListener(this, SensorManager.SENSOR_ACCELEROMETER);
            mSensorMgr = null;
        }
    }

    public void setOnShakeListener(OnShakeListener listener) {
        mShakeListener = listener;
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
        }
    }

    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {

    }
}
