package ug.sparkpl.network.collections;

import ug.sparkpl.momoapi.network.collections.CollectionsApiService;
import ug.sparkpl.network.BaseTest;

import org.junit.jupiter.api.Test;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Retrofit;

import static org.junit.Assert.assertEquals;


public class MockCollectionsClientTest extends BaseTest {
  /**
   * test get token.
   *
   * @throws Exception when network error
   */
  @Test
  public void testgetToken() throws Exception {
    final MockWebServer server = new MockWebServer();
    final String path = "/collection/v1_0/requesttopay";

    server.enqueue(new MockResponse()
        .setBody(payloadFromResource("resources/collections/token.json"))
        .setResponseCode(200));


    Retrofit retrofitClient = getRetrofit("/");

    CollectionsApiService apiService = retrofitClient.create(CollectionsApiService.class);
    apiService.getToken("", "").execute();


    RecordedRequest request = server.takeRequest();
    // Make sure we made the request to the required path
    assertEquals(path, request.getPath());


  }


}
