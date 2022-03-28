package com.anphat.supplier.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class ViewUtil {

    private static int windowWidthPortrait = 0;
    private static int windowWidthLandscape = 0;

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getWindowWidth(Context context) {
        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            return getWindowWidthPortrait(context);
        }
        else {
            return getWindowWidthLandscape(context);
        }
    }

    private static int getWindowWidthPortrait(Context context) {
        if(windowWidthPortrait == 0) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            windowWidthPortrait = metrics.widthPixels;
        }

        return windowWidthPortrait;
    }

    private static int getWindowWidthLandscape(Context context) {
        if(windowWidthLandscape == 0) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            windowWidthLandscape = metrics.widthPixels;
        }

        return windowWidthLandscape;
    }


}
