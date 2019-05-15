package com.example.my_app;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Phone_home extends Activity {
	Button b1,b2,b3,b4,b5;
	JSONParser jsonParser = new JSONParser();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_home);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		b3=(Button)findViewById(R.id.button3);
		b4=(Button)findViewById(R.id.button4);
		
		b5=(Button)findViewById(R.id.button5);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Intent i=new Intent(getApplicationContext(),View_location.class);
				
				//startActivity(i);
				
				 SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			     String ip=sh.getString("ip","");
			     String imei=Phone_details.imei.get(Phone_details.pos);
		
	
				try
				{
					String url="http://"+ip+":5000/viewlocation";
					Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();

					List<NameValuePair> params=new ArrayList<NameValuePair>();
					
					params.add(new BasicNameValuePair("imei",imei));
				
					JSONObject json=(JSONObject)jsonParser.makeHttpRequest(url,"GET",params);
					
					String res=json.getString("res");
				
					if(res.equalsIgnoreCase("no"))
					{
						
						Toast.makeText(getApplicationContext(),"No Location Found..!", Toast.LENGTH_LONG).show();
		
					
					}
					else
					{
			
						String[] r=res.split("#");
						
						Toast.makeText(getApplicationContext(), r[0]+","+r[1], Toast.LENGTH_LONG).show();
						
						
				Uri gmmIntentUri = Uri.parse("geo:"+r[0]+","+r[1]);
				Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
				mapIntent.setPackage("com.google.android.apps.maps");
				if (mapIntent.resolveActivity(getPackageManager()) != null) {
				    startActivity(mapIntent);
				}
				}
				}
				catch(Exception e){
					
					
					Toast.makeText(getApplicationContext(), e.getMessage()+"", Toast.LENGTH_LONG).show();
				}
				}
		});
		
		
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Call_log.class);
				
				startActivity(i);
				
			}
		});
		b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Messagelog.class);
				
				startActivity(i);
				
			}
		});
		b4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Backup_files.class);
				
				startActivity(i);
				
			}
		});
		b5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Erase_files.class);
				
				startActivity(i);
				
			}
		});
	}

@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_mh, menu);
		return true;
		}

		}
