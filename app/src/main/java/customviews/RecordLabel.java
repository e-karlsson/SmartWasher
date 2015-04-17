package customviews;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import model.WashRecord;

/**
 * Created by xxottosl on 2015-04-16.
 */
public class RecordLabel extends LinearLayout {

    public RecordLabel(Context context, WashRecord record) {
        super(context);
        setOrientation(VERTICAL);
        setWeightSum(2);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        setLayoutParams(params);

        LinearLayout top = container(context);
        LinearLayout bot = container(context);

        TextView dateView = text(context, record.getRunDateString());
        TextView energyView = text(context, String.format("%.3f",record.getKiloWattHours())+" kWh");
        TextView programView = text(context, record.getProgramInfo().getName()+" "+record.getProgramInfo().getDegree()+" °C");
        TextView priceView = text(context, String.format("%.3f",record.getCost())+" öre");



        addView(top);
        addView(bot);

        ((LinearLayout)(top.getChildAt(0))).addView(dateView);
        ((LinearLayout)(top.getChildAt(1))).addView(energyView);
        ((LinearLayout)(bot.getChildAt(0))).addView(programView);
        ((LinearLayout)(bot.getChildAt(1))).addView(priceView);


    }

    private LinearLayout container(Context context){
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(HORIZONTAL);
        layout.setWeightSum(10);

        LinearLayout.LayoutParams paramsMain = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0);
        paramsMain.weight = 1;
        layout.setLayoutParams(paramsMain);

        layout.setPadding(10,20,10,20);

        LinearLayout left = new LinearLayout(context);
        left.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

        LinearLayout.LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 7f);
        left.setLayoutParams(params);

        LinearLayout right = new LinearLayout(context);
        right.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

        LinearLayout.LayoutParams params2 = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 3f);
        right.setLayoutParams(params2);

        layout.addView(left);
        layout.addView(right);

        return layout;
    }

    private TextView text(Context context, String string){
        TextView tv = new TextView(context);
        tv.setText(string);

        return tv;
    }
}
