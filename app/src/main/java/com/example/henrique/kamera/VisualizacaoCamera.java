package com.example.henrique.kamera;

import android.content.Context;
import android.graphics.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class VisualizacaoCamera extends SurfaceView implements SurfaceHolder.Callback{

    private Camera mCamera;

    public VisualizacaoCamera (Context ctx, Camera camera){
        super(ctx);
        mCamera = camera;
        getHolder().addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
