package com.seokceed.mygirl.share;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

import com.kakao.kakaolink.KakaoLink;
import com.seokceed.mygirl.R;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class KakaoShare {

    public static void shareImageKakao(Activity activity, Uri imagePath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, imagePath);
//		intent.setPackage("com.kakao.talk");
        activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.share_what)));
    }

    public static void shareAppKakao(Activity activity) {
        ArrayList<Map<String, String>> metaInfoArray = new ArrayList<Map<String, String>>();
        Map<String, String> metaInfoAndroid = new Hashtable<String, String>(1);
        metaInfoAndroid.put("os", "android");
        metaInfoAndroid.put("devicetype", "phone");
        metaInfoAndroid.put("installurl", "market://details?id=com.seokceed.mygirl");
        metaInfoAndroid.put("executeurl", "MyGirl://startActivity");

        metaInfoArray.add(metaInfoAndroid);

        KakaoLink kakaoLink = KakaoLink.getLink(activity.getApplicationContext());
        if (!kakaoLink.isAvailableIntent()) return;
        try {
            kakaoLink.openKakaoAppLink(activity, "https://play.google.com/store/apps/details?id=com.seokceed.mygirl", activity.getString(R.string.kakao_share_msg),
                    activity.getPackageName(),
                    activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName,
                    activity.getString(R.string.app_name), "UTF-8", metaInfoArray);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}
