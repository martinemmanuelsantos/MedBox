package com.martinemmanuelsantos.medbox;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private TextView textviewTotalSupplyUnits, textviewLowSupplyUnits;
    private ImageView imageviewIcon;
    private Spinner spinnerDoseType, spinnerMethodTaken, spinnerInstruction;
    private Switch switchPrescription;
    private Button btnAddMedicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);


        // createEditTexts();
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

    private void createTextViews() {

        textviewTotalSupplyUnits = (TextView) findViewById(R.id.textview_add_medication_total_supply_units);
        textviewLowSupplyUnits = (TextView) findViewById(R.id.textview_add_medication_low_supply_units);

    }

    private void createImageView() {
        imageviewIcon = (ImageView) findViewById(R.id.imageview_add_medication_icon);

        imageviewIcon.setOnClickListener(new View.OnClickListener() {
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
                textviewTotalSupplyUnits.setText(spinnerDoseType.getSelectedItem().toString());
                textviewLowSupplyUnits.setText(spinnerDoseType.getSelectedItem().toString());
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

        btnAddMedicine = (Button) findViewById(R.id.button_add_medicine_next);
        btnAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddMedicationActivity.this, MedicationScheduleActivity.class);
                startActivity(intent);
            }
        });

    }

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
            imageviewIcon.setImageBitmap(imageBitmap);
        }
    }
}
