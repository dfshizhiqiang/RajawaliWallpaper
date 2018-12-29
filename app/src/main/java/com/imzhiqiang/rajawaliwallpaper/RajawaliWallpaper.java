package com.imzhiqiang.rajawaliwallpaper;

import org.rajawali3d.renderer.ISurfaceRenderer;
import org.rajawali3d.view.ISurface;
import org.rajawali3d.wallpaper.Wallpaper;

public class RajawaliWallpaper extends Wallpaper {

    private ISurfaceRenderer mRenderer;

    @Override
    public Engine onCreateEngine() {
        String render = SpUtils.getInstance(this).getRenderer();
        if (RenderListFragment.FLOWER.equals(render)) {
            mRenderer = new FlowerRenderer(this, null);
        } else {
            mRenderer = new EarthRenderer(this, null);
        }
        return new WallpaperEngine(getBaseContext(), mRenderer,
                ISurface.ANTI_ALIASING_CONFIG.NONE);
    }
}
