package sdk;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Created by xxottosl on 2015-04-09.
 */
public abstract class GetExecutor<T> {

    private CallBack callBack;
    private static ObjectMapper mapper;

     public GetExecutor(String url, CallBack<T> callBack){
         this.callBack = callBack;
         Log.d("HTTP GET", url);
         new Get().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
     }


    private class Get extends AsyncGetTask{

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(error){
                callBack.onError(washerError);
            }else{
                Log.d("HTTP RESULT", result);
                try{
                    T t = parse(result);
                    callBack.onSuccess(t);
                }catch(Exception e){
                    Log.d("myapp", Log.getStackTraceString(e));
                    callBack.onError(new WasherError(""+e.getMessage()));
                }
            }
        }
    }

    public abstract T parse(String json) throws Exception;


    public static ObjectMapper getMapper() {
        if(mapper != null) return mapper;
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        GetExecutor.mapper = mapper;
        return mapper;
    }


}
