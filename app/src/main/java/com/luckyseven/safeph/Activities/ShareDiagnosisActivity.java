package com.luckyseven.safeph.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.nearby.exposurenotification.TemporaryExposureKey;

import java.util.Calendar;
import java.util.List;

import com.luckyseven.safeph.ExposureNotificationApp;
import com.luckyseven.safeph.ErrorHandling.CustomError;
import com.luckyseven.safeph.ErrorHandling.ErrorHandling;
import com.luckyseven.safeph.Listeners.IExposureListener;
import com.luckyseven.safeph.Models.DiagnosisModel;
import com.luckyseven.safeph.Models.PositiveTestManager;
import com.luckyseven.safeph.R;
import com.luckyseven.safeph.Services.CallBackInterface.IResponseCallback;
import com.luckyseven.safeph.Services.UserService;
import com.luckyseven.safeph.Utilities.ExposureNotifications.ExposureNotificationWrapper;
import com.luckyseven.safeph.Utilities.Util;
import com.luckyseven.safeph.databinding.FragmentShareDiagnosisEditBinding;

public class ShareDiagnosisActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, IExposureListener {
    private static final int CODE_LENGTH = 8;
    private FragmentShareDiagnosisEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentShareDiagnosisEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.shareNextButton.setOnClickListener(view -> ExposureNotificationWrapper.get().requestTokenHistory(this));
        binding.home.setOnClickListener(view -> onBackPressed());
        binding.btnDate.setOnClickListener(v -> startDatePicker());
        binding.shareTestIdentifier.addTextChangedListener(shareTestIdentifierTextWatcher);
        binding.mainContainer.setOnClickListener(v -> Util.hideKeyboard(binding.tvShareId,this));
    }

    private void startDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void shareExposureInfo(DiagnosisModel diagnosisModel) {
        ProgressBarDialog.showDialog(getSupportFragmentManager(), "");
        UserService.diagnosisKeys(binding.shareTestIdentifier.getText().toString().trim(), diagnosisModel, new IResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                ProgressBarDialog.dismissDialog();
                PositiveTestManager.get().add();
                Toast.makeText(ExposureNotificationApp.getInstance(), R.string.exposure_info_upload_successful, Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

            @Override
            public void onError(CustomError exception) {
                ProgressBarDialog.dismissDialog();
                ErrorHandling.handlingError(ShareDiagnosisActivity.this, exception, () -> shareExposureInfo(diagnosisModel));
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        binding.shareTestDate.setText(Util.dayDateFormat(year, month, dayOfMonth));
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onApiException(ApiException apiException, int requestCode) {
        try {
            apiException.getStatus().startResolutionForResult(this, requestCode);
        } catch (IntentSender.SendIntentException e) {
            Toast.makeText(ExposureNotificationApp.getInstance(), getString(R.string.internal_api_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onExposureKeysRetrieved(List<TemporaryExposureKey> keys) {
        // DiagnosisModel test = DiagnosisModel.getTestModel();
        // DiagnosisModel diagnosisModel = test;
        DiagnosisModel diagnosisModel = new DiagnosisModel(keys);
        shareExposureInfo(diagnosisModel);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ExposureNotificationWrapper.REQUEST_CODE_GET_TEMP_EXPOSURE_KEY_HISTORY && resultCode == Activity.RESULT_OK) {
            ExposureNotificationWrapper.get().requestTokenHistory(this);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    TextWatcher shareTestIdentifierTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.toString().length() == CODE_LENGTH) {
                setBtnEnabling(binding.shareNextButton, 1, true);
            } else {
                setBtnEnabling(binding.shareNextButton, .5f, false);
            }
        }
    };

    private void setBtnEnabling(View view, float alpha, boolean isEnabled) {
        view.setAlpha(alpha);
        view.setEnabled(isEnabled);
        view.setClickable(isEnabled);
    }
}
