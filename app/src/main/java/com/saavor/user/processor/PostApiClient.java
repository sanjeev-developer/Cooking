package com.saavor.user.processor;

import android.util.Log;


import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by inderbagga on 26/09/16.
 */
public class PostApiClient {


    OnResultReceived mOnResultReceived;
    RequestSource from;
    OkHttpClient mOkHttpClient = new OkHttpClient();;

    public PostApiClient(OnResultReceived mOnResultReceived) {

        this.mOnResultReceived=mOnResultReceived;
    }

    public void setRequestSource(RequestSource from) {
        this.from = from;
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public void  executePostRequest(String strApiUrl,String jsonString){

        RequestBody body = RequestBody.create(JSON, jsonString);
        Log.e("jsonString",jsonString);
        Request request = new Request.Builder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .url(strApiUrl)
                .post(body)
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
                catch (IOException ioe)
                {

                    try
                    {
                        ioe.printStackTrace();
                        Log.e("result ", ioe.getMessage());
                        mOnResultReceived.dispatchString(from,"-2");
                    }
                    catch (Exception e)
                    {
                        System.out.println("server not responding");
                    }

                }
                catch (Exception e){

                    try
                    {
                        e.printStackTrace();
                        Log.e("result ",e.getMessage());mOnResultReceived.dispatchString(from,"-3");
                    }
                    catch (Exception e1)
                    {
                        System.out.println("server not responding");
                    }

                }
            }
        });
    }

    private String printException(String message) {

       return message;//oGson.toJson(new HandleException(Text.getExceptionMessage(message)));
    }
}