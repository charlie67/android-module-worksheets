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

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import to.charlie.faa.R;
import to.charlie.faa.model.CatList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatsFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, NumberPicker.OnValueChangeListener
{

	private static final int GRID_COLUMN_COUNT = 2;
	private TextView setProximityButton;
	private FloatingActionButton floatingActionButton;

	public CatsFragment()
	{
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		final View view = inflater.inflate(R.layout.fragment_cats, container, false);

		setupSpinner(view, R.id.breeds_spinner, R.array.breed_array);
		setupSpinner(view, R.id.gender_spinner, R.array.gender_array);
		setupSpinner(view, R.id.age_spinner, R.array.age_range_array);

		setProximityButton = view.findViewById(R.id.proximity_button);
		setProximityButton.setOnClickListener(this);
		setProximityButton.setText(getResources().getString(R.string.distance, getResources().getInteger(R.integer.min_proximity_distance)));

		RecyclerView listCats = view.findViewById(R.id.cat_list);
		listCats.setHasFixedSize(true);
		GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), GRID_COLUMN_COUNT);
		listCats.setLayoutManager(gridLayoutManager);

		CatList cats = new CatList();
		CatsRecyclerWithArrayAdapter catsRecyclerAdapter = new CatsRecyclerWithArrayAdapter(getContext(), cats.getCats());

		listCats.setAdapter(catsRecyclerAdapter);

		catsRecyclerAdapter.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				TextView nameView = v.findViewById(R.id.catNameTextView);
				Toast.makeText(getContext(), "Cat " + nameView.getText() + " clicked", Toast.LENGTH_SHORT).show();
			}
		});

		floatingActionButton = view.findViewById(R.id.fab_add);

		floatingActionButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// Deal with the FAB button press
				// Must be  coordinator layout
				View contentView = view.findViewById(R.id.cats_coordinator);
				Snackbar snackbar = Snackbar.make(contentView, "Create cat FAB", Snackbar.LENGTH_LONG);
				View snackbarView = snackbar.getView();
				CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbarView.getLayoutParams();

				params.bottomMargin = 0;
				snackbarView.setLayoutParams(params);

				snackbar.setAction("Undo", new View.OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						// Code to handle Undo goes here
					}
				});
				snackbar.show();
			}
		});

		return view;
	}

	private void setupSpinner(View view, int spinnerResourceId, int arrayResourceId)
	{
		Spinner spin = view.findViewById(spinnerResourceId);
		spin.setSelection(1);
		spin.setOnItemSelectedListener(this);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), arrayResourceId, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(adapter);
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
	{
		Toast.makeText(this.getContext(), "Item " + i + " selected", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView)
	{

	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.proximity_button:
				showNumberPicker(view, getResources().getText(R.string.proximity_dialog_title).toString());
				break;
		}
	}

	private void showNumberPicker(View view, String title)
	{
		NumberPickerDialogFragment newDialog = new NumberPickerDialogFragment();
		Bundle args = new Bundle();

		args.putInt(NumberPickerDialogFragment.MIN_VALUE, getResources().getInteger(R.integer.min_proximity_distance));

		args.putInt(NumberPickerDialogFragment.MAX_VALUE, getResources().getInteger(R.integer.max_proximity_distance));

		args.putString(NumberPickerDialogFragment.MESSAGE, getResources().getString(R.string.max_distance_text));

		newDialog.setArguments(args);
		newDialog.show(getChildFragmentManager(), title);
	}

	@Override
	public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal)
	{
		setProximityButton.setText(getResources().getString(R.string.distance, numberPicker.getValue()));
	}
}
