package com.example.thirdeye;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Custom2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.custom2, menu);
		return true;
	}

}
