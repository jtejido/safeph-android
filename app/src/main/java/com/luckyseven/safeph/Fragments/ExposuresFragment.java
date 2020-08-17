package com.luckyseven.safeph.Fragments;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import org.jetbrains.annotations.NotNull;

import com.luckyseven.safeph.Activities.MainActivity;
import com.luckyseven.safeph.Activities.PrivacyActivity;
import com.luckyseven.safeph.Adapters.ExposureEntityAdapter.ExposureEntityAdapter;
import com.luckyseven.safeph.Dialogs.ExposureInfoDialog;
import com.luckyseven.safeph.Models.ExposureSummaryCollection;
import com.luckyseven.safeph.Models.ExposureSummaryModel;
import com.luckyseven.safeph.R;
import com.luckyseven.safeph.Utilities.LanguageUtil;
import com.luckyseven.safeph.databinding.FragmentExposureHomeBinding;

public class ExposuresFragment extends Fragment implements MainActivity.IExposureStatusChanged {
    private Context context;
    private FragmentExposureHomeBinding binding;
    private MainActivity activity;

    public ExposuresFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExposureHomeBinding.inflate(getLayoutInflater());
        context = getContext();
        if (getActivity() instanceof MainActivity) {
            activity = (MainActivity) getActivity();
        }
        binding.switchOnOff.setOnCheckedChangeListener(switchOnOfChangeListener);
        binding.tvInfo.setOnClickListener(onInfoClickListener);
        setup();
        setupLocale();
        return binding.getRoot();
    }

    private View.OnClickListener onInfoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, PrivacyActivity.class);
            startActivity(intent);
        }
    };

    private CompoundButton.OnCheckedChangeListener switchOnOfChangeListener = (compoundButton, b) -> {
        if (compoundButton.isChecked()) {
            setContactsView();
        } else {
            setNoContactsView();
        }
    };

    private void setup() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        binding.rvExposureEntities.setLayoutManager(gridLayoutManager);
        ExposureEntityAdapter exposureEntityAdapter = new ExposureEntityAdapter(context);
        binding.rvExposureEntities.setAdapter(exposureEntityAdapter);
        binding.btnSwitch.setOnClickListener(v -> toggleExposure());
        activity.setExposureListener(this);
        setupRiskLevel();
    }

    private void setupRiskLevel() {
        ExposureSummaryCollection.getInstance().loadCollection(() -> {
            ExposureSummaryModel exposureSummary = ExposureSummaryCollection.getInstance().getRiskiestSummaryModel();
            if (exposureSummary != null && exposureSummary.matchedKeyCount > 0 && exposureSummary.isRisky()) {
                binding.llWarning.setVisibility(View.VISIBLE);
                binding.tvMessage.setVisibility(View.GONE);
                if (exposureSummary.isHighRisk()) {
                    setupHighRisk();
                } else if (exposureSummary.isMediumRisk()) {
                    setupMediumRisk();
                } else if (exposureSummary.isLowRisk()) {
                    setupLowRisk();
                }
                binding.llWarning.setOnClickListener(v -> ExposureInfoDialog.start(context, exposureSummary));
            } else {
                binding.tvMessage.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupHighRisk() {
        binding.ivWarning.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_high_risk2));
        binding.tvHighRisk.setText(R.string.high_risk);
        binding.tvHighRisk.setTextColor(ContextCompat.getColor(context, R.color.red_color));
    }

    private void setupMediumRisk() {
        binding.ivWarning.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_risk2));
        binding.tvHighRisk.setText(R.string.medium_risk);
        binding.tvHighRisk.setTextColor(ContextCompat.getColor(context, R.color.yellow_text));
    }

    private void setupLowRisk() {
        binding.ivWarning.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_risk2));
        binding.tvHighRisk.setText(R.string.low_risk);
        binding.tvHighRisk.setTextColor(ContextCompat.getColor(context, R.color.yellow_text));
    }

    private void setupLocale() {
        View.OnClickListener onLanguageClicked;
        if (LanguageUtil.getLanguage().equalsIgnoreCase(getString(R.string.tagalog_language_short))) {
            binding.ivLocale.setImageResource(R.drawable.icon_ph);
            binding.tvLocale.setText(getString(R.string.tagalog_language_short));
            onLanguageClicked = v -> setLanguage(getString(R.string.english_language_short));
        } else {
            binding.ivLocale.setImageResource(R.drawable.icon_en);
            binding.tvLocale.setText(getString(R.string.english_language_short));
            onLanguageClicked = v -> setLanguage(getString(R.string.tagalog_language_short));
        }
        binding.llLocale.setOnClickListener(onLanguageClicked);
    }

    private void setLanguage(String language) {
        LanguageUtil.setLanguage(getActivity(), language);
        requireActivity().recreate();
    }

    private void setNoContactsView() {
        binding.ivContacts.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_nocontacts));
        binding.rlSwitch.setBackground(ContextCompat.getDrawable(context, R.drawable.button_grey));
        binding.tvOn.setTextColor(ContextCompat.getColor(context, R.color.grey_text_color));
        binding.tvAnonymous.setText(R.string.no_contacts);
        binding.tvOn.setText(R.string.off);
    }

    private void setContactsView() {
        binding.ivContacts.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_contacts));
        binding.rlSwitch.setBackground(ContextCompat.getDrawable(context, R.drawable.button_blue));
        binding.tvAnonymous.setText(R.string.anonymous);
        binding.tvOn.setTextColor(ContextCompat.getColor(context, R.color.white));
        binding.tvOn.setText(R.string.off);
    }

    private void toggleExposure() {
        if (binding.switchOnOff.isChecked()) {
            stopExposure();
        } else {
            startExposure();
        }
    }

    private void startExposure() {
        if (activity != null) {
            activity.startExposureNotifications();
        }
    }

    private void stopExposure() {
        if (activity != null) {
            activity.stopExposureNotifications();
        }
    }

    @Override
    public void onExposureStatusChanged(boolean exposureEnabled) {
        binding.switchOnOff.setChecked(exposureEnabled);
        binding.tvOn.setText(exposureEnabled ? getString(R.string.on) : getString(R.string.off));
        binding.tvMessage.setText(exposureEnabled ? getString(R.string.on_message) : getString(R.string.off_message));
        binding.rlSwitch.setBackground(ContextCompat.getDrawable(context, exposureEnabled ? R.drawable.button_blue : R.drawable.button_grey));
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.checkExposureEnabled();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive (Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                onExposureStatusChanged(intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1) != BluetoothAdapter.STATE_OFF);
            }
        }
    };
}
