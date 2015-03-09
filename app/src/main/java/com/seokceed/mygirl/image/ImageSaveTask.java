package com.seokceed.mygirl.image;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import com.seokceed.mygirl.R;
import com.seokceed.mygirl.share.KakaoShare;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by gamepari on 2015-03-09.
 */
public class ImageSaveTask extends AsyncTask<Bitmap, Void, String> {

    Activity mActivity;
    ProgressDialog mProgressDialog;
    MediaScannerConnection msc;

    public ImageSaveTask(Activity mActivity) {
        this.mActivity = mActivity;
        mProgressDialog = new ProgressDialog(mActivity);
    }

    @Override
        protected void onPostExecute(String result) {
        mProgressDialog.dismiss();
            if (result != null) {
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.toast_saved), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.toast_failed), Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            mProgressDialog.setMessage(mActivity.getResources().getString(R.string.dialog_saving));
            mProgressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Bitmap... params) {

            File imageFile = MediaFileUtil.getOutputMediaFile(MediaFileUtil.MEDIA_TYPE_IMAGE);
            OutputStream out = null;

            //TODO BUG 3
            if (imageFile == null) return null;

            try {
                imageFile.createNewFile();
                out = new FileOutputStream(imageFile);
                params[0].compress(Bitmap.CompressFormat.PNG, 100, out);
                out.close();
                connectMediaScan(imageFile.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return imageFile.toString();
        }

    private void connectMediaScan(final String filename) {
        msc = new MediaScannerConnection(mActivity, new MediaScannerConnection.MediaScannerConnectionClient() {

            @Override
            public void onScanCompleted(String path, Uri uri) {
                msc.disconnect();
            }

            @Override
            public void onMediaScannerConnected() {
                msc.scanFile(filename, "image/png");
            }
        });
        msc.connect();
    }

}
