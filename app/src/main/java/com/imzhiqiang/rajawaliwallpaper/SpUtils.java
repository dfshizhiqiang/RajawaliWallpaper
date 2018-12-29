package com.imzhiqiang.rajawaliwallpaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SpUtils implements SharedPreferences.OnSharedPreferenceChangeListener {

    private volatile static SpUtils instance;

    private final SharedPreferences sp;

    private String renderer;

    public static SpUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (SpUtils.class) {
                if (instance == null) {
                    instance = new SpUtils(context);
                }
            }
        }
        return instance;
    }

    private SpUtils(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        sp.registerOnSharedPreferenceChangeListener(this);
        updateRenderer();
    }

    private void updateRenderer() {
        renderer = sp.getString("renderer", "");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updateRenderer();
    }

    public String getRenderer() {
        return renderer;
    }

    public void setRenderer(String renderer) {
        sp.edit().putString("renderer", renderer).apply();
    }
}
