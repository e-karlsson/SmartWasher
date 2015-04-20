package extra;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by xxottosl on 2015-04-14.
 */
public class Storage {

    private static Context context;

    private static final String savePath = "washersave";
    //settings
    public static final String REMINDER_TIME = "remindertime";
    public static final String STATIC_PRICE = "staticprice";
    public static final String NOTIFY_STATE = "notifystate";
    public static final String IS_STATIC = "isstatic";

    //start
    public static final String CHEAP_PRICE = "cheapPrice";
    public static final String USE_WIND = "useWind";
    public static final String LAST_DEGREE_STRING = "lastDegreeString";
    public static final String LAST_DEGREE_ID = "lastDegreeID";
    public static final String LAST_PROGRAM_STRING = "lastProgramString";
    public static final String LAST_PROGRAM_ID = "lastProgramID";

    //GCM
    public static final String REG_ID = "regid";
    public static final String APP_VER = "appver";
    public static final String SENT_ID = "sentid";


    public static void init(Context context){
        Storage.context = context;
    }


    public static int loadInt(String name, int defValue){
        SharedPreferences prefs = context.getSharedPreferences(savePath, 0);

        return prefs.getInt(name, defValue);

    }

    public static float loadFloat(String name, float defValue){
        SharedPreferences prefs = context.getSharedPreferences(savePath, 0);

        return prefs.getFloat(name, defValue);

    }

    public static boolean loadBoolean(String name, boolean defValue){
        SharedPreferences prefs = context.getSharedPreferences(savePath, 0);

        return prefs.getBoolean(name, defValue);
    }

    public static String loadString(String name, String defValue){
        SharedPreferences prefs = context.getSharedPreferences(savePath, 0);

        return prefs.getString(name, defValue);
    }

    public static void saveInt(String name, int saveValue){
        SharedPreferences prefs = context.getSharedPreferences(savePath, 0);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(name, saveValue);
        Log.d("SAVED INT: "+name,""+saveValue);
        editor.commit();
    }

    public static void saveFloat(String name, float saveValue){
        SharedPreferences prefs = context.getSharedPreferences(savePath, 0);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putFloat(name, saveValue);
        Log.d("SAVED FLOAT: "+name,""+saveValue);
        editor.commit();
    }

    public static void saveBoolean(String name, boolean saveValue){
        SharedPreferences prefs = context.getSharedPreferences(savePath, 0);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean(name, saveValue);
        Log.d("SAVED BOOLEAN: "+name,""+saveValue);
        editor.commit();
    }

    public static void saveString(String name, String saveValue){
        SharedPreferences prefs = context.getSharedPreferences(savePath, 0);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(name, saveValue);
        Log.d("SAVED STRING: " + name, "" + saveValue);
        editor.commit();
    }

}
