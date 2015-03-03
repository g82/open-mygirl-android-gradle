package com.seokceed.mygirl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.edmodo.cropper.CropImageView;

import java.io.FileNotFoundException;


public class CropActivity extends ActionBarActivity implements View.OnClickListener {

    private CropImageView cropImageView;

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Context context, Uri uri,
                                                         int reqWidth, int reqHeight) throws FileNotFoundException {

        ParcelFileDescriptor descriptor = context.getContentResolver().openFileDescriptor(uri, "r");

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFileDescriptor(descriptor.getFileDescriptor(), null, options);


        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFileDescriptor(descriptor.getFileDescriptor(), null, options);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crop);

        findViewById(R.id.btn_rotate).setOnClickListener(this);
        findViewById(R.id.btn_crop).setOnClickListener(this);

        cropImageView = (CropImageView) findViewById(R.id.CropImageView);
        cropImageView.setGuidelines(1);

        Uri uri = getIntent().getData();

        new ImageTask().execute(uri);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_rotate:
                cropImageView.rotateImage(90);
                break;


            case R.id.btn_crop:
                //TODO compress image
                //Bitmap croppedBitmap = cropImageView.getCroppedImage();
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

    private class ImageTask extends AsyncTask<Uri, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Uri... uris) {

            Bitmap bitmap = null;

            try {
                bitmap = decodeSampledBitmapFromResource(CropActivity.this, uris[0], 720, 720);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                return bitmap;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if (bitmap != null) {
                cropImageView.setImageBitmap(bitmap);
            }

        }
    }
}
