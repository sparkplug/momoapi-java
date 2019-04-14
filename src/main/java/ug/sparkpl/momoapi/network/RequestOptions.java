package ug.sparkpl.momoapi.network;


import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class RequestOptions {


    private String COLLECTION_USER_ID;
    private String COLLECTION_API_SECRET;
    private String COLLECTION_PRIMARY_KEY;

    private String REMITTANCE_PRIMARY_KEY;
    private String REMITTANCE_USER_ID;
    private String REMITTANCE_API_SECRET;


    private String DISBURSEMENT_PRIMARY_KEY;
    private String DISBURSEMENT_USER_ID;
    private String DISBURSEMENT_API_SECRET;

    private String BASE_URL = "https://ericssonbasicapi2.azure-api.net";


    private RequestOptions() {

    }

    public static Builder builder() {
        return new Builder();
    }


    public String getCollectionUserId() {
        return this.COLLECTION_USER_ID;
    }

    public String getCollectionApiSecret() {
        return this.COLLECTION_API_SECRET;
    }

    public String getCollectionPrimaryKey() {
        return this.COLLECTION_PRIMARY_KEY;
    }

    public String getRemittancePrimaryKey() {
        return this.REMITTANCE_PRIMARY_KEY;
    }


    public String getRemittanceUserId() {
        return this.REMITTANCE_USER_ID;
    }

    public String getRemittanceApiSecret() {
        return this.REMITTANCE_API_SECRET;
    }


    public String getDisbursementPrimaryKey() {
        return this.DISBURSEMENT_PRIMARY_KEY;
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


    public static final class Builder {

        private String COLLECTION_USER_ID;
        private String COLLECTION_API_SECRET;
        private String COLLECTION_PRIMARY_KEY;
        private String REMITTANCE_PRIMARY_KEY;
        private String REMITTANCE_USER_ID;
        private String REMITTANCE_API_SECRET;
        private String DISBURSEMENT_PRIMARY_KEY;
        private String DISBURSEMENT_USER_ID;
        private String DISBURSEMENT_API_SECRET;

        private String BASE_URL = "https://ericssonbasicapi2.azure-api.net";

        private String CURRENCY = "EUR";


        /**
         * Constructs a request options builder with the global parameters (API key, client ID and
         * API version) as default values.
         */
        public Builder() {
            this.COLLECTION_USER_ID = System.getenv("COLLECTION_USER_ID");
            this.COLLECTION_API_SECRET = System.getenv("COLLECTION_API_SECRET");
            this.COLLECTION_PRIMARY_KEY = System.getenv("COLLECTION_PRIMARY_KEY");

            this.REMITTANCE_PRIMARY_KEY = System.getenv("REMITTANCE_PRIMARY_KEY");
            this.REMITTANCE_USER_ID = System.getenv("REMITTANCE_USER_ID");
            this.REMITTANCE_API_SECRET = System.getenv("REMITTANCE_API_SECRET");

            this.DISBURSEMENT_PRIMARY_KEY = System.getenv("DISBURSEMENT_PRIMARY_KEY");
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


        public Builder setCollectionUserId(String collectionUserId) {
            this.COLLECTION_USER_ID = collectionUserId;
            return this;
        }

        public Builder setCollectionApiSecret(String collectionApiSecret) {
            this.COLLECTION_API_SECRET = collectionApiSecret;
            return this;
        }

        public Builder setCollectionPrimaryKey(String collectionPrimaryKey) {
            this.COLLECTION_PRIMARY_KEY = collectionPrimaryKey;
            return this;
        }

        public Builder setRemittanceUserId(String remittanceUserId) {
            this.REMITTANCE_USER_ID = remittanceUserId;
            return this;
        }

        public Builder setRemittanceApiSecret(String remittanceApiSecret) {
            this.REMITTANCE_API_SECRET = remittanceApiSecret;
            return this;
        }

        public Builder setRemittancePrimaryKey(String remittancePrimaryKey) {
            this.REMITTANCE_PRIMARY_KEY = remittancePrimaryKey;
            return this;
        }

        public Builder setDisbursementUserId(String disbursementUserId) {
            this.DISBURSEMENT_USER_ID = disbursementUserId;
            return this;
        }

        public Builder setDisbursementApiSecret(String disbursementApiSecret) {
            this.DISBURSEMENT_API_SECRET = disbursementApiSecret;
            return this;
        }

        public Builder setDisbursementPrimaryKey(String disbursementPrimaryKey) {
            this.DISBURSEMENT_PRIMARY_KEY = disbursementPrimaryKey;
            return this;
        }

        public Builder setBaseUrl(String url) {
            this.BASE_URL = url;
            return this;
        }


        public RequestOptions build() {
            RequestOptions opts = new RequestOptions();
            opts.COLLECTION_API_SECRET = this.COLLECTION_API_SECRET;
            opts.COLLECTION_PRIMARY_KEY = this.COLLECTION_PRIMARY_KEY;
            opts.COLLECTION_USER_ID = this.COLLECTION_USER_ID;

            opts.REMITTANCE_USER_ID = this.REMITTANCE_USER_ID;
            opts.REMITTANCE_PRIMARY_KEY = this.REMITTANCE_PRIMARY_KEY;
            opts.REMITTANCE_API_SECRET = this.REMITTANCE_API_SECRET;

            opts.DISBURSEMENT_API_SECRET = this.DISBURSEMENT_API_SECRET;
            opts.DISBURSEMENT_PRIMARY_KEY = this.DISBURSEMENT_PRIMARY_KEY;
            opts.DISBURSEMENT_USER_ID = this.DISBURSEMENT_USER_ID;

            return opts;

        }


    }


    public static class InvalidRequestOptionsException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public InvalidRequestOptionsException(String message) {
            super(message);
        }
    }


}



