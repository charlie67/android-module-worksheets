package uk.ac.aber.dcs.cs31620.simplefileexample;

import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

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
    private EditText editor;
    private File textFile;

    private static final ExternalType LOCATION_TYPE = ExternalType.PRIVATE_EXTERNAL;

    private enum ExternalType {
        PUBLIC_EXTERNAL,
        PRIVATE_EXTERNAL,
        PRIVATE_INTERNAL
    }

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
    public void onStart() {
        super.onStart();

        if (LOCATION_TYPE.equals(ExternalType.PUBLIC_EXTERNAL)) {
            if (isExternalStorageWritable())
                textFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), TEXT_FILE);
        } else if (LOCATION_TYPE.equals(ExternalType.PRIVATE_EXTERNAL)){
            if (isExternalStorageWritable())
                textFile = new File(this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), TEXT_FILE);
        } else {
            textFile = new File(getFilesDir(), TEXT_FILE);
        }

        // Read in any data
        readData();

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

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
