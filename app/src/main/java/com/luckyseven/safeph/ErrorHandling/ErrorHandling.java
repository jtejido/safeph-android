package com.luckyseven.safeph.ErrorHandling;

import android.content.Context;

import com.luckyseven.safeph.Listeners.INoNetworkListener;
import com.luckyseven.safeph.R;
import com.luckyseven.safeph.Utilities.Util;


public class ErrorHandling {
    public static void handlingError(Context context, CustomError customError, INoNetworkListener listener) {

        switch (customError.errorCode) {
            case 400: {
                customError.errorMessage = context.getString(R.string.invalid_call);
                createMessage(context, customError);
            }
            break;

            case 403: {
                if (customError.errorMessage!=null && customError.errorMessage.contains("Code expired")) {
                    customError.errorMessage = context.getString(R.string.expired_code);
                } else {
                    customError.errorMessage = context.getString(R.string.invalid_code);
                }

                createMessage(context, customError);
            }
            break;

            default: {
                createMessage(context, new CustomError(context.getString(R.string.no_service)));
            }
        }
    }

    private static void createMessage(Context context, CustomError customError) {
        if (customError.errorMessage != null) {
            Util.startErrorDialog(customError.errorMessage, context);
        }
    }
}