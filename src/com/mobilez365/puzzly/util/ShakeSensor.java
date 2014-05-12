package com.mobilez365.puzzly.util;

import android.content.Context;
import android.hardware.SensorListener;
import android.hardware.SensorManager;

/**
 * Created by andrewtivodar on 12.05.2014.
 */
public class ShakeSensor implements SensorListener {

    private Context mContext;
    private OnShakeListener mShakeListener;
    private SensorManager mSensorMgr;
    private static final int SHAKE_THRESHOLD = 400;
    private long lastUpdate = 0;
    private float last_x = -1.0f, last_y = -1.0f, last_z = -1.0f;

    public interface OnShakeListener
    {
        public void onShake();
    }

    public ShakeSensor(Context context)
    {
        mContext = context;
        resume();
    }

    public void resume() {
        mSensorMgr = (SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorMgr == null) {
            throw new UnsupportedOperationException("Sensors not supported");
        }
        boolean supported = mSensorMgr.registerListener(this, SensorManager.SENSOR_ACCELEROMETER, SensorManager.SENSOR_DELAY_GAME);
        if (!supported) {
            mSensorMgr.unregisterListener(this, SensorManager.SENSOR_ACCELEROMETER);
            throw new UnsupportedOperationException("Accelerometer not supported");
        }
    }

    public void pause() {
        if (mSensorMgr != null) {
            mSensorMgr.unregisterListener(this, SensorManager.SENSOR_ACCELEROMETER);
            mSensorMgr = null;
        }
    }

    public void setOnShakeListener(OnShakeListener listener)
    {
        mShakeListener = listener;
    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {
        if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float x = values[SensorManager.DATA_X];
                float y = values[SensorManager.DATA_Y];
                float z = values[SensorManager.DATA_Z];

                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    if (mShakeListener != null) {
                        mShakeListener.onShake();
                    }
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {

    }
}
