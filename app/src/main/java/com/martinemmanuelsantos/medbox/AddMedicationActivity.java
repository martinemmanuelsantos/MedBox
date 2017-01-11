package com.martinemmanuelsantos.medbox;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
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
    private EditText editTextTotalSupply, editTextLowSupply;
    private TextView textViewTotalSupplyUnits, textViewLowSupplyUnits;
    private ImageView imageViewIcon;
    private Spinner spinnerDoseType, spinnerMethodTaken, spinnerInstruction;
    private Switch switchPrescription;
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

        editTextTotalSupply = (EditText) findViewById(R.id.edit_text_add_medication_total_supply);
        editTextLowSupply = (EditText) findViewById(R.id.edit_text_add_medication_low_supply);

    }

    private void createTextViews() {

        textViewTotalSupplyUnits = (TextView) findViewById(R.id.text_view_add_medication_total_supply_units);
        textViewLowSupplyUnits = (TextView) findViewById(R.id.text_view_add_medication_low_supply_units);
    }

    private void createImageView() {
        imageViewIcon = (ImageView) findViewById(R.id.image_view_add_medication_icon);

        imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

                Toast.makeText(getApplicationContext(), "You are a fucking homo", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createSpinners() {

        spinnerDoseType = (Spinner) findViewById(R.id.spinner_add_medication_dose_type);
        spinnerMethodTaken = (Spinner) findViewById(R.id.spinner_add_medication_method_taken);
        spinnerInstruction = (Spinner) findViewById(R.id.spinner_add_medication_instruction);

        /*ArrayAdapter adapterDoseType = ArrayAdapter.createFromResource(this, R.array.dose_types, android.R.layout.simple_spinner_item);
        spinnerDoseType.setAdapter(adapterDoseType);*/
        spinnerDoseType.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textViewTotalSupplyUnits.setText(spinnerDoseType.getSelectedItem().toString());
                textViewLowSupplyUnits.setText(spinnerDoseType.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void createSwitch() {

        switchPrescription = (Switch) findViewById(R.id.switch_add_medication_prescription);

    }

    private void createButton() {

        buttonNextActivity = (Button) findViewById(R.id.button_add_medicine_next);
        buttonNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: Write data from this activity into database then move on to next activity

                Intent intent = new Intent(AddMedicationActivity.this, MedicationScheduleActivity.class);
                startActivity(intent);

            }
        });

    }

    //endregion

    //region Camera Helper Methods

    private File getFile() {
        File folder = new File("sdcard/camera_app");

        if (!folder.exists()) {
            folder.mkdir();
        }

        return new File(folder,"image.jpg");
    }

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
