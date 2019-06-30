package com.aritra.mobioticspoc.network;

import android.content.Context;
import android.text.TextUtils;

import com.aritra.mobioticspoc.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MasterAPIClient {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder builder;
    private static Retrofit retrofit;
    //private static String authToken;

    private MasterAPIClient() {
        //Private Constructor to avoid initialization
    }

    public static <S> S createService(
            Class<S> serviceClass, final Context context) {
        setup(context);
        return retrofit.create(serviceClass);
    }

    private static Gson getGson() {
        return new GsonBuilder().setLenient().excludeFieldsWithoutExposeAnnotation().create();
    }

    private static void setup(Context context) {
        String API_BASE_URL = context.getString(R.string.SERVER_URL);
        if (builder == null) {
            builder =
                    new Retrofit.Builder()
                            .baseUrl(API_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(getGson()));
        }
        if (retrofit == null) {
            retrofit = builder.build();
        }
    }
}

