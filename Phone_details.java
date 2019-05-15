package com.example.my_app;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Phone_details extends Activity implements OnItemClickListener{
	ListView l1;
	Button b17;

    SharedPreferences sh;
    JSONParser jsonParser = new JSONParser();
    String url="",ip="",hid="";
    JSONArray ar=new JSONArray();
    public static ArrayList<String>id,imei,model,os,ph_num;
    	
    
    public static int pos=0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_details);
		ListView l1= (ListView)findViewById(R.id.listview6);
		 b17=(Button)findViewById(R.id.button17);
		 
		 b17.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i=new Intent(getApplicationContext(),Phone_reg.class);
	                startActivity(i);

				}
			});
        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip=sh.getString("ip","");
        hid=sh.getString("id","");
        
        
        url="http://"+ip+":5000/mh_view_phone_details";
        
        
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("hid", hid));
        try {
            JSONArray json=(JSONArray) jsonParser.makeHttpRequest(url ,"GET", params);
            String s=null;
            
            //name = new ArrayList<String>(json.length());
            imei = new ArrayList<String>(json.length());
            model = new ArrayList<String>(json.length());
            os = new ArrayList<String>(json.length());
            ph_num= new ArrayList<String>(json.length());
            

            for (int i1 = 0; i1 < json.length(); i1++) {
                JSONObject c = json.getJSONObject(i1);
                
                //name.add(c.getString("name"));
                imei.add(c.getString("imei"));
                model.add(c.getString("model"));
                os.add(c.getString("os"));
              	ph_num.add(c.getString("ph_no"));
                

            }
           l1.setAdapter(new Cust2(this,model,imei,ph_num));
            l1.setOnItemClickListener(this);
            
            
        }catch(Exception e)
        {
        	Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();
           Log.d("=============",e+"");

        }

        
        
        
	}
	




        
	
		
       
			
			
			
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.phone_details, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		pos=arg2;
		Editor ed=sh.edit();
		ed.putString("imei", imei.get(pos));
		ed.commit();
		
		Intent i=new Intent(getApplicationContext(),Phone_home.class);
        startActivity(i);
		
		
	}
	
	

		
	
}
	
	

		
	





