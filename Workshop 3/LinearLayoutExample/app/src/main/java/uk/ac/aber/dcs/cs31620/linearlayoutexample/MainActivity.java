package uk.ac.aber.dcs.cs31620.linearlayoutexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView greeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_constraint_layout);

        editText = findViewById(R.id.textInput);
        greeting = findViewById(R.id.greeting);
    }


    public void changeGreeting(View view) {
        greeting.setText(editText.getText());
    }
}
