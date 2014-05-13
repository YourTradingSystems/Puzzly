package com.mobilez365.puzzly.screens;


import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity implements SensorEventListener{
    

    

	
	
	GameView mGameView;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGameView = new GameView(this);
        setContentView(mGameView);
        
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    	
    }
    
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    
    
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void onSensorChanged(SensorEvent event) {

		
		float R[] = new float[9];
		float orientation[] = new float[3];
		SensorManager.getOrientation(R, orientation);
		
		mGameView.processOrientationEvent(orientation);
		
		Log.d("azimuth",Float.toString(orientation[0]));
		Log.d("pitch",Float.toString(orientation[1]));
		Log.d("role",Float.toString(orientation[2]));

	}
    

}

