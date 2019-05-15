package com.example.thirdeye;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Head_update_work extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_head_update_work);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.head_update_work, menu);
		return true;
	}

}
