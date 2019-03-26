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
import android.content.Intent;
import java.util.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText uid, pwd;
    Button login,newu;
    SQLiteDatabase db;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uid = (EditText) findViewById(R.id.uid);
        pwd = (EditText) findViewById(R.id.pwd);
        login=(Button)findViewById(R.id.login);
        newu=(Button)findViewById(R.id.newu);
        login.setOnClickListener(this);
        newu.setOnClickListener(this);
        db = openOrCreateDatabase("EmployeeDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS attendence(uid VARCHAR,date varchar);");
        db.execSQL("CREATE TABLE IF NOT EXISTS users(uid VARCHAR,name varchar,pwd varchar,dept varchar);");
    }
    public void onClick(View view) {
        if (view == login) {
            Cursor c=db.rawQuery("SELECT * FROM users WHERE uid='"+uid.getText()+"'", null);
            if(c.moveToFirst())
            {
                //Toast.makeText(getApplicationContext(),"password is!!"+c.getString(2)+"pwd is "+pwd.getText(),Toast.LENGTH_SHORT).show();
                if(pwd.getText().toString().equals(c.getString(2))==true)
                {
                    Toast.makeText(getApplicationContext(),"welcome!! "+uid.getText(),Toast.LENGTH_SHORT).show();
                    intent=new Intent(this,welcome.class);
                    Bundle b=new Bundle();
                    b.putString("uid",uid.getText().toString());
                    intent.putExtras(b);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(),"Invalid Username Or Password!!",Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(getApplicationContext(),"Invalid Username Or Password!!",Toast.LENGTH_SHORT).show();
        }
        if(view == newu)
        {
            //Toast.makeText(getApplicationContext(),"momving to next page!!",Toast.LENGTH_SHORT).show();
            intent=new Intent(this,newuser.class);
            startActivity(intent);
        }
    }
}
