package uk.ac.aber.dcs.cs31620.catsviewer;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Let's try and start an activity to view cats using an implicit
        // Intent. The selected target component will have an intent
        // filter that includes ACTION_VIEW. We also specify
        // a MIME type URI, to focus the search
        Intent intent = new Intent("uk.ac.aber.dcs.cs31620.faa.action.VIEW_CATS", Uri.parse("http://faa.cs31620.dcs.aber.ac.uk/cats"));
        //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://faa.cs31620.dcs.aber.ac.uk/cats"));
        startActivity(intent);
    }
}
