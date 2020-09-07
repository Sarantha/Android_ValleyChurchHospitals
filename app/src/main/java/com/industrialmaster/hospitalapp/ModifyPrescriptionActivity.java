package com.industrialmaster.hospitalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ModifyPrescriptionActivity extends Activity implements View.OnClickListener {

    private Button update,remove;
    private EditText updateSubject,updateDescription;
    private long _id;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_prescription);

        dbManager = new DBManager(this);
        dbManager.open();

        updateSubject = findViewById(R.id.subject_update);
        updateDescription = findViewById(R.id.desc_update);
        update = findViewById(R.id.saveChanges);
        remove = findViewById(R.id.delete);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");

        _id = Long.parseLong(id);
        updateSubject.setText(name);
        updateDescription.setText(desc);

        update.setOnClickListener(this);
        remove.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.saveChanges:
                String title = updateSubject.getText().toString();
                String desc = updateDescription.getText().toString();
                dbManager.update(_id,title,desc);
                this.returnHome();
                break;

            case R.id.delete:
                dbManager.delete(_id);
                this.returnHome();
        }
    }

    private void returnHome() {
        Intent intent = new Intent(getApplicationContext(),PrescriptionActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
