package ug.sparkpl.network.collections;

import java.io.IOException;
import java.util.HashMap;

import ug.sparkpl.momoapi.models.Balance;
import ug.sparkpl.momoapi.network.MomoApiException;
import ug.sparkpl.momoapi.network.RequestOptions;
import ug.sparkpl.momoapi.network.collections.CollectionsClient;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;

public class LiveCollectionsClientTest {

  @Test
  public void testRequestToPay() throws IOException {

    RequestOptions opts = RequestOptions.builder()
        .build();
    assertNotNull(opts.getCollectionPrimaryKey());
    assertNotNull(opts.getCollectionApiSecret());
    assertNotNull(opts.getCollectionUserId());


    HashMap<String, String> collMap = new HashMap<String, String>();
    collMap.put("amount", "100");
    collMap.put("mobile", "0782123456");
    collMap.put("externalId", "ext123");
    collMap.put("payeeNote", "testNote");
    collMap.put("payerMessage", "testMessage");

    CollectionsClient client = new CollectionsClient(opts);

    try {
      String transactionRef = client.requestToPay(collMap);
      assertNotNull(transactionRef);

      Balance bl = client.getBalance();

      assertNotNull(bl.getBalance());


    } catch (MomoApiException e) {
      e.printStackTrace();
    }


  }


}
