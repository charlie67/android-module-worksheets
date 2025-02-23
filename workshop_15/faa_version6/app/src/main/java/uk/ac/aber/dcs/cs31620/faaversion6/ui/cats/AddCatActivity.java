package uk.ac.aber.dcs.cs31620.faaversion6.ui.cats;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import androidx.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import uk.ac.aber.dcs.cs31620.faaversion6.R;
import uk.ac.aber.dcs.cs31620.faaversion6.model.AddCatViewModel;
import uk.ac.aber.dcs.cs31620.faaversion6.model.Cat;
import uk.ac.aber.dcs.cs31620.faaversion6.model.Gender;
import uk.ac.aber.dcs.cs31620.faaversion6.model.util.DateTimeConverter;
import uk.ac.aber.dcs.cs31620.faaversion6.model.util.ResourceUtil;

public class AddCatActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String NAME_KEY = "NAME";
    private static final String DATE_KEY = "DATE";
    private static final String IMAGE_PATH_KEY = "IMAGE_PATH";
    private static final String DESCRIPTION_KEY = "DESCRIPTION";
    private static final int REQUEST_TAKE_PHOTO = 1;
    private TextView dobButton;
    private Spinner breedsSpinner;
    private Spinner genderSpinner;

    private String[] genderValues;
    private String[] breedValues;
    private String selectedBreed;
    private String selectedGender;
    private Date selectedDate;
    private String selectedDateStr = "";
    private EditText nameField;
    private AddCatViewModel addCatViewModel;
    private EditText descriptionField;
    private String imagePath = "";
    private File photoFile;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cat);

        addCatViewModel = ViewModelProviders.of(this).get(AddCatViewModel.class);

        // We don't want to show them the default "any" value at the start. That way we don't
        // need to check this when we save. copyOfRange gives us the part we're interested in
        String[] values = getResources().getStringArray(R.array.gender_array);
        genderValues = Arrays.copyOfRange(values, 1, values.length);
        values = getResources().getStringArray(R.array.breed_array);
        breedValues = Arrays.copyOfRange(values, 1, values.length);

        dobButton = findViewById(R.id.datePicker);
        dobButton.setOnClickListener(this);

        nameField = findViewById(R.id.catName);
        descriptionField = findViewById(R.id.description);
        FloatingActionButton addButton = findViewById(R.id.add);
        addButton.setOnClickListener(this);

        if (savedInstanceState != null) {
            String catName = savedInstanceState.getString(NAME_KEY, "");
            if (catName.length() > 0)
                nameField.setText(catName);
            String description = savedInstanceState.getString(DESCRIPTION_KEY, "");
            if (description.length() > 0)
                descriptionField.setText(description);
            imagePath = savedInstanceState.getString(IMAGE_PATH_KEY, "");
            selectedDateStr = savedInstanceState.getString(DATE_KEY, "");
            if (selectedDateStr.length() > 0) {
                selectedDate = DateTimeConverter.fromStringToDate(selectedDateStr,
                        getString(R.string.date_format));
                dobButton.setText(selectedDateStr);
            }
        }

        imageView = findViewById(R.id.catImage);

        ResourceUtil.loadImageBitmap(this, imagePath, imageView);

        imageView.setOnClickListener(this);

        // Place the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        breedsSpinner = setupSpinner(R.id.breeds_spinner, breedValues);
        genderSpinner = setupSpinner(R.id.gender_spinner, genderValues);
    }

    private Spinner setupSpinner(int spinnerResourceId, String[] values) {
        //Getting the instance of breeds Spinner and applying OnItemSelectedListener on it
        Spinner spin = findViewById(spinnerResourceId);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Check the parent to see which spinner
                if (parent == breedsSpinner) {
                    selectedBreed = breedValues[position];
                } else if (parent == genderSpinner) {
                    selectedGender = genderValues[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Creating the ArrayAdapter instance having the name list and our own layout
        // so that the text is right justified and the correct size
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item);

        adapter.addAll(values);

        // Specify the layout to use when the list of items appears. A predefined layout will do
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(adapter);

        return spin;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.datePicker:
                displayDatePicker();
                break;
            case R.id.add:
                insertCat();
                break;
            case R.id.catImage:
                takePicture();
                break;
        }
    }

    private void takePicture() {
        // Code obtained and adapted from: https://developer.android.com/training/camera/photobasics
        // See configuration instructions added to AndroidManifest.xml
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = null;
            try {
                photoFile = ResourceUtil.createImageFile(this);
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(this, getString(R.string.cannot_create_image_file), Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        getPackageName(),
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Get data from the intent
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                // We can get the file
                if (photoFile != null) {
                    imagePath = photoFile.getAbsolutePath();
                    ResourceUtil.loadImageBitmap(this, imagePath, imageView);
                }
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * The user either clicked the back button or got here via the home button.
     * We check that they reall want to leave the page.
     */
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getText(R.string.really_leave_page_title))
                .setMessage(getResources().getText(R.string.really_exit))
                .setNegativeButton(android.R.string.no, null) // Do nothing
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    /**
                     * Only called if the dialog OK button was pressed.
                     * We just continue to return
                     */
                    public void onClick(DialogInterface arg0, int arg1) {

                        // We must do this otherwise we won't go back!
                        AddCatActivity.super.onBackPressed();
                    }
                }).create().show();
    }


    private void insertCat() {

        // We need to check that values are sensible. If they're not then we won't insert
        // a new cat record.
        if (selectedDate != null && nameField.getText().length() > 0) {
            Cat newCat = new Cat(
                    nameField.getText().toString(),
                    Gender.valueOf(selectedGender),
                    selectedBreed,
                    selectedDate,
                    new Date(),
                    imagePath,
                    descriptionField.getText().toString());
            addCatViewModel.insertCat(newCat);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.missing_data), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Called when the activity view is about to be destroyed. Save the state so that we can recreate it when
     * the activity view is rebuilt
     *
     * @param savedInstanceState
     */
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(NAME_KEY, nameField.getText().toString());
        if (selectedDateStr.length() > 0)
            savedInstanceState.putString(DATE_KEY, selectedDateStr);
        if (imagePath.length() > 0)
            savedInstanceState.putString(IMAGE_PATH_KEY, imagePath);
        if (descriptionField.length() > 0)
            savedInstanceState.putString(DESCRIPTION_KEY, descriptionField.toString());

        super.onSaveInstanceState(savedInstanceState);
    }

    private void displayDatePicker() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat df = new SimpleDateFormat(getString(R.string.date_format), Locale.getDefault());

                selectedDate = cal.getTime();
                selectedDateStr = df.format(selectedDate);

                dobButton.setText(selectedDateStr);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
