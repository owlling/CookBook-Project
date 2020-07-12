package com.owlling.cookbook.model.net;

import com.owlling.cookbook.constants.Constants;
import com.owlling.cookbook.utils.PrefUtil;

import java.io.IOException;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static final String TAG = RetrofitService.class.getSimpleName();

    private static RetrofitService Instance = null;

    public static RetrofitService getInstance() {
        if (Instance == null)
            Instance = new RetrofitService();
        return Instance;
    }

    private Retrofit retrofit;

    private RetrofitService() {
        init();
    }

    private void init() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(null);
        builder.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("uid", PrefUtil.getUid());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient okHttpClientMultiUrl = RetrofitUrlManager.getInstance().with(builder.addInterceptor(logging)).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClientMultiUrl)
                .build();
    }

    public <T> T createApi(Class<T> clazz) {
        return retrofit.create(clazz);
    }

}
