package com.seokceed.mygirl;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.seokceed.mygirl.editor.CanvasView;
import com.seokceed.mygirl.image.ImageLoaderTask;


public class EditorActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        findViewById(R.id.btn_finish).setOnClickListener(this);

        Uri imageUri = getIntent().getData();

        final CanvasView canvasView = (CanvasView) findViewById(R.id.cv_canvas);
        canvasView.setOnTouchListener(canvasView);

        new ImageLoaderTask(this, canvasView).execute(imageUri);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
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

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_finish:
                setResult(RESULT_OK);
                finish();
                break;
        }

    }
}
