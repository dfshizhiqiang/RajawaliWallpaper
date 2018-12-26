package com.imzhiqiang.rajawaliwallpaper;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;

import org.rajawali3d.Object3D;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Sphere;

public class EarthRenderer extends SimpleRenderer {

    private static final String TAG = "EarthRenderer";

    private Object3D mSphere;

    public EarthRenderer(Context context, @Nullable SimpleFragment fragment) {
        super(context, fragment);
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
}
