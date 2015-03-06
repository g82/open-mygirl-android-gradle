package com.seokceed.mygirl;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edmodo.cropper.CropImageView;
import com.seokceed.mygirl.image.ImageLoaderTask;


public class CropFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "CropFragment";
    private CropImageView cropImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_crop, container, false);

        v.findViewById(R.id.btn_rotate).setOnClickListener(this);
        v.findViewById(R.id.btn_crop).setOnClickListener(this);

        cropImageView = (CropImageView) v.findViewById(R.id.CropImageView);
        cropImageView.setGuidelines(1);

        Uri uri = getActivity().getIntent().getData();

        new ImageLoaderTask(getActivity(), cropImageView).execute(uri);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_rotate:
                cropImageView.rotateImage(90);
                break;


            case R.id.btn_crop:
                //TODO compress image
                Bitmap croppedBitmap = cropImageView.getCroppedImage();
                ((EditorActivity) getActivity()).setmCroppedBitmap(croppedBitmap);

                Fragment inputFragment = new InputFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.rl_edit_container, inputFragment, "input");
                transaction.commit();
                break;
        }
    }

}
