package to.charlie.faa.ui.cats;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import to.charlie.faa.R;

public class NumberPickerDialogFragment extends DialogFragment {

    static final String MIN_VALUE = "min_value";
    static final String MAX_VALUE = "max_value";
    static final String MESSAGE = "message";

    private NumberPicker.OnValueChangeListener valueChangeListener;

    public NumberPickerDialogFragment() {
        //empty constructor required
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment parent = getParentFragment();

        if (parent instanceof NumberPicker.OnValueChangeListener) {
            valueChangeListener = (NumberPicker.OnValueChangeListener) parent;
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final NumberPicker numberPicker = new NumberPicker(getActivity());

        Bundle args = this.getArguments();
        int minValue = args.getInt(MIN_VALUE);
        int maxValue = args.getInt(MAX_VALUE);
        String message = args.getString(MESSAGE);

        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.choose_dialog_title);
        builder.setMessage(message);

        builder.setPositiveButton(R.string.ok_button_text,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (valueChangeListener != null) {
                            valueChangeListener.onValueChange(
                                    numberPicker,
                                    numberPicker.getValue(),
                                    numberPicker.getValue()
                            );
                        }
                    }
                });

        builder.setNegativeButton(R.string.cancel_button_text,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface,
                                        int which) {
                    }
                });

        builder.setView(numberPicker);
        return builder.create();
    }
}