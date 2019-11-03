package uk.ac.aber.dcs.cs31620.faaversion5.ui.cats;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.widget.NumberPicker;

import uk.ac.aber.dcs.cs31620.faaversion5.R;

/**
 * Fragment allows us to display a dialog containing a number picker. This means we
 * can simplify the UI in the main fragment display to be, say, a button, rather than embed the
 * awkward NumberPicker directly in the UI.
 *
 * @author http://www.zoftino.com/android-numberpicker-dialog-example and Chris Loftus (adapted)
 * @version 1 18/06/2018.
 */
public class NumberPickerDialogFragment extends DialogFragment {

    public static final String MIN_VALUE = "min_value";
    public static final String MAX_VALUE = "max_value";
    public static final String MESSAGE = "message";

    private static final String NUMBER_PICKER_KEY = "number_picker_value";

    private NumberPicker.OnValueChangeListener valueChangeListener;
    private NumberPicker numberPicker;

    public NumberPickerDialogFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // This is a way of recreating reference to the parent fragment, but
        // only if it's a value change listener. That way we can
        // inform the listener if the NumberPicker value changes
        Fragment parent = getParentFragment();
        if (parent instanceof NumberPicker.OnValueChangeListener){
           valueChangeListener = (NumberPicker.OnValueChangeListener)parent;
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        numberPicker = new NumberPicker(getActivity());
        Bundle args = this.getArguments();
        int minValue = args.getInt(MIN_VALUE);
        int maxValue = args.getInt(MAX_VALUE);
        String message = args.getString(MESSAGE);
        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);

        if (savedInstanceState != null){
            numberPicker.setValue(savedInstanceState.getInt(NUMBER_PICKER_KEY, minValue));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.choose_dialog_title);
        builder.setMessage(message);

        builder.setPositiveButton(R.string.ok_button_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (valueChangeListener != null) {
                    valueChangeListener.onValueChange(numberPicker,
                            numberPicker.getValue(), numberPicker.getValue());
                }
            }
        });

        builder.setNegativeButton(R.string.cancel_button_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                // Do nothing
                //valueChangeListener.onValueChange(numberPicker,
                        //numberPicker.getValue(), numberPicker.getValue());
            }
        });

        builder.setView(numberPicker);
        return builder.create();
    }

    /**
     * Called when the fragment view is about to be destroyed. Save the state so that we can recreate it when
     * the fragment view is rebuilt
     * @param savedInstanceState
     */
   public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt(NUMBER_PICKER_KEY, numberPicker.getValue());
        super.onSaveInstanceState(savedInstanceState);
    }
}
