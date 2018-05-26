package com.chilingaryan.expandablerecyclerview.network;

import android.Manifest;
import android.support.annotation.RequiresPermission;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String TAG = RetrofitClient.class.getSimpleName();
    private static String baseUrl = "https://api.themoviedb.org/3/";
    private static volatile Retrofit sRetrofit = null;
    private static RetrofitService retrofitService;

    public RetrofitClient() {
    }

    public static RetrofitService getApiService() {
        return initRetrofitService();
    }

    private static RetrofitService initRetrofitService() {
        if (retrofitService == null) {
            synchronized (RetrofitClient.class) {
                if (retrofitService == null) {
                    retrofitService = getRetrofit().create(RetrofitService.class);
                }
            }
        }
        return retrofitService;
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    private synchronized static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            synchronized (RetrofitClient.class) {
                if (sRetrofit == null) {
                    sRetrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl)
//                            .client(createClient())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return sRetrofit;
    }

//    private static OkHttpClient createClient() {
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//            @Override
//            public void log(String message) {
//                if (message.startsWith("{")) {
//                    int spacesToIndentEachLevel = 2;
//                    try {
//                        Timber.d(new JSONObject(message).toString(spacesToIndentEachLevel));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Timber.d(message);
//                }
//
//            }
//        });
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        return new Builder()
////                .cache(new Cache(new File(AndroidApplication.getContext().getCacheDir(), "http"), 1024 * 1024 * 10))
//                .readTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .addInterceptor(new HeaderInterceptor())
//                .addInterceptor(httpLoggingInterceptor)
//                .build();
//    }

//    private static class HeaderInterceptor implements Interceptor {
//
//        @Override
//        public Response intercept(@NonNull Chain chain) throws IOException {
//            Request original = chain.request();
//            Request.Builder requestBuilder = original.newBuilder();
////                    .header("Accept", "application/json")
////                    .header("Authorization", auth.token_type + " " + auth.access_token) //change these to match your own Auth model
//            Request request = requestBuilder.build();
//            return chain.proceed(request);
//
//        }
//    }


}
