package com.example.phone;

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

public class Notification extends Activity {
	 SharedPreferences sp;

	    JSONParser jParser = new JSONParser();
	    String url="",ip="";
	    ListView lv;

	    ArrayList<String> msg,details,head;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		 lv=(ListView)findViewById(R.id.listView);

	        sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	        ip=sp.getString("ip","");
	        url="http://"+ip+":5000/Notification";

	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("id", sp.getString("id","")));
	        JSONArray json= null;
	        
	        try {
	            json = (JSONArray) jParser.makeHttpRequest(url ,"GET", params);
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        
	        JSONArray ar=new JSONArray();
	        try {


	            msg = new ArrayList<String>(ar.length());
	            details = new ArrayList<String>(ar.length());
	            head = new ArrayList<String>(ar.length());

	            for (int i1 = 0; i1 < json.length(); i1++) {
	                JSONObject c = json.getJSONObject(i1);

	                msg.add(c.getString("msg_name"));
	                details.add(c.getString("msg_details"));
	                head.add(c.getString("h_name"));


	            }
	            lv.setAdapter(new Custom(getApplicationContext(),msg,details,head));

	        }catch(Exception e)
	        {
	            Log.d("=============",e+"");

	        }

	    }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notification, menu);
		return true;
	}

}
