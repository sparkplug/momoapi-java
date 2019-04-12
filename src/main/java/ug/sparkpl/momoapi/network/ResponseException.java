package ug.sparkpl.momoapi.network;

import lombok.NonNull;

public class ResponseException extends RuntimeException {
    private final retrofit2.Response response;

    public ResponseException(final @NonNull retrofit2.Response response) {
        this.response = response;
    }

    public @NonNull retrofit2.Response response() {
        return response;
    }
}
