package com.sreeyainfotech.paginationwithrecyclerview.networkcall;

import android.content.Context;

import com.sreeyainfotech.paginationwithrecyclerview.Utilities;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by KSTL on 24-04-2017.
 */

public class ConnectivityInterceptor implements Interceptor {

    private Context mContext;

    public ConnectivityInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!Utilities.isNetworkAvailable(mContext)) {
            throw new NoConnectivityException();
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }

    private class NoConnectivityException extends IOException
    {
        @Override
        public String getMessage() {
            return "Please check network connectivity.";
        }
    }
}
