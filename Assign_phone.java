package com.example.my_app;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Assign_phone extends Activity implements OnItemSelectedListener{
	Button b1;
	Spinner s1,s2;
	SharedPreferences sp;
	String url="",ip="",hid="";;
	JSONParser parser=new JSONParser();
	  JSONArray ar = new JSONArray();
	  private static final int FILE_SELECT_CODE1 = 2800;
	    String path1="",fileName="";

	    ArrayList<String> eid,name,imei ;
	    String em_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assign_phone);
		s1=(Spinner)findViewById(R.id.spinner1);
		s2=(Spinner)findViewById(R.id.spinner2);
		b1=(Button)findViewById(R.id.button1);
		
		
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		ip=sp.getString("ip", "");
		 hid=sp.getString("id","");
		
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
		 
		 try {
			  
			 url="http://"+ip+":5000/view_name";

		        List<NameValuePair> params = new ArrayList<NameValuePair>();
		        params.add(new BasicNameValuePair("lid", hid));
		        JSONArray ar=(JSONArray)parser.makeHttpRequest(url ,"GET", params);
		         // String s=null;
		            
		            eid = new ArrayList<String>(ar.length());
		            name = new ArrayList<String>(ar.length());


		            for (int i1 = 0; i1 < ar.length(); i1++) {
		                JSONObject c = ar.getJSONObject(i1);
		               eid.add(c.getString("eid"));
		               name.add(c.getString("name"));
		               Log.d("ress", ""+c);

		            }
		        
		           
	            ArrayAdapter<String> ad1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name);
	            s2.setAdapter(ad1);
	            s2.setOnItemSelectedListener(this);
		        }
		        catch(Exception e)
		        {
		        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();	
		        }
		 
		 
		 
		 try {
			  
			 url="http://"+ip+":5000/view_phone";

		        List<NameValuePair> params = new ArrayList<NameValuePair>();
		        params.add(new BasicNameValuePair("lid", hid));
		        JSONArray ar=(JSONArray)parser.makeHttpRequest(url ,"GET", params);
		         // String s=null;
		            
		            //hid = new ArrayList<String>(ar.length());
		            imei = new ArrayList<String>(ar.length());


		            for (int i1 = 0; i1 < ar.length(); i1++) {
		                JSONObject c = ar.getJSONObject(i1);
		               //hid.add(c.getString("hid"));
		               imei.add(c.getString("imei"));
		               Log.d("ress", ""+c);

		            }
		        
		           
	            ArrayAdapter<String> ad1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,imei);
	            s1.setAdapter(ad1);
	            s1.setOnItemSelectedListener(this);
		        }
		        catch(Exception e)
		        {
		        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();	
		        }
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				String imei=s1.getSelectedItem().toString();
				//String em_id=s2.getSelectedItem().toString();
                
                try
				{
					url="http://"+ip+":5000/assign_phone";
					Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();

					List<NameValuePair> params=new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("imei",imei));
					params.add(new BasicNameValuePair("eid",em_id));
					

					JSONObject json=(JSONObject)parser.makeHttpRequest(url,"GET",params);
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
		getMenuInflater().inflate(R.menu.assign_phone, menu);
		return true;
	}

	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		if(arg0==s2)
		{
			em_id=eid.get(arg2);
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
