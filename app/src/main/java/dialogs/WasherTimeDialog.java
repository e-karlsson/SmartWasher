package dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.NumberPicker;

import com.washer.smart.smartwasher.R;

/**
 * Created by xxottosl on 2015-04-14.
 */
public class WasherTimeDialog extends CustomDialog {

    NumberPicker minPicker, hourPicker, dayPicker;

    public WasherTimeDialog(Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_picker_time);

        minPicker = (NumberPicker) findViewById(R.id.np_picker_minute);
        hourPicker = (NumberPicker) findViewById(R.id.np_picker_hour);
        dayPicker = (NumberPicker) findViewById(R.id.np_picker_day);



        initPickers();
        initButtons();
    }

    private void initButtons(){
        findViewById(R.id.tv_picker_time_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

                if(okCallback != null){
                    int d = dayPicker.getValue();
                    int h = hourPicker.getValue();
                    int m = minPicker.getValue();

                    String day = dayPicker.getDisplayedValues()[d];
                    String hour = hourPicker.getDisplayedValues()[h];
                    String min = minPicker.getDisplayedValues()[m];

                    okCallback.run(new int[]{d,h,m}, new String[]{day, hour, min});
                }

            }
        });

        findViewById(R.id.tv_picker_time_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

                if(cancelFunction != null)
                    cancelFunction.run();
            }
        });
    }

    private void initPickers(){
        int minutes = 60;
        int gap = 5;
        String[] minuteStrings = new String[minutes/gap];

        for(int i = 0; i < minutes/gap; ++i)
            minuteStrings[i] = makeTwo(gap*i);

        int hours = 24;
        String[] hourStrings = new String[hours];
        for(int i = 0; i < hours; ++i)
            hourStrings[i] = makeTwo(i);

        String[] dayStrings = generateDateStrings();
        int days = dayStrings.length;

        minPicker.setMinValue(0);
        minPicker.setMaxValue((minutes/gap) -1);
        minPicker.setDisplayedValues(minuteStrings);

        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(hours -1);
        hourPicker.setDisplayedValues(hourStrings);

        dayPicker.setMinValue(0);
        dayPicker.setMaxValue(days -1);
        dayPicker.setDisplayedValues(dayStrings);

        dayPicker.setWrapSelectorWheel(false);
        dayPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        hourPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        minPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

    }

    public void setIds(int day, int hour, int min){
        dayPicker.setValue(day);
        hourPicker.setValue(hour);
        minPicker.setValue(min);
    }

    private String[] generateDateStrings(){
        int days = 365;
        String[] dayStrings = new String[days];
        dayStrings[0] = "idag";
        dayStrings[1] = "imorgon";
        for(int i = 2; i < days; i++){

            dayStrings[i] = "om "+i+" dagar";
        }

        return dayStrings;
    }

    private String makeTwo(int number){
        if(number < 10) return "0"+number;
        return ""+number;
    }

}
