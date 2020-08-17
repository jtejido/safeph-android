package com.luckyseven.safeph.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.luckyseven.safeph.R;
import com.luckyseven.safeph.Utilities.LanguageUtil;
import com.luckyseven.safeph.databinding.ActivityPrivacyBinding;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class PrivacyActivity extends AppCompatActivity {

    private ActivityPrivacyBinding binding;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrivacyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ivMzLogo.setImageDrawable(getDrawable(R.drawable.doh));
        binding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.tvTermsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.terms_url)));
                startActivity(intent);
            }
        });

        binding.tvPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_url)));
                startActivity(intent);
            }
        });

        binding.tvAccessibilityPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.accessibility_url)));
                startActivity(intent);
            }
        });

        binding.tvLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.license_url)));
                startActivity(intent);
            }
        });
    }

}
