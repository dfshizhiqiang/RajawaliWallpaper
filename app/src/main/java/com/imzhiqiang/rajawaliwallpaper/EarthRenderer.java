package com.imzhiqiang.rajawaliwallpaper;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;

import org.rajawali3d.Object3D;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Sphere;

import java.util.Arrays;

public class EarthRenderer extends SimpleRenderer implements SensorEventListener {

    private static final String TAG = "EarthRenderer";

    private Object3D mSphere;
    private SensorManager mSensorManager;
    private Sensor mAccSensor;
    private Sensor mMagnSensor;

    private float[] accValues = new float[3];
    private float[] magnValues = new float[3];

    public EarthRenderer(Context context, @Nullable SimpleFragment fragment) {
        super(context, fragment);
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mAccSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void initScene() {
        try {
            Material material = new Material();
            material.addTexture(new Texture("earthColors",
                    R.drawable.earthtruecolor_nasa_big));
            material.setColorInfluence(0);
            mSphere = new Sphere(1, 24, 24);
            mSphere.setMaterial(material);
            getCurrentScene().addChild(mSphere);
        } catch (ATexture.TextureException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Camera initial orientation: " + getCurrentCamera().getOrientation());
        getCurrentCamera().enableLookAt();
        getCurrentCamera().setLookAt(0, 0, 0);
        getCurrentCamera().setZ(6);
        getCurrentCamera().setOrientation(getCurrentCamera().getOrientation().inverse());
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mMagnSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onRender(long ellapsedRealtime, double deltaTime) {
        super.onRender(ellapsedRealtime, deltaTime);
        mSphere.rotate(Vector3.Axis.Y, 1.0);
    }

    @Override
    public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accValues = event.values.clone();
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magnValues = event.values.clone();
        }
        float[] R = new float[9];
        float[] values = new float[3];
        SensorManager.getRotationMatrix(R, null, accValues, magnValues);
        SensorManager.getOrientation(R, values);
        Log.d(TAG, "onSensorChanged: values = " + Arrays.toString(values));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
