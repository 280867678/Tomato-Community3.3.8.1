package retrofit2.adapter.rxjava2;

import retrofit2.Response;

/* loaded from: classes4.dex */
public final class Result<T> {
    public static <T> Result<T> error(Throwable th) {
        if (th == null) {
            throw new NullPointerException("error == null");
        }
        return new Result<>(null, th);
    }

    public static <T> Result<T> response(Response<T> response) {
        if (response == null) {
            throw new NullPointerException("response == null");
        }
        return new Result<>(response, null);
    }

    private Result(Response<T> response, Throwable th) {
    }
}
