package ug.sparkpl.momoapi.network.disbursements;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import ug.sparkpl.momoapi.models.AccessToken;
import ug.sparkpl.momoapi.models.Balance;
import ug.sparkpl.momoapi.models.Transaction;
import ug.sparkpl.momoapi.models.Transfer;
import ug.sparkpl.momoapi.network.RequestOptions;
import ug.sparkpl.momoapi.utils.DateTimeTypeConverter;

import org.joda.time.DateTime;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class DisbursementsClient {
  RequestOptions opts;
  Gson gson;
  private DisbursementsSession session;
  private DisbursementsApiService apiService;
  private OkHttpClient httpClient;
  private Retrofit retrofitClient;

  /**
   * DisbursementsClient.
   *
   * @param opts RequestOptions
   */
  public DisbursementsClient(RequestOptions opts) {
    this.opts = opts;
    this.gson = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
        .create();

    this.session = new DisbursementsSession();

    final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


    final OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();

    // Only log in debug mode to avoid leaking sensitive information.


    okhttpbuilder.addInterceptor(
        new DisbursementsAuthorizationInterceptor(this.session, this.opts));
    okhttpbuilder.addInterceptor(httpLoggingInterceptor);


    okhttpbuilder.connectTimeout(30, TimeUnit.SECONDS);
    okhttpbuilder.readTimeout(30, TimeUnit.SECONDS);
    okhttpbuilder.writeTimeout(30, TimeUnit.SECONDS);


    this.httpClient = okhttpbuilder
        .build();


    this.retrofitClient = new Retrofit.Builder()
        .client(this.httpClient)
        .baseUrl(opts.getBaseUrl())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(ScalarsConverterFactory.create())
        .build();

    this.apiService = this.retrofitClient.create(DisbursementsApiService.class);


  }


  /**
   * getToken.
   *
   * @return AccessToken
   * @throws IOException when network error occurs
   */
  public AccessToken getToken() throws IOException {
    String credentials = Credentials.basic(this.opts.getDisbursementUserId(),
        this.opts.getDisbursementApiSecret());
    Response<AccessToken> token = this.apiService
        .getToken(credentials, this.opts.getDisbursementPrimaryKey()).execute();
    return token.body();
  }

  /**
   * getBalance.
   *
   * @return Balance
   * @throws IOException when network error occurs
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
   * transfer money.
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
  public String transfer(String mobile, String amount,
                         String externalId, String payeeNote,
                         String payerMessage, String currency) throws IOException {
    Transfer rbody = new Transfer(mobile, amount, externalId, payeeNote, payerMessage, currency);
    String ref = UUID.randomUUID().toString();
    this.apiService.transfer(rbody, ref).execute();
    return ref;

  }

  /**
   * transfer money.
   *
   * @param opts HashMap
   * @return String
   * @throws IOException when there is a network error
   */
  public String transfer(HashMap<String, String> opts) throws IOException {
    Transfer rbody = new Transfer(opts.get("mobile"), opts.get("amount"),
        opts.get("externalId"), opts.get("payeeNote"),
        opts.get("payerMessage"), opts.getOrDefault("currency", this.opts.getCurrency()));
    String ref = UUID.randomUUID().toString();
    this.apiService.transfer(rbody, ref).execute();
    return ref;

  }

}
