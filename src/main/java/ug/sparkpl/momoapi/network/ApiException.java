package ug.sparkpl.momoapi.network;

import lombok.NonNull;
import ug.sparkpl.momoapi.models.ErrorResponse;

/**
 * An exception class wrapping an {@link ErrorResponse}.
 */
public final class ApiException extends ResponseException {
    private final ErrorResponse error;

    public ApiException(final @NonNull ErrorResponse error, final @NonNull retrofit2.Response response) {
        super(response);
        this.error= error;
    }

    public @NonNull ErrorResponse errorResponse() {
        return error;
    }
}
