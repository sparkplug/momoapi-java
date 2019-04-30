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
 {'apiKey': 'b0431db58a9b41faa8f5860230xxxxxx', 'UserId': '053c6dea-dd68-xxxx-xxxx-c830dac9f401'}
```

These are the credentials we shall use for the sandbox environment. In production, these credentials are provided for you on the MTN OVA management dashboard after KYC requirements are met.


## Configuration

Before we can fully utilize the library, we need to specify global configurations. The global configuration using the requestOpts builder. By default, these are picked from environment variables,
but can be overidden using the RequestOpts builder

* `BASE_URL`: An optional base url to the MTN Momo API. By default the staging base url will be used
* `ENVIRONMENT`: Optional enviroment, either "sandbox" or "production". Default is 'sandbox'
* `CURRENCY`: currency by default its EUR
* `CALLBACK_HOST`: The domain where you webhooks urls are hosted. This is mandatory.
* `COLLECTION_PRIMARY_KEY`: The collections API primary key,
* `COLLECTION_USER_ID`:  The collection User Id
* `COLLECTION_API_SECRET`:  The Collection API secret
* `REMITTANCE_USER_ID`:  The Remittance User ID
* `REMITTANCE_API_SECRET`: The Remittance API Secret
* `REMITTANCE_PRIMARY_KEY`: The Remittance Subscription Key
* `DISBURSEMENT_USER_ID`: The Disbursement User ID
* `DISBURSEMENT_API_SECRET`: The Disbursement API Secret
* `DISBURSEMENT_PRIMARY_KEY`: The Disbursement Primary Key

Once you have specified the global variables, you can now provide the product-specific variables. Each MoMo API product requires its own authentication details i.e its own `Subscription Key`, `User ID` and `User Secret`, also sometimes refered to as the `API Secret`. As such, we have to configure subscription keys for each product you will be using.



The full list of configuration options can be seen in the example below:


You will only need to configure the variables for the product(s) you will be using.


### Per-request Configuration




``` java

 RequestOptions opts = RequestOptions.builder()
                        .setCollectionApiSecret("MY_SECRET_API_KEY")
                        .setCollectionPrimaryKey("MY_SECRET_SUBSCRIPTION_KEY")
                        .setCollectionUserId("MYSECRET_USER_ID")
                        .setBaseUrl("NEW_BASE_URL")  // Override the default base url
                        .setCurrency("UGX") // Override default currency
                        .setTargetEnvironment("env") // Override default target environment
                        build();

```

## Usage


## Collections

The collections client can be created with the following paramaters. Note that the `COLLECTION_USER_ID` and `COLLECTION_API_SECRET` for production are provided on the MTN OVA dashboard;

* `COLLECTION_PRIMARY_KEY`: Primary Key for the `Collection` product on the developer portal.
* `COLLECTION_USER_ID`: For sandbox, use the one generated with the `mtnmomo` command.
* `COLLECTION_API_SECRET`: For sandbox, use the one generated with the `mtnmomo` command.


MomoCollectionsExample.java

```java
import java.util.HashMap;
import java.util.Map;

import ug.sparkpl.momoapi.network.RequestOptions;
import ug.sparkpl.momoapi.network.collections.CollectionsClient;


public class MomoCollectionsExample {

    public static void main(String[] args)  {

         // Make a request to pay call
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


### Methods

1. `requestToPay`: This operation is used to request a payment from a consumer (Payer). The payer will be asked to authorize the payment. The transaction is executed once the payer has authorized the payment. The transaction will be in status PENDING until it is authorized or declined by the payer or it is timed out by the system. Status of the transaction can be validated by using `getTransactionStatus`.

2. `getTransaction`: Retrieve transaction information using the `transactionId` returned by `requestToPay`. You can invoke it at intervals until the transaction fails or succeeds. If the transaction has failed, it will throw an appropriate error.

3. `getBalance`: Get the balance of the account.

4. `isPayerActive`: check if an account holder is registered and active in the system.


## Disbursement

The Disbursements client can be created with the following paramaters. Note that the `DISBURSEMENT_USER_ID` and `DISBURSEMENT_API_SECRET` for production are provided on the MTN OVA dashboard;

* `DISBURSEMENT_PRIMARY_KEY`: Primary Key for the `Disbursement` product on the developer portal.
* `DISBURSEMENT_USER_ID`: For sandbox, use the one generated with the `mtnmomo` command.
* `DISBURSEMENT_API_SECRET`: For sandbox, use the one generated with the `mtnmomo` command.

Making a disbursements request

MomoDisbursementsExample.java

```java
import java.util.HashMap;
import java.util.Map;

import ug.sparkpl.momoapi.network.RequestOptions;
import ug.sparkpl.momoapi.network.collections.CollectionsClient;


public class MomoDisbursementsExample {

    public static void main(String[] args)  {

         // Make a request to pay call
         RequestOptions opts = RequestOptions.builder()
                        .setDisbursementApiSecret("MY_SECRET_API_KEY")
                        .setDisbursementPrimaryKey("MY_SECRET_SUBSCRIPTION_KEY")
                        .setDisbursementUserId("MYSECRET_USER_ID").build();


                HashMap<String, String> collMap = new HashMap<String, String>();
                collMap.put("amount", "100");
                collMap.put("mobile", "1234");
                collMap.put("externalId", "ext123");
                collMap.put("payeeNote", "testNote");
                collMap.put("payerMessage", "testMessage");

                DisbursementsClient client = new DisbursementsClient(opts);

                try {
                    String transactionRef = client.transfer(collMap);
                    System.out.println(transactionRef);
                } catch (MomoApiException e) {
                    e.printStackTrace();
                }
    }
}
```

### Methods

1. `transfer`: Used to transfer an amount from the owner’s account to a payee account. Status of the transaction can be validated by using the `getTransactionStatus` method.

2. `getTransaction`: Retrieve transaction information using the `transactionId` returned by `transfer`. You can invoke it at intervals until the transaction fails or succeeds.

2. `getBalance`: Get your account balance.

3. `isPayerActive`: This method is used to check if an account holder is registered and active in the system.



## Development

You must have Gradle installed. To run the tests:

    ./gradlew test


The library uses [Project Lombok][lombok]. While it is not a requirement, you might want to install a [plugin][lombok-plugins] for your favorite IDE to facilitate development.

[lombok]: https://projectlombok.org
[lombok-plugins]: https://projectlombok.org/setup/overview

<!--
# vim: set tw=79:
-->