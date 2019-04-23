# MomoAPI Java Bindings

You can sign up for a MOMOAPI  account at https://https://momodeveloper.mtn.com

## Requirements

Java 1.8 or later.

## Installation

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>ug.sparkpl</groupId>
  <artifactId>mtnmomo-java</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "ug.sparkpl:mtnmomo-java:1.0.0"
```


## Usage

MomoExample.java

```java
import java.util.HashMap;
import java.util.Map;

import ug.sparkpl.momoapi.network.RequestOptions;
import ug.sparkpl.momoapi.network.collections.CollectionsClient;


public class MomoExample {

    public static void main(String[] args)  {
         RequestOptions opts = RequestOptions.builder()
                        .setCollectionApiSecret("MY_SECRET_API_KEY")
                        .setCollectionPrimaryKey("MY_SECRET_SUBSCRIPTION_KEY")
                        .setCollectionUserId("MYSECRET_USER_ID").build();


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
```


### Per-request Configuration

By default, you can set



``` java

```



## Development

You must have Gradle installed. To run the tests:

    ./gradlew test


The library uses [Project Lombok][lombok]. While it is not a requirement, you might want to install a [plugin][lombok-plugins] for your favorite IDE to facilitate development.

[connect]: https://stripe.com/connect
[lombok]: https://projectlombok.org
[lombok-plugins]: https://projectlombok.org/setup/overview
[stripe-mock]: https://github.com/stripe/stripe-mock

<!--
# vim: set tw=79:
-->