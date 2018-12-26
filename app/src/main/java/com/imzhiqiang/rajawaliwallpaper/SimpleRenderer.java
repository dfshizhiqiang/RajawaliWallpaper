package com.imzhiqiang.rajawaliwallpaper;

import android.content.Context;
import android.support.annotation.Nullable;

import org.rajawali3d.renderer.Renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public abstract class SimpleRenderer extends Renderer {

    final SimpleFragment fragment;

    public SimpleRenderer(Context context, @Nullable SimpleFragment fragment) {
        super(context);
        this.fragment = fragment;
    }

    @Override
    public void onRenderSurfaceCreated(EGLConfig config, GL10 gl, int width, int height) {
        if (fragment != null) fragment.showLoader();
        super.onRenderSurfaceCreated(config, gl, width, height);
        if (fragment != null) fragment.hideLoader();
    }
}
