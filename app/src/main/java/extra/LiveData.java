package extra;

import model.LiveRecord;

/**
 * Created by xxottosl on 2015-04-16.
 */
public class LiveData {

    private static LiveRecord record;

    public static synchronized LiveRecord getLiveRecord(){
        return record;
    }

    public static synchronized void setLiveRecord(LiveRecord record){
        LiveData.record = record;
    }

    public static synchronized int getState(){
        if(record == null) return 0;
        return record.getState();
    }

}
