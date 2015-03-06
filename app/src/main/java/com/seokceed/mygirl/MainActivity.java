package com.seokceed.mygirl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.seokceed.mygirl.image.PhotoCommonMethods;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public static final int REQUEST_EDIT = 2394;
    private static final String TAG = "MainActivity";
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.vp_pager);
        mViewPager.setAdapter(new MainPageAdapter(getSupportFragmentManager()));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PhotoCommonMethods.clearTempFiles();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //start ContentListActivity

        Intent i = new Intent(this, ContentListActivity.class);

        if (requestCode == PhotoCommonMethods.REQ_CAMERA && resultCode == RESULT_OK) {

            // galaxy nexus     /mnt/sdcard/ITS_HERE/ITS_HERE_20150303_160348.jpg
            // lg g3       /storage/emulated/0/ITS_HERE/ITS_HERE_20150303_160448.jpg

            if (ImportImageFragement.cameraOutputFile != null) {
                i.setData(Uri.fromFile(ImportImageFragement.cameraOutputFile));
            } else {
                i.setData(data.getData());
            }

            startActivityForResult(i, REQUEST_EDIT);

        } else if (requestCode == PhotoCommonMethods.REQ_GALLERY && resultCode == RESULT_OK) {

            if (data != null) {
                i.setData(data.getData());
                startActivityForResult(i, REQUEST_EDIT);
            }

        } else if (requestCode == REQUEST_EDIT && resultCode == RESULT_OK) {

            Toast.makeText(this, "forwarded", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private class MainPageAdapter extends FragmentPagerAdapter {

        private List<Fragment> listFragments;

        private MainPageAdapter(FragmentManager fm) {
            super(fm);

            listFragments = new ArrayList<>();
            listFragments.add(new ImportImageFragement());
            listFragments.add(new ExperimentalFragment());
            listFragments.add(new InfoFragment());

        }

        @Override
        public Fragment getItem(int position) {
            return listFragments.get(position);
        }

        @Override
        public int getCount() {
            return listFragments.size();
        }
    }
}
