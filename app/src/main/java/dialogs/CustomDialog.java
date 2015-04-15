package dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by xxottosl on 2015-04-15.
 */
public abstract class CustomDialog extends Dialog {
    protected Callback okCallback;
    protected Runnable cancelFunction;

    public CustomDialog(Context context) {
        super(context);

        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

                dismiss();
                if(cancelFunction != null)
                    cancelFunction.run();
            }
        });

    }

    public void bindOK(Callback callback){
        okCallback = callback;
    }

    public void bindCancel(Runnable function){
       cancelFunction = function;
    }


    public abstract static class Callback{
        public abstract void run(int [] ids, String[] tags);
    }


}
