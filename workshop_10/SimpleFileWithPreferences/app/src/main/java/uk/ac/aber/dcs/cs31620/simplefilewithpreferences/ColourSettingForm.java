package uk.ac.aber.dcs.cs31620.simplefilewithpreferences;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/**
 * Uses the ListActivity's default ListView to display an array of possible text colours.
 * The associated integer value for the chosen colour is returned to the calling activity.
 *
 * @author Created by Chris Loftus in February 2012. Copyright 2012 Aberystwyth
 *         University. All rights reserved.
 */
public class ColourSettingForm extends ListActivity {

    // This ensures that when used as a key, the key is unique throughout the application.
    public static final String COLOUR = "uk.ac.aber.dcs.cs31620.simplefilewithpreferences.ColourSettingForm.COLOUR";
    private static final String LOG_TAG = "ColourSettingForm";
    private int[] colourValues;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We create the array of colours from a resource. More flexible than hard-coding.
        String [] colourNames = getResources().getStringArray(R.array.colour_names);

        // These are the equivalent colour integer values. A further enhancement would be
        // to also change the background of each list item (or a part of the background) to
        // each of the possible colours (an exercise for the reader!)
        colourValues = getResources().getIntArray(R.array.colour_values);

        // We use the predefined layout for each row.
        setListAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, colourNames));
    }

    @Override
    protected void onListItemClick(ListView l, View view, int position, long id) {
        Log.i(LOG_TAG, "onListItemClick started");

        // We are assuming that the two arrays are the same size and that values
        // correspond by position. Clearly some defensive programming needed here!
        int colourSelected = colourValues[position];
        Intent data = new Intent();
        data.putExtra(COLOUR, colourSelected);
        setResult(RESULT_OK, data);
        finish();
    }
}
