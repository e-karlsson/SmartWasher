package dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.washer.smart.smartwasher.R;

import org.apache.http.auth.NTUserPrincipal;
import org.w3c.dom.Text;

/**
 * Created by xxottosl on 2015-04-15.
 */
public class WasherProgramDialog extends CustomDialog {

    NumberPicker programPicker, degreePicker;
    TextView rightButtonTitle;

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


        initPickers();
        initButtons();
    }

    private void initButtons(){
        findViewById(R.id.tv_picker_program_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
    }

    private void initPickers(){


        String[] programNames = new String[]{"Bomull","Ylle","Fintvätt","Snabbtvätt","Träningskläder","Handtvätt"};
        String[] degrees = new String[]{"20","30","40","60","95"};


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
    }


}
