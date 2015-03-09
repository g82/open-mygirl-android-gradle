package com.seokceed.mygirl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gamepari on 3/3/15.
 */
public class InfoFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_info, container, false);

        v.findViewById(R.id.tv_opensource).setOnClickListener(this);
        v.findViewById(R.id.btn_mail).setOnClickListener(this);

        return v;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_mail:
                Uri uri = Uri.parse("mailto:seokceed@gmail.com");
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.send_mail_title));
                startActivity(i);
                break;

            case R.id.tv_opensource:
                Uri uri3 = Uri.parse("https://github.com/seokceed/open-mygirl-android-gradle");
                Intent i3 = new Intent(Intent.ACTION_VIEW, uri3);
                startActivity(i3);
                break;

        }



    }
}
