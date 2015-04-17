package customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.washer.smart.smartwasher.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import extra.GraphData;
import model.EnergyPoint;
import model.WashRecord;

/**
 * Created by xxottosl on 2015-04-17.
 */
public class EnergyGraph extends GraphView {
    public EnergyGraph(Context context, GraphData data) {
        super(context);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 70f);
        setLayoutParams(params);

        LineGraphSeries<DataPoint> seriesEnergy = new LineGraphSeries<>();

        ArrayList<GraphData.DateData> dds = data.getDates();

        DataPoint[] pointsEnergy = new DataPoint[dds.size()];

        for(int i = 0; i < pointsEnergy.length; ++i){
            GraphData.DateData dd = dds.get(i);
            pointsEnergy[i] = new DataPoint(i, dd.getTotalEnergy());
        }


        addSeries(seriesEnergy);

        getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {

            @Override
            public String formatLabel(double value, boolean isValueX) {
                return "";
            }
        });


        float scale = data.getTotalEnergy()/data.getTotalPrice();

        seriesEnergy.resetData(pointsEnergy);

        LineGraphSeries<DataPoint> seriesPrice = new LineGraphSeries<>();

        DataPoint[] pointsPrice = new DataPoint[dds.size()];

        for(int i = 0; i < pointsPrice.length; ++i){
            GraphData.DateData dd = dds.get(i);
            pointsPrice[i] = new DataPoint(i, dd.getTotalPrice()*scale);
        }


        addSeries(seriesPrice);

        seriesPrice.setColor(getResources().getColor(R.color.tieto_green));

        seriesPrice.resetData(pointsPrice);
    }

    public EnergyGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EnergyGraph(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }



}
