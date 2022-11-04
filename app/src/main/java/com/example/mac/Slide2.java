package com.example.mac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Slide2 extends AppCompatActivity {
    private TextView tv;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    public String s1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide2);
        tv=findViewById(R.id.mm);
tv1=findViewById(R.id.mm1);
tv2=findViewById(R.id.mm2);
tv3=findViewById(R.id.mm3);
        Intent intent = getIntent();
        String text = intent.getStringExtra(MainActivity.EXTRA_TEXT);
      String m[]=text.split(",");
tv.setText("PROVINCE: "+m[0]);
tv1.setText("Data reported till: "+m[1]);
if(m[2].charAt(0)=='-')
    m[2]='0'+"";
tv2.setText("Total Cases: "+m[2]);
tv3.setText("Total Deaths: "+m[3]);
    }
}
