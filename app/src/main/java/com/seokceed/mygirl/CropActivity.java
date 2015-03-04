package com.seokceed.mygirl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.edmodo.cropper.CropImageView;
import com.seokceed.mygirl.image.ImageLoaderTask;
import com.seokceed.mygirl.image.PhotoCommonMethods;

import java.io.File;
import java.io.IOException;


public class CropActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = "CropActivity";
    private CropImageView cropImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crop);

        findViewById(R.id.btn_rotate).setOnClickListener(this);
        findViewById(R.id.btn_crop).setOnClickListener(this);

        cropImageView = (CropImageView) findViewById(R.id.CropImageView);
        cropImageView.setGuidelines(1);

        Uri uri = getIntent().getData();
        new ImageLoaderTask(this, cropImageView).execute(uri);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_rotate:
                cropImageView.rotateImage(90);
                break;


            case R.id.btn_crop:
                //TODO compress image
                final Bitmap croppedBitmap = cropImageView.getCroppedImage();

                new AsyncTask<Void, Void, File>() {
                    @Override
                    protected File doInBackground(Void... voids) {
                        try {
                            File tempFile = PhotoCommonMethods.saveImageFromBitmap(croppedBitmap);

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

                            // MainActivity -> CropActivity (FORWARD) -> EditorActivity -> MainActivity
                            Intent i = new Intent(CropActivity.this, EditorActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                            i.setData(Uri.fromFile(file));
                            startActivity(i);
                            finish();
                        }
                    }
                }.execute();

                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
