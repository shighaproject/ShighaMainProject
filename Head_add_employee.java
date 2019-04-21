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

public class Head_add_employee extends Activity implements OnItemClickListener {
	Button b1;
	JSONParser Parser=new JSONParser();
	
	ListView v;
	SharedPreferences sh;
	String ip="",url="",lid="",hid="";
	public static ArrayList<String>em_type,name,place,dob,gender,mobile,email,quali,exp,photo,eid;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_head_add_employee);
		b1=(Button)findViewById(R.id.button1);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		ip=sh.getString("ip", "");
		hid=sh.getString("id","");
		Toast.makeText(getApplicationContext(),"hid="+hid, Toast.LENGTH_LONG).show();
		v=(ListView)findViewById(R.id.listView1);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Employee_reg.class);
				startActivity(i);
				
			}
		});
		
		url="http://"+sh.getString("ip", "")+":5000/tl_view_employee";
		

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
       eid=new ArrayList<String>();
       em_type=new ArrayList<String>();
   
       name=new ArrayList<String>();
      place=new ArrayList<String>();
       dob=new ArrayList<String>();
       gender=new ArrayList<String>();
       mobile=new ArrayList<String>();
       email=new ArrayList<String>();
       quali=new ArrayList<String>();
       exp=new ArrayList<String>();
       photo=new ArrayList<String>();
       

      
 
       for (int i = 0; i < json.length(); i++) {
                JSONObject c=json.getJSONObject(i);
                
                eid.add(c.getString("eid"));
                em_type.add(c.getString("em_type"));
                name.add(c.getString("name"));
               place.add(c.getString("place"));
                dob.add(c.getString("dob"));
                gender.add(c.getString("gender"));
                mobile.add(c.getString("mobile"));
                email.add(c.getString("email"));
                quali.add(c.getString("qualification"));
                exp.add(c.getString("experience"));
                photo.add(c.getString("photo"));
                
                

               
              
           }
       ArrayAdapter<String>ad=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,name);
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
		getMenuInflater().inflate(R.menu.head_add_employee, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		
		
		Intent i=new Intent(getApplicationContext(),Head_view_profile.class);
		i.putExtra("empid", eid.get(arg2));
		i.putExtra("emptype", em_type.get(arg2));
		i.putExtra("name", name.get(arg2));
		i.putExtra("place", place.get(arg2));
		i.putExtra("dob", dob.get(arg2));
		i.putExtra("gender", gender.get(arg2));
		i.putExtra("mobile", mobile.get(arg2));
		i.putExtra("email", email.get(arg2));
		i.putExtra("qualification", quali.get(arg2));
		i.putExtra("experience", exp.get(arg2));
		i.putExtra("photo", photo.get(arg2));
		
		startActivity(i);
	}

}
