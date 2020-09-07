package com.industrialmaster.hospitalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class PrescriptionActivity extends AppCompatActivity {

    private DBManager dbManager;
    private ListView listView;
    private SimpleCursorAdapter adapter;

    final String[] from = new String[]{DatabaseHelper._ID,DatabaseHelper.SUBJECT,DatabaseHelper.DESC};

    final int[] to = new int[] {R.id.id,R.id.title_prescription,R.id.description};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        listView = findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this,R.layout.activity_view_record,cursor,from,to,0);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView idTextView = view.findViewById(R.id.id);
                TextView titleTextView = view.findViewById(R.id.title_prescription);
                TextView descrip = view.findViewById(R.id.description);

                String id = idTextView.getText().toString();
                String title = titleTextView.getText().toString();
                String desc2 = descrip.getText().toString();

                Intent modifyIntent = new Intent(getApplicationContext(),ModifyPrescriptionActivity.class);
                modifyIntent.putExtra("title",title);
                modifyIntent.putExtra("desc",desc2);
                modifyIntent.putExtra("id",id);

                startActivity(modifyIntent);
            }
        });

//        listView.setOnClickListener(new AdapterView.OnItemClickListener(){
//
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                TextView idTextView = view.findViewById(R.id.id);
//                TextView titleTextView = view.findViewById(R.id.title);
//                TextView descrip = view.findViewById(R.id.description);
//
//                String id = idTextView.getText().toString();
//                String title = titleTextView.getText().toString();
//                String desc2 = descrip.getText().toString();
//
//
//                Intent modifyIntent = new Intent(getApplicationContext(),ModifyPrescriptionActivity.class);
//                modifyIntent.putExtra("title",title);
//                modifyIntent.putExtra("desc",desc2);
//                modifyIntent.putExtra("id",id);
//
//                startActivity(modifyIntent);
//            }
//        });
    }

    public void onAddNew (View view){
        Intent intent = new Intent(PrescriptionActivity.this,AddPrescriptionActivity.class);
        startActivity(intent);
    }
}
