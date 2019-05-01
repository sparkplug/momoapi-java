package ug.sparkpl.network.collections;

import java.util.concurrent.TimeUnit;

import ug.sparkpl.momoapi.network.collections.CollectionsApiService;
import ug.sparkpl.momoapi.utils.DateTimeTypeConverter;
import ug.sparkpl.network.BaseTest;

import org.joda.time.DateTime;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static org.junit.Assert.assertEquals;

public class CollectionsClientTest extends BaseTest {
  /**
   * test get token.
   *
   * @throws Exception when network error
   */
  public void testgetToken() throws Exception {
    final MockWebServer server = new MockWebServer();
    final String path = "/collection/v1_0/requesttopay";

    server.enqueue(new MockResponse()
        .setBody(payloadFromResource("resources/collections/token.json"))
        .setResponseCode(200));


    final Gson gson = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
        .create();


    final OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();


    okhttpbuilder.connectTimeout(30, TimeUnit.SECONDS);
    okhttpbuilder.readTimeout(30, TimeUnit.SECONDS);
    okhttpbuilder.writeTimeout(30, TimeUnit.SECONDS);

    OkHttpClient httpClient = okhttpbuilder
        .build();


    Retrofit retrofitClient = new Retrofit.Builder()
        .client(httpClient)
        .baseUrl("/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(ScalarsConverterFactory.create())
        .build();

    CollectionsApiService apiService = retrofitClient.create(CollectionsApiService.class);
    apiService.getToken("", "").execute();


    RecordedRequest request = server.takeRequest();
    // Make sure we made the request to the required path
    assertEquals(path, request.getPath());


  }


}
