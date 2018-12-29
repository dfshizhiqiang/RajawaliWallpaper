package com.imzhiqiang.rajawaliwallpaper;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.SurfaceTexture;
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
import org.rajawali3d.primitives.Plane;
import org.rajawali3d.primitives.Sphere;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class EarthRenderer extends SimpleRenderer implements SensorEventListener {

    private static final String TAG = "EarthRenderer";

    private Object3D mSphere;

    private Context mContext;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    public EarthRenderer(Context context, @Nullable SimpleFragment fragment) {
        super(context, fragment);
        mContext = context;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void initScene() {
        try {

            Plane backgroundPlane = new Plane(15, 15, 1, 1);
            Material backgroundMaterial = new Material();
            backgroundMaterial.addTexture(new Texture("backgroud", R.drawable.background));
            backgroundMaterial.setColorInfluence(0);
            backgroundPlane.setMaterial(backgroundMaterial);
            backgroundPlane.setZ(-3.8);
            getCurrentScene().addChild(backgroundPlane);

            Material material = new Material();
            material.addTexture(new Texture("earth",
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
        getCurrentCamera().setZ(7.5);
        getCurrentCamera().setOrientation(getCurrentCamera().getOrientation().inverse());
    }

    @Override
    public void onRenderSurfaceCreated(EGLConfig config, GL10 gl, int width, int height) {
        super.onRenderSurfaceCreated(config, gl, width, height);
        Log.d(TAG, "onRenderSurfaceCreated: thread = " + Thread.currentThread().getName());
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
        Log.d(TAG, "onResume: thread = " + Thread.currentThread().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        Log.d(TAG, "onPause: thread = " + Thread.currentThread().getName());
    }

    @Override
    public void onRenderSurfaceDestroyed(SurfaceTexture surface) {
        super.onRenderSurfaceDestroyed(surface);
        Log.d(TAG, "onRenderSurfaceDestroyed: thread = " + Thread.currentThread().getName());
    }

    @Override
    public void onRenderSurfaceSizeChanged(GL10 gl, int width, int height) {
        super.onRenderSurfaceSizeChanged(gl, width, height);
        Log.d(TAG, "onRenderSurfaceSizeChanged: thread = " + Thread.currentThread().getName());
    }

    private double cameraX, cameraY;
    static double xK = 0.13;
    static double yK = 0.13;

    @Override
    protected void onRender(long ellapsedRealtime, double deltaTime) {
        super.onRender(ellapsedRealtime, deltaTime);
        mSphere.rotate(Vector3.Axis.Y, 1.0);

        if (dgX != 0 || dgY != 0) {

            cameraX = getCurrentCamera().getX() + dgX * xK;
            cameraY = getCurrentCamera().getY() + dgY * yK;

            Log.d(TAG, String.format("onRender: cameraX = %s cameraY = %s", cameraX, cameraY));

            getCurrentCamera().setX(cameraX);
            getCurrentCamera().setY(cameraY);
        }

        getCurrentCamera().setLookAt(mSphere.getPosition());
    }

    @Override
    public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }

    private static final double alpha = 0.8;
    private double[] gravity = new double[3];
    private double[] lastGravity = new double[2];
    private double roll, pitch;
    private double dgX, dgY;

    @Override
    public void onSensorChanged(SensorEvent event) {

        // low-pass filter
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        double gX = gravity[0];
        double gY = gravity[1];
        double gZ = gravity[2];

        // normalize gravity vector at first
        double gSum = Math.sqrt(gX * gX + gY * gY + gZ * gZ);
        if (gSum != 0) {
            gX /= gSum;
            gY /= gSum;
            gZ /= gSum;
        }
        if (gZ != 0) {
            roll = Math.atan2(gX, gZ) * 180 / Math.PI;
        }
        pitch = Math.sqrt(gX * gX + gZ * gZ);
        if (pitch != 0) {
            pitch = Math.atan2(gY, pitch) * 180 / Math.PI;
        }

        dgX = roll - lastGravity[0];
        dgY = pitch - lastGravity[1];

        // if device orientation is close to vertical – rotation around x is almost undefined – skip!
        if (gY > 0.99) dgX = 0;
        // if rotation was too intensive – more than 180 degrees – skip it
        if (dgX > 180) dgX = 0;
        if (dgX < -180) dgX = 0;
        if (dgY > 180) dgY = 0;
        if (dgY < -180) dgY = 0;

        if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // landscape mode – swap dgX and dgY
            double temp = dgY;
            dgY = dgX;
            dgX = temp;
        }

        lastGravity[0] = roll;
        lastGravity[1] = pitch;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
