package com.example.thirdeye;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("NewApi") public class Login extends Activity {
	EditText e1,e2;
	Button b1;
	SharedPreferences sp;
	String url="",ip="";
	JSONParser parser=new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        e1=(EditText)findViewById(R.id.editText1);
        e2=(EditText)findViewById(R.id.editText2);
        b1=(Button)findViewById(R.id.button1);
        sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       
        
        ip="192.168.43.18";
   
        

		SharedPreferences.Editor ed=sp.edit();
		ed.putString("ip",ip);
		ed.commit();
		
		

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
				String uname=e1.getText().toString();
				String pass=e2.getText().toString();
				try
				{
					url="http://"+ip+":5000/log";
					Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();

					List<NameValuePair> params=new ArrayList<NameValuePair>();
					
					params.add(new BasicNameValuePair("username",uname));
					params.add(new BasicNameValuePair("password",pass));

					JSONObject json=(JSONObject)parser.makeHttpRequest(url,"GET",params);
					String res=json.getString("task");
					Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();

					if(res.equals("invalid"))
					{
						Toast.makeText(getApplicationContext(),"INVALID USERNAME OR PASSWORD",Toast.LENGTH_LONG).show();
					}
					else
					{
						SharedPreferences.Editor ed=sp.edit();
						ed.putString("id",res);
						ed.commit();
						Toast.makeText(getApplicationContext(),"successfully login",Toast.LENGTH_LONG).show();
						Intent i=new Intent(getApplicationContext(),Home.class);
						startActivity(i);
						
					}

				}
				catch (Exception e)
				{
					Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();

				}
				
				
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    
}
