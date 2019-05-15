package com.example.thirdeye;

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
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Head_view_pc_details extends Activity implements OnItemClickListener{
	Button b1;
	JSONParser Parser=new JSONParser();
	
	ListView v;
	SharedPreferences sh;
	String ip="",url="",lid="",hid="";
	public static ArrayList<String>sid,sys_num,sys_adrs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_head_view_pc_details);
		b1=(Button)findViewById(R.id.button);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		ip=sh.getString("ip", "");
		hid=sh.getString("id","");
		
		Toast.makeText(getApplicationContext(),"hid="+hid, Toast.LENGTH_LONG).show();
		v=(ListView)findViewById(R.id.listview3);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Head_register_pc.class);
				startActivity(i);
				
			}
		});
	       
	       
	        url="http://"+ip+":5000/tl_view_pc_details";
	        JSONArray json=null;

	    	
	        try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("lid",hid));
				json = (JSONArray)Parser.makeHttpRequest(url,"GET", params);
		} 		catch (JSONException e1) {
			
				e1.printStackTrace();
		}

			String s=null;
				
				
				
				
				try {
				   	Log.d("==============", "Emmmntered");
				   
				       Log.d("Msg+++++++++++++++++++++++++++++++++++++++++++++++", json.toString());
				       sid=new ArrayList<String>();
				       sys_num=new ArrayList<String>();
				       sys_adrs=new ArrayList<String>();
				       
				       for (int i = 0; i < json.length(); i++) {
			                JSONObject c=json.getJSONObject(i);
			                
			                sid.add(c.getString("sid"));
			                sys_num.add(c.getString("sys_num"));
			                sys_adrs.add(c.getString("sys_adrs"));
			               
			                

			               
			              
			           }
				      

		        
			       ArrayAdapter<String>ad=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,sys_adrs);
			       v.setAdapter(ad);
			       v.setOnItemClickListener(this);
				 }
				   catch(Exception e)
				   {
				   	
				   	Log.d("==============", "Catched");
				       
				   Log.d( "Errorrr"+e,"================");
				   }
						
						
						
						
						
						
						
						
						
								
					}

				   
				      
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.head_view_pc_details, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		 Toast.makeText(getApplicationContext(),sys_adrs.get(arg2),Toast.LENGTH_LONG).show();
	        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	        SharedPreferences.Editor ed=sh.edit();
	        ed.putString("sid",sys_adrs.get(arg2));
	        ed.commit();

	        Intent in=new Intent(getApplicationContext(),PChome.class);
	        startActivity(in);
	    }
	}

		
	


