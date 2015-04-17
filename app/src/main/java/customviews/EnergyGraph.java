package customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;

import model.EnergyPoint;
import model.WashRecord;

/**
 * Created by xxottosl on 2015-04-17.
 */
public class EnergyGraph extends GraphView {
    public EnergyGraph(Context context, List<WashRecord> records) {
        super(context);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 50f);
        setLayoutParams(params);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        WashRecord record = records.get(0);
        List<EnergyPoint> points = record.getPoints();

        DataPoint[] dataPoints = new DataPoint[points.size()];

        for(int i = 0; i < dataPoints.length; ++i){
            dataPoints[i] = new DataPoint(i, points.get(i).getValue());
        }

        series.setTitle("HEj");
        series.resetData(dataPoints);

        addSeries(series);
    }

    public EnergyGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EnergyGraph(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }



}
