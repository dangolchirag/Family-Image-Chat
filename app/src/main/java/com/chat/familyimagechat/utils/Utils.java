package com.chat.familyimagechat.utils;

import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {
    public static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) dp, Resources.getSystem().getDisplayMetrics());
    }
}
