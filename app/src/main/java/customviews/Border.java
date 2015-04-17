package customviews;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by xxottosl on 2015-04-17.
 */
public class Border extends LinearLayout {

    public Border(Context context, int color, int padSide, int padTop) {
        super(context);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        setLayoutParams(params);

        setBackgroundColor(color);
        setPadding(padSide, padTop, padSide, padTop);

    }


}
