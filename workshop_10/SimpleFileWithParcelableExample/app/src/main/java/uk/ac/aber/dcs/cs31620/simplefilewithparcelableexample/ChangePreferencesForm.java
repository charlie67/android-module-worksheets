package uk.ac.aber.dcs.cs31620.simplefilewithparcelableexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
/**
 * Presents a preferences form to allow the setting of text colour, background colour and
 * text size. Demonstrates the use of Parcelable to wrap up these values when returning them
 * to the calling activity. Also demonstrates the use of spinners, alert dialogs and
 * overriding of onBackPressed
 * The associated integer value for the chosen colour is returned to the calling activity.
 * 
 * @author Created by Chris Loftus in February 2012. Copyright 2012 Aberystwyth
 *         University. All rights reserved.
 */
public class ChangePreferencesForm extends AppCompatActivity {

	public static final String PREFERENCES = "PREFERENCES";
	//private static final String LOG_TAG = "ChangePreferencesForm";
	private int[] colourValues;
	private Spinner textColourSpinner;
	private Spinner backgroundColourSpinner;
	private Spinner textSizeSpinner;
	private EditorPreferences preferences;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.settings);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		textColourSpinner = findViewById(R.id.text_colour_spinner);
		backgroundColourSpinner = findViewById(R.id.background_colour_spinner);
		textSizeSpinner = findViewById(R.id.text_size_spinner);
		
		// We'll use one listener object that handles selection on any of the spinners
		PreferencesItemSelectedListener preferencesSpinnerListener = new PreferencesItemSelectedListener();
		
		// Call private setAdapter method to create an ArrayAdapter and associate with a spinner.
		// We pass in the array resource id so that we can populate the adapter with data.
		// We also pass in the listener to associate with each spinner.
		setAdapter(textColourSpinner, R.array.colour_names, preferencesSpinnerListener);
		setAdapter(backgroundColourSpinner, R.array.colour_names, preferencesSpinnerListener);
		setAdapter(textSizeSpinner, R.array.text_sizes, preferencesSpinnerListener);
		
		// These are the equivalent colour integer values for the colour_names. 
		colourValues = getResources().getIntArray(R.array.colour_values);
		
		// The text sizes have to be stored as a string array in the resource since adapters
		// will only work with arrays of strings. So here we create an equivalent int array
		// to make it easier find the position of the passed in preference's text size within
		// the array (see below)
		int[] textSizeValues = toIntArray(getResources().getStringArray(R.array.text_sizes));
		
		// Get the Intent associated with the incoming request and extract its preferences Parcelable
		// We should really do some defensive programming here. What if it didn't exist?
		Intent intent = getIntent();	
		preferences = intent.getParcelableExtra(PREFERENCES);
		
		// Now set the selection value for each of the spinners. We use a private finder method
		// to search the values arrays for the preference value.
		textColourSpinner.setSelection(findPosition(colourValues, preferences.getTextColour()));
		backgroundColourSpinner.setSelection(findPosition(colourValues, preferences.getBackgroundColour()));
		textSizeSpinner.setSelection(findPosition(textSizeValues, preferences.getTextSize()));	
	}

	private int[] toIntArray(String[] stringArray) {
		int[] result = new int[stringArray.length];
		for (int i=0; i<result.length; i++){
			result[i] = Integer.parseInt(stringArray[i]);
		}
		return result;
	}

	private int findPosition(int[] resourceArray, int value) {
		for (int i=0; i<resourceArray.length; i++){
			if (resourceArray[i] == value)
				return i;
		}
		return 0; // As a default return first position
	}

	private void setAdapter(Spinner spinner, int arrayResourceId,
			PreferencesItemSelectedListener spinnerListener) {
		
		// Allows us to create from a resource rather than a hard-coded array
		// We use a predefined view for the top showing item (at top of drop-down list)
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, arrayResourceId, android.R.layout.simple_spinner_item);
		// We provide a different layout (again predefined) for the drop-down items:
		// radio button list.
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
	    spinner.setAdapter(adapter);
	    spinner.setOnItemSelectedListener(spinnerListener);
	}

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
	
	/**
	 * Rather than have an explicit "return" button we rely on the back button press. However,
	 * we need to send back the preferences Parcelable using an Intent and calling setResult(...).
	 * We also use an AlertDialog to check whether the user really wanted to return. This is 
	 * overkill in this example, but provides an example you might want to use elsewhere.
	 */
	@Override
	public void onBackPressed() {
	    new AlertDialog.Builder(this)
	        .setTitle(getResources().getText(R.string.really_exit_title))
	        .setMessage(getResources().getText(R.string.really_exit))
	        .setNegativeButton(android.R.string.no, null)
	        .setPositiveButton(android.R.string.yes, new OnClickListener() {

	        	/**
	        	 * Only called if the dialog OK button was pressed.
	        	 * Prepare the Intent result to return. We create a
	        	 * Parcelable and return
	        	 */
	            public void onClick(DialogInterface arg0, int arg1) {
	            	Intent data = new Intent();
	            	data.putExtra(PREFERENCES, preferences);
	            	setResult(RESULT_OK, data);

	            	// We must do this otherwise we won't go back!
	                ChangePreferencesForm.super.onBackPressed();
	            }
	        }).create().show();
	}


	/**
	 * Internal class that handles spinner selections.
	 *
	 */
	class PreferencesItemSelectedListener implements OnItemSelectedListener {

		/**
		 * Called when a spinner item is selected. pos will equate to the
		 * position in the ArrayAdapter's array allowing us to get the item and place in the
		 * preferences Parcelable
		 */
		@Override
		public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	 
	    	 if (parent == textColourSpinner){
	    		 preferences.setTextColour(colourValues[pos]);
	    	 } else if (parent == backgroundColourSpinner){
	    		preferences.setBackgroundColour(colourValues[pos]);
	    	 } else if (parent == textSizeSpinner){
	    		 preferences.setTextSize(Integer.parseInt((String)parent.getItemAtPosition(pos)));
	    	 } // else do nothing
	    }

	    @Override
	    public void onNothingSelected(AdapterView<?> parent) {
	      // Do nothing.
	    }
	}
	
	
}
