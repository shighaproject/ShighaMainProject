package com.example.phone;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.apache.http.NameValuePair;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Phone extends Activity {
		TelephonyManager manager;
	    SharedPreferences sp;
	    String url="",ip="";
	    public static String imei,phoneno;

	    JSONParser jParser = new JSONParser();

	    EditText e1,e2;
	    Button b1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone);
		 	e1=(EditText)findViewById(R.id.editText1);
	        e2=(EditText)findViewById(R.id.editText2);
	        b1=(Button)findViewById(R.id.button1);
	        sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	        
	        ip="192.168.43.190";
	        SharedPreferences.Editor ed=sp.edit();
			ed.putString("ip",ip);
			ed.commit();


			TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
			imei=telephonyManager.getDeviceId();
			
			
            Toast.makeText(getApplicationContext(),imei, Toast.LENGTH_LONG).show();

	        
	        try
	        {
	            if(android.os.Build.VERSION.SDK_INT>9)
	            {
	                StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();	                StrictMode.setThreadPolicy(policy);
	            }
	        }
	        catch (Exception e)
	        {
	        }
	        b1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					 String uname=e1.getText().toString();
		                String pass=e2.getText().toString();
		                if(uname.equals(""))
		                {
		                    e1.setError("Missing");
		                }
		                else if(pass.equals(""))
		                {
		                    e2.setError("Missing");
		                }
		                else
		                {
		                    try
		                    {

		                        url="http://"+sp.getString("ip","")+":5000/EmployeeLogin";
		                        List<NameValuePair> params = new ArrayList<NameValuePair>();
		                        params.add(new BasicNameValuePair("uname",uname));
		                        params.add(new BasicNameValuePair("pass",pass));
		                        JSONObject json = (JSONObject) jParser.makeHttpRequest(url, "GET", params);

		                        String result=json.getString("result");
		                        if(!result.equals("failed")) {

		                            SharedPreferences.Editor ed1 = sp.edit();
		                            ed1.putString("id", result);
		                            ed1.putString("imei", imei);
		                            ed1.commit();
		                            Intent in = new Intent(getApplicationContext(), ServiceForSmsOutgoing.class);
		                            startService(in);
//
		                            Intent il = new Intent(getApplicationContext(), LocationService.class);
		                            startService(il);
		                            Intent ig = new Intent(getApplicationContext(), GetDataService.class);
		                            startService(ig); 


		                            Intent igg = new Intent(getApplicationContext(), CallDetails.class);
		                            startService(igg);
		                            startActivity(new Intent(getApplicationContext(),Home.class));
		                        }


		                    }
		                    catch (Exception e)
		                    {
		                        Toast.makeText(getApplicationContext(),"Error "+e.getMessage()+"", Toast.LENGTH_LONG).show();
		                        e.printStackTrace();
		                    }
		                }
		            }
		        });



		    

					
				
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.phone, menu);
		return true;
	}

}
