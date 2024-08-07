package com.example.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public final String LOG_TAG ="myLogs";

    Button btnAdd,btnRead,btnClear;
    EditText etName,etEmail;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.add);
        btnRead = findViewById(R.id.read);
        btnClear = findViewById(R.id.clear);
        etName = findViewById(R.id.Etname);
        etEmail = findViewById(R.id.email);

        btnAdd.setOnClickListener(this);
        btnRead.setOnClickListener(this);
        btnClear.setOnClickListener(this);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        ContentValues cv = new ContentValues();
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (v.getId()){
            case R.id.add:
                Log.d(LOG_TAG,"---- insert in mytable");
                cv.put("name",name);
                cv.put("email",email);
                long rowId = db.insert("mytable",null,cv);
                Log.d(LOG_TAG,"---- rows in mytable = " +  rowId);
                break;
            case R.id.read:

                Log.d(LOG_TAG,"---- Rows in mytable");

                Cursor c = db.query("mytable", new String[]{"id AS _id", "name", "email"}, null, null, null, null, null);
                MyListAdapter adapter = new MyListAdapter(this, c);
                ListView listView = findViewById(R.id.listView);
                listView.setAdapter(adapter);
                break;

                /*
                Log.d(LOG_TAG,"---- Rows in mytable");
                Cursor c = db.query("mytable",null,null,null,null,null,null);
                if(c.moveToFirst()){
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("name");
                    int emailColIndex = c.getColumnIndex("email");

                    do {
                        Log.d(LOG_TAG,"ID = "+c.getInt(idColIndex)
                                +",name = " +c.getString(nameColIndex)
                                +",email = "+ c.getString(emailColIndex));
                    }while(c.moveToNext());
                }else
                    Log.d(LOG_TAG,"0 Rows");
                c.close();
                break;

                */
            case R.id.clear:
                Log.d(LOG_TAG,"----Clear mytable");
                int clearCount = db.delete("mytable",null,null);
                Log.d(LOG_TAG,"deleted rows count = "+ clearCount);
                break;
        }
        dbHelper.close();
    }
}