package ug.sparkpl.momoapi.network;


import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class RequestOptions {


  private final String COLLECTION_USER_ID;
  private final String COLLECTION_API_SECRET;
  private final String collectionPrimaryKey;

  private final String REMITTANCE_PRIMARY_KEY;
  private final String REMITTANCE_USER_ID;
  private final String REMITTANCE_API_SECRET;


  private final String disbursementPrimaryKey;
  private final String DISBURSEMENT_USER_ID;
  private final String DISBURSEMENT_API_SECRET;

  private final String BASE_URL;
  private final String targetEnvironment;
  private final String CURRENCY;

  public RequestOptions(String COLLECTION_API_SECRET, String collectionPrimaryKey, String COLLECTION_USER_ID, String REMITTANCE_USER_ID, String REMITTANCE_PRIMARY_KEY, String REMITTANCE_API_SECRET, String DISBURSEMENT_API_SECRET, String disbursementPrimaryKey, String DISBURSEMENT_USER_ID, String BASE_URL, String targetEnvironment, String CURRENCY) {

    this.COLLECTION_API_SECRET = COLLECTION_API_SECRET;
    this.collectionPrimaryKey = collectionPrimaryKey;
    this.COLLECTION_USER_ID = COLLECTION_USER_ID;

    this.REMITTANCE_USER_ID = REMITTANCE_USER_ID;
    this.REMITTANCE_PRIMARY_KEY = REMITTANCE_PRIMARY_KEY;
    this.REMITTANCE_API_SECRET = REMITTANCE_API_SECRET;

    this.DISBURSEMENT_API_SECRET = DISBURSEMENT_API_SECRET;
    this.disbursementPrimaryKey = disbursementPrimaryKey;
    this.DISBURSEMENT_USER_ID = DISBURSEMENT_USER_ID;
    this.BASE_URL = BASE_URL;
    this.targetEnvironment = targetEnvironment;
    this.CURRENCY = CURRENCY;


  }

  public static Builder builder() {
    return new Builder();
  }

  public Builder toBuilder() {
    return new Builder()
            .setCollectionPrimaryKey(this.collectionPrimaryKey)
            .setCollectionApiSecret(this.COLLECTION_API_SECRET)
            .setCollectionUserId(this.COLLECTION_USER_ID)
            .setCurrency(this.CURRENCY)
            .setBaseUrl(this.BASE_URL)
            .setTargetEnvironment(this.targetEnvironment)
            .setDisbursementApiSecret(this.DISBURSEMENT_API_SECRET)
            .setDisbursementPrimaryKey(this.disbursementPrimaryKey)
            .setDisbursementUserId(this.DISBURSEMENT_USER_ID)
            .setRemittanceApiSecret(this.REMITTANCE_API_SECRET)
            .setRemittancePrimaryKey(this.REMITTANCE_PRIMARY_KEY)
            .setRemittanceUserId(this.REMITTANCE_USER_ID);

  }

  public String getCollectionUserId() {
    return this.COLLECTION_USER_ID;
  }

  public String getCollectionApiSecret() {
    return this.COLLECTION_API_SECRET;
  }

  public String getCollectionPrimaryKey() {
    return this.collectionPrimaryKey;
  }

  public String getRemittancePrimaryKey() {
    return this.REMITTANCE_PRIMARY_KEY;
  }


  public String getRemittanceUserId() {
    return REMITTANCE_USER_ID;
  }

  public String getRemittanceApiSecret() {
    return this.REMITTANCE_API_SECRET;
  }


  public String getDisbursementPrimaryKey() {
    return this.disbursementPrimaryKey;
  }


  public String getDisbursementUserId() {
    return this.DISBURSEMENT_USER_ID;
  }

  public String getDisbursementApiSecret() {
    return this.DISBURSEMENT_API_SECRET;
  }

  public String getBaseUrl() {
    return this.BASE_URL;
  }

  public String getTargetEnvironment() {
    return this.targetEnvironment;
  }

  public String getCurrency() {
    return this.CURRENCY;
  }


  public static class Builder {

    private String COLLECTION_USER_ID;
    private String COLLECTION_API_SECRET;
    private String collectionPrimaryKey;
    private String REMITTANCE_PRIMARY_KEY;
    private String REMITTANCE_USER_ID;
    private String REMITTANCE_API_SECRET;
    private String disbursementPrimaryKey;
    private String DISBURSEMENT_USER_ID;
    private String DISBURSEMENT_API_SECRET;

    private String BASE_URL = "https://ericssonbasicapi2.azure-api.net";

    private String CURRENCY = "EUR";

    private String targetEnvironment = "sandbox";


    /**
     * Constructs a request options builder with the global parameters (API key, client ID and
     * API version) as default values.
     */
    public Builder() {
      this.COLLECTION_USER_ID = System.getenv("COLLECTION_USER_ID");
      this.COLLECTION_API_SECRET = System.getenv("COLLECTION_API_SECRET");
      this.collectionPrimaryKey = System.getenv("collectionPrimaryKey");

      this.REMITTANCE_PRIMARY_KEY = System.getenv("REMITTANCE_PRIMARY_KEY");
      this.REMITTANCE_USER_ID = System.getenv("REMITTANCE_USER_ID");
      this.REMITTANCE_API_SECRET = System.getenv("REMITTANCE_API_SECRET");

      this.disbursementPrimaryKey = System.getenv("disbursementPrimaryKey");
      this.DISBURSEMENT_USER_ID = System.getenv("DISBURSEMENT_USER_ID");
      this.DISBURSEMENT_API_SECRET = System.getenv("DISBURSEMENT_API_SECRET");

    }

    private static String normalizeKey(String key) {

      String normalized = key.trim();
      if (normalized.isEmpty()) {
        throw new InvalidRequestOptionsException("Empty  key specified!");
      }
      return normalized;
    }

    public String getCollectionPrimaryKey() {
      return this.collectionPrimaryKey;
    }

    public Builder setCollectionPrimaryKey(String collectionPrimaryKey) {
      this.collectionPrimaryKey = collectionPrimaryKey;
      return this;
    }

    public String getCollectionUserId() {
      return this.COLLECTION_USER_ID;
    }

    public Builder setCollectionUserId(String collectionUserId) {
      this.COLLECTION_USER_ID = collectionUserId;
      return this;
    }

    public String getCollectionApiSecret() {
      return this.COLLECTION_API_SECRET;
    }

    public Builder setCollectionApiSecret(String collectionApiSecret) {
      this.COLLECTION_API_SECRET = collectionApiSecret;
      return this;
    }

    public String getRemittancePrimaryKey() {
      return this.REMITTANCE_PRIMARY_KEY;
    }

    public Builder setRemittancePrimaryKey(String remittancePrimaryKey) {
      this.REMITTANCE_PRIMARY_KEY = remittancePrimaryKey;
      return this;
    }

    public String getRemittanceUserId() {
      return REMITTANCE_USER_ID;
    }

    public Builder setRemittanceUserId(String remittanceUserId) {
      this.REMITTANCE_USER_ID = remittanceUserId;
      return this;
    }

    public String getRemittanceApiSecret() {
      return this.REMITTANCE_API_SECRET;
    }

    public Builder setRemittanceApiSecret(String remittanceApiSecret) {
      this.REMITTANCE_API_SECRET = remittanceApiSecret;
      return this;
    }

    public String getDisbursementPrimaryKey() {
      return this.disbursementPrimaryKey;
    }

    public Builder setDisbursementPrimaryKey(String disbursementPrimaryKey) {
      this.disbursementPrimaryKey = disbursementPrimaryKey;
      return this;
    }

    public String getDisbursementUserId() {
      return this.DISBURSEMENT_USER_ID;
    }

    public Builder setDisbursementUserId(String disbursementUserId) {
      this.DISBURSEMENT_USER_ID = disbursementUserId;
      return this;
    }

    public String getDisbursementApiSecret() {
      return this.DISBURSEMENT_API_SECRET;
    }

    public Builder setDisbursementApiSecret(String disbursementApiSecret) {
      this.DISBURSEMENT_API_SECRET = disbursementApiSecret;
      return this;
    }

    public String getBaseUrl() {
      return this.BASE_URL;
    }

    public Builder setBaseUrl(String url) {
      this.BASE_URL = url;
      return this;
    }

    public String getTargetEnvironment() {
      return this.targetEnvironment;
    }

    public Builder setTargetEnvironment(String environment) {
      this.targetEnvironment = environment;
      return this;
    }

    public String getCurrency() {
      return this.CURRENCY;
    }

    public Builder setCurrency(String currency) {
      this.CURRENCY = currency;
      return this;
    }

    public RequestOptions build() {
      return new RequestOptions(
              this.COLLECTION_API_SECRET,
              this.collectionPrimaryKey,
              this.COLLECTION_USER_ID,

              this.REMITTANCE_USER_ID,
              this.REMITTANCE_PRIMARY_KEY,
              this.REMITTANCE_API_SECRET,

              this.DISBURSEMENT_API_SECRET,
              this.disbursementPrimaryKey,
              this.DISBURSEMENT_USER_ID,
              this.BASE_URL,
              this.targetEnvironment,
              this.CURRENCY
      );

    }


  }

  public static class InvalidRequestOptionsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidRequestOptionsException(String message) {
      super(message);
    }
  }


}



