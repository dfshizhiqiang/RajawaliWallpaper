package com.imzhiqiang.rajawaliwallpaper;

import org.rajawali3d.renderer.ISurfaceRenderer;

public class EarthFragment extends SimpleFragment {

    @Override
    public ISurfaceRenderer createRenderer() {
        return new EarthRenderer(getActivity(), this);
    }
}
