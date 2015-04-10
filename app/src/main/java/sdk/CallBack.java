package sdk;

/**
 * Created by xxottosl on 2015-04-09.
 */
public abstract class CallBack<T> {

    public abstract void onSuccess(T t);

    public abstract void onError(WasherError error);





}
