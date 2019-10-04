package uk.ac.aber.dcs.cs31620.twoscreenapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Colour slider activity: displays four colour seekbars and a button to display a second screen
 * @author Chris Loftus
 * @version 22/11/2011
 */
public class MainActivity extends AppCompatActivity {

    private static final int DISPLAY_NAME_COLOUR_SCREEN_REQUEST_CODE = 0;
    private static final CharSequence DEFAULT_TEXT = "Dark grey";
    private static final String NAME_COLOUR_KEY = "NC";
    private TextView namedColour;
    private SeekBar redBar;
    private SeekBar greenBar;
    private SeekBar blueBar;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View screen = findViewById(R.id.screen);
        namedColour = findViewById(R.id.backgroundColour);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(NAME_COLOUR_KEY)) {
                namedColour.setText(savedInstanceState
                        .getString(NAME_COLOUR_KEY));
            }
        }
        if (namedColour.getText().length() == 0)
            namedColour.setText(DEFAULT_TEXT);

        Colour screenColour = new Colour();

        redBar = findViewById(R.id.seekBarRed);
        redBar.setOnSeekBarChangeListener(new ColourChangeListener(screen,
                RGBType.RED, screenColour));
        greenBar = findViewById(R.id.seekBarGreen);
        greenBar.setOnSeekBarChangeListener(new ColourChangeListener(screen,
                RGBType.GREEN, screenColour));
        blueBar = findViewById(R.id.seekBarBlue);
        blueBar.setOnSeekBarChangeListener(new ColourChangeListener(screen,
                RGBType.BLUE, screenColour));
        initialise();
    }

    private void initialise() {
        redBar.setProgress(redBar.getMax() / 2);
        greenBar.setProgress(greenBar.getMax() / 2);
        blueBar.setProgress(blueBar.getMax() / 2);
    }

    public void displayNameColourScreen(View v) {
        Intent displayNameColourIntent = new Intent(this,
                NameColourActivity.class);
        this.startActivityForResult(displayNameColourIntent,
                DISPLAY_NAME_COLOUR_SCREEN_REQUEST_CODE);
    }

    public void resetBackground(View v) {
        initialise();
        namedColour.setText(DEFAULT_TEXT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Get data from the intent
        if (requestCode == DISPLAY_NAME_COLOUR_SCREEN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                namedColour.setText(data
                        .getStringExtra(NameColourActivity.NAMED_COLOUR));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(NAME_COLOUR_KEY, namedColour.getText().toString());
    }
}
