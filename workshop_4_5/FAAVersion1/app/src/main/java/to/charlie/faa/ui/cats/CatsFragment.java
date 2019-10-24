package to.charlie.faa.ui.cats;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import to.charlie.faa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatsFragment extends Fragment implements AdapterView.OnItemSelectedListener,
        View.OnClickListener, NumberPicker.OnValueChangeListener {

    private TextView setProximityButton;

    public CatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cats, container, false);

        setupSpinner(view, R.id.breeds_spinner, R.array.breed_array);
        setupSpinner(view, R.id.gender_spinner, R.array.gender_array);
        setupSpinner(view, R.id.age_spinner, R.array.age_range_array);

        setProximityButton = view.findViewById(R.id.proximity_button);
        setProximityButton.setOnClickListener(this);
        setProximityButton.setText(
                getResources().getString(
                        R.string.distance,
                        getResources().getInteger(R.integer.min_proximity_distance)));

        return view;
    }

    private void setupSpinner(View view, int spinnerResourceId, int arrayResourceId) {
        Spinner spin = view.findViewById(spinnerResourceId);
        spin.setSelection(1);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                arrayResourceId,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this.getContext(), "Item " + i + " selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.proximity_button:
                showNumberPicker(view, getResources().getText(R.string.proximity_dialog_title).toString());
                break;
        }
    }

    private void showNumberPicker(View view, String title) {
        NumberPickerDialogFragment newDialog = new NumberPickerDialogFragment();
        Bundle args = new Bundle();

        args.putInt(NumberPickerDialogFragment.MIN_VALUE,
                getResources().getInteger(R.integer.min_proximity_distance));

        args.putInt(NumberPickerDialogFragment.MAX_VALUE,
                getResources().getInteger(R.integer.max_proximity_distance));

        args.putString(NumberPickerDialogFragment.MESSAGE,
                getResources().getString(R.string.max_distance_text));

        newDialog.setArguments(args);
        newDialog.show(getChildFragmentManager(), title);
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
        setProximityButton.setText(getResources().getString(R.string.distance, numberPicker.getValue()));
    }
}
