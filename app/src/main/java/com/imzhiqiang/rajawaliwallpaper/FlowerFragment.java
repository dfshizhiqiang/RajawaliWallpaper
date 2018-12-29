package com.imzhiqiang.rajawaliwallpaper;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.rajawali3d.renderer.ISurfaceRenderer;

public class FlowerFragment extends SimpleFragment {

    @Override
    public ISurfaceRenderer createRenderer() {
        return new FlowerRenderer(getActivity(), this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_set_papaer, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.set_wallpaper) {
            SpUtils.getInstance(requireContext()).setRenderer(RenderListFragment.FLOWER);
            startWallpaperService();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startWallpaperService() {
        Intent intent = new Intent();
        intent.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(requireContext(), RajawaliWallpaper.class));
        startActivity(intent);
    }
}
