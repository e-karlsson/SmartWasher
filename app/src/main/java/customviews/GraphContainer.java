package customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.washer.smart.smartwasher.R;

import extra.GraphData;

/**
 * Created by xxottosl on 2015-04-17.
 */
public class GraphContainer extends LinearLayout {

    LinearLayout left, bot, right;

    public GraphContainer(Context context, GraphData data) {
        super(context);
        setOrientation(VERTICAL);
        setWeightSum(100f);

        LayoutParams paramsMain = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(paramsMain);

        LinearLayout top = new LinearLayout(context);
        top.setOrientation(HORIZONTAL);
        LayoutParams topParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 85f);
        top.setLayoutParams(topParams);
        top.setWeightSum(100f);

        bot = new LinearLayout(context);
        LayoutParams botParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,15f);
        bot.setLayoutParams(botParams);
        bot.setOrientation(HORIZONTAL);


        left = new LinearLayout(context);
        LayoutParams sideParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 15f);
        left.setLayoutParams(sideParams);
        left.setOrientation(VERTICAL);

        EnergyGraph graph = new EnergyGraph(context, data);
        LayoutParams graphParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 70f);
        graph.setLayoutParams(graphParams);

        right = new LinearLayout(context);
        right.setLayoutParams(sideParams);
        right.setOrientation(VERTICAL);



        left.setWeightSum(5f);
        right.setWeightSum(5f);
        bot.setWeightSum(7f);
        bot.setGravity(Gravity.LEFT);

        top.addView(left);
        top.addView(graph);
        top.addView(right);

        addView(top);
        addView(bot);

        createLeftTags(context, data);
        createRightTags(context, data);
        createBotTags(context, data);

    }

    private String format(float value){
        return String.format("%.2f", value);
    }

    private void createLeftTags(Context context, GraphData data){
        float highestEnergy = data.getHighestEnergy();
        left.addView(leftTag(context, "kWh"));
        left.addView(leftTag(context, ""+format(highestEnergy)));
        left.addView(leftTag(context, ""+format(highestEnergy*0.75f)));
        left.addView(leftTag(context, ""+format(highestEnergy/2f)));
        left.addView(leftTag(context, "0"));

    }

    private void createRightTags(Context context, GraphData data){
        float highestPrice = data.getHighestPrice();
        right.addView(rightTag(context, "Öre"));
        right.addView(rightTag(context, format(highestPrice)));
        right.addView(rightTag(context, format(highestPrice*0.75f)));
        right.addView(rightTag(context, format(highestPrice/2f)));
        right.addView(rightTag(context, "0"));

    }

    private void createBotTags(Context context, GraphData data){

        if(data.isWeek()) {
            bot.addView(botTag(context, "Datum"));
            bot.addView(botTag(context, data.getDateAt(0f)));
            bot.addView(botTag(context, data.getDateAt(0.25f)));
            bot.addView(botTag(context, data.getDateAt(0.50f)));
            bot.addView(botTag(context, data.getDateAt(0.75f)));
            bot.addView(botTag(context, data.getDateAt(1f)));
        }else if(data.isMonth()){
            bot.addView(botTag(context, "Vecka"));
            bot.addView(botTag(context, ""));
            bot.addView(botTag(context, data.getWeekAt(0f)));
            bot.addView(botTag(context, data.getWeekAt(0.33f)));
            bot.addView(botTag(context, data.getWeekAt(0.66f)));
            bot.addView(botTag(context, data.getWeekAt(1f)));
        }else{
            bot.addView(botTag(context, "Månad"));
            bot.addView(botTag(context, ""));
            bot.addView(botTag(context, data.getMonthAt(0f)));
            bot.addView(botTag(context, data.getMonthAt(0.33f)));
            bot.addView(botTag(context, data.getMonthAt(0.66f)));
            bot.addView(botTag(context, data.getMonthAt(1f)));
        }
    }

    private TextView botTag(Context context, String text){
        TextView tv = new TextView(context);


        LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
        tv.setLayoutParams(params);


        tv.setTextSize(10f);

        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        tv.setText(text);
        return tv;
    }

    private TextView leftTag(Context context, String text){
        TextView tv = new TextView(context);


        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f);
        tv.setLayoutParams(params);


        tv.setTextSize(10f);
        tv.setTextColor(getResources().getColor(R.color.tieto_darkblue));
        tv.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        tv.setText(text);
        return tv;
    }

    private TextView rightTag(Context context, String text){
        TextView tv = new TextView(context);


        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f);
        tv.setLayoutParams(params);


        tv.setTextSize(10f);
        tv.setTextColor(getResources().getColor(R.color.tieto_green));
        tv.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        tv.setText(text);
        return tv;
    }




    public GraphContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GraphContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
