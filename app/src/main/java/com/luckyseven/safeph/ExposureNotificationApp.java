package com.luckyseven.safeph;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.webkit.WebView;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatDelegate;

import com.crashlytics.android.Crashlytics;

import java.util.Locale;

import com.luckyseven.safeph.Utilities.GlobalEnvironment;
import com.luckyseven.safeph.Utilities.LanguageUtil;
import io.fabric.sdk.android.Fabric;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class ExposureNotificationApp extends Application {
    public static final String DEFAULT_FONT_PATH = "fonts/roboto_regular.ttf";
    public static final String SERVER_URL = "http://localhost:8000/";
    public static final String SERVER_URL_TEST = "http://localhost:8000/";
    @SuppressLint("StaticFieldLeak")
    private static Context mInstance;
    public static GlobalEnvironment globalEnvironment;
    public static SharedPreferences sharedPref;

    @Override
    public void onCreate() {
        super.onCreate();
        new WebView(this).destroy();
        mInstance = this;
        sharedPref = getSharedPreferences(mInstance.getResources().getString(R.string.ExposureAppPrefFile), Context.MODE_PRIVATE);
        setupGlobalEnvironment();
        setupCalligraphy();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        LanguageUtil.setLanguageInit(this);
        Fabric.with(this, new Crashlytics());
    }

    private static void setupGlobalEnvironment() {
        globalEnvironment = new GlobalEnvironment();

        if (isProduction()) {
            globalEnvironment.globalURL = SERVER_URL;
        } else {
            globalEnvironment.globalURL = SERVER_URL_TEST;
        }
    }

    private void setupCalligraphy() {
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath(DEFAULT_FONT_PATH)
                                .setFontAttrId(R.attr.fontPath)
                                .build())).build());
    }

    public static boolean isProduction() {
        return mInstance.getResources().getBoolean(R.bool.is_production);
    }

    public static synchronized Context getInstance() {
        return mInstance;
    }

    public static String getStr(@StringRes int stringId) {
        Configuration config = new Configuration(getInstance().getResources().getConfiguration());
        config.setLocale(new Locale(LanguageUtil.getImageLanguage()));
        Resources resources = new Resources(getInstance().getAssets(), Resources.getSystem().getDisplayMetrics(), config);
        return resources.getString(stringId);
    }
}
