package com.imzhiqiang.rajawaliwallpaper;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;

import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.plugins.AlphaMaskMaterialPlugin;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.primitives.Plane;

public class FlowerRenderer extends SimpleRenderer {

    private static final String TAG = "FlowerRenderer";

    private Plane backgroundPlane2;

    public FlowerRenderer(Context context, @Nullable SimpleFragment fragment) {
        super(context, fragment);
    }

    @Override
    protected void initScene() {
        try {

            AlphaMaskMaterialPlugin plugin = new AlphaMaskMaterialPlugin(0.9f);
            AlphaMaskMaterialPlugin plugin2 = new AlphaMaskMaterialPlugin(0.9f);

            Plane backgroundPlane = new Plane(4, 4, 1, 1);
            Material backgroundMaterial = new Material();
            backgroundMaterial.addTexture(new Texture("backgroud", R.drawable.flower1));
            backgroundMaterial.setColorInfluence(0);
            backgroundPlane.setMaterial(backgroundMaterial);
            backgroundPlane.setZ(-1.2);
            getCurrentScene().addChild(backgroundPlane);

            Plane backgroundPlane1 = new Plane(1.4f, 1.4f, 1, 1);
            Material backgroundMaterial1 = new Material();
            backgroundMaterial1.addTexture(new Texture("flower1", R.drawable.flower2));
            backgroundMaterial1.setColorInfluence(0);
            backgroundMaterial1.addPlugin(plugin);
            backgroundPlane1.setMaterial(backgroundMaterial1);
            backgroundPlane1.setZ(-0.6);
            getCurrentScene().addChild(backgroundPlane1);

            backgroundPlane2 = new Plane(1f, 1f, 1, 1);
            Material backgroundMaterial2 = new Material();
            backgroundMaterial2.addTexture(new Texture("flower2", R.drawable.flower3));
            backgroundMaterial2.setColorInfluence(0);
            backgroundMaterial2.addPlugin(plugin2);
            backgroundPlane2.setMaterial(backgroundMaterial2);
            getCurrentScene().addChild(backgroundPlane2);

        } catch (ATexture.TextureException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Camera initial orientation: " + getCurrentCamera().getOrientation());
        getCurrentCamera().enableLookAt();
        getCurrentCamera().setLookAt(0, 0, 0);
        getCurrentCamera().setZ(6.6);
        getCurrentCamera().setOrientation(getCurrentCamera().getOrientation().inverse());
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onRender(long ellapsedRealtime, double deltaTime) {
        super.onRender(ellapsedRealtime, deltaTime);

        getCurrentCamera().setLookAt(backgroundPlane2.getPosition());
    }

    @Override
    public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }
}
