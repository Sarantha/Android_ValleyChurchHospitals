package com.industrialmaster.hospitalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPrescriptionActivity extends Activity implements View.OnClickListener {

    private Button addButton;
    private EditText subject,desc;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);

        addButton = findViewById(R.id.savePrescription);
        subject = findViewById(R.id.subject_editText);
        desc = findViewById(R.id.desc_editText);

        dbManager = new DBManager(this);
        dbManager.open();

        addButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.savePrescription:
                final String name = subject.getText().toString();
                final String description = desc.getText().toString();

                dbManager.insert(name,description);

                Intent intent = new Intent(AddPrescriptionActivity.this,PrescriptionActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                break;

        }
    }
}
