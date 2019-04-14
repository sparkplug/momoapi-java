package ug.sparkpl.momoapi.network.collections;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.joda.time.DateTime;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ug.sparkpl.momoapi.Utils.DateTimeTypeConverter;
import ug.sparkpl.momoapi.network.RequestOptions;

import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class CollectionsAuthorizationInterceptor implements Interceptor {

    private CollectionsApiService apiService;
    private CollectionSession session;
    private RequestOptions opts;

    public CollectionsAuthorizationInterceptor(CollectionSession session, RequestOptions opts) {
        this.apiService = apiService;
        this.session = session;

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
                .create();


        final OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();

        // Only log in debug mode to avoid leaking sensitive information.


        okhttpbuilder.connectTimeout(30, TimeUnit.SECONDS);
        okhttpbuilder.readTimeout(30, TimeUnit.SECONDS);
        okhttpbuilder.writeTimeout(30, TimeUnit.SECONDS);


        OkHttpClient httpClient = okhttpbuilder
                .build();


        Retrofit retrofitClient = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(this.opts.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        this.apiService = retrofitClient.create(CollectionsApiService.class);

    }

    /**
     * this method is API implemetation specific
     * might not work with other APIs
     **/
    public String getAuthorizationHeader(String email, String password) {
        String credential = email + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(credential.getBytes());
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response mainResponse = chain.proceed(chain.request());
        Request mainRequest = chain.request();

        if (session.isLoggedIn()) {
            // if response code is 401 or 403, 'mainRequest' has encountered authentication error
            if (mainResponse.code() == 401 || mainResponse.code() == 403) {
                String authKey = getAuthorizationHeader(session.getEmail(), session.getPassword());
                // request to login API to get fresh token
                // synchronously calling login API
                retrofit2.Response<Authorization> loginResponse = apiService.loginAccount(authKey).execute();

                if (loginResponse.isSuccessful()) {
                    // login request succeed, new token generated
                    Authorization authorization = loginResponse.body();
                    // save the new token
                    session.saveToken(authorization.getToken());
                    // retry the 'mainRequest' which encountered an authentication error
                    // add new token into 'mainRequest' header and request again
                    Request.Builder builder = mainRequest.newBuilder().header("Authorization", session.getToken()).
                            method(mainRequest.method(), mainRequest.body());
                    mainResponse = chain.proceed(builder.build());
                }
            }
        }

        return mainResponse;
    }
}
