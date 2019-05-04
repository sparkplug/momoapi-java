package ug.sparkpl.momoapi.network;


import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class RequestOptions {


  private final String collectionUserId;
  private final String collectionApiSecret;
  private final String collectionPrimaryKey;

  private final String remittancePrimaryKey;
  private final String remittanceUserId;
  private final String remittanceApiSecret;


  private final String disbursementPrimaryKey;
  private final String disbursementUserId;
  private final String disbursementApiSecret;

  private final String baseUrl;
  private final String targetEnvironment;
  private final String currency;

  /**
   * Build options.
   *
   * @param collectionApiSecret    String
   * @param collectionPrimaryKey   String
   * @param collectionUserId       String
   * @param remittanceUserId       String
   * @param remittancePrimaryKey   String
   * @param remittanceApiSecret    String
   * @param disbursementApiSecret  String
   * @param disbursementPrimaryKey String
   * @param disbursementUserId     String
   * @param baseUrl                String
   * @param targetEnvironment      String
   * @param currency               String
   */
  public RequestOptions(String collectionApiSecret,
                        String collectionPrimaryKey,
                        String collectionUserId,
                        String remittanceUserId,
                        String remittancePrimaryKey,
                        String remittanceApiSecret,
                        String disbursementApiSecret,
                        String disbursementPrimaryKey,
                        String disbursementUserId,
                        String baseUrl,
                        String targetEnvironment,
                        String currency) {

    this.collectionApiSecret = collectionApiSecret;
    this.collectionPrimaryKey = collectionPrimaryKey;
    this.collectionUserId = collectionUserId;

    this.remittanceUserId = remittanceUserId;
    this.remittancePrimaryKey = remittancePrimaryKey;
    this.remittanceApiSecret = remittanceApiSecret;

    this.disbursementApiSecret = disbursementApiSecret;
    this.disbursementPrimaryKey = disbursementPrimaryKey;
    this.disbursementUserId = disbursementUserId;
    this.baseUrl = baseUrl;
    this.targetEnvironment = targetEnvironment;
    this.currency = currency;


  }

  /**
   * Builder.
   *
   * @return Builder
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * for playing nice with unittests.
   *
   * @return Builder
   */
  public Builder toBuilder() {
    return new Builder()
        .setCollectionPrimaryKey(this.collectionPrimaryKey)
        .setCollectionApiSecret(this.collectionApiSecret)
        .setCollectionUserId(this.collectionUserId)
        .setCurrency(this.currency)
        .setBaseUrl(this.baseUrl)
        .setTargetEnvironment(this.targetEnvironment)
        .setDisbursementApiSecret(this.disbursementApiSecret)
        .setDisbursementPrimaryKey(this.disbursementPrimaryKey)
        .setDisbursementUserId(this.disbursementUserId)
        .setRemittanceApiSecret(this.remittanceApiSecret)
        .setRemittancePrimaryKey(this.remittancePrimaryKey)
        .setRemittanceUserId(this.remittanceUserId);

  }

  /**
   * get Collection Id.
   *
   * @return String
   */
  public String getCollectionUserId() {
    return this.collectionUserId;
  }

  /**
   * get collection API secret.
   *
   * @return String
   */
  public String getCollectionApiSecret() {
    return this.collectionApiSecret;
  }

  /**
   * Get collection Primary Key.
   *
   * @return String
   */
  public String getCollectionPrimaryKey() {
    return this.collectionPrimaryKey;
  }

  /**
   * Get Remittance Primary Key.
   *
   * @return String
   */

  public String getRemittancePrimaryKey() {
    return this.remittancePrimaryKey;
  }


  /**
   * get Remittance user Id.
   *
   * @return String
   */
  public String getRemittanceUserId() {
    return remittanceUserId;
  }

  /**
   * Get Remittance Api Secret.
   *
   * @return String
   */
  public String getRemittanceApiSecret() {
    return this.remittanceApiSecret;
  }


  /**
   * Get Disbursement Primnary Key.
   *
   * @return String
   */
  public String getDisbursementPrimaryKey() {
    return this.disbursementPrimaryKey;
  }


  /**
   * Get Disbursement User Id.
   *
   * @return String
   */
  public String getDisbursementUserId() {
    return this.disbursementUserId;
  }

  /**
   * Get Disbursement Api Secret.
   *
   * @return String
   */
  public String getDisbursementApiSecret() {
    return this.disbursementApiSecret;
  }

  /**
   * Get Base Url.
   *
   * @return String
   */
  public String getBaseUrl() {
    return this.baseUrl;
  }

  /**
   * Get Target Environment.
   *
   * @return String
   */
  public String getTargetEnvironment() {
    return this.targetEnvironment;
  }

  /**
   * get Currency.
   *
   * @return String
   */
  public String getCurrency() {
    return this.currency;
  }


  public static class Builder {

    private String collectionUserId;
    private String collectionApiSecret;
    private String collectionPrimaryKey;
    private String remittancePrimaryKey;
    private String remittanceUserId;
    private String remittanceApiSecret;
    private String disbursementPrimaryKey;
    private String disbursementUserId;
    private String disbursementApiSecret;

    private String baseUrl = "https://ericssonbasicapi2.azure-api.net";

    private String currency = "EUR";

    private String targetEnvironment = "sandbox";


    /**
     * Constructs a request options builder with the global parameters (API key, client ID and
     * API version) as default values.
     */
    public Builder() {
      this.collectionUserId = System.getenv("COLLECTION_USER_ID");
      this.collectionApiSecret = System.getenv("COLLECTION_API_SECRET");
      this.collectionPrimaryKey = System.getenv("COLLECTION_PRIMARY_KEY");

      this.remittancePrimaryKey = System.getenv("REMITTANCE_PRIMARY_KEY");
      this.remittanceUserId = System.getenv("REMITTANCE_USER_ID");
      this.remittanceApiSecret = System.getenv("REMITTANCE_API_SECRET");

      this.disbursementPrimaryKey = System.getenv("DISBURSEMENT_PRIMARY_KEY");
      this.disbursementUserId = System.getenv("DISBURSEMENT_USER_ID");
      this.disbursementApiSecret = System.getenv("DISBURSEMENT_API_SECRET");

    }


    /**
     * Normalize Keys.
     *
     * @param key String
     * @return String
     */
    private static String normalizeKey(String key) {

      String normalized = key.trim();
      if (normalized.isEmpty()) {
        throw new InvalidRequestOptionsException("Empty  key specified!");
      }
      return normalized;
    }

    /**
     * Get Collection primary key.
     *
     * @return String
     */
    public String getCollectionPrimaryKey() {
      return this.collectionPrimaryKey;
    }

    /**
     * Set Collection Primary Key.
     *
     * @param collectionPrimaryKey String
     * @return String
     */
    public Builder setCollectionPrimaryKey(String collectionPrimaryKey) {
      this.collectionPrimaryKey = collectionPrimaryKey;
      return this;
    }

    /**
     * get Collection User Id.
     *
     * @return String
     */
    public String getCollectionUserId() {
      return this.collectionUserId;
    }

    /**
     * Set Collection User Id.
     *
     * @param collectionUserId String
     * @return Builder
     */
    public Builder setCollectionUserId(String collectionUserId) {
      this.collectionUserId = collectionUserId;
      return this;
    }

    /**
     * get Collection Api Secret.
     *
     * @return String
     */
    public String getCollectionApiSecret() {
      return this.collectionApiSecret;
    }

    /**
     * Set Collection Api Secret.
     *
     * @param collectionApiSecret String
     * @return String
     */
    public Builder setCollectionApiSecret(String collectionApiSecret) {
      this.collectionApiSecret = collectionApiSecret;
      return this;
    }

    /**
     * Get Remittance Primary Key.
     *
     * @return String
     */
    public String getRemittancePrimaryKey() {
      return this.remittancePrimaryKey;
    }

    /**
     * Set Remittance Primary Key.
     *
     * @param remittancePrimaryKey String
     * @return Builder
     */
    public Builder setRemittancePrimaryKey(String remittancePrimaryKey) {
      this.remittancePrimaryKey = remittancePrimaryKey;
      return this;
    }

    /**
     * Get Remittance User Id.
     *
     * @return String
     */
    public String getRemittanceUserId() {
      return remittanceUserId;
    }

    /**
     * Set Remittance User Id.
     *
     * @param remittanceUserId String
     * @return Builder
     */

    public Builder setRemittanceUserId(String remittanceUserId) {
      this.remittanceUserId = remittanceUserId;
      return this;
    }

    /**
     * Set Remittance Api Secret.
     *
     * @return String
     */
    public String getRemittanceApiSecret() {
      return this.remittanceApiSecret;
    }

    /**
     * Set Remittance Api Secret.
     *
     * @param remittanceApiSecret String
     * @return Builder
     */
    public Builder setRemittanceApiSecret(String remittanceApiSecret) {
      this.remittanceApiSecret = remittanceApiSecret;
      return this;
    }

    /**
     * Get Disbursements Primary Key.
     *
     * @return String
     */
    public String getDisbursementPrimaryKey() {
      return this.disbursementPrimaryKey;
    }

    /**
     * Set Disbursements Primary Key.
     *
     * @param disbursementPrimaryKey String
     * @return Builder
     */
    public Builder setDisbursementPrimaryKey(String disbursementPrimaryKey) {
      this.disbursementPrimaryKey = disbursementPrimaryKey;
      return this;
    }

    /**
     * Get Disbursements User Id.
     *
     * @return String
     */
    public String getDisbursementUserId() {
      return this.disbursementUserId;
    }

    /**
     * Set Disbursements User Id.
     *
     * @param disbursementUserId String
     * @return Builder
     */
    public Builder setDisbursementUserId(String disbursementUserId) {
      this.disbursementUserId = disbursementUserId;
      return this;
    }

    /**
     * Get Disbursements Api Secret.
     *
     * @return String
     */
    public String getDisbursementApiSecret() {
      return this.disbursementApiSecret;
    }

    /**
     * Set Disbursement Api Secret.
     *
     * @param disbursementApiSecret String
     * @return Builder
     */
    public Builder setDisbursementApiSecret(String disbursementApiSecret) {
      this.disbursementApiSecret = disbursementApiSecret;
      return this;
    }

    /**
     * Get Base Url.
     *
     * @return String
     */
    public String getBaseUrl() {
      return this.baseUrl;
    }

    /**
     * Set Base Url.
     *
     * @param url String
     * @return Builder
     */
    public Builder setBaseUrl(String url) {
      this.baseUrl = url;
      return this;
    }

    /**
     * Get Target Environment.
     *
     * @return String
     */
    public String getTargetEnvironment() {
      return this.targetEnvironment;
    }

    /**
     * Set Target Environment.
     *
     * @param environment String
     * @return Builder
     */
    public Builder setTargetEnvironment(String environment) {
      this.targetEnvironment = environment;
      return this;
    }

    /**
     * Get Currency.
     *
     * @return String
     */
    public String getCurrency() {
      return this.currency;
    }

    /**
     * Set Currency.
     *
     * @param currency String
     * @return Builder
     */
    public Builder setCurrency(String currency) {
      this.currency = currency;
      return this;
    }

    /**
     * RequestOptions.
     *
     * @return RequestOptions
     */
    public RequestOptions build() {
      return new RequestOptions(
          this.collectionApiSecret,
          this.collectionPrimaryKey,
          this.collectionUserId,

          this.remittanceUserId,
          this.remittancePrimaryKey,
          this.remittanceApiSecret,

          this.disbursementApiSecret,
          this.disbursementPrimaryKey,
          this.disbursementUserId,
          this.baseUrl,
          this.targetEnvironment,
          this.currency
      );

    }


  }

  public static class InvalidRequestOptionsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Ovveride.
     *
     * @param message String
     */
    public InvalidRequestOptionsException(String message) {
      super(message);
    }
  }


}



