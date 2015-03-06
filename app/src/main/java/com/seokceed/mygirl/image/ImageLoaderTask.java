package com.seokceed.mygirl.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.edmodo.cropper.CropImageView;
import com.seokceed.mygirl.editor.CanvasView;

import java.io.FileNotFoundException;

/**
 * Created by gamepari on 3/4/15.
 */
public class ImageLoaderTask extends AsyncTask<Uri, Void, Bitmap> {

    private static final String TAG = "ImageLoaderTask";
    Context mContext;

    ImageView mImageView;
    CropImageView mCropImageView;
    CanvasView mCanvasView;

    public ImageLoaderTask(Context context, ImageView imageView) {
        mContext = context;
        mImageView = imageView;
    }

    public ImageLoaderTask(Context context, CropImageView cropImageView) {
        mContext = context;
        mCropImageView = cropImageView;
    }

    public ImageLoaderTask(Context context, CanvasView canvasView) {

        mContext = context;
        mCanvasView = canvasView;

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);


        if (bitmap != null) {

            if (mCropImageView != null) {
                mCropImageView.setImageBitmap(bitmap);
            } else if (mImageView != null) {
                mImageView.setImageBitmap(bitmap);
            } else if (mCanvasView != null) {
                mCanvasView.initBackground(bitmap);
            }

        } else {
            Log.d(TAG, "bitmap is null");
        }
    }

    @Override
    protected Bitmap doInBackground(Uri... uris) {

        Bitmap bitmap = null;

        try {
            bitmap = PhotoCommonMethods.decodeSampledBitmapFromUri(mContext, uris[0], 720, 720);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            return bitmap;
        }
    }
}
