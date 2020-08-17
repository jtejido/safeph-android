package com.luckyseven.safeph.Services.CallBackInterface;


import com.luckyseven.safeph.ErrorHandling.CustomError;

public interface IResponseCallback<T> {
    void onSuccess(T response);
    void onError(CustomError exception);
}
