package com.tiha.anphatsu.data.network.apiretrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitTest {
    static OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(chain -> {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }).build();

    static Gson gson = new GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();

    private static final Retrofit.Builder builder
            = new Retrofit.Builder()
            .baseUrl("https://gasanphat.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson));

    private static final Retrofit retrofit = builder.build();

    public static MultipartBody getMultipartBody(Map<String, String> keySet) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String str : keySet.keySet()) {
            builder.addFormDataPart(str, keySet.get(str));
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

    public static MultipartBody getMultipartFileBody(Map<String, String> key, Map<String, File> keySet) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String str : key.keySet()) {
            builder.addFormDataPart(str, key.get(str));
        }
        for (String str : keySet.keySet()) {
            builder.addFormDataPart(str, keySet.get(str).getName(), RequestBody.create(MediaType.parse("*/*"), keySet.get(str)));
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
