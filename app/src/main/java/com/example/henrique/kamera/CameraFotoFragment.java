package com.example.henrique.kamera;


import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFotoFragment extends Fragment implements View.OnClickListener, ViewTreeObserver.OnGlobalLayoutListener {

    File mCaminhoFoto;
    ImageView mImageViewFoto;
    CarregarImageTask mTask;

    int mLarguraImagem;
    int mAlturaImagem;

  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setRetainInstance(true);

      String caminhoFoto = Util.getUltimaMidia(getActivity(), Util.MIDIA_FOTO);
      if (caminhoFoto != null){
          mCaminhoFoto = new File(caminhoFoto);
      }
  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      View layout = inflater.inflate(R.layout.fragment_camera_foto, container, false);
      layout.findViewById(R.id.btnFotoFrag).setOnClickListener(this);
      mImageViewFoto = (ImageView) layout.findViewById(R.id.imgFotoFrag);
      layout.getViewTreeObserver().addOnGlobalLayoutListener(this);
        return layout;
    }
    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode , data);

        if (resultCode == Activity.RESULT_OK && requestCode == Util.REQUESTCODE_FOTO){
            carregarImagem();
        }
    }
    @Override
    public void onGlobalLayout () {
      if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1){ {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
              getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
          }
      }
      } else {
          getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
      }
      mLarguraImagem = mImageViewFoto.getWidth();
      mAlturaImagem = mImageViewFoto.getHeight();
      carregarImagem();
    }
    @Override
    public void onClick (View view){
      if (view.getId() == R.id.btnFotoFrag){
          ArrayList<String> permissions = new ArrayList<>();
          if (ActivityCompat.checkSelfPermission(getActivity(),
                  Manifest.permission.READ_EXTERNAL_STORAGE)!=
                  PackageManager.PERMISSION_GRANTED){
              permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
          }if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
              permissions.add(Manifest.permission.CAMERA);
          } if (permissions.isEmpty()){
              abrirCamera();
          } else {
            String[] permissoesParaRequisitar = new String[permissions.size()];
            permissions.toArray(permissoesParaRequisitar);
            ActivityCompat.requestPermissions(getActivity(), permissoesParaRequisitar, 0);
          }
      }
    }

    private void abrirCamera (){
      mCaminhoFoto = Util.novaMidia(Util.MIDIA_FOTO);
      Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCaminhoFoto));
      startActivityForResult(it, Util.REQUESTCODE_FOTO);
    }
    private void carregarImagem (){
      if (mCaminhoFoto != null && mCaminhoFoto.exists()){
          if (mTask == null || mTask.getStatus() != AsyncTask.Status.RUNNING){
              mTask = new CarregarImageTask();
              mTask.execute();
          }
      }
    }
    class CarregarImageTask extends AsyncTask<Void, Void, Bitmap>{
      @Override
        protected Bitmap doInBackground(Void... voids){
          return Util.carregarImagem(
                  mCaminhoFoto,
                  mLarguraImagem,
                  mAlturaImagem
          );
      }
      @Override
        protected void onPostExecute(Bitmap bitmap){
          super.onPostExecute(bitmap);
          if(bitmap != null){
              mImageViewFoto.setImageBitmap(bitmap);
              Util.salvarUltimaMidia(getActivity(),
                      Util.MIDIA_FOTO, mCaminhoFoto.getAbsolutePath());
          }
      }
    }

}
