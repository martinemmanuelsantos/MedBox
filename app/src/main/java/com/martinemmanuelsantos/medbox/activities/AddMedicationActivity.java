package com.martinemmanuelsantos.medbox.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
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

import com.martinemmanuelsantos.medbox.R;
import com.martinemmanuelsantos.medbox.database.Medication;

/**
 * Created by nutel on 1/5/2017.
 */

public class AddMedicationActivity extends AppCompatActivity {

    /* Constants */
    static final int REQUEST_IMAGE_CAPTURE = 1;

    /* UI Elements */
    private EditText editTextMedicationName, editTextCustomDoseUnits, editTextTotalSupply, editTextLowSupply, editTextCustomMethodTaken, editTextNotes;
    private TextView textViewTotalSupplyUnits, textViewLowSupplyUnits;
    private ImageView imageViewIcon;
    private Spinner spinnerDoseType, spinnerMethodTaken, spinnerInstruction;
    private Switch switchPrescription, switchLowSupplyWarning;
    private Button buttonNextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        // Create UI elements
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

        editTextMedicationName = (EditText) findViewById(R.id.edit_text_add_medication_name);
        editTextCustomDoseUnits = (EditText) findViewById(R.id.edit_text_add_medication_custom_units);
        editTextTotalSupply = (EditText) findViewById(R.id.edit_text_add_medication_total_supply);
        editTextLowSupply = (EditText) findViewById(R.id.edit_text_add_medication_low_supply);
        editTextCustomMethodTaken = (EditText) findViewById(R.id.edit_text_add_medication_custom_method_taken);
        editTextNotes = (EditText) findViewById(R.id.edit_text_add_medication_notes);

