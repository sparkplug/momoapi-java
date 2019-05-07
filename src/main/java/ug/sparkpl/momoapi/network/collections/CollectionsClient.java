package ug.sparkpl.momoapi.network.collections;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import ug.sparkpl.momoapi.models.AccessToken;
import ug.sparkpl.momoapi.models.Balance;
import ug.sparkpl.momoapi.models.RequestToPay;
import ug.sparkpl.momoapi.models.Transaction;
import ug.sparkpl.momoapi.network.BaseClient;
import ug.sparkpl.momoapi.network.RequestOptions;

import com.google.gson.Gson;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CollectionsClient extends BaseClient {


  RequestOptions opts;
  Gson gson;
  private CollectionSession session;
  private CollectionsApiService apiService;
  private OkHttpClient httpClient;
  private Retrofit retrofitClient;
  private Retrofit client;


  /**
   * CollectionsClient.
   *
   * @param opts RequestOptions
   */
  public CollectionsClient(RequestOptions opts) {
    this.opts = opts;
    this.gson = getGson();

    this.session = new CollectionSession();

    final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


    final OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();

    // Only log in debug mode to avoid leaking sensitive information.


    okhttpbuilder.addInterceptor(new CollectionsAuthorizationInterceptor(this.session, this.opts));
    okhttpbuilder.addInterceptor(httpLoggingInterceptor);


    okhttpbuilder.connectTimeout(60, TimeUnit.SECONDS);
    okhttpbuilder.readTimeout(60, TimeUnit.SECONDS);
    okhttpbuilder.writeTimeout(60, TimeUnit.SECONDS);


    this.httpClient = okhttpbuilder
        .build();


    this.retrofitClient = new Retrofit.Builder()
        .client(this.httpClient)
        .baseUrl(opts.getBaseUrl())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(ScalarsConverterFactory.create())
        .build();

    this.apiService = this.retrofitClient.create(CollectionsApiService.class);


  }


  /**
   * get access Token.
   *
   * @return AccessToken
   * @throws IOException when there is a network error
   */
  public AccessToken getToken() throws IOException {
    String credentials = Credentials.basic(this.opts.getCollectionUserId(),
        this.opts.getCollectionApiSecret());
    Response<AccessToken> token = this.apiService
        .getToken(credentials, this.opts.getCollectionPrimaryKey()).execute();

    return token.body();
  }


  /**
   * get Account Balance.
   *
   * @return Balance
   * @throws IOException when there is a network error
   */
  public Balance getBalance() throws IOException {
    Response<Balance> balance = this.apiService
        .getBalance().execute();
    return balance.body();

  }

  /**
   * get Transaction.
   *
   * @param ref String
   * @return Transaction
   * @throws IOException when there is a network error
   */
  public Transaction getTransaction(String ref) throws IOException {
    Response<Transaction> transaction = this.apiService
        .getTransactionStatus(ref).execute();
    return transaction.body();

  }


  /**
   * Request To Pay.
   *
   * @param mobile       String
   * @param amount       String
   * @param externalId   String
   * @param payeeNote    String
   * @param payerMessage String
   * @param currency     String
   * @return String
   * @throws IOException when there is a network error
   */
  public String requestToPay(String mobile, String amount, String externalId, String payeeNote,
                             String payerMessage, String currency) throws IOException {
    RequestToPay rbody = new RequestToPay(mobile, amount, externalId,
        payeeNote, payerMessage, currency);
    String ref = UUID.randomUUID().toString();
    this.apiService.requestToPay(rbody, ref).execute();
    return ref;

  }

  /**
   * Request To Pay.
   *
   * @param opts HashMap
   * @return String
   * @throws IOException when there is a network error
   */
  public String requestToPay(HashMap<String, String> opts) throws IOException {
    RequestToPay rbody = new RequestToPay(opts.get("mobile"), opts.get("amount"),
        opts.get("externalId"), opts.get("payeeNote"), opts.get("payerMessage"),
        opts.getOrDefault("currency", this.opts.getCurrency()));
    String ref = UUID.randomUUID().toString();
    this.apiService.requestToPay(rbody, ref).execute();
    return ref;

  }


}
