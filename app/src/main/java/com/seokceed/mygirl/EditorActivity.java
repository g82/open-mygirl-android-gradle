package com.seokceed.mygirl;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;


public class EditorActivity extends ActionBarActivity {

    private Bitmap mCroppedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Fragment fragment = new CropFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.rl_edit_container, fragment, "crop");
        transaction.commit();
    }

    public Bitmap getmCroppedBitmap() {
        return mCroppedBitmap;
    }

    public void setmCroppedBitmap(Bitmap mCroppedBitmap) {
        this.mCroppedBitmap = mCroppedBitmap;
    }

}
