package ug.sparkpl.network;

import org.junit.jupiter.api.Test;
import ug.sparkpl.momoapi.network.RequestOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestOptionsTest {

    @Test
    public void testPersistentValuesInToBuilder() {
        RequestOptions.Builder opts = RequestOptions.builder()
                .setCollectionApiSecret("sec")
                .setCollectionPrimaryKey("123")
                .setCollectionUserId("1234")
                .setCurrency("UGX")
                .setTargetEnvironment("test")
                .setBaseUrl("new_base")
                .setRemittanceUserId("remituid")
                .setRemittancePrimaryKey("remKey")
                .setRemittanceApiSecret("remSecret")
                .setDisbursementUserId("duid")
                .setRemittanceApiSecret("dSecret")
                .setRemittancePrimaryKey("dKey")
                .build().toBuilder();


        // assuming these are stable across a given stripe integration

        assertEquals("123", opts.getCollectionPrimaryKey());
        assertEquals("sec", opts.getCollectionApiSecret());
        assertEquals("1234", opts.getCollectionUserId());
        assertEquals("UGX", opts.getCurrency());
        assertEquals("test", opts.getTargetEnvironment());
        assertEquals("UGX", opts.getCurrency());
        assertEquals("new_base", opts.getBaseUrl());
        assertEquals("remituid", opts.getRemittanceUserId());
        assertEquals("remKey", opts.getRemittancePrimaryKey());
        assertEquals("remSecret", opts.getRemittanceApiSecret());
        assertEquals("dKey", opts.getDisbursementPrimaryKey());
        assertEquals("dSecret", opts.getDisbursementApiSecret());
        assertEquals("duid", opts.getDisbursementUserId());


    }

}
