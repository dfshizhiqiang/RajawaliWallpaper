package com.imzhiqiang.rajawaliwallpaper;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import org.rajawali3d.renderer.ISurfaceRenderer;
import org.rajawali3d.view.IDisplay;
import org.rajawali3d.view.ISurface;
import org.rajawali3d.view.SurfaceView;

public abstract class SimpleFragment extends Fragment implements IDisplay {

    protected FrameLayout mRoot;
    protected ISurface mRenderSurface;
    protected ISurfaceRenderer mRenderer;
    protected ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mRoot = view.findViewById(R.id.root_view);
        mRenderSurface = view.<SurfaceView>findViewById(R.id.rajwali_surface);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);

        mRenderer = createRenderer();
        onBeforeApplyRenderer();
        applyRenderer();

        return view;
    }

    @LayoutRes
    protected int getLayoutId() {
        return R.layout.fragment_simple;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRoot.removeView((View) mRenderSurface);
    }

    protected void onBeforeApplyRenderer() {
    }

    @CallSuper
    protected void applyRenderer() {
        mRenderSurface.setSurfaceRenderer(mRenderer);
    }

    @CallSuper
    public void showLoader() {
        mProgressBar.post(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @CallSuper
    public void hideLoader() {
        mProgressBar.post(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }


}
