package com.example.thirdeye;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class Head_view_profile extends Activity {
	EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
	
    Button b2;
	ImageView img1;
	 SharedPreferences sp;
	 String url="",ip="",lid="",hid="",eid="";
	JSONParser parser=new JSONParser();
	
	
	
	
        @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_head_view_profile);
		e8=(EditText)findViewById(R.id.editText8);
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
		e3=(EditText)findViewById(R.id.editText3);
		e4=(EditText)findViewById(R.id.editText4);
		e5=(EditText)findViewById(R.id.editText5);
		e6=(EditText)findViewById(R.id.editText6);
		e7=(EditText)findViewById(R.id.editText7);
		e9=(EditText)findViewById(R.id.editText9);
		img1=(ImageView)findViewById(R.id.imageView1);
		
		b2=(Button)findViewById(R.id.button2);
		
		sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		ip=sp.getString("ip", "");
		hid=sp.getString("id","");
		eid=getIntent().getStringExtra("empid");
		String em_type=getIntent().getStringExtra("emptype");
		e8.setText(em_type);
		String name=getIntent().getStringExtra("name");
		e1.setText(name);
		String place=getIntent().getStringExtra("place");
		e2.setText(place);
		String dob=getIntent().getStringExtra("dob");
		e3.setText(dob);
		
		String gender=getIntent().getStringExtra("gender");
		e9.setText(gender);
		String mobile=getIntent().getStringExtra("mobile");
		e4.setText(mobile);
		String email=getIntent().getStringExtra("email");
		e5.setText(email);
		String qualification=getIntent().getStringExtra("qualification");
		e6.setText(qualification);
		String experience=getIntent().getStringExtra("experience");
		e7.setText(experience);
		String img=getIntent().getStringExtra("photo");
		java.net.URL thumb_u;
		 try {
				
				
				http://"+sp.getString("ip", "")+":5000//static//photos//"+c.get(position)		
					thumb_u = new java.net.URL("http://"+ip+":5000/static/employee_pic/"+img);
			Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
			img1.setImageDrawable(thumb_d);
				

			
//			Picasso.with(Context)
//		    .load(urll)		    		   
//		    .error(R.drawable.nophoto)
//			    .into(img);
//			 
//				
				
			}
			catch(Exception e){
				Log.d("uuuuuuuuuuuuu", e.toString());
			}
		
		
		
		
		
		
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try
				{
				url="http://"+ip+":5000/delete_profile";
				Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();

				List<NameValuePair> params=new ArrayList<NameValuePair>();
				
				params.add(new BasicNameValuePair("id",eid));
				JSONObject json=(JSONObject)parser.makeHttpRequest(url,"GET",params);
				String res=json.getString("task");
				Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();
				if(res.equals("deleted"))
				{
					
					Toast.makeText(getApplicationContext(),"successfully done",Toast.LENGTH_LONG).show();
					Intent i=new Intent(getApplicationContext(),Head_add_employee.class);
					startActivity(i);
				}
				else
				{
					Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_LONG).show();
				}
				}
				catch (Exception e)
				{

				}
				
				
				
				
				
				
			}
		});
        
		
			

		
		
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.head_view_profile, menu);
		return true;
	}

}
