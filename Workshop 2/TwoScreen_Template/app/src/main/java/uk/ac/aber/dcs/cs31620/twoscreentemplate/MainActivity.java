package uk.ac.aber.dcs.cs31620.twoscreentemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int DISPLAY_NAME_COLOR_SCREEN_REQUEST_CODE = 0;
    private static final CharSequence DEFAULT_TEXT = "Dark grey";
    private static final String NAME_COLOUR_KEY = "NC";
    private TextView namedColour;
    private SeekBar redBar;
    private SeekBar greenBar;
    private SeekBar blueBar;

    /**
     * Called when the activity is first created.
     */
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(NAME_COLOUR_KEY, namedColour.getText().toString());
    }

    public void displayNameColourScreen(View view)
    {
        Intent displayNameColourIntent = new Intent(this, NameColorActivity.class);
        this.startActivityForResult(displayNameColourIntent, DISPLAY_NAME_COLOR_SCREEN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DISPLAY_NAME_COLOR_SCREEN_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                namedColour.setText(data.getStringExtra(NameColorActivity.NAMED_COLOUR));
            }
        }
    }

    public void resetBackground(View view)
    {
        redBar.setProgress(redBar.getMax() / 2);
        greenBar.setProgress(greenBar.getMax() / 2);
        blueBar.setProgress(blueBar.getMax() / 2);

        namedColour.setText(DEFAULT_TEXT);
    }
}
