package ug.sparkpl.momoapi.Utils.rx;

import lombok.NonNull;
import rx.Observable;
import ug.sparkpl.momoapi.Utils.Utils;

/**
 * Created by mossplix on 4/29/17.
 */

public final class CoalesceTransformer<T> implements Observable.Transformer<T, T> {
    private final T theDefault;

    public CoalesceTransformer(final @NonNull T theDefault) {
        this.theDefault = theDefault;
    }

    @Override
    public @NonNull
    Observable<T> call(final @NonNull Observable<T> source) {
        return source
                .map(Utils.coalesceWith(theDefault));
    }
}
