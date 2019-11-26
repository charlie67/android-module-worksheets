package uk.ac.aber.dcs.cs31620.faaversion7.ui.cats.listcats;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import uk.ac.aber.dcs.cs31620.faaversion7.R;
import uk.ac.aber.dcs.cs31620.faaversion7.model.Cat;
import uk.ac.aber.dcs.cs31620.faaversion7.model.CatsViewModel;
import uk.ac.aber.dcs.cs31620.faaversion7.ui.cats.addcat.AddCatActivity;

/**
 * UI fragment to display the inmates of the FAA.
 *
 * @author Chris Loftus
 * @version 2 26/07/2019.
 */
public class CatsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, NumberPicker.OnValueChangeListener {
    private static final String PROXIMITY_KEY = "proximity";
    private static final int GRID_COLUMN_COUNT = 2;

    private CatsViewModel catsViewModel;

    private TextView setProximityButton;
    private int proximityValue;
    private CatsRecyclerWithListAdapter catsRecyclerAdapter;
    private Spinner breedsSpinner;
    private Spinner genderSpinner;
    private Spinner ageSpinner;
    private LiveData<List<Cat>> oldCatList;

    public CatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_cats, container, false);

        breedsSpinner = setupSpinner(view, R.id.breeds_spinner, R.array.breed_array);
        genderSpinner = setupSpinner(view, R.id.gender_spinner, R.array.gender_array);
        ageSpinner = setupSpinner(view, R.id.age_spinner, R.array.age_range_array);

        catsViewModel = ViewModelProviders.of(this).get(CatsViewModel.class);
        catsRecyclerAdapter = catsViewModel.getAdapter();
        if (catsRecyclerAdapter == null){
            catsRecyclerAdapter = new CatsRecyclerWithListAdapter(getContext());
            catsViewModel.setAdapter(catsRecyclerAdapter);
        }

        setProximityButton = view.findViewById(R.id.proximity_button);
        setProximityButton.setOnClickListener(this);

        if (savedInstanceState != null) {
            proximityValue = savedInstanceState.getInt(PROXIMITY_KEY, getResources().getInteger(R.integer.min_proximity_distance));
        } else {
            proximityValue = getResources().getInteger(R.integer.min_proximity_distance);
        }
        setProximityButton.setText(getString(R.string.distance, proximityValue));

        // Display the cats in a RecyclerView. Initially we will use hard coded values
        RecyclerView listCats = view.findViewById(R.id.cat_list);

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView. The
        // database version of this app varies the size, so not appropriate
        //listCats.setHasFixedSize(true);

        // Use a GridLayout manager. Second parameter is number of columns.
        // LinearLayout is also possible, but we want two cats per row and
        // all the columns lined up
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), GRID_COLUMN_COUNT);
        listCats.setLayoutManager(gridLayoutManager);

        listCats.setAdapter(catsRecyclerAdapter);

        catsRecyclerAdapter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Get the cats name and display it
                TextView nameView = v.findViewById(R.id.catNameTextView);
                Toast.makeText(getContext(), "Cat " + nameView.getText() + " clicked", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton floatingActionButton = view.findViewById(R.id.fab_add);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Deal with the FAB button press
                Intent newCatIntent = new Intent(getActivity(), AddCatActivity.class);
                startActivity(newCatIntent);
                /*View contentView = view.findViewById(R.id.cats_coordinator);
                Snackbar snackbar = Snackbar.make(contentView, "Create cat FAB", Snackbar.LENGTH_LONG);
                snackbar.setAction("Undo", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // Code to handle Undo goes here
                    }
                });
                snackbar.show();*/
            }
        });

        return view;
    }

    private void setupObserver() {
        LiveData<List<Cat>> catList = searchForCats();

        // If we have replaced the LiveData list that we are interested in
        // then we need to remove any observers associated with the old
        // LiveData list otherwise it will continue to emit onChange events
        // that will affect the accuracy of the catsRecyclerAdapter display
        if (!Objects.equals(oldCatList, catList)) {
            if (oldCatList != null) {
                oldCatList.removeObservers(this);
            }
            oldCatList = catList;
        }

        // Only add an observer to the LiveData object if it doesn't
        // already have one. We have to do this check since searchForCats
        // will just return the same LiveData object if the search criteria have not changed
        if (!catList.hasObservers()) {

            catList.observe(this, new Observer<List<Cat>>() {
                @Override
                public void onChanged(@Nullable List<Cat> cats) {
                    catsRecyclerAdapter.changeDataSet(cats);
                }
            });
        }
    }

    private LiveData<List<Cat>> searchForCats() {
        String selectedBreed = breedsSpinner.getSelectedItem().toString();
        String selectedGender = genderSpinner.getSelectedItem().toString();
        String selectedAge = ageSpinner.getSelectedItem().toString();

        return catsViewModel.getCats(selectedBreed, selectedGender, selectedAge);
    }

    private Spinner setupSpinner(View view, int spinnerResourceId, int arrayResourceId) {
        //Getting the instance of breeds Spinner and applying OnItemSelectedListener on it
        Spinner spin = view.findViewById(spinnerResourceId);
        spin.setSelection(1);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the name list and default spinner layout
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this.getContext(),
                        arrayResourceId,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of items appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(adapter);

        return spin;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.proximity_button:
                showNumberPicker(v, getResources().getText(R.string.proximity_dialog_title).toString());
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        setupObserver();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        proximityValue = picker.getValue();
        setProximityButton.setText(getString(R.string.distance, picker.getValue()));
    }

    /**
     * Called when the fragment view is about to be destroyed. Save the state so that we can recreate it when
     * the fragment view is rebuilt
     *
     * @param savedInstanceState
     */
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(PROXIMITY_KEY, proximityValue);

        super.onSaveInstanceState(savedInstanceState);
    }

    private void showNumberPicker(View view, String title) {
        NumberPickerDialogFragment newDialog = new NumberPickerDialogFragment();
        Bundle args = new Bundle();
        args.putInt(NumberPickerDialogFragment.MIN_VALUE, getResources().getInteger(R.integer.min_proximity_distance));
        args.putInt(NumberPickerDialogFragment.MAX_VALUE, getResources().getInteger(R.integer.max_proximity_distance));
        args.putString(NumberPickerDialogFragment.MESSAGE, getString(R.string.max_distance_text));

        newDialog.setArguments(args);
        newDialog.show(getChildFragmentManager(), title);
    }

}