        // Change the appropriate TextViews when a custom unit is entered
        editTextCustomDoseUnits.addTextChangedListener(new TextWatcher() {
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
                // If R.string.Custom is selected, show the custom dose unit EditText, otherwise hide it
                // and set the text to the spinner value
                if (spinnerDoseType.getSelectedItem().toString().equals(getResources().getString(R.string.custom))) {
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

        spinnerMethodTaken.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // If R.string.Custom is selected, show the custom dose unit EditText, otherwise hide it
                // and set the text to the spinner value
                if (spinnerMethodTaken.getSelectedItem().toString().equals(getResources().getString(R.string.custom))) {
                    findViewById(R.id.edit_text_add_medication_custom_method_taken).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.edit_text_add_medication_custom_method_taken).setVisibility(View.INVISIBLE);
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

                Medication medication = buildMedication();

                if (medication == null) { return; }

                // Go to the MedicationScheduleActivity
                Intent intent = new Intent(AddMedicationActivity.this, MedicationScheduleActivity.class);
                intent.putExtra("medicationObject", medication);
                startActivity(intent);

            }
        });

    }

    //endregion

    //region Medication Builder

    private Medication buildMedication() {

        Medication medication = new Medication();

        // Medication Name
        String medicationName = editTextMedicationName.getText().toString();
        // Check if the EditText is empty
        if (medicationName.matches("")) {

            // Highlight the color of the empty EditText, notify the user and exit onClick
            editTextMedicationName.getBackground().setColorFilter(getResources().getColor(R.color.colorInvalid), PorterDuff.Mode.SRC_ATOP);
            Toast.makeText(getApplicationContext(), "Please enter the name of your medication name", Toast.LENGTH_LONG).show();
            return null;

        } else {

            // Save medication name and return EditText highlight to original state
            medication.setMedicationName(medicationName);
            editTextMedicationName.getBackground().setColorFilter(null);

        }

        // Is Prescription
        medication.setPrescription(switchPrescription.isChecked());

        // Dose Type
        if (editTextCustomDoseUnits.isShown()) {

            String customDoseType = editTextCustomDoseUnits.getText().toString();

            // Check if the EditText is empty
            if (customDoseType.matches("")) {

                // Highlight the color of the empty EditText, notify the user and exit onClick
                editTextCustomDoseUnits.getBackground().setColorFilter(getResources().getColor(R.color.colorInvalid), PorterDuff.Mode.SRC_ATOP);
                Toast.makeText(getApplicationContext(), "Please enter the type of dose for your medication", Toast.LENGTH_LONG).show();
                return null;

            } else {

                // Save dose type and return EditText highlight to original state
                medication.setDoseType(customDoseType);
                editTextCustomDoseUnits.getBackground().setColorFilter(null);

            }

        } else {

            medication.setDoseType(spinnerDoseType.getSelectedItem().toString());

        };

        // Remaining Supply
        String remainingSupply = editTextTotalSupply.getText().toString();
        // Check if the EditText is empty
        if (remainingSupply.matches("")) {

            // Highlight the color of the empty EditText, notify the user and exit onClick
            editTextTotalSupply.getBackground().setColorFilter(getResources().getColor(R.color.colorInvalid), PorterDuff.Mode.SRC_ATOP);
            Toast.makeText(getApplicationContext(), "Please enter your total supply", Toast.LENGTH_LONG).show();
            return null;

        } else {

            int remainingSupplyNum = Integer.parseInt(remainingSupply);
            if (remainingSupplyNum < 0) {

                // Highlight the color of the empty EditText, notify the user and exit onClick
                editTextTotalSupply.getBackground().setColorFilter(getResources().getColor(R.color.colorInvalid), PorterDuff.Mode.SRC_ATOP);
                Toast.makeText(getApplicationContext(), "Your total supply cannot be negative", Toast.LENGTH_LONG).show();
                return null;

            } else {

                // Save total supply and return EditText highlight to original state
                medication.setRemainingSupply(Integer.parseInt(remainingSupply));
                editTextTotalSupply.getBackground().setColorFilter(null);

            }

        }

        // Is Low Supply Warning
        medication.setLowSupplyWarning(switchLowSupplyWarning.isChecked());

        // Low Supply Value
        if (editTextLowSupply.isShown()) {

            String lowSupplyValue = editTextLowSupply.getText().toString();

            // Check if the EditText is empty
            if (lowSupplyValue.matches("")) {

                // Highlight the color of the empty EditText, notify the user and exit onClick
                editTextLowSupply.getBackground().setColorFilter(getResources().getColor(R.color.colorInvalid), PorterDuff.Mode.SRC_ATOP);
                Toast.makeText(getApplicationContext(), "Please enter low supply value", Toast.LENGTH_LONG).show();
                return null;

            } else {

                int lowSupplyNum = Integer.parseInt(remainingSupply);
                if (lowSupplyNum < 0) {

                    // Highlight the color of the empty EditText, notify the user and exit onClick
                    editTextLowSupply.getBackground().setColorFilter(getResources().getColor(R.color.colorInvalid), PorterDuff.Mode.SRC_ATOP);
                    Toast.makeText(getApplicationContext(), "The low supply value cannot be negative", Toast.LENGTH_LONG).show();
                    return null;

                } else {

                    // Save total supply and return EditText highlight to original state
                    medication.setLowSupplyValue(Integer.parseInt(lowSupplyValue));
                    editTextLowSupply.getBackground().setColorFilter(null);

                }

            }

        } else {
            // Nothing to enter if low LowSupplyWarning switch is off
        }

        // Method Taken
        if (editTextCustomMethodTaken.isShown()) {

            String customMethodTaken = editTextCustomMethodTaken.getText().toString();

            // Check if the EditText is empty
            if (customMethodTaken.matches("")) {

                // Highlight the color of the empty EditText, notify the user and exit onClick
                editTextCustomMethodTaken.getBackground().setColorFilter(getResources().getColor(R.color.colorInvalid), PorterDuff.Mode.SRC_ATOP);
                Toast.makeText(getApplicationContext(), "Please enter the method taken for your medication", Toast.LENGTH_LONG).show();
                return null;

            } else {

                // Save dose type and return EditText highlight to original state
                medication.setMethodTaken(customMethodTaken);
                editTextCustomMethodTaken.getBackground().setColorFilter(null);

            }

        } else {

            medication.setMethodTaken(spinnerMethodTaken.getSelectedItem().toString());

        };

        // Instruction
        medication.setInstruction(spinnerInstruction.getSelectedItemId());

        // Notes
        medication.setNotes(editTextNotes.getText().toString());

        return medication;

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
