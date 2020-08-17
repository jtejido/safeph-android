package com.luckyseven.safeph.Models;

import com.google.common.io.BaseEncoding;
import com.luckyseven.safeph.R;

import com.luckyseven.safeph.ExposureNotificationApp;
import com.luckyseven.safeph.Utilities.Util;

public class TemporaryExposureKey {
    public String key;
    public Long rollingStartNumber;
    public Integer rollingPeriod;
    public Integer transmissionRisk;

    public TemporaryExposureKey(String key, Long rollingStartNumber, Integer rollingPeriod, Integer transmissionRisk) {
        this.key = key;
        this.rollingStartNumber = rollingStartNumber;
        this.rollingPeriod = rollingPeriod;
        this.transmissionRisk = transmissionRisk;
    }

    public TemporaryExposureKey(com.google.android.gms.nearby.exposurenotification.TemporaryExposureKey temporaryExposureKey) {
        this.key = BaseEncoding.base64().encode(temporaryExposureKey.getKeyData());
        this.rollingStartNumber = (long) temporaryExposureKey.getRollingStartIntervalNumber();
        this.rollingPeriod = temporaryExposureKey.getRollingPeriod();
        this.transmissionRisk = temporaryExposureKey.getTransmissionRiskLevel();
    }

    public String getDateString() {
        return Util.dayDateFormat(rollingStartNumber * 600000);
    }

    public String getExposureAge() {
        int days = (int) ((System.currentTimeMillis() - (rollingStartNumber * 600000)) / 86400000);
        return days + ((days % 10 == 1) ? ExposureNotificationApp.getStr(R.string.day) : ExposureNotificationApp.getStr(R.string.days));
    }
}
