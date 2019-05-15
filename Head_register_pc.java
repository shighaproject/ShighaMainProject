package com.example.thirdeye;

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
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Head_register_pc extends Activity {
	Button b1;
	EditText e5,e1;
	String ip="",url="",id=" ",hid="";
	SharedPreferences sh;
	JSONParser jsonParser = new JSONParser();
	String sid,sys_num,sys_adrs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_head_register_pc);
		 b1=(Button)findViewById(R.id.button2);
		 e1=(EditText) findViewById(R.id.editText1);
	     e5=(EditText) findViewById(R.id.editText5);
	     
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
	       b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
					String sys_num=e1.getText().toString();
				 
	                String sys_adrs=e5.getText().toString();
	                Toast.makeText(getApplicationContext(),sys_adrs,Toast.LENGTH_LONG).show();

	                try
					{
						url="http://"+ip+":5000/tl_reg_system";
						Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();

						List<NameValuePair> params=new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("sys_num",sys_num));
						params.add(new BasicNameValuePair("hid",hid));
						
						params.add(new BasicNameValuePair("sys_adrs",sys_adrs));
					

						JSONObject json=(JSONObject)jsonParser.makeHttpRequest(url,"GET",params);
						 String s=null;
		                    s=json.getString("result");
						
						 if(s.equals("success"))
						{
							Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
							Intent i=new Intent(getApplicationContext(),Head_view_pc_details.class);
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
		getMenuInflater().inflate(R.menu.head_register_pc, menu);
		return true;
	}

}
