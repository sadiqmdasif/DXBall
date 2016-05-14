package com.apache.dxballfinal;
/**
 * Created by apache on 12-29-2015.
 */

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


public class GameActivity extends Activity implements SensorEventListener {


    static int layoutWidth;
    static int layoutHeight;
    SensorManager mSensorManager;
    Sensor mSensorAccelerometer;
    Bar bar = new Bar();
    private double[] gravity;
    private double accX = 0;
    private double accY = 0;
    private double accZ = 0;
    private double currentX = .5;
    private Runnable r;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(new GameCanvas(this));
        layoutWidth = getWindowManager().getDefaultDisplay().getWidth();
        layoutHeight = getWindowManager().getDefaultDisplay().getHeight();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this, mSensorAccelerometer);
        handler.removeCallbacks(r);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(r);
        mSensorManager.unregisterListener(this, mSensorAccelerometer);
    }

    @Override
    public void onSensorChanged(final SensorEvent event) {

        r = new Runnable() {
            int count = 0;

            public void run() {

                handler.postDelayed(this, 1);

                if (count == 0) {

                    Log.d("Sensor", "");

                    accX = event.values[0];
                    accY = event.values[1];
                    accZ = event.values[2];
                    if (currentX > accX) {
                        //    bar.setMove(-10);
                        GameCanvas.bar.moveBar(layoutWidth, "right", GameCanvas.move);
                    }
                    if (-currentX < accX) {
                        //   bar.setMove(+10);
                        GameCanvas.bar.moveBar(layoutWidth, "left", -GameCanvas.move);

                    }


                    mSensorManager.unregisterListener(GameActivity.this, mSensorAccelerometer);
                    count++;


                } else {

                    mSensorManager.registerListener(GameActivity.this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                    count = 0;
                    handler.removeCallbacks(this);
                }
            }
        };
        r.run();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
