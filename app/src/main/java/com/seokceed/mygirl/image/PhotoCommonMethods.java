package com.seokceed.mygirl.image;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by seokceed on 2014-11-22.
 */
public class PhotoCommonMethods {

    public static final int REQ_GALLERY = 10000;
    public static final int REQ_CAMERA = 10100;
    public static final int REQ_SHOWAD = 10534;

    public static final int MEDIA_TYPE_IMAGE = 20001;
    public static final int MEDIA_TYPE_VIDEO = 20002;
    public static final int MEDIA_TYPE_TEMP = 20003;

    private static final String PREFIX = "ITS_HERE_";
    private static final String TEMP_PREFIX = "TEMP_";
    private static final String DIRECTORY_NAME = "ITS_HERE";

    public static Uri CAMERA_URI = null;

    /*
    gallery
    return (Intent) data.getDataString();
    content://media/external/images/media/12446

    camera
    return CAMERA_URI file:///storage/emulated/0/Sootah/Sootah_20141122_214427.png
     */

    public static void photoFromGallery(Activity activity, String chooserTitle) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(intent, chooserTitle), REQ_GALLERY);
    }

    public static void photoFromCamera(Activity activity, File outputFile) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFile));
            activity.startActivityForResult(takePictureIntent, REQ_CAMERA);
        }
    }


    /*
    public static void sharePhotoFromUri(Activity activity, Uri fileUri, PhotoMetaData photoMetaData) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, photoMetaData.convertAddressString());
        shareIntent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(shareIntent, activity.getString(R.string.share)), REQ_SHOWAD);
    }
    */

    public static void clearTempFiles() {

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), DIRECTORY_NAME);

        if (mediaStorageDir.isDirectory()) {
            FilenameFilter filenameFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    if (filename.startsWith(TEMP_PREFIX)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            };
            File[] tempFiles = mediaStorageDir.listFiles(filenameFilter);
            for (File tempFile : tempFiles) {
                Log.d("clearTempFiles", tempFile.getName());
                tempFile.delete();
            }
        }
    }

    /**
     * Create a file Uri for saving an image or video
     */
    public static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), DIRECTORY_NAME);
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("file error", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String timeStamp = format.format(new Date());

        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + PREFIX + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_TEMP) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + TEMP_PREFIX + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + PREFIX + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    public static void copyStream(InputStream inputStream, OutputStream outputStream) throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        outputStream.flush();
        outputStream.close();
    }

    public static String getFilePathUsingCursor(Context context, Uri uri) throws IllegalArgumentException {

        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

        String filePath = null;

        if (cursor != null && cursor.moveToFirst()) {

            int column;

            try {
                column = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                filePath = cursor.getString(column);
            } catch (IllegalArgumentException e) {
                throw e;
            } finally {
                cursor.close();
            }
        }

        return filePath;
    }

    public static String createTempFileFromURI(Context context, Uri uri) {

        try {
            //because some photo stored google photo cloud.
            ParcelFileDescriptor descriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            Bitmap tempBitmap = BitmapFactory.decodeFileDescriptor(descriptor.getFileDescriptor());

            File tempFile = PhotoCommonMethods.getOutputMediaFile(MEDIA_TYPE_TEMP);

            if (tempBitmap != null) {
                FileOutputStream fos = new FileOutputStream(tempFile);
                tempBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
                return tempFile.getPath();
            } else {
                return null;
            }
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }

    }

    /*
    public static boolean setMetaDataToFile(File bitmapFile, PhotoMetaData photoMetaData) {

        try {
            ExifInterface exif = new ExifInterface(bitmapFile.getPath());

            LatLng latLng = photoMetaData.getLatLng();

            int num1Lat = (int) Math.floor(latLng.latitude);
            int num2Lat = (int) Math.floor((latLng.latitude - num1Lat) * 60);
            double num3Lat = (latLng.latitude - ((double) num1Lat + ((double) num2Lat / 60))) * 3600000;

            int num1Lon = (int) Math.floor(latLng.longitude);
            int num2Lon = (int) Math.floor((latLng.longitude - num1Lon) * 60);
            double num3Lon = (latLng.longitude - ((double) num1Lon + ((double) num2Lon / 60))) * 3600000;

            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, num1Lat + "/1," + num2Lat + "/1," + num3Lat + "/1000");
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, num1Lon + "/1," + num2Lon + "/1," + num3Lon + "/1000");


            if (latLng.latitude > 0) {
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "N");
            } else {
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "S");
            }

            if (latLng.longitude > 0) {
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "E");
            } else {
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "W");
            }

            exif.saveAttributes();

            return true;

        } catch (IOException e) {
            return false;
        }
    }


    public static PhotoMetaData getMetaDataFromURI(Context context, int requestCode, Intent imageData) {

        PhotoMetaData photoMetaData = new PhotoMetaData();
        String filePath = null;

        if (imageData == null && CAMERA_URI != null) {
            filePath = PhotoCommonMethods.CAMERA_URI.getPath();
        } else if (imageData != null) {

            String dataUriStr = imageData.getDataString();

            Log.d("ReceivedURI", dataUriStr);

            if (dataUriStr.startsWith("file://")) {
                //file type Camera, dropbox... ndrive, daumcloud
                filePath = imageData.getData().getPath();
            } else if (dataUriStr.startsWith("content://")) {
                //content type default gallery, photo,...
                try {
                    //get photo from local sdcard.
                    filePath = getFilePathUsingCursor(context, imageData.getData());

                    if (filePath == null || filePath.equals("")) {
                        filePath = createTempFileFromURI(context, imageData.getData());
                    }

                } catch (IllegalArgumentException e) {
                    //some photos stored google photo cloud.
                    filePath = createTempFileFromURI(context, imageData.getData());
                }
            }
        }

        if (filePath == null) return null;

        photoMetaData.setFilePath(filePath);

        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filePath);
        } catch (IOException e) {
            return photoMetaData;
        }

        if (exif != null) {

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);

            int degree = 0;

            if (orientation != -1) {
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }

                photoMetaData.setOrientation_degree(degree);
            }

            float[] output = {0.f, 0.f};

            if (exif.getLatLong(output)) {
                LatLng latLng = new LatLng(output[0], output[1]);
                photoMetaData.setLatLng(latLng);
            }
        }

        return photoMetaData;
    }
    */

    public static boolean deleteFileFromUri(Uri uri) {
        File file = new File(uri.getPath());
        return file.delete();
    }


    public static Bitmap bitmapFromView(View view) {

        view.setDrawingCacheEnabled(true);
        //view.buildDrawingCache(true);
        Bitmap captureBitmap = view.getDrawingCache(true);
        //view.setDrawingCacheEnabled(false);

        return captureBitmap;
    }

    public static void recycleBitmap(Bitmap bitmap) {

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD) {
            bitmap.recycle();
        } else {
            bitmap = null;
        }
    }

    public static Bitmap decodeSampledBitmapFromUri(Context context, Uri uri,
                                                    int reqWidth, int reqHeight) throws FileNotFoundException {

        ParcelFileDescriptor descriptor = context.getContentResolver().openFileDescriptor(uri, "r");

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFileDescriptor(descriptor.getFileDescriptor(), null, options);


        // Calculate inSampleSize
        options.inSampleSize = PhotoCommonMethods.calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFileDescriptor(descriptor.getFileDescriptor(), null, options);
    }

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

    public static File saveImageFromBitmap(Bitmap bitmap) throws IOException {

        FileOutputStream fos;

        File bitmapFile = getOutputMediaFile(MEDIA_TYPE_TEMP);

        fos = new FileOutputStream(bitmapFile);

        boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

        fos.close();

        return isSuccess ? bitmapFile : null;
    }

    public static Bitmap setRotateBitmap(Bitmap bitmap, int degrees) {

        Bitmap rotatedBitmap = null;

        if (degrees != 0 && bitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);

            rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);

        }

        return rotatedBitmap;
    }

}
