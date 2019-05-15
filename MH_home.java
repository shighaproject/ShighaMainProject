package com.example.my_app;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MH_home extends Activity {
	Button b1,b2,b3,b4,b5,b7,b8;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mh_home);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		b3=(Button)findViewById(R.id.button3);
		b4=(Button)findViewById(R.id.button4);
		
		b5=(Button)findViewById(R.id.button5);
	
		b7=(Button)findViewById(R.id.button7);
		b8=(Button)findViewById(R.id.button8);
		b8.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),MH_add_emp.class);
				
				startActivity(i);
				
			}
		});
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),MH_view_admin_work.class);
				
				startActivity(i);
				
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),MH_view_assigned_work.class);
				
				startActivity(i);
				
			}
		});
		b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),View_work_report.class);
				
				startActivity(i);
				
				
			}
		});
		
		b4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Assign_phone.class);
				
				startActivity(i);
				
				
			}
		});
		
		
		b5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Phone_details.class);
				
				startActivity(i);
				
				
			}
		});
	
		b7.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Login_mh.class);
				
				startActivity(i);
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mh_home, menu);
		return true;
	}

}
