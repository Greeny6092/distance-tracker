package com.example.dtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class newuser extends AppCompatActivity implements View.OnClickListener {
    EditText uid, pwd,dept,name;
    Button register;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);
        uid = (EditText) findViewById(R.id.uid);
        pwd = (EditText) findViewById(R.id.pwd);
        name = (EditText) findViewById(R.id.name);
        dept = (EditText) findViewById(R.id.dept);
        register=(Button)findViewById(R.id.register);
        register.setOnClickListener(this);
        db = openOrCreateDatabase("EmployeeDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS attendence(uid VARCHAR,date real);");
        db.execSQL("CREATE TABLE IF NOT EXISTS users(uid VARCHAR,name varchar,pwd varchar,dept varchar);");
    }
    public void onClick(View view)
    {
        if(view == register)
        {
            Cursor c=db.rawQuery("SELECT * FROM users WHERE uid='"+uid.getText()+"'", null);
            if(c.moveToFirst())
            {
                Toast.makeText(getApplicationContext(),"Already Registered!!!"+uid.getText(),Toast.LENGTH_SHORT).show();
            }
            else
            {
                db.execSQL("INSERT INTO users VALUES('"+uid.getText()+"','"+name.getText()+ "','"+pwd.getText()+"','"+dept.getText()+"');");
                Toast.makeText(getApplicationContext(),"Registered Successfully!!!"+uid.getText(),Toast.LENGTH_SHORT).show();
            }
        }
    }

}
