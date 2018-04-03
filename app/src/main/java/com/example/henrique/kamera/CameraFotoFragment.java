package com.example.henrique.kamera;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFotoFragment extends Fragment implements View.OnClickListener, ViewTreeObserver.OnGlobalLayoutListener {

    File mCaminhoFoto;
    ImageView mImageViewFoto;


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

    }

}
