package com.saavor.user.processor;

import android.util.Log;


import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class GetApiClient {

    OnResultReceived mOnResultReceived;
    RequestSource from;
    OkHttpClient mOkHttpClient = new OkHttpClient();

    public GetApiClient(OnResultReceived mOnResultReceived) {

        this.mOnResultReceived=mOnResultReceived;
    }

    public void setRequestSource(RequestSource from) {
        this.from = from;
    }

    public void  executeGetRequest(String url){

        Log.e("URL",url);

        Request request = new Request.Builder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(final Call call, IOException e) {

                mOnResultReceived.dispatchString(from,printException("-2"));
            }

            @Override
            public void onResponse(Call call, final Response response) {

                try{
                    mOnResultReceived.dispatchString(from,response.body().string());
                }
                catch (IOException ioe){ mOnResultReceived.dispatchString(from,printException("-2"));}
                catch (Exception e){ mOnResultReceived.dispatchString(from,printException("-3"));}
            }
        });
    }

    private String printException(String message) {

       return message;
    }
}