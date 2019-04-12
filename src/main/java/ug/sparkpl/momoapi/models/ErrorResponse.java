package ug.sparkpl.momoapi.models;

import lombok.NonNull;
import ug.sparkpl.momoapi.Utils.AutoGson;
import ug.sparkpl.momoapi.network.ApiException;

import javax.annotation.Nullable;
import java.util.List;

@AutoGson
public abstract class ErrorResponse {

    public abstract @Nullable
    List<String> errorMessages();
    public abstract int httpCode();


        /**
     * Tries to extract an {@link ErrorResponse} from an exception, and if it
     * can't returns null.
     */
    public static @Nullable
    ErrorResponse fromThrowable(final @NonNull Throwable t) {
        if (t instanceof ApiException) {
            final ApiException exception = (ApiException) t;
            return exception.errorResponse();
        }

        return null;
    }



    /**
     * Returns the first error message available, or `null` if there are none.
     */
    public @Nullable String errorMessage() {
        if (errorMessages() == null) {
            return null;
        } else {
            return first(errorMessages());
        }
    }

    public static @Nullable
    <T> T first(final @NonNull List<T> xs) {
        return xs.size() > 0 ? xs.get(0) : null;
    }

}
