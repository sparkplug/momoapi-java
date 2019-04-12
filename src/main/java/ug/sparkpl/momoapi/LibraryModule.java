package ug.sparkpl.momoapi;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
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
import ug.sparkpl.momoapi.Utils.ApiRetrofit;
import ug.sparkpl.momoapi.Utils.DateTimeTypeConverter;
import ug.sparkpl.momoapi.models.CurrentUserType;
import ug.sparkpl.momoapi.network.ApiRequestInterceptor;
import ug.sparkpl.momoapi.network.collections.CollectionsApiService;
import ug.sparkpl.momoapi.network.collections.CollectionsClient;

import javax.inject.Singleton;

@Module
class LibraryModule {


    @Provides
    @Singleton
    Scheduler provideScheduler() {
        return Schedulers.computation();
    }

    @Provides
    @Singleton
    @NonNull
    CollectionsClient provideCollectionsClient(final @NonNull CollectionsApiService apiService, final @NonNull Gson gson, final @NonNull String clientId) {
        return new CollectionsClient(apiService, gson);
    }


    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
                .create();
    }


    private @NonNull
    Retrofit createRetrofit(final @NonNull HttpUrl apiEndpoint, final @NonNull Gson gson, final @NonNull OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(apiEndpoint)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


    }

    @Provides
    @Singleton
    @NonNull
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }


    @Provides
    @Singleton
    @NonNull
    CollectionsApiService provideCollectionsApiService(final @ApiRetrofit @NonNull Retrofit apiRetrofit) {
        return apiRetrofit.create(CollectionsApiService.class);
    }


    @Provides
    @Singleton
    @ApiRetrofit
    @NonNull
    Retrofit provideApiRetrofit(final @NonNull HttpUrl apiEndpoint,
                                final @NonNull Gson gson,
                                final @NonNull OkHttpClient okHttpClient) {
        return createRetrofit(apiEndpoint, gson, okHttpClient);
    }

    @Provides
    @Singleton
    @NonNull
    ApiRequestInterceptor provideApiRequestInterceptor(final @NonNull String clientId,
                                                       final @NonNull CurrentUserType currentUser, final @NonNull HttpUrl apiEndpoint) {
        return new ApiRequestInterceptor(clientId, currentUser, apiEndpoint.toString());
    }


    @Provides
    @Singleton
    @NonNull
    OkHttpClient provideOkHttpClient(final @NonNull ApiRequestInterceptor apiRequestInterceptor,
                                     final @NonNull HttpLoggingInterceptor httpLoggingInterceptor) {

        final OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // Only log in debug mode to avoid leaking sensitive information.

        builder.addInterceptor(httpLoggingInterceptor);


        return builder
                .addInterceptor(apiRequestInterceptor)
                .build();
    }
}