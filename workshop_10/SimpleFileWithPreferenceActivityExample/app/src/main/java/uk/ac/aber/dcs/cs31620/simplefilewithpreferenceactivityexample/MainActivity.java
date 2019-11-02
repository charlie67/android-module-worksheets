package uk.ac.aber.dcs.cs31620.simplefilewithpreferenceactivityexample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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
    private static final int CHANGE_COLOUR_SETTING_REQUEST_CODE = 0;

    private EditText editor;
    private File textFile;
    private String defaultTextColour;
    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editor = findViewById(R.id.text_to_save);
    }

    @Override
    public void onStart(){
        super.onStart();

        // We have to provide the full path if we want to check later whether the file exists
        textFile = new File(getFilesDir(), TEXT_FILE);

        // Read in any data
        readData();

        // Either use the default colour or if a colour preference has been set, use that.
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        defaultTextColour = (String) getResources().getString(R.string.default_text_colour);

        // parseInt won't work with 8 hex characters, so I have to explicitly "or" in the alpha component
        int textColour = 0xFF000000 | Integer.parseInt(sharedPrefs.getString("text_colour", defaultTextColour), 16);
        editor.setTextColor(textColour);
    }

    /**
     * This is the recommended place to save persistent data (survives between app invocations). In our case
     * we save to a file.
     */
    @Override
    public void onPause() {
        super.onPause();
        // Save the text
        String name = textFile.getAbsolutePath();
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(textFile)));
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
                    new FileInputStream(textFile)));
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
     * An example of how to create an options menu. In this example we programmatically
     * add a menu item (will add to the bottom of any menu defined in superclasses). The
     * menu item does not belong to a menu group (flat structure, MENU.NONE).
     * We give it the id 0 so that we can use that in the onOptionsItemSelected method.
     * We don't care about the positioning of the item in the menu (Menu.NONE).
     * We provide the text for the menu item.
     * A more flexible approach (shown in a later example) is to use a menu layout file and
     * use a MenuInflater.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 0, Menu.NONE, getString(R.string.change_text_colour_setting));
        return super.onCreateOptionsMenu(menu); // Must be true for the menu to be displayed.
    }

    /**
     * Here we handle the menu item click event. If it's item 0 then
     * start an activity to display text colour choices.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            Intent changeTextColourSettingIntent = new Intent(this,
                    ColourSettingForm.class);

            startActivityForResult(changeTextColourSettingIntent,
                    CHANGE_COLOUR_SETTING_REQUEST_CODE);
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
        if (requestCode == CHANGE_COLOUR_SETTING_REQUEST_CODE){
            // We simply see what preferences are now

            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            String textColourStr = sharedPrefs.getString("text_colour", "" + defaultTextColour);
            editor.setTextColor(0xFF000000 | Integer.parseInt(textColourStr, 16));
        }
    }
}
