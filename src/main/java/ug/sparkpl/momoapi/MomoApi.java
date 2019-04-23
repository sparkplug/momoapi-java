package ug.sparkpl.momoapi;


import ug.sparkpl.momoapi.network.MomoApiException;
import ug.sparkpl.momoapi.network.RequestOptions;
import ug.sparkpl.momoapi.network.collections.CollectionsClient;

import java.io.IOException;
import java.util.HashMap;

public class MomoApi {

    MomoApi() {

    }

    public static void main(String[] args) throws IOException {
        RequestOptions opts = RequestOptions.builder()
                .setCollectionApiSecret("MY_SECRET_API_KEY")
                .setCollectionPrimaryKey("MY_SECRET_SUBSCRIPTION_KEY")
                .setCollectionUserId("MYSECRET_USER_ID")
                .setCurrency("UGX")

                .build();


        HashMap<String, String> collMap = new HashMap<String, String>();
        collMap.put("amount", "100");
        collMap.put("mobile", "1234");
        collMap.put("externalId", "ext123");
        collMap.put("payeeNote", "testNote");
        collMap.put("payerMessage", "testMessage");

        CollectionsClient client = new CollectionsClient(opts);

        try {
            String transactionRef = client.requestToPay(collMap);
            System.out.println(transactionRef);
        } catch (MomoApiException e) {
            e.printStackTrace();
        }


    }


}
