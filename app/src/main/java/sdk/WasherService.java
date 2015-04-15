package sdk;

import model.LiveRecord;
import model.Settings;
import model.StartStatus;
import model.Status;
import model.WashRecords;

/**
 * Created by xxottosl on 2015-04-09.
 */
public class WasherService {

    private static boolean initialized = false;
    private static String baseUrl;

    public static void init(String baseUrl, int port){
        WasherService.initialized = true;
        WasherService.baseUrl = baseUrl+":"+port+"/";
    }

    public static boolean isInitialized(){
        return initialized;
    }


    /**
     * Gets live data from server
     * @param callBack handler funciton for callback
     */
    public static void getLiveRecord(CallBack<LiveRecord> callBack){

        GetExecutor executor = new GetExecutor(baseUrl+"get/live", callBack) {
            @Override
            public Object parse(String json) throws Exception {
                LiveRecord record = GetExecutor.getMapper().readValue(json, LiveRecord.class);
                return record;
            }
        };

    }

    /**
     * Gets a fixed amount (or less) of WashRecords from the server
     * @param amount the amount of records wanted
     * @param callBack handling function for callback
     */
    public static void getHistoryByAmount(int amount, CallBack<WashRecords> callBack){

        GetExecutor executor = new GetExecutor(baseUrl+"get/history?amount="+amount, callBack) {
            @Override
            public Object parse(String json) throws Exception {
                WashRecords records = GetExecutor.getMapper().readValue(json, WashRecords.class);
                return records;
            }
        };

    }

    /**
     * Gets all WashRecords since a given milli time
     * @param time milli time given in epoch format
     * @param callBack handling function for callback
     */
    public static void getHistoryByTime(long time, CallBack<WashRecords> callBack){

        GetExecutor executor = new GetExecutor(baseUrl+"get/history?time="+time, callBack) {
            @Override
            public Object parse(String json) throws Exception {
                WashRecords records = GetExecutor.getMapper().readValue(json, WashRecords.class);
                return records;
            }
        };

    }


    /**
     * Stops the running device
     * @param callBack handling function for callback
     */
    public static void stop(CallBack<Status> callBack){
        GetExecutor executor = new GetExecutor(baseUrl+"stop", callBack) {
            @Override
            public Object parse(String json) throws Exception {
                Status status = GetExecutor.getMapper().readValue(json, Status.class);
                return status;
            }
        };
    }

    /**
     * Starts the device at a given time
     * @param time start time given in milli time since epoch
     * @param washTime program time given in minutes
     * @param callBack handling function for callback
     */
    public static void startAt(long time, int washTime, String name, String degree, CallBack<StartStatus> callBack){
        GetExecutor executor = new GetExecutor(baseUrl+"start?time="+time+"&washTime="+washTime+"&name="+name+"&degree="+degree, callBack) {
            @Override
            public Object parse(String json) throws Exception {
                StartStatus status = GetExecutor.getMapper().readValue(json, StartStatus.class);
                return status;
            }
        };
    }


    /**
     * Starts the device to be ready at a specific time. Can give either lowPrice or useWind as extra command
     * @param time end time given in milli time since epoch
     * @param washTime program time given in minutes
     * @param lowPrice true if lowPrice setting is wanted
     * @param useWind true if user wants to use wind energy
     * @param callBack handling function for callback
     */
    public static void startReadyAt(long time, int washTime, String name, String degree, boolean lowPrice, boolean useWind, CallBack<StartStatus> callBack){
        String extraParams = "";
        if(lowPrice) extraParams += "&lowPrice=true";
        if(useWind) extraParams += "&useWind=true";

        GetExecutor executor = new GetExecutor(baseUrl+"start?readyAt="+time+"&washTime="+washTime+"&name="+name+"&degree="+degree+extraParams, callBack) {
            @Override
            public Object parse(String json) throws Exception {
                StartStatus status = GetExecutor.getMapper().readValue(json, StartStatus.class);
                return status;
            }
        };
    }

    /**
     * Sets the servers settings
     * @param push true if user wants notifications
     * @param reminderTime time before second reminder in minutes
     * @param staticPrice true if user has static price
     * @param price static price given in float
     * @param callBack handling function for callback
     */
    public static void setSettings(boolean push, int reminderTime, boolean staticPrice, float price, CallBack<Status> callBack){


        GetExecutor executor = new GetExecutor(baseUrl+"set/settings?push="+push+"&reminderTime="+reminderTime+"&static="+staticPrice+"&price="+price, callBack) {
            @Override
            public Object parse(String json) throws Exception {
                Status status = GetExecutor.getMapper().readValue(json, Status.class);
                return status;
            }
        };
    }

    /**
     * Gets settings from server
     * @param callBack handling function for callback
     */
    public static void getSettings(CallBack<Settings> callBack){


        GetExecutor executor = new GetExecutor(baseUrl+"get/settings", callBack) {
            @Override
            public Object parse(String json) throws Exception {
                Settings settings = GetExecutor.getMapper().readValue(json, Settings.class);
                return settings;
            }
        };
    }


    public static void setDone(CallBack<Status> callBack){

        GetExecutor executor = new GetExecutor(baseUrl+"set/done", callBack) {
            @Override
            public Object parse(String json) throws Exception {
                Status status = GetExecutor.getMapper().readValue(json, Status.class);
                return status;
            }
        };
    }

}
