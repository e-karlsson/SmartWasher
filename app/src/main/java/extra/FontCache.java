package extra;

/**
 * Created by xxottosl on 2015-04-15.
 */
import java.util.Hashtable;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

public class FontCache {

    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

    public static Typeface get(String name, Context context) {
        Typeface tf = fontCache.get(name);
        if(tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), name);
            }
            catch (Exception e) {
                Log.d("fontError", e.getMessage());
                return null;
            }
            fontCache.put(name, tf);
        }
        return tf;
    }

    public static void setCustomFont(TextView textview, String font, Context context) {
        if(font == null) {
            return;
        }
        Typeface tf = FontCache.get(font, context);
        if(tf != null) {
            textview.setTypeface(tf);
        }else{
            Log.d("FONT "+font+" is ","NULL");
        }
    }

}
