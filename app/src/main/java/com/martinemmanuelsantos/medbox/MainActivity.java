package com.martinemmanuelsantos.medbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnMedicineLog, btnMedicineList, btnAddMedicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createButtons();
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

    private void createButtons() {

        btnMedicineLog = (Button) findViewById(R.id.button_main_medicine_log);
        btnMedicineList = (Button) findViewById(R.id.button_main_medicine_list);
        btnAddMedicine = (Button) findViewById(R.id.button_main_add_medicine);

        btnMedicineLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create MedicineLogActivity.class and activity_medicine_log.xml
                Toast.makeText(getApplicationContext(), "Create MedicineLogActivity.class and activity_medicine_log.xml", Toast.LENGTH_LONG).show();;
            }
        });

        btnMedicineList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create MedicineListActivity.class and activity_medicine_list.xml
                Toast.makeText(getApplicationContext(), "Create MedicineListActivity.class and activity_medicine_list.xml", Toast.LENGTH_LONG).show();;
            }
        });

        btnAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMedicationActivity.class);
                startActivity(intent);
            }
        });

    }

}
