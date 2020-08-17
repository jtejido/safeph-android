/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.luckyseven.safeph.Utilities.ExposureNotifications;

import android.content.Context;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.exposurenotification.ExposureConfiguration;
import com.google.android.gms.nearby.exposurenotification.ExposureInformation;
import com.google.android.gms.nearby.exposurenotification.ExposureNotificationClient;
import com.google.android.gms.nearby.exposurenotification.ExposureSummary;
import com.google.android.gms.nearby.exposurenotification.TemporaryExposureKey;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.common.collect.Lists;

import java.io.File;
import java.util.List;

public class ExposureNotificationClientWrapper {

  private static ExposureNotificationClientWrapper INSTANCE;

  final ExposureNotificationClient exposureNotificationClient;
  private final ExposureConfiguration config;

  public static final String FAKE_TOKEN_1 = "FAKE_TOKEN_1";
  public static final String FAKE_TOKEN_2 = "FAKE_TOKEN_2";
  public static final String FAKE_TOKEN_3 = "FAKE_TOKEN_3";

  public static ExposureNotificationClientWrapper get(Context context) {
    if (INSTANCE == null) {
      INSTANCE = new ExposureNotificationClientWrapper(context);
    }
    return INSTANCE;
  }

  public ExposureNotificationClientWrapper(Context context) {
    exposureNotificationClient = Nearby.getExposureNotificationClient(context);
    config = ExposureConfigurations.get();
  }

  public Task<Void> start() {
    return exposureNotificationClient.start();
  }

  public Task<Void> stop() {
    return exposureNotificationClient.stop();
  }

  public Task<List<TemporaryExposureKey>> getTemporaryExposureKeyHistory() {
    return exposureNotificationClient.getTemporaryExposureKeyHistory();
  }

  /**
   * Provides diagnosis key files with a stable token and {@link ExposureConfiguration} given by
   * {@link ExposureConfigurations}.
   */
  public Task<Void> provideDiagnosisKeys(List<File> files, String token) {
    return exposureNotificationClient.provideDiagnosisKeys(files, config, token);
  }

  /**
   * Gets the {@link ExposureSummary} using the stable token.
   *
   * <p>If the token matches the fake tokens, it will return fake results.
   */
  public Task<ExposureSummary> getExposureSummary(String token) {
    // Check for fake matches.
    if (FAKE_TOKEN_1.equals(token)) {
      return Tasks.forResult(
          new ExposureSummary.ExposureSummaryBuilder()
              .setMatchedKeyCount(2)
              .setDaysSinceLastExposure(1)
              .build());
    } else if (FAKE_TOKEN_2.equals(token)) {
      return Tasks.forResult(
          new ExposureSummary.ExposureSummaryBuilder()
              .setMatchedKeyCount(1)
              .setDaysSinceLastExposure(2)
              .build());
    } else if (FAKE_TOKEN_3.equals(token)) {
      return Tasks.forResult(
          new ExposureSummary.ExposureSummaryBuilder()
              .setMatchedKeyCount(0)
              .setDaysSinceLastExposure(3)
              .build());
    }
    // Otherwise return the real API.
    return exposureNotificationClient.getExposureSummary(token);
  }

  /**
   * Gets the {@link List} of {@link ExposureInformation} using the stable token.
   *
   * <p>If the token matches the fake tokens, it will return fake results.
   */
  public Task<List<ExposureInformation>> getExposureInformation(String token) {
    if (FAKE_TOKEN_1.equals(token)) {
      long millisInDay = 24L * 60L * 60L * 1000L;
      return Tasks.forResult(
          Lists.newArrayList(
              new ExposureInformation.ExposureInformationBuilder()
                  .setAttenuationValue(1)
                  .setDateMillisSinceEpoch(
                      millisInDay * (System.currentTimeMillis() / millisInDay))
                  .setDurationMinutes(5)
                  .build(),
              new ExposureInformation.ExposureInformationBuilder()
                  .setAttenuationValue(1)
                  .setDateMillisSinceEpoch(1588377600000L)
                  .setDurationMinutes(10)
                  .build()));
    } else if (FAKE_TOKEN_2.equals(token)) {
      return Tasks.forResult(
          Lists.newArrayList(
              new ExposureInformation.ExposureInformationBuilder()
                  .setAttenuationValue(1)
                  .setDateMillisSinceEpoch(1588636800000L)
                  .setDurationMinutes(5)
                  .build()));
    }
    return exposureNotificationClient.getExposureInformation(token);
  }
}
