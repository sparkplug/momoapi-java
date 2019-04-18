package ug.sparkpl.momoapi.network;

/**
 * An exception class for the response.
 */
public final class ApiException extends RuntimeException {


    public ApiException(String response) {
        super(response);

    }
}
