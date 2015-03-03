package com.seokceed.mygirl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seokceed.mygirl.image.PhotoCommonMethods;

import java.io.File;

/**
 * Created by gamepari on 3/3/15.
 */
public class ImportImageFragement extends Fragment implements View.OnClickListener {

    public static File cameraOutputFile;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.page_createimage, container, false);

        v.findViewById(R.id.btn_camera).setOnClickListener(this);
        v.findViewById(R.id.btn_gallery).setOnClickListener(this);
        v.findViewById(R.id.btn_save).setOnClickListener(this);
        v.findViewById(R.id.btn_share).setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_camera:
                cameraOutputFile = PhotoCommonMethods.getOutputMediaFile(PhotoCommonMethods.MEDIA_TYPE_IMAGE);
                PhotoCommonMethods.photoFromCamera(getActivity(), cameraOutputFile);
                break;

            case R.id.btn_gallery:
                PhotoCommonMethods.photoFromGallery(getActivity(), "Open!");
                break;


            case R.id.btn_save:
                break;

            case R.id.btn_share:
                break;

        }

    }
}
