package com.example.my_app;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Employee_home extends Activity {
	Button b1,b4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_employee_home);
		b1=(Button)findViewById(R.id.button1);
		
		b4=(Button)findViewById(R.id.button4);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),View_mh_work_details.class);
				
				startActivity(i);
				
			}
		});
		
	b4.setOnClickListener(new View.OnClickListener() {
		
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
		getMenuInflater().inflate(R.menu.employee_home, menu);
		return true;
	}

}
