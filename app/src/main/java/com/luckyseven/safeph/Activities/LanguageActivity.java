package com.luckyseven.safeph.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.nearby.exposurenotification.TemporaryExposureKey;

import java.util.List;

import com.luckyseven.safeph.Listeners.IExposureListener;
import com.luckyseven.safeph.R;
import com.luckyseven.safeph.Utilities.ExposureNotifications.ExposureNotificationWrapper;
import com.luckyseven.safeph.Utilities.LanguageUtil;
import com.luckyseven.safeph.databinding.ActivityLanguageBinding;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class LanguageActivity extends AppCompatActivity {

    ActivityLanguageBinding binding;
    Context context;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLanguageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.context = this;
        LanguageUtil.setLanguageInit(context);

        LanguageUtil.setLanguageFlag(context, true);

        setup();
    }

    private void setup() {
        binding.ivTitle.setImageDrawable(getDrawable(R.drawable.logo));
        binding.ivMz.setImageDrawable(getDrawable(R.drawable.doh));
        setupLanguage();

        binding.rlTagalog.setOnClickListener(view -> changeLanguage(LanguageActivity.this.getString(R.string.tagalog_language_short)));
        binding.rlEnglish.setOnClickListener(view -> changeLanguage(LanguageActivity.this.getString(R.string.english_language_short)));
        binding.llStart.setOnClickListener(view -> startNextActivity());
    }

    private void setupLanguage() {
        if (LanguageUtil.getLanguage().equals("ph")) {
            binding.rlTagalog.setBackground(ContextCompat.getDrawable(context, R.drawable.button_selected_language));
            binding.rlEnglish.setBackground(ContextCompat.getDrawable(context, R.drawable.button_language));
        } else {
            binding.rlTagalog.setBackground(ContextCompat.getDrawable(context, R.drawable.button_language));
            binding.rlEnglish.setBackground(ContextCompat.getDrawable(context, R.drawable.button_selected_language));
        }
    }

    private void startNextActivity() {
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

    private void startAppInfoActivity() {
        Intent intent = new Intent(LanguageActivity.this, AppInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void startMainActivity() {
        Intent intent = new Intent(LanguageActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void changeLanguage(String locale) {
        LanguageUtil.setLanguage(this, locale);
        recreate();
    }

    @Override
    public void recreate() {
        startActivity(getIntent());
        finish();
    }
}
