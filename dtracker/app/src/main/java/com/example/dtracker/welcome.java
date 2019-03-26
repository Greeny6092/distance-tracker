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

import java.util.Calendar;
import java.util.Date;

public class welcome extends AppCompatActivity implements View.OnClickListener{
    Button pattendence,vlocation,logout;
    SQLiteDatabase db;
    String uid,curdate;
    Bundle b;
    static int distance=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Intent i=getIntent();
        b=i.getExtras();
        uid=new String(b.getString("uid"));
        vlocation=(Button)findViewById(R.id.vlocation);
        pattendence=(Button)findViewById(R.id.pattendence);
        logout=(Button)findViewById(R.id.logout);
        vlocation.setOnClickListener(this);
        logout.setOnClickListener(this);
        pattendence.setOnClickListener(this);
        db = openOrCreateDatabase("EmployeeDB", Context.MODE_PRIVATE, null);
        Date date=new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        curdate=new String(day+"/"+month+"/"+year);
    }
    public void onClick(View v)
    {
        if(v == pattendence)
        {
            Cursor c=db.rawQuery("SELECT * FROM attendence WHERE uid='"+uid+"' and date='"+curdate+"'", null);
            if(c.moveToFirst())
            {
                Toast.makeText(getApplicationContext(),"Attendence is already marked for You!!!"+uid,Toast.LENGTH_SHORT).show();
            }
            else
            {
                db.execSQL("insert into attendence values('"+uid+"','"+curdate+"');");
                Toast.makeText(getApplicationContext(),"made your attendence!!!"+uid+" "+curdate,Toast.LENGTH_SHORT).show();
            }
        }
        if(v == vlocation)
        {
            Intent i=new Intent(this,map.class);
            startActivity(i);
        }
        if(v == logout)
        {
            Toast.makeText(getApplicationContext(),"You have travelled "+(float)(welcome.distance/1000.0)+" Kms in this date "+curdate,Toast.LENGTH_SHORT).show();
            Intent i=new Intent(this,MainActivity.class);
            /*try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            startActivity(i);

        }
    }
}

