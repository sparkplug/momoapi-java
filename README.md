# MomoAPI Java Bindings

You can sign up for a MOMOAPI  account at https://https://momodeveloper.mtn.com

[![Build Status](https://travis-ci.com/sparkplug/momoapi-java.svg?branch=master)](https://travis-ci.com/sparkplug/momoapi-java)
[![Coverage Status](https://coveralls.io/repos/github/sparkplug/momoapi-java/badge.svg?branch=master)](https://coveralls.io/github/sparkplug/momoapi-java?branch=master)
[![Join the community on Spectrum](https://withspectrum.github.io/badge/badge.svg)](https://spectrum.chat/momo-api-developers/)

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

# Sandbox Environment

## Creating a sandbox environment API user

Next, we need to get the `User ID` and `User Secret` and to do this we shall need to use the Primary Key for the Product to which we are subscribed, as well as specify a host. The library ships with a commandline application that helps to create sandbox credentials. It assumes you have created an account on `https://momodeveloper.mtn.com` and have your `Ocp-Apim-Subscription-Key`.

```bash
## within the project, on the command line. In this example, our domain is akabbo.ug
$ ./gradlew provisionUser --args='-Ocp-Apim-Subscription-Key <primary-Key> --providerCallBackHost akabbo.ug'
```

The `providerCallBackHost` is your callback host and `Ocp-Apim-Subscription-Key` is your API key for the specific product to which you are subscribed. The `API Key` is unique to the product and you will need an `API Key` for each product you use. You should get a response similar to the following:

```bash
Here is your User Id and API secret : {'apiKey': 'b0431db58a9b41faa8f5860230xxxxxx', 'UserId': '053c6dea-dd68-xxxx-xxxx-c830dac9f401'}
```

These are the credentials we shall use for the sandbox environment. In production, these credentials are provided for you on the MTN OVA management dashboard after KYC requirements are met.



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

[lombok]: https://projectlombok.org
[lombok-plugins]: https://projectlombok.org/setup/overview

<!--
# vim: set tw=79:
-->