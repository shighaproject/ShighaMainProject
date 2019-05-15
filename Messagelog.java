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
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Messagelog extends Activity  implements OnItemClickListener{
	  ListView l1;
	    SharedPreferences sp;
	    ArrayList<String> phno,type,date,msg,time;
	    public static boolean at[];
	    JSONParser jsonParser = new JSONParser();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messagelog);
		 l1=(ListView)findViewById(R.id.listView);
		 sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		  String ip=sp.getString("ip","");
		  String imei=Phone_details.imei.get(Phone_details.pos);



	        try {
	            String url = "http://" + sp.getString("ip","") + ":5000/msg_log_head";
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add(new BasicNameValuePair("imei", imei));
	            JSONArray json = (JSONArray) jsonParser.makeHttpRequest(url, "GET", params);

	            phno = new ArrayList<String>();
	            type = new ArrayList<String>();
	            date = new ArrayList<String>();
	            time = new ArrayList<String>();
	            msg = new ArrayList<String>();


	            for (int j = 0; j < json.length(); j++) {
	                JSONObject c1 = json.getJSONObject(j);
	                phno.add(c1.getString("ph_no"));
	                type.add(c1.getString("msg_type"));
	                date.add(c1.getString("date"));
	                time.add(c1.getString("time"));
	                msg.add(c1.getString("msg"));
	            }
	            l1.setAdapter(new Custom4(getApplicationContext(),phno,type,date,time));
	            l1.setOnItemClickListener(this);


	        } catch (JSONException e) {


	        }
	    }

	
	  @Override
	    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

	        AlertDialog.Builder alert = new AlertDialog.Builder(Messagelog.this);

	        alert.setTitle("Message Content");
	        alert.setMessage(msg.get(i));
	        alert.show();

	    }
	}
