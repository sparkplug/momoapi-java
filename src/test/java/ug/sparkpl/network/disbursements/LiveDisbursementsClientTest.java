package ug.sparkpl.network.disbursements;

import java.io.IOException;
import java.util.HashMap;

import ug.sparkpl.momoapi.models.Balance;
import ug.sparkpl.momoapi.network.MomoApiException;
import ug.sparkpl.momoapi.network.RequestOptions;
import ug.sparkpl.momoapi.network.disbursements.DisbursementsClient;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;

public class LiveDisbursementsClientTest {

  @Test
  public void testTransfer() throws IOException {


    RequestOptions opts = RequestOptions.builder()
        .build();


    HashMap<String, String> collMap = new HashMap<String, String>();
    collMap.put("amount", "100");
    collMap.put("mobile", "0782181656");
    collMap.put("externalId", "ext123");
    collMap.put("payeeNote", "testNote");
    collMap.put("payerMessage", "testMessage");

    DisbursementsClient client = new DisbursementsClient(opts);

    try {
      String transactionRef = client.transfer(collMap);
      assertNotNull(transactionRef);

      Balance bl = client.getBalance();

      assertNotNull(bl);

      assertNotNull(bl.getBalance());


    } catch (MomoApiException e) {
      e.printStackTrace();
    }

  }

}
