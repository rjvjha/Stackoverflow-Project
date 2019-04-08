package com.example.rajeevjha.stackoverflow.data;


import android.content.SharedPreferences;

import com.example.rajeevjha.stackoverflow.application.MyApplication;

import java.util.Set;

/**
 * Class to manage & manipulate SharedPreferences
 */

public class PreferenceHelper {

    // constant keys
    public static final String IS_LOGIN_PREF = "isLogin";
    public static final String PREF_KEY_Token = "token";
    public static final String PREF_KEY_TAGS = "selectedTags";

    public static Boolean getBoolean(String key, Boolean defValue) {
        return MyApplication.getPreferences().getBoolean(key, defValue);
    }

    public static void putBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = MyApplication.getPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String getString(String key, String defValue) {
        return MyApplication.getPreferences().getString(key, defValue);
    }

    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = MyApplication.getPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static int getInt(String key, int defValue) {
        return MyApplication.getPreferences().getInt(key, defValue);
    }

    public static void putInt(String key, int value) {
        SharedPreferences.Editor editor = MyApplication.getPreferences().edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void putStringSet(String key, Set<String> value) {
        SharedPreferences.Editor editor = MyApplication.getPreferences().edit();
        editor.putStringSet(key, value);
        editor.commit();

    }

    public static Set<String> getTagsStringSet(Set<String> defSet) {
        return MyApplication.getPreferences().getStringSet(PREF_KEY_TAGS, defSet);
    }

    public static boolean getLoginCheck() {
        return getBoolean(IS_LOGIN_PREF, false);
    }

    public static void setLoginCheck(boolean tested) {
        putBoolean(IS_LOGIN_PREF, tested);
    }


}
