package uk.ac.aber.dcs.cs31620.twoscreentemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NameColorActivity extends AppCompatActivity
{
	static String NAMED_COLOUR = "uk.ac.aber.dcs.cs31620.twoscreenapp.NAMED_COLOUR";
	private EditText colour;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name_color);
		colour = findViewById(R.id.setColourTextInput);
	}

	public void setColourClick(View view)
	{
		Intent data = new Intent();

		String namedColour = colour.getText().toString();
		data.putExtra(NAMED_COLOUR, namedColour);
		setResult(RESULT_OK, data);
		finish();
	}
}
