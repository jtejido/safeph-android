package com.luckyseven.safeph.Utilities.Enums;

import com.luckyseven.safeph.ExposureNotificationApp;
import com.luckyseven.safeph.R;

public enum TransmissionRiskEnum {
    LowRisk(50),
    MediumRisk(501),
    HighRisk(769);

    int value;

    TransmissionRiskEnum(int i) {
        this.value = i;
    }

    public static String getRiskTitle(int riskLevel) {
        if (riskLevel < MediumRisk.value) {
            return ExposureNotificationApp.getStr(R.string.low_risk);
        }
        if (riskLevel < HighRisk.value) {
            return ExposureNotificationApp.getStr(R.string.medium_risk);
        }
        return ExposureNotificationApp.getStr(R.string.high_risk);
    }

    public static String getRiskShortDescription(int riskLevel) {
        if (riskLevel < MediumRisk.value) {
            return ExposureNotificationApp.getStr(R.string.low_risk_short);
        }
        if (riskLevel < HighRisk.value) {
            return ExposureNotificationApp.getStr(R.string.medium_risk_short);
        }
        return ExposureNotificationApp.getStr(R.string.high_risk_short);
    }

    public static String getRiskLongDescription(int riskLevel) {
        if (isHighRisk(riskLevel)) {
            return ExposureNotificationApp.getStr(R.string.low_risk_long);
        }
        if (isMediumRisk(riskLevel)) {
            return ExposureNotificationApp.getStr(R.string.medium_risk_long);
        }
        return ExposureNotificationApp.getStr(R.string.high_risk_long);
    }

    public static boolean isHighRisk(int value) {
        return value >= HighRisk.value;
    }

    public static boolean isMediumRisk(int value) {
        return value >= MediumRisk.value && value < HighRisk.value;
    }

    public static boolean isLowRisk(int value) {
        return value >= LowRisk.value && value < MediumRisk.value;
    }

    public static boolean isRisky(int value) {
        return value >= LowRisk.value;
    }
}
