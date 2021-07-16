package com.example.root.build_model;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ComponentActivity;

public class AppUtils {
    public static void hideSoftInput(ComponentActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)activity.
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            View v = activity.getCurrentFocus();
            if (v == null) {
                return;
            }
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /**
     * 初始化夜间模式
     */
    public static void setNightMode() {
         boolean nightMode = true;
         AppCompatDelegate.setDefaultNightMode(nightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
     }

//    public static IMyAidlInterface getIMyAidlInterface(Context context) {
//        return ((MainActivity)context).iMyAidlInterface;
//    }
}
