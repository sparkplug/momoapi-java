package ug.sparkpl.momoapi;


import ug.sparkpl.momoapi.models.Balance;
import ug.sparkpl.momoapi.models.Transaction;
import ug.sparkpl.momoapi.network.RequestOptions;
import ug.sparkpl.momoapi.network.remittances.RemittancesClient;

import java.io.IOException;

public class MomoApi {

    MomoApi() {

    }

    public static void main(String[] args) {

        RequestOptions opts = RequestOptions.builder().build();
        RemittancesClient client = new RemittancesClient(opts);


        try {
            Balance bl = client.getBalance();

            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&");

            System.out.println(bl.getBalance());

            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            String ref = client.transfer("0782631853", "4560", "234567", "dd", "rty", "EUR");


            System.out.println(ref + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            Transaction tr = client.getTransactionStatus(ref);


            System.out.println(tr.getStatus());


        } catch (IOException e) {
            System.out.println(e.toString());

        }


    }


}
