package ug.sparkpl.momoapi.network;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.NonNull;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.joda.time.DateTime;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.schedulers.Schedulers;
import ug.sparkpl.momoapi.Utils.DateTimeTypeConverter;
import ug.sparkpl.momoapi.network.collections.CollectionsApiService;


class BaseClient {

    BaseClient() {

    }


    Scheduler getScheduler() {
        return Schedulers.computation();
    }


    Gson getGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
                .create();
    }


    Retrofit createRetrofit(final @NonNull HttpUrl apiEndpoint, final @NonNull Gson gson, final @NonNull OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(apiEndpoint)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    HttpLoggingInterceptor getHttpLoggingInterceptor() {
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }


    CollectionsApiService provideCollectionsApiService(final @NonNull Retrofit apiRetrofit) {
        return apiRetrofit.create(CollectionsApiService.class);
    }


    Retrofit getApiRetrofit(final @NonNull HttpUrl apiEndpoint,
                            final @NonNull Gson gson,
                            final @NonNull OkHttpClient okHttpClient) {
        return createRetrofit(apiEndpoint, gson, okHttpClient);
    }


}