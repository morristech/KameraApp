package com.example.henrique.kamera;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    private Camera mCamera;
    private CameraVisual mPreview;
    private MediaRecorder mMediaRecorder;
    private boolean mGravando;
    private boolean mTirouFoto;
    private Button mBtnCapturar;
    private File mCaminhoArquivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Uri uri = getIntent().getParcelableExtra(MediaStore.EXTRA_OUTPUT);
        if (uri != null){
            mCaminhoArquivo = new File(uri.getPath());
        }
        mBtnCapturar = (Button) findViewById(R.id.btnCapturarFoto);
        mBtnCapturar.setOnClickListener(this);
    }
    private boolean cameraDisponivel (){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }
    private void liberarCamera(){
        if (mCamera != null){
            mCamera.release();
            mCamera = null;
            mPreview.getHolder().removeCallback(mPreview);
        }
    }
    private void abrirCamera (){
        try {
            mCamera = Camera.open();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void tirarFoto(){
        if (mTirouFoto){
            setResult(RESULT_OK);
            finish();
        } else {
            mCamera.takePicture(null, null, mPicture);
        }
    }
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            if (mCaminhoArquivo != null) {
                try {
                    FileOutputStream fos = new FileOutputStream(mCaminhoArquivo);
                    fos.write(data);
                    fos.close();
                    mTirouFoto = true;
                    mBtnCapturar.setText("OK");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
