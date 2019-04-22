package ug.sparkpl.momoapi.network.remittances;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import org.joda.time.DateTime;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ug.sparkpl.momoapi.Utils.DateTimeTypeConverter;
import ug.sparkpl.momoapi.models.AccessToken;
import ug.sparkpl.momoapi.network.ApiException;
import ug.sparkpl.momoapi.network.RequestOptions;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemittancesAuthorizationInterceptor implements Interceptor {
    Logger logger;
    private RemittancesApiService apiService;
    private RemittancesSession session;
    private RequestOptions opts;

    public RemittancesAuthorizationInterceptor(RemittancesSession session, RequestOptions opts) {

        this.session = session;
        this.opts = opts;
        this.logger = Logger.getLogger(RemittancesAuthorizationInterceptor.class.getName());

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
                .create();


        final OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();

        // Only log in debug mode to avoid leaking sensitive information.
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        okhttpbuilder.addInterceptor(httpLoggingInterceptor);


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

        this.apiService = retrofitClient.create(RemittancesApiService.class);

    }


    private Request request(final Request initialRequest) {

        this.logger.log(Level.INFO, "Using token >>>>>>>>>>>>>>>>> " + this.session.getToken());


        return initialRequest.newBuilder()
                //.header("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + this.session.getToken())
                .addHeader("Ocp-Apim-Subscription-Key", this.opts.getRemittancePrimaryKey())
                .addHeader("X-Target-Environment", this.opts.getTargetEnvironment())

                .method(initialRequest.method(), initialRequest.body())
                .build();
    }


    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        okhttp3.Response mainResponse = chain.proceed(request(chain.request()));
        Request mainRequest = chain.request();


        // if response code is 401 or 403, 'mainRequest' has encountered authentication error
        if (mainResponse.code() == 401 || mainResponse.code() == 403) {


            this.logger.log(Level.INFO, "<<<<<<<<<<<<<<<Getting Fresh Token");


            String credentials = Credentials.basic(this.opts.getRemittanceUserId(), this.opts.getRemittanceApiSecret());
            Response<AccessToken> loginResponse = this.apiService
                    .getToken(credentials, this.opts.getRemittancePrimaryKey()).execute();

            if (loginResponse.isSuccessful()) {
                // login request succeed, new token generated
                AccessToken token = loginResponse.body();
                // save the new token
                this.session.saveToken(token.getToken());
                // retry the 'mainRequest' which encountered an authentication error
                // add new token into 'mainRequest' header and request again
                Request.Builder builder = mainRequest.newBuilder().addHeader("Authorization", "Bearer " + this.session.getToken())
                        .addHeader("Ocp-Apim-Subscription-Key", this.opts.getRemittancePrimaryKey())
                        .addHeader("X-Target-Environment", this.opts.getTargetEnvironment()).
                                method(mainRequest.method(), mainRequest.body());
                mainResponse = chain.proceed(builder.build());
            }
        } else if (!mainResponse.isSuccessful()) {

            this.logger.log(Level.INFO, "<<<<<<<<<<<<<<< ETETETET  " + mainResponse.code() + "  .." + mainResponse.body().string());


            throw new ApiException(mainResponse.body().string());


        }


        return mainResponse;
    }
}
