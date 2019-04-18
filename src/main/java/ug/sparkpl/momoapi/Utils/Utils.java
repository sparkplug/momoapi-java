package ug.sparkpl.momoapi.Utils;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import lombok.NonNull;
import retrofit2.HttpException;
import retrofit2.Response;
import ug.sparkpl.momoapi.models.MomoApiError;

import javax.annotation.Nullable;


public class Utils {

    private Utils() {
    }

    public static boolean isNull(final @Nullable Object object) {
        return object == null;
    }

    public static boolean isNotNull(final @Nullable Object object) {
        return object != null;
    }

    /**
     * Returns the first non-`null` value of its arguments.
     */
    @NonNull
    public static <T> T coalesce(final @Nullable T value, final @NonNull T theDefault) {
        if (value != null) {
            return value;
        }
        return theDefault;
    }

    /**
     * Converts a {@link String} to a {@link Boolean}, or null if the boolean cannot be parsed.
     */
    public static @Nullable
    Boolean toBoolean(final @Nullable String s) {
        if (s != null) {
            return Boolean.parseBoolean(s);
        }

        return null;
    }

    /**
     * Converts a {@link String} to an {@link Integer}, or null if the integer cannot be parsed.
     */
    public static @Nullable
    Integer toInteger(final @Nullable String s) {
        if (s != null) {
            try {
                return Integer.parseInt(s);
            } catch (final @NonNull NumberFormatException e) {
                return null;
            }
        }

        return null;
    }

    /**
     * Converts an {@link Integer} to a {@link String}, can be null if the integer is also null.
     */
    public static @Nullable
    String toString(final @Nullable Integer n) {
        if (n != null) {
            return Integer.toString(n);
        }

        return null;
    }

    /**
     * Converts a {@link Long} to a {@link String}, can be null if the long is also null.
     */
    public static @Nullable
    String toString(final @Nullable Long n) {
        if (n != null) {
            return Long.toString(n);
        }

        return null;
    }

    /**
     * Converts a {@link Float} to a {@link String}, can be null if the float is also null.
     */
    public static @Nullable
    String toString(final @Nullable Float n) {
        if (n != null) {
            return Float.toString(n);
        }

        return null;
    }

    /**
     * Converts a {@link Double} to a {@link String}, can be null if the double is also null.
     */
    public static @Nullable
    String toString(final @Nullable Double n) {
        if (n != null) {
            return Double.toString(n);
        }

        return null;
    }

    /**
     * Cast a `null`able value into a non-`null` value, and throw a `NullPointerException` if the value is `null`.
     */
    public static @NonNull
    <T> T requireNonNull(final @Nullable T value) throws NullPointerException {
        return requireNonNull(value, "Value should not be null.");
    }

    /**
     * Cast a `null`able value into a non-`null` value, and throw a `NullPointerException` if the value is `null`. Provide
     * a message for a better description of why you require this value to be non-`null`.
     */
    public static @NonNull
    <T> T requireNonNull(final @Nullable T value, final @NonNull Class<T> klass) throws NullPointerException {
        return requireNonNull(value, klass.toString() + " required to be non-null.");
    }

    /**
     * Cast a `null`able value into a non-`null` value, and throw a `NullPointerException` if the value is `null`. Provide
     * a message for a better description of why you require this value to be non-`null`.
     */
    public static @NonNull
    <T> T requireNonNull(final @Nullable T value, final @NonNull String message) throws NullPointerException {
        if (value == null) {
            throw new NullPointerException(message);
        }
        return value;
    }

    /**
     * Returns true if the Throwable is an instance of RetrofitError with an
     * http status code equals to the given one.
     */
    public static boolean isHttpStatusCode(Throwable throwable, int statusCode) {
        return throwable instanceof HttpException
                && ((HttpException) throwable).code() == statusCode;
    }

    public MomoApiError parseError(Response<JsonObject> response) {
        Gson gson = new Gson();

        MomoApiError error;

        try {
            error = gson.fromJson(response.body(), MomoApiError.class);
        } catch (JsonSyntaxException e) {
            return new MomoApiError();
        }

        return error;
    }
}

