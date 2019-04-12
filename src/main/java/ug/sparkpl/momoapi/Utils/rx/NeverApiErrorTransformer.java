package ug.sparkpl.momoapi.Utils.rx;


import lombok.NonNull;
import rx.Observable;
import rx.functions.Action1;
import ug.sparkpl.momoapi.models.ErrorResponse;

import javax.annotation.Nullable;

/**
 * Created by mossplix on 4/29/17.
 */

final class NeverApiErrorTransformer<T> implements Observable.Transformer<T, T> {
    private final @Nullable
    Action1<ErrorResponse> errorAction;

    protected NeverApiErrorTransformer() {
        this.errorAction = null;
    }

    protected NeverApiErrorTransformer(final @Nullable Action1<ErrorResponse> errorAction) {
        this.errorAction = errorAction;
    }

    @Override
    public @NonNull
    Observable<T> call(final @NonNull Observable<T> source) {
        return source
                .doOnError(e -> {
                    final ErrorResponse env = ErrorResponse.fromThrowable(e);
                    if (env != null && errorAction != null) {
                        errorAction.call(env);
                    }
                })
                .onErrorResumeNext(e -> {
                    if (ErrorResponse.fromThrowable(e) == null) {
                        return Observable.error(e);
                    } else {
                        return Observable.empty();
                    }
                });
    }
}
