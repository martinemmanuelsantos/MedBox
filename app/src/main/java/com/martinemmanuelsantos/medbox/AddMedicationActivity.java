package com.martinemmanuelsantos.medbox;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by nutel on 1/5/2017.
 */

public class AddMedicationActivity extends AppCompatActivity {

    /* Constants */
    static final int REQUEST_IMAGE_CAPTURE = 1;

    /* UI Elements */
    private EditText editTextCustomUnits, editTextTotalSupply, editTextLowSupply;
    private TextView textViewTotalSupplyUnits, textViewLowSupplyUnits;
    private ImageView imageViewIcon;
    private Spinner spinnerDoseType, spinnerMethodTaken, spinnerInstruction;
    private Switch switchPrescription, switchLowSupplyWarning;
    private Button buttonNextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        createEditTexts();
        createTextViews();
        createImageView();
        createSpinners();
        createSwitch();
        createButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //region Create UI elements and set listeners

    private void createEditTexts() {

        editTextCustomUnits = (EditText) findViewById(R.id.edit_text_add_medication_custom_units);
        editTextTotalSupply = (EditText) findViewById(R.id.edit_text_add_medication_total_supply);
        editTextLowSupply = (EditText) findViewById(R.id.edit_text_add_medication_low_supply);

        // Change the appropriate TextViews when a custom unit is entered
        editTextCustomUnits.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textViewTotalSupplyUnits.setText(charSequence);
                textViewLowSupplyUnits.setText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void createTextViews() {

        textViewTotalSupplyUnits = (TextView) findViewById(R.id.text_view_add_medication_total_supply_units);
        textViewLowSupplyUnits = (TextView) findViewById(R.id.text_view_add_medication_low_supply_units);

    }

    private void createImageView() {

        imageViewIcon = (ImageView) findViewById(R.id.image_view_add_medication_icon);

        // Create an intent to launch the default camera app
        imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

    }

    private void createSpinners() {

        spinnerDoseType = (Spinner) findViewById(R.id.spinner_add_medication_dose_type);
        spinnerMethodTaken = (Spinner) findViewById(R.id.spinner_add_medication_method_taken);
        spinnerInstruction = (Spinner) findViewById(R.id.spinner_add_medication_instruction);

        // Set the units on the appropriate TextViews as the dose type is changed
        spinnerDoseType.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // If "Other" is selected, show the custom dose unit EditText, otherwise hide it
                // and set the text to the spinner value
                if (spinnerDoseType.getSelectedItem().toString().equals("Other")) {
                    findViewById(R.id.edit_text_add_medication_custom_units).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.edit_text_add_medication_custom_units).setVisibility(View.INVISIBLE);
                    textViewTotalSupplyUnits.setText(spinnerDoseType.getSelectedItem().toString());
                    textViewLowSupplyUnits.setText(spinnerDoseType.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

    }

    private void createSwitch() {

        switchPrescription = (Switch) findViewById(R.id.switch_add_medication_prescription);
        switchLowSupplyWarning = (Switch) findViewById(R.id.switch_add_medication_low_supply_warning);

        // Control the visibility of the low supply value EditText
        switchLowSupplyWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchLowSupplyWarning.isChecked()) {
                    findViewById(R.id.linear_layout_add_medication_low_supply_value).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.linear_layout_add_medication_low_supply_value).setVisibility(View.GONE);
                }
            }
        });

    }

    private void createButton() {

        buttonNextActivity = (Button) findViewById(R.id.button_add_medicine_next);

        buttonNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: Write data from this activity into database then move on to next activity

                // Go to the MedicationScheduleActivity
                Intent intent = new Intent(AddMedicationActivity.this, MedicationScheduleActivity.class);
                startActivity(intent);

            }
        });

    }

    //endregion

    //region Camera Helper Methods

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageViewIcon.setImageBitmap(imageBitmap);
        }
    }

    //endregion

}
