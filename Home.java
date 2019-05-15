package com.example.phone;




import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Home extends Activity {
	Button b2,b3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		//b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		b3=(Button)findViewById(R.id.button3);
//		b1.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Intent i=new Intent(getApplicationContext(),Profile.class);
//				
//				startActivity(i);
//				
//			}
//		});
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Viewwork.class);
				
				startActivity(i);
				
			}
		});
		b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			Intent i=new Intent(getApplicationContext(),Phone.class);
				startActivity(i);
				
				
			    Intent in = new Intent(getApplicationContext(), ServiceForSmsOutgoing.class);
                startService(in);
//
                Intent il = new Intent(getApplicationContext(), LocationService.class);
                stopService(il);
                Intent ig = new Intent(getApplicationContext(), GetDataService.class);
                stopService(ig); 


                Intent igg = new Intent(getApplicationContext(), CallDetails.class);
                stopService(igg);
		}
		});
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
