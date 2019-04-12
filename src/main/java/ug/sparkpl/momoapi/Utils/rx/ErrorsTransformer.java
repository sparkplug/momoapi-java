package ug.sparkpl.momoapi.Utils.rx;


import lombok.NonNull;
import rx.Notification;
import rx.Observable;

/**
 * Created by mossplix on 4/29/17.
 */

public final class ErrorsTransformer<T> implements Observable.Transformer<Notification<T>, Throwable> {

    @Override
    public @NonNull
    Observable<Throwable> call(final @NonNull Observable<Notification<T>> source) {
        return source
                .filter(Notification::hasThrowable)
                .map(Notification::getThrowable);
    }
}

