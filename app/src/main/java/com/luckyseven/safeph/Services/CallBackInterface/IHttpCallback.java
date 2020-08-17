package com.luckyseven.safeph.Services.CallBackInterface;


import com.luckyseven.safeph.ErrorHandling.CustomError;

public interface IHttpCallback {
    void onSuccess(Object string);
    void onError(CustomError error);
}
