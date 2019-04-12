package ug.sparkpl.momoapi.network;

import lombok.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ug.sparkpl.momoapi.models.CurrentUserType;

import java.io.IOException;

public final class ApiRequestInterceptor implements Interceptor {
    private final String clientId;
    private final CurrentUserType currentUser;
    private final String endpoint;
    private String social_type ="";

    public ApiRequestInterceptor(final @NonNull String clientId, final @NonNull CurrentUserType currentUser,
                                 final @NonNull String endpoint) {
        this.clientId = clientId;
        this.currentUser = currentUser;
        this.endpoint = endpoint;
    }

    @Override
    public Response intercept(final @NonNull Chain chain) throws IOException {
        return chain.proceed(request(chain.request()));
    }

    private Request request(final @NonNull Request initialRequest) {


        return initialRequest.newBuilder()
                //.header("Accept", "application/json")
                .header ("user-token",currentUser.getAccessToken())
                .method (initialRequest.method (), initialRequest.body ())
                //.url(url(initialRequest.url()))
                .build();
    }

    /*private HttpUrl url(final @NonNull HttpUrl initialHttpUrl) {


        final HttpUrl.Builder builder = initialHttpUrl.newBuilder()
                .setQueryParameter("client_id", clientId);
        if (currentUser.exists()) {
            builder.setQueryParameter("oauth_token", currentUser.getAccessToken());
        }

        return builder.build();
    }*/



}
