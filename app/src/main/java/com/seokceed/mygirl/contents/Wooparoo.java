package com.seokceed.mygirl.contents;

import android.content.res.Resources;

import com.seokceed.mygirl.R;

import java.util.Random;

public class Wooparoo {

    public static final String getRandomPerText(Resources res) {
        Random r = new Random();
        int random = r.nextInt(6);

        switch (random) {
            case 0:
                return res.getString(R.string.per_sec);

            case 1:
                return res.getString(R.string.per_minute);

            case 2:
                return res.getString(R.string.per_day);

            case 3:
                return res.getString(R.string.per_week);

            case 4:
                return res.getString(R.string.per_month);

            case 5:
                return res.getString(R.string.per_year);

            default:
                return res.getString(R.string.per_minute);
        }
    }

    public static final String getRandomMountText(Resources res) {
        Random r = new Random();
        int random = r.nextInt(999) + 1;

        int random2 = r.nextInt(3);

        String mount = null;

        switch (random2) {
            case 0:
                mount = res.getString(R.string.kilo);
                break;

            case 1:
                mount = res.getString(R.string.mass);
                break;

            case 2:
                mount = res.getString(R.string.ton);
                break;
        }

        String text = String.valueOf(random) + mount;
        return text;
    }

    public static final int getRandomElementFrame() {

        Random r = new Random();
        int random = r.nextInt(8) + 1;

        switch (random) {

            case 1:
                return R.drawable.wooparoo_forest;

            case 2:
                return R.drawable.wooparoo_earth;

            case 3:
                return R.drawable.wooparoo_fire;

            case 4:
                return R.drawable.wooparoo_ice;

            case 5:
                return R.drawable.wooparoo_thunder;

            case 6:
                return R.drawable.wooparoo_water;

            case 7:
                return R.drawable.wooparoo_wind;

            case 8:
                return R.drawable.wooparoo_star;

            default:
                return R.drawable.wooparoo_forest;
        }
    }

}
