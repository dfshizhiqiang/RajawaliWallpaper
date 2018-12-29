package com.imzhiqiang.rajawaliwallpaper;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.rajawali3d.renderer.ISurfaceRenderer;

public class EarthFragment extends SimpleFragment {

    private EditText editTextX, editTextY;
    private Button btnConfirm;

    @Override
    public ISurfaceRenderer createRenderer() {
        return new EarthRenderer(getActivity(), this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void onBeforeApplyRenderer() {
        super.onBeforeApplyRenderer();
        editTextX = mRoot.findViewById(R.id.editX);
        editTextY = mRoot.findViewById(R.id.editY);
        btnConfirm = mRoot.findViewById(R.id.btn_confirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textX = editTextX.getText().toString();
                String textY = editTextY.getText().toString();

                if (!TextUtils.isEmpty(textX)) {
                    try {
                        EarthRenderer.xK = Double.parseDouble(textX);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        Toast.makeText(requireContext(), "输入的不是数字", Toast.LENGTH_SHORT).show();
                    }

                }

                if (!TextUtils.isEmpty(textY)) {
                    try {
                        EarthRenderer.yK = Double.parseDouble(textY);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        Toast.makeText(requireContext(), "输入的不是数字", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_earth;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_set_papaer, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.set_wallpaper) {
            SpUtils.getInstance(requireContext()).setRenderer(RenderListFragment.EARTH);
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
