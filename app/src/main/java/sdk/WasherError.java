package sdk;

/**
 * Created by xxottosl on 2015-04-09.
 */
public class WasherError {

    private String reason;

    public WasherError(String reason){
        this.reason = reason;
    }

    public String getReason(){
        return reason;
    }


    public static WasherError NOT_INIT = new WasherError("WasherService not initialized.");
    public static WasherError BAD_JSON = new WasherError("Couldn't parse json from server.");
    public static WasherError BAD_CONNECTION = new WasherError("Connection problem.");
}
