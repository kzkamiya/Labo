package com.me.kzkamiya.android.flashviewer1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Staff extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.staff);
	}

    public void onNavButtonClick(View view) {
    	Intent intent = null;
    	switch (view.getId()) {
		case R.id.btn_sensors:
			intent = new Intent(this, MainActivity.class);
			break;
		case R.id.btn_staff:
			intent = new Intent(this, Staff.class);
			break;
		case R.id.btn_starlog:
			intent = new Intent(this, StarLog.class);
			break;
		}
    	startActivity(intent);
    }
}
