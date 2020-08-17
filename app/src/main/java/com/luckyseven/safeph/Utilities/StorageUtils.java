package com.luckyseven.safeph.Utilities;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.luckyseven.safeph.ExposureNotificationApp;

public class StorageUtils {
    public static void saveBoolean(String key, boolean bool) {
        SharedPreferences.Editor editor = ExposureNotificationApp.sharedPref.edit();
        editor.putBoolean(key, bool);
        editor.apply();
    }

    public static void saveInt(String key, int n) {
        SharedPreferences.Editor editor = ExposureNotificationApp.sharedPref.edit();
        editor.putInt(key, n);
        editor.apply();
    }

    public static void saveLong(String key, long n) {
        SharedPreferences.Editor editor = ExposureNotificationApp.sharedPref.edit();
        editor.putLong(key, n);
        editor.apply();
    }

    public static void saveString(String key, String text) {
        SharedPreferences.Editor editor = ExposureNotificationApp.sharedPref.edit();
        editor.putString(key, text);
        editor.apply();
    }

    public static void saveObject(String key, Object o) {
        String json = new Gson().toJson(o);
        saveString(key, json);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        if (ExposureNotificationApp.sharedPref.contains(key)) {
            return ExposureNotificationApp.sharedPref.getBoolean(key, defValue);
        }
        return defValue;
    }

    public static Integer getInt(String key) {
        if (ExposureNotificationApp.sharedPref.contains(key)) {
            return ExposureNotificationApp.sharedPref.getInt(key, 0);
        }
        return null;
    }

    public static Long getLong(String key) {
        if (ExposureNotificationApp.sharedPref.contains(key)) {
            return ExposureNotificationApp.sharedPref.getLong(key, 0);
        }
        return 0L;
    }

    public static String getString(String key) {
        if (ExposureNotificationApp.sharedPref.contains(key)) {
            return ExposureNotificationApp.sharedPref.getString(key, null);
        }
        return null;
    }

    public static Object getObject(String key, Class classObject) {
        try {
            String json = getString(key);
            return new Gson().fromJson(json, classObject);
        } catch (Exception e) {
            return null;
        }
    }

    public static void delete(String... keys) {
        SharedPreferences.Editor editor = ExposureNotificationApp.sharedPref.edit();
        for (String key : keys) {
            editor.remove(key);
        }
        editor.apply();
    }
}
