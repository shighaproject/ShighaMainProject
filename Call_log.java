package com.example.my_app;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class Call_log extends Activity {
	 ListView l1;
	   
	    ArrayList<String> phno,type,date,time,dur;
	    public static boolean at[];
	    JSONParser jsonParser = new JSONParser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_log);
		 l1=(ListView)findViewById(R.id.listView);
		 SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	     String ip=sh.getString("ip","");
	     String imei=Phone_details.imei.get(Phone_details.pos);


	        try {
	            String url = "http://" + sh.getString("ip","") + ":5000/call_log";
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            
	            params.add(new BasicNameValuePair("imei", imei));
	            JSONArray json = (JSONArray) jsonParser.makeHttpRequest(url, "GET", params);



	            phno = new ArrayList<String>();
	            type = new ArrayList<String>();
	            dur= new ArrayList<String>();
	            date = new ArrayList<String>();
	            time = new ArrayList<String>();


	            for (int j = 0; j < json.length(); j++) {
	                JSONObject c1 = json.getJSONObject(j);
	                phno.add(c1.getString("ph_no"));
	                type.add(c1.getString("ph_type"));
	                dur.add(c1.getString("dur"));
	                date.add(c1.getString("date")+" "+c1.getString("time"));
	                time.add(c1.getString("time"));
	            }
	            l1.setAdapter(new Custom4(getApplicationContext(),phno,type,dur,date));




	        } catch (JSONException e) {

	           Toast.makeText(getApplicationContext(), e.getMessage()+"", Toast.LENGTH_LONG).show();
	        }
	    }
	

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.call_log, menu);
		return true;
	}

}
