package ug.sparkpl.momoapi.network.collections;


import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import ug.sparkpl.momoapi.models.AccessToken;
import ug.sparkpl.momoapi.network.MomoApiException;
import ug.sparkpl.momoapi.network.RequestOptions;
import ug.sparkpl.momoapi.utils.DateTimeTypeConverter;

import org.joda.time.DateTime;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CollectionsAuthorizationInterceptor implements Interceptor {

  Logger logger;
  private CollectionsApiService apiService;
  private CollectionSession session;
  private RequestOptions opts;

  /**
   * CollectionsAuthorizationInterceptor.
   *
   * @param session CollectionSession
   * @param opts    RequestOptions
   */
  public CollectionsAuthorizationInterceptor(CollectionSession session, RequestOptions opts) {

    this.session = session;
    this.opts = opts;
    this.logger = Logger.getLogger(CollectionsAuthorizationInterceptor.class.getName());

    final Gson gson = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
        .create();


    final OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();

    // Only log in debug mode to avoid leaking sensitive information.
    final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
    okhttpbuilder.addInterceptor(httpLoggingInterceptor);


    okhttpbuilder.connectTimeout(60, TimeUnit.SECONDS);
    okhttpbuilder.readTimeout(60, TimeUnit.SECONDS);
    okhttpbuilder.writeTimeout(60, TimeUnit.SECONDS);


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
   * request wrapper.
   *
   * @param initialRequest Request
   * @return Request
   */
  private Request request(final Request initialRequest) {

    this.logger.log(Level.INFO, "Using token >>>>>>>>>>>>>>>>> " + this.session.getToken());


    return initialRequest.newBuilder()
        //.header("Accept", "application/json")
        .addHeader("Authorization", "Bearer " + this.session.getToken())
        .addHeader("Ocp-Apim-Subscription-Key", this.opts.getCollectionPrimaryKey())
        .addHeader("X-Target-Environment", this.opts.getTargetEnvironment())

        .method(initialRequest.method(), initialRequest.body())
        .build();
  }


  /**
   * Intercept.
   *
   * @param chain Chain
   * @return Response
   * @throws IOException when there is a network error
   */
  @Override
  public okhttp3.Response intercept(Chain chain) throws IOException {


    okhttp3.Response mainResponse = chain.proceed(request(chain.request()));


    Request mainRequest = chain.request();


    // if response code is 401 or 403, 'mainRequest' has encountered authentication error
    if (mainResponse.code() == 401 || mainResponse.code() == 403) {


      this.logger.log(Level.INFO, "<<<<<<<<<<<<<<<Getting Fresh Token");


      String credentials = Credentials.basic(this.opts.getCollectionUserId(),
          this.opts.getCollectionApiSecret());
      Response<AccessToken> loginResponse = this.apiService
          .getToken(credentials, this.opts.getCollectionPrimaryKey()).execute();

      if (loginResponse.isSuccessful()) {
        // login request succeed, new token generated
        AccessToken token = loginResponse.body();
        // save the new token
        this.session.saveToken(token.getToken());
        // retry the 'mainRequest' which encountered an authentication error
        // add new token into 'mainRequest' header and request again
        Request.Builder builder = mainRequest.newBuilder().addHeader("Authorization",
            "Bearer " + this.session.getToken())
            .addHeader("Ocp-Apim-Subscription-Key", this.opts.getCollectionPrimaryKey())
            .addHeader("X-Target-Environment", this.opts.getTargetEnvironment())
            .method(mainRequest.method(), mainRequest.body());
        mainResponse = chain.proceed(builder.build());
       
      }
    } else if (mainResponse.code() == 400 || mainResponse.code() == 500 || mainResponse.code() == 404) {
      String error = "";

      try {
        error = mainResponse.body().string();
      } catch (IllegalStateException e) {

      }


      throw new MomoApiException(error);


    } else {

      Integer numRequests = 0;

      while (numRequests < 3) {

        okhttp3.Response r = chain.proceed(chain.request());
        if (r.isSuccessful()) {
          return r;


        }

        numRequests++;
      }

    }


    return mainResponse;


  }

}
