package uk.ac.aber.dcs.cs31620.simplefilewithparcelableexample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    // Hard-coded pathname. Clearly a real app should provide some more flexible naming scheme.
    private static final String TEXT_FILE = "file.txt";
    private static final String LOG_TAG = "SimpleFileExample";
    private static final String TEXT_COLOUR = "uk.ac.aber.dcs.cs31620.simplefilewithparcelableexample.TEXT_COLOUR";
    private static final String BACKGROUND_COLOUR = "uk.ac.aber.dcs.cs31620.simplefilewithparcelableexample.BACKGROUND_COLOUR";
    private static final String TEXT_SIZE = "uk.ac.aber.dcs.cs31620.simplefilewithparcelableexample.TEXT_SIZE";

    private static final int CHANGE_PREFERENCES_REQUEST_CODE = 0;
    private EditText editor;
    private File textFile;
    private SharedPreferences settings;
    private EditorPreferences preferences;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editor = findViewById(R.id.text_to_save);
    }

    @Override
    public void onStart(){
        super.onStart();

        textFile = new File(getFilesDir(), TEXT_FILE);
        // Read in any data
        readData();

        // We obtain the app's preferences. By default, the class name is used as the preferences file name
        // under shared_prefs folder. MODE_PRIVATE means that the preferences are private to this app
        // (plus any other apps with the same user ID).
        settings = getPreferences(MODE_PRIVATE);

        // Either use the defaults if a defaults preference has been set, use that.
        int defaultTextColour = ContextCompat.getColor(getApplicationContext(), R.color.default_text_colour);
        int defaultBackgroundColour = ContextCompat.getColor(getApplicationContext(), R.color.default_background_colour);
        String defaultTextSizeStr = getResources().getString(R.string.default_text_size);

        int defaultTextSize = Integer.parseInt(defaultTextSizeStr);

        int textColour = settings.getInt(TEXT_COLOUR, defaultTextColour);
        editor.setTextColor(textColour);

        int backgroundColour = settings.getInt(BACKGROUND_COLOUR, defaultBackgroundColour);
        editor.setBackgroundColor(backgroundColour);

        int textSize = settings.getInt(TEXT_SIZE, defaultTextSize);
        editor.setTextSize(textSize);

        preferences = new EditorPreferences(textColour, backgroundColour, textSize);

    }

    /**
     * This is the recommended place to save persistent data (survives between app invocations). In our case
     * we save to a file.
     */
    @Override
    public void onPause() {
        super.onPause();
        // Save the text
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(textFile)))
        ) {
            writer.write(editor.getText().toString());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem trying to open/create file: " + TEXT_FILE);
        }
    }

    private void readData() {
        // Read the text back in
        String eol = System.getProperty("line.separator");

        // We only read from an existing file if it exists
        if (textFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(textFile)))
            ) {
                String line;
                StringBuilder buffer = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append(eol);
                }

                if (buffer.length() > 0) {
                    editor.setText(buffer.toString());
                    editor.setSelection(buffer.length() - 1);
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem trying to open/read file: " + TEXT_FILE);
            }
        }
    }

    /**
     * An example of how to create an options menu. In this example we
     * simply inflate the menu from a menu layout file.
     * We also want to add a standard drawable to the preferences item
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu from XML resource. Inflate our menu items before any
        // super-classes so that our items are at the top
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editor_options_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Here we handle the menu item click event. If it's item 0 then start an activity
     * to request screen preferences.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_preferences) {
            // We send the current preferences to the activity so that the user
            // can see what the existing values are
            Intent changePreferencesIntent = new Intent(this,
                    ChangePreferencesForm.class);

            // The extra data may be a Parcelable. Using a Parcelable saves having
            // to put each primitive item as a separate piece of data. This makes the
            // code more object-oriented and requires fewer keys.
            changePreferencesIntent.putExtra(ChangePreferencesForm.PREFERENCES, preferences);

            startActivityForResult(changePreferencesIntent, CHANGE_PREFERENCES_REQUEST_CODE);
            return true;
        }
        return false;
    }

    /**
     * Deal with the activity result.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Get data from the intent
        // Make sure we are receiving a result from the called activity
        if (requestCode == CHANGE_PREFERENCES_REQUEST_CODE){
            // If it completed successfully
            if (resultCode == RESULT_OK){
                // Extract the returned preferences replacing anything we already have
                preferences = data.getParcelableExtra(ChangePreferencesForm.PREFERENCES);

                // To change the preferences we must get an Editor, make the changes on the
                // Editor and then commit. This ensures that the preferences file is updated
                // atomically. If two apps are trying to commit at the same time, it's that last
                // one to commit that wins.
                SharedPreferences.Editor preEditor = settings.edit();

                // We cannot store Parcelables in long-lived persistent files (discussed in
                // associated talk)
                preEditor.putInt(TEXT_COLOUR, preferences.getTextColour());
                preEditor.putInt(BACKGROUND_COLOUR, preferences.getBackgroundColour());
                preEditor.putInt(TEXT_SIZE, preferences.getTextSize());
                preEditor.apply();

                // Update the editor's properties with what we got back.
                editor.setTextColor(preferences.getTextColour());
                editor.setBackgroundColor(preferences.getBackgroundColour());
                editor.setTextSize(preferences.getTextSize());
            }
        }
    }
}
