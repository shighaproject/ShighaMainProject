package com.example.my_app;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;


import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Phone_reg extends Activity {
	Button b2;
	EditText e5,e6,e7,e8;
	String ip="",url="",hid="";
	SharedPreferences sh;
	JSONParser jsonParser = new JSONParser();
	String id,imei,model,os,ph_num;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_reg);
		b2=(Button)findViewById(R.id.button2);
		 e5=(EditText) findViewById(R.id.editText5);
	     e6=(EditText) findViewById(R.id.editText6);
	     e7=(EditText) findViewById(R.id.editText7);
	     e8=(EditText) findViewById(R.id.editText8);
	     
	     sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	       ip=sh.getString("ip","");
	       hid=sh.getString("id","");
	       
	       try
	        {
	            if(android.os.Build.VERSION.SDK_INT>9)
	            {
	                StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
	                StrictMode.setThreadPolicy(policy);
	            }
	        }
	        catch(Exception e)
	        {

	        }
	       b2.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
						String imei=e5.getText().toString();
					 
		                String model=e6.getText().toString();
		                String os=e7.getText().toString();
		                String ph_num=e8.getText().toString();
		                
		                Toast.makeText(getApplicationContext(),ph_num,Toast.LENGTH_LONG).show();

		                try
						{
							url="http://"+ip+":5000/mh_reg_phone";
							Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();

							List<NameValuePair> params=new ArrayList<NameValuePair>();
							params.add(new BasicNameValuePair("imei",imei));
							params.add(new BasicNameValuePair("hid",hid));
							
							params.add(new BasicNameValuePair("model",model));
							params.add(new BasicNameValuePair("os",os));
							params.add(new BasicNameValuePair("ph_num",ph_num));
						

							JSONObject json=(JSONObject)jsonParser.makeHttpRequest(url,"GET",params);
							 String s=null;
			                    s=json.getString("result");
							
							 if(s.equals("success"))
							{
								Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
								Intent i=new Intent(getApplicationContext(),Phone_details.class);
								startActivity(i);
							}
	               else
	               {
	                   Toast.makeText(getApplicationContext(),"NOT SUCCESS",Toast.LENGTH_LONG).show();


	               }
	           }
	           catch(Exception e)
	           {
	               Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();

	           }


		            	
				}
			});
	    }

		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.phone_reg, menu);
		return true;
	}

}
