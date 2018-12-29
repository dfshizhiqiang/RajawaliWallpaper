package com.imzhiqiang.rajawaliwallpaper;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RenderListFragment extends ListFragment {

    public static final String EARTH = "Earth";
    public static final String FLOWER = "Flower";

    private String[] renderList = new String[]{
            EARTH, FLOWER
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, renderList));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startPreview(position);
    }

    private void startPreview(int position) {
        String render = null;
        if (position == 0) {
            render = EARTH;
        } else if (position == 1) {
            render = FLOWER;
        }
        if (render == null) return;
        startActivity(new Intent().setClass(requireContext(), RenderActivity.class).putExtra("render", render));
    }

    private void startLWPSerice(int position) {
        if (position == 0) {
            SpUtils.getInstance(requireContext()).setRenderer(EARTH);
        } else {
            SpUtils.getInstance(requireContext()).setRenderer(FLOWER);
        }
        startLWP();
    }

    private void startLWP() {
        Intent intent = new Intent();
        intent.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(requireContext(), RajawaliWallpaper.class));
        startActivity(intent);
    }
}
