package com.example.mac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String EXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";
    private RequestQueue mQueue;
private TextView tv;
private RequestQueue m1Queue;
private Button btn;
private  TextView tv1;
private TextView tv2;
private TextView tv3;
private int count=0;
private int count1=0;
private int count2=0;
private String date="";
    //public ListView liss;
String s="";
    String sex="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner = findViewById(R.id.sp);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        mQueue = Volley.newRequestQueue(this);
      tv=findViewById(R.id.vv);
        tv1=findViewById(R.id.vv2);
        tv2=findViewById(R.id.vv3);
        tv3=findViewById(R.id.vv4);

        btn=findViewById(R.id.button);
      m1Queue=Volley.newRequestQueue(this);
      btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              count=0;
                      count1=0;
                      count2=0;
              jsonparse();
             // openActivity2();
          }
      });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        String url = "https://api.opencovid.ca/summary";


        // tv.append("9inside9");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //   tv.append(response+"");
                        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject employee = jsonArray.getJSONObject(i);
                                String firstName = employee.getString("region");
                                if(firstName.equals("ON"))
                                    firstName="Ontario";
                                else   if(firstName.equals("AB"))
                                    firstName="Alberta";
                                else   if(firstName.equals("MB"))
                                    firstName="Manitoba";
                                else   if(firstName.equals("NB"))
                                    firstName="New Brunswick";
                                else if(firstName.equals("QC"))
                                    firstName="Quebec";
                                else if(firstName.equals("NS"))
                                    firstName="Nova Scotia";
                                else if(firstName.equals("SK"))
                                    firstName="Saskatchewan";
                                else if(firstName.equals("PE"))
                                    firstName="PEI";
                                else if(firstName.equals("YT"))
                                    firstName="Yukon";
                                if(text.equals(firstName)) {
                                    s= employee.getString("region") +','+employee.getString("date")+ ',' + employee.getString("cases") +
                                            ','+ employee.getString("deaths");
//tv.setText(s);

                                    openActivity2(s);

                                }}

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void openActivity2(String st) {
//String ss=jsonparse(st);
        Intent intent = new Intent(this,Slide2.class);
        intent.putExtra(EXTRA_TEXT,st);
        startActivity(intent);
    }
  private void jsonparse(){
      String url = "https://api.opencovid.ca/summary";
      // tv.append("9inside9");
      count2=0;
      count1=0;
      JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
              new Response.Listener<JSONObject>() {
                  @Override
                  public void onResponse(JSONObject response) {
                      //   tv.append(response+"");

                      try {
                          JSONArray jsonArray = response.getJSONArray("data");
                          for (int i = 0; i < jsonArray.length(); i++) {
                              JSONObject employee = jsonArray.getJSONObject(i);
                              //String count=employee.getString("active_cases_change");
                             count += employee.getInt("cases");
                           count1+=employee.getInt("deaths");
                           count2+=employee.getInt("cases_daily");
                            date=employee.getString("date");
                             }
                      tv1.setText("                  Cases Reported till "+date+":: "+count);
                         tv2.setText("Deaths Reported: "+count1);
                          tv3.setText("Total active cases: "+count2);
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  }
              }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
              error.printStackTrace();
          }
      });

      m1Queue.add(request);    }

}
