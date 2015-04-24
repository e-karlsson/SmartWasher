package dialogs;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.washer.smart.smartwasher.R;

import org.apache.http.auth.NTUserPrincipal;
import org.w3c.dom.Text;

import java.util.Arrays;

import extra.Timer;
import extra.WasherInfo;

/**
 * Created by xxottosl on 2015-04-15.
 */
public class WasherProgramDialog extends CustomDialog {

    NumberPicker programPicker, degreePicker;
    TextView rightButtonTitle, displayTime;
    long startTime;
    boolean readyAt;
    int textColor;
    boolean fromSched = false;
    boolean okTime = true;
    int buttonTextColor;

    public WasherProgramDialog(Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_picker_program);

        programPicker = (NumberPicker) findViewById(R.id.np_picker_program);
        degreePicker = (NumberPicker) findViewById(R.id.np_picker_degree);
        rightButtonTitle = (TextView) findViewById(R.id.tv_picker_program_ok);
        displayTime = (TextView) findViewById(R.id.tv_picker_program_display_time);
        textColor = displayTime.getCurrentTextColor();
        buttonTextColor = rightButtonTitle.getCurrentTextColor();

        initPickers();
        initButtons();
    }

    private void initButtons(){
        findViewById(R.id.tv_picker_program_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOkTime()) return;
                dismiss();

                if(okCallback != null){
                    int p = programPicker.getValue();
                    int d = degreePicker.getValue();


                    String program = programPicker.getDisplayedValues()[p];
                    String degree = degreePicker.getDisplayedValues()[d];


                    okCallback.run(new int[]{p,d}, new String[]{program,degree});
                }

            }
        });

        findViewById(R.id.tv_picker_program_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

                if(cancelFunction != null)
                    cancelFunction.run();
            }
        });
    }

    public void setRightButton(String title){
        rightButtonTitle.setText(title);
    }

    public void setIds(int program, int degree){
        programPicker.setValue(program);
        degreePicker.setValue(degree);
        updateDisplayTime();
    }

    public void setStartTime(long time, boolean readyAt){
        startTime = time;
        this.readyAt = readyAt;
        fromSched = true;
    }

    private void initPickers(){


        String[] programNames = WasherInfo.programNames;
        String[] degrees = WasherInfo.degreeNames;

        Arrays.sort(programNames);

        programPicker.setMinValue(0);
        programPicker.setMaxValue(programNames.length -1);
        programPicker.setDisplayedValues(programNames);

        degreePicker.setMinValue(0);
        degreePicker.setMaxValue(degrees.length -1);
        degreePicker.setDisplayedValues(degrees);

        programPicker.setWrapSelectorWheel(false);
        degreePicker.setWrapSelectorWheel(false);

        programPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        degreePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        programPicker.setOnValueChangedListener( new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                updateDisplayTime();
            }
        });
        degreePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                updateDisplayTime();
            }
        });

        updateDisplayTime();
    }

    private void updateDisplayTime(){
        String progName = programPicker.getDisplayedValues()[programPicker.getValue()];
        String degreeName = degreePicker.getDisplayedValues()[degreePicker.getValue()];

        int washTime = WasherInfo.getWashTime(progName, degreeName);
        int multiplier = (readyAt ? -1 : 0);
        long startingTime = fromSched ? startTime+washTime*60000*multiplier : (startTime = System.currentTimeMillis());

        Timer.TimeInfo ti = Timer.translate(startingTime);
        Timer.TimeInfo tiDone = Timer.translate(startTime+washTime*60000*(1+multiplier));
        String dispString = "Tvättid "+washTime+" minuter\n" +
                "Startar kl "+ti.getHour()+":"+ti.getMinute()+"\n" +
                        "Färdig kl "+tiDone.getHour()+":"+tiDone.getMinute();


        if(okTime = (fromSched && startingTime < System.currentTimeMillis())) {
            displayTime.setTextColor(Color.RED);
            dispString += "\nStarttiden har redan passerat";
            rightButtonTitle.setTextColor(Color.LTGRAY);
        }
        else {
            displayTime.setTextColor(textColor);
            rightButtonTitle.setTextColor(buttonTextColor);
        }

        displayTime.setText(dispString);

    }

    public boolean isOkTime(){
        return !okTime;
    }
}
