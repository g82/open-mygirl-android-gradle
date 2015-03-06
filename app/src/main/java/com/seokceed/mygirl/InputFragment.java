package com.seokceed.mygirl;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seokceed.mygirl.editor.CanvasView;
import com.seokceed.mygirl.image.PhotoCommonMethods;

import java.io.File;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputFragment extends Fragment {


    private static final String TAG = "InputFragment";

    public InputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_input, container, false);

        final CanvasView canvasView = (CanvasView) v.findViewById(R.id.cv_canvas);
        Bitmap croppedBitmap = ((EditorActivity) getActivity()).getmCroppedBitmap();
        canvasView.initBackground(croppedBitmap);

        v.findViewById(R.id.btn_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AsyncTask<Void, Void, File>() {
                    @Override
                    protected File doInBackground(Void... voids) {
                        try {
                            File tempFile = PhotoCommonMethods.saveImageFromBitmap(((EditorActivity) getActivity()).getmCroppedBitmap());

                            if (tempFile == null) {
                                Log.d(TAG, "File compress failed.");
                            } else {
                                Log.d(TAG, tempFile.getAbsolutePath());
                                return tempFile;
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(File file) {
                        super.onPostExecute(file);

                        if (file != null) {
                            getActivity().setResult(ActionBarActivity.RESULT_OK);
                            getActivity().finish();
                        }
                    }
                }.execute();

            }
        });

        return v;
    }

}
