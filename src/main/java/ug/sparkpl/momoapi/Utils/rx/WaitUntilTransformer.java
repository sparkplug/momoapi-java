package ug.sparkpl.momoapi.Utils.rx;

/**
 * Created by mossplix on 5/9/17.
 */

import lombok.NonNull;

import rx.Observable;

public final class WaitUntilTransformer<T, R> implements Observable.Transformer<T, T> {
    @NonNull private final Observable<R> until;

    public WaitUntilTransformer(final @NonNull Observable<R> until) {
        this.until = until;
    }

    @Override
    public Observable<T> call(final @NonNull Observable<T> source) {
        return until.take(1).flatMap(__ -> source);
    }
}
