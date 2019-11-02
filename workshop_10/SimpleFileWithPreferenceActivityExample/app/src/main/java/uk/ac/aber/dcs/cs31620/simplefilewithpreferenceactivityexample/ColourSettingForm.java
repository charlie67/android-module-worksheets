package uk.ac.aber.dcs.cs31620.simplefilewithpreferenceactivityexample;

import android.os.Bundle;


import android.preference.PreferenceFragment;

/**
 * Uses the ListActivity's default ListView to display an array of possible text colours.
 * The associated integer value for the chosen colour is returned to the calling activity.
 * 
 * @author Created by Chris Loftus in February 2012. Copyright 2012 Aberystwyth
 *         University. All rights reserved.
 */
public class ColourSettingForm extends AppCompatPreferenceActivity {


    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
    public void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }


    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
