package com.luckyseven.safeph.Services;

import com.luckyseven.safeph.ErrorHandling.CustomError;
import com.luckyseven.safeph.Models.ExposureUrlModel;
import com.luckyseven.safeph.Utilities.HttpUtilities;
import com.luckyseven.safeph.Models.DiagnosisModel;
import com.luckyseven.safeph.Services.CallBackInterface.IHttpCallback;
import com.luckyseven.safeph.Services.CallBackInterface.IResponseCallback;
import com.luckyseven.safeph.Services.Client.ApiClient;
import com.luckyseven.safeph.Services.Interface.IUserService;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class UserService {
    public static void diagnosisKeys(String authCode, DiagnosisModel input, final IResponseCallback callback) {
        IUserService iservice = ApiClient.getClient().create(IUserService.class);

        final Call<ResponseBody> call = iservice.diagnosisKeys(authCode, input);

        HttpUtilities httpUtilities = new HttpUtilities();
        httpUtilities.post(call, new IHttpCallback() {
            @Override
            public void onSuccess(Object object) {
                callback.onSuccess(object);
            }

            @Override
            public void onError(CustomError error) {
                callback.onError(error);
            }
        });
    }

    public static void getExposure(String url, final IResponseCallback callback) {
        IUserService iservice = ApiClient.getClient().create(IUserService.class);

        final Call<ResponseBody> call = iservice.getExposure(url);

        HttpUtilities httpUtilities = new HttpUtilities();
        httpUtilities.post(call, new IHttpCallback() {
            @Override
            public void onSuccess(Object object) {
                callback.onSuccess(object);
            }

            @Override
            public void onError(CustomError error) {

                callback.onError(error);
            }
        });
    }

    public static void getUrls(final IResponseCallback callback) {
        IUserService iservice = ApiClient.getClient().create(IUserService.class);

        final Call<ExposureUrlModel> call = iservice.getUrls();

        HttpUtilities httpUtilities = new HttpUtilities();
        httpUtilities.post(call, new IHttpCallback() {
            @Override
            public void onSuccess(Object object) {
                callback.onSuccess(object);
            }

            @Override
            public void onError(CustomError error) {
                callback.onError(error);
            }
        });
    }

    public static void checkGooglePlay(String url, final IResponseCallback callback) {
        IUserService iservice = ApiClient.getClient().create(IUserService.class);

        final Call<ResponseBody> call = iservice.checkGooglePlay(url);

        HttpUtilities httpUtilities = new HttpUtilities();
        httpUtilities.post(call, new IHttpCallback() {
            @Override
            public void onSuccess(Object object) {
                callback.onSuccess(object);
            }

            @Override
            public void onError(CustomError error) {
                callback.onError(error);
            }
        });
    }
}
