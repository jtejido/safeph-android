package com.luckyseven.safeph.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.luckyseven.safeph.R;
import com.luckyseven.safeph.Utilities.LanguageUtil;
import com.luckyseven.safeph.databinding.ActivityUpdateBinding;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class UpdateActivity extends AppCompatActivity {
    private ActivityUpdateBinding binding;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.tvUpgrade.setOnClickListener(this::openGooglePlay);
        binding.ivLogo.setImageDrawable(getDrawable(R.drawable.doh));
    }

    public void openGooglePlay(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setData(Uri.parse(getString(R.string.google_play_link)));
        startActivity(intent);
        finish();
    }
}
