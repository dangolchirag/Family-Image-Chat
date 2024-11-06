package com.chat.familyimagechat.utils;

import android.util.TypedValue;
import android.content.res.Resources;
public class Utils {
    public static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                (float) dp,
                Resources.getSystem().getDisplayMetrics()
        );
    }
}
