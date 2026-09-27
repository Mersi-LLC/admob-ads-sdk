package com.mersillc.ads.sdk.util;

import static com.mersillc.ads.sdk.util.Constant.TOKEN;
import static com.mersillc.ads.sdk.util.Constant.VALUE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import com.google.android.libraries.ads.mobile.sdk.banner.AdSize;
import com.mersillc.ads.sdk.gdpr.LegacyGDPR;

import java.nio.charset.StandardCharsets;

public class Tools {

    @SuppressWarnings("deprecation")
    public static AdSize getAdSize(Activity activity) {
        int adWidth;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            android.view.WindowMetrics windowMetrics = activity.getWindowManager().getCurrentWindowMetrics();
            android.graphics.Rect bounds = windowMetrics.getBounds();
            float density = activity.getResources().getDisplayMetrics().density;
            adWidth = (int) (bounds.width() / density);
        } else {
            Display display = activity.getWindowManager().getDefaultDisplay();
            DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);
            float widthPixels = outMetrics.widthPixels;
            float density = outMetrics.density;
            adWidth = (int) (widthPixels / density);
        }
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }

    public static AdSize getAdSizeMREC() {
        return AdSize.MEDIUM_RECTANGLE;
    }



    public static String decode(String code) {
        return decodeBase64(decodeBase64(decodeBase64(code)));
    }

    public static String decodeBase64(String code) {
        byte[] valueDecoded = Base64.decode(code.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
        return new String(valueDecoded);
    }

    public static String jsonDecode(String code) {
        String data = code.replace(TOKEN, VALUE);
        byte[] valueDecoded = Base64.decode(data.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
        return new String(valueDecoded);
    }

}
