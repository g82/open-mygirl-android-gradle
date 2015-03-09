package com.seokceed.mygirl;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.seokceed.mygirl.image.ImageSaveTask;
import com.seokceed.mygirl.image.PhotoCommonMethods;

import java.io.File;

/**
 * Created by gamepari on 3/3/15.
 */
public class ImportImageFragement extends Fragment implements View.OnClickListener {

    public static File cameraOutputFile;

    private Bitmap mResultBitmap;

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
                cameraOutputFile = PhotoCommonMethods.getOutputMediaFile(PhotoCommonMethods.MEDIA_TYPE_TEMP);
                PhotoCommonMethods.photoFromCamera(getActivity(), cameraOutputFile);
                break;

            case R.id.btn_gallery:
                PhotoCommonMethods.photoFromGallery(getActivity(), "Open!");
                break;


            case R.id.btn_save:
                if (mResultBitmap == null) {
                    Toast.makeText(getActivity(), getString(R.string.need_image), Toast.LENGTH_SHORT).show();
                } else {
                    new ImageSaveTask(getActivity()).execute(mResultBitmap);
                }
                break;

            case R.id.btn_share:
                break;

        }
    }
}
