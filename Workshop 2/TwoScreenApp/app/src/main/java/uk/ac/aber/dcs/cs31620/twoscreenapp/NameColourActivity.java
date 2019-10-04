package uk.ac.aber.dcs.cs31620.twoscreenapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Handles the second screen
 * @author Chris Loftus
 * @version 22/11/2011
 */
public class NameColourActivity extends AppCompatActivity {
    public static final String NAMED_COLOUR =
            "uk.ac.aber.dcs.cs31620.twoscreenapp.NAMED_COLOUR";
    private EditText colour;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_name_colour);
        colour = findViewById(R.id.setColourTextInput);
    }

    public void setColourClick(View v) {
        Intent data = new Intent();
        int resultCode = RESULT_OK;

        String namedColour = colour.getText().toString();
        data.putExtra(NAMED_COLOUR, namedColour);
        setResult(resultCode, data);
        finish();
    }
}
