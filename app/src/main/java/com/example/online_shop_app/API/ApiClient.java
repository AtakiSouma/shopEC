package com.example.online_shop_app.API;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static String baseURL = "https://1206-118-69-70-166.ngrok-free.app/api/v1/";
//    private  static String baseLocalUrl = "http://192.168.1.6:8080/api/v1/";
    private  static String baseLocalUrl = "http://192.168.43.159:8080/api/v1/";
    private static Retrofit retrofit;
    private ApiClient() {
        // Private constructor to prevent instantiation
    }

    public static Retrofit getClient(Context context, boolean useLocal) {
        if (retrofit == null) {
            retrofit = createRetrofit(context,useLocal ?  baseLocalUrl : baseURL);
        }
        return retrofit;
    }
    private static Retrofit createRetrofit(Context context, String baseUrl) {
        OkHttpClient okHttpClient = createOkHttpClient(context);
        Gson gson = createGson();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }
    private static OkHttpClient createOkHttpClient(Context context) {
        return new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(createAuthorizationInterceptor(context))
                .addInterceptor(createLoggingInterceptor())
                .build();
    }
    private static Interceptor createAuthorizationInterceptor(Context context) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                SharedPreferences sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
                String accessToken = sharedPreferences.getString("accessToken", "");

                Request request = chain.request();
                Request.Builder builder = request.newBuilder();
                if (!accessToken.isEmpty()) {
                    builder.addHeader("Authorization", "Bearer " + accessToken);
                }
                return chain.proceed(builder.build());
            }
        };
    }
    private static HttpLoggingInterceptor createLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    private static Gson createGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }
//    public static Retrofit getClient() {
//        Interceptor interceptor = chain -> {
//            Request request = chain.request();
//            Request.Builder builder = request.newBuilder();
//            builder.addHeader("Authorization" , "");
//            return chain.proceed(builder.build());
//        };
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
//                .readTimeout(30, TimeUnit.SECONDS)
//                .connectTimeout(30,TimeUnit.SECONDS)
//                .retryOnConnectionFailure(true)
//                .addInterceptor(interceptor)
//                .addInterceptor(loggingInterceptor);
//
//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder().baseUrl(baseLocalUrl)
//                    .addConverterFactory(GsonConverterFactory.create(gson)).client(okBuilder.build()).build();
//        }
//        return retrofit;
//    }
}
