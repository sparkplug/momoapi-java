package ug.sparkpl.network;

import org.junit.jupiter.api.Test;
import ug.sparkpl.momoapi.network.RequestOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestOptionsTest {

    @Test
    public void testPersistentValuesInToBuilder() {
        RequestOptions opts = RequestOptions.builder()
                .setCollectionApiSecret("sec")
                .setCollectionPrimaryKey("123")
                .setCollectionUserId("1234").build();


        // only api keys and account should persist
        // assuming these are stable across a given stripe integration
        assertEquals("sec", opts.getCollectionApiSecret());
        assertEquals("123", opts.getCollectionPrimaryKey());
        assertEquals("1234", opts.getRemittanceUserId());
    }

}
