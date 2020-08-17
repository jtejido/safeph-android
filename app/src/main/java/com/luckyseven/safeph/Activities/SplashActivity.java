package com.luckyseven.safeph.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.nearby.exposurenotification.TemporaryExposureKey;

import java.util.List;

import com.luckyseven.safeph.Listeners.IExposureListener;
import com.luckyseven.safeph.R;
import com.luckyseven.safeph.Utilities.ExposureNotifications.ExposureNotificationWrapper;
import com.luckyseven.safeph.Utilities.Util;
import com.luckyseven.safeph.Utilities.VersionChecker;
import com.luckyseven.safeph.databinding.ActivitySplashBinding;

import com.luckyseven.safeph.Utilities.LanguageUtil;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 1000;

    ActivitySplashBinding binding;
    Context context;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.context = this;
        binding.ivSplash.setImageDrawable(getDrawable(R.drawable.splash_screen));
    }


    @Override
    protected void onStart() {
        super.onStart();
        LanguageUtil.setLanguageInit(context);
        new Handler().postDelayed(() -> checkVersion(), SPLASH_TIME_OUT);


    }

    private void checkVersion()
    {
        VersionChecker.check(newVersionFound -> {
            if (newVersionFound) {
                startUpdateActivity();
            } else {
                startNextActivity();
            }
        });
    }

    private void startNextActivity() {
        if (!LanguageUtil.getLanguageFlag()) {
            startLanguageActivity();
        } else {
            ExposureNotificationWrapper.get().checkEnabled(new IExposureListener() {
                @Override
                public void onStarted() {
                    startMainActivity();
                }

                @Override
                public void onStopped() {
                    startAppInfoActivity();
                }

                @Override
                public void onApiException(ApiException apiException, int requestCode) {

                }

                @Override
                public void onExposureKeysRetrieved(List<TemporaryExposureKey> keys) {

                }
            });
        }
    }

    private void startLanguageActivity() {
        Intent intent = new Intent(SplashActivity.this, LanguageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void startAppInfoActivity() {
        Intent intent = new Intent(SplashActivity.this, AppInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void startMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void startUpdateActivity() {
        Intent intent = new Intent(context, UpdateActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
