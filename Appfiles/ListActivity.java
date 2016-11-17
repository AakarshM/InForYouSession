package aakarsh.inforyousession;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import static android.support.v7.widget.AppCompatDrawableManager.get;

public class ListActivity extends AppCompatActivity {
    String Major;
    String url = "https://api.uwaterloo.ca/v2/resources/infosessions.json?key=91f430c20c3a6dbfbccc52f9db2302ca";
    ArrayAdapter<String> listAdapter;
    ListView lview;
    Date currentDate;
    Date eventDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Bundle extras = getIntent().getExtras();
        Major = extras.getString("MajorKey");
        lview = (ListView) findViewById(R.id.lview);
        getDataWaterlooApi(url);
    }

    public void getDataWaterlooApi(String url){

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        convertData(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void convertData(String data){
        try {
            JSONObject obj = new JSONObject(data);
            JSONArray data2 = (JSONArray) obj.get("data");
            LinkedHashMap<String, ArrayList<String>> employer  = new LinkedHashMap<>();
            ArrayList<String> employerNames = new ArrayList<>();
            String currentDateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String date3 = "2016-12-01";


            //int year = calendar.get(Cale)




                for(int i=0; i < data2.length(); i++){
                    //System.out.println(data2.get(i));
                    ArrayList<String> employerInfo = new ArrayList<>();
                    String audienceArray = data2.getJSONObject(i).getString("audience");
                    String date = data2.getJSONObject(i).getString("date");
                    String start = data2.getJSONObject(i).getString("start_time");
                    String end = data2.getJSONObject(i).getString("end_time");
                    String employerName = data2.getJSONObject(i).getString("employer");
                    String  place = data2.getJSONObject(i).getJSONObject("building").getString("name");
                    String room = data2.getJSONObject(i).getJSONObject("building").getString("room");
                    String description = data2.getJSONObject(i).getString("description");

                   // System.out.println(audienceArray);
                    try {
                        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(currentDateString);
                        eventDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
                        System.out.println(currentDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if(audienceArray.toString().toLowerCase().trim().contains(Major) && eventDate.after(currentDate)){
                       employerInfo.add(date);
                       employerInfo.add(start);
                       employerInfo.add(end);
                       employerInfo.add(place);
                       employerInfo.add(room);
                       employerInfo.add(description);
                       employer.put(employerName, employerInfo);
                   }
               //  System.out.println(employerName);

                }

            for(String company: employer.keySet()){
               System.out.println(company);
                employerNames.add(company);
            }
           createListView(employerNames, employer);


        } catch (JSONException e) {
            e.printStackTrace();
        }
       // Log.i("", result.toString());

    }


    public void createListView(final ArrayList<String> employerList, final HashMap<String, ArrayList<String>> employerMap){
        listAdapter = new ArrayAdapter<String>(ListActivity.this, android.R.layout.simple_list_item_1, employerList);
       lview.setAdapter(listAdapter);
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String employerClicked = employerList.get(position);
                for(String employer: employerMap.keySet()){
                    if(employer.equals(employerClicked)){
                        Intent goToClicked = new Intent(getApplicationContext(), ListItemClicked.class);
                        goToClicked.putExtra("list", employerMap.get(employer));
                        goToClicked.putExtra("name", employer);
                       startActivity(goToClicked);
                    //System.out.println(employerMap.get(employer).toString());
                    }

                }


            }
        });
    }

}
