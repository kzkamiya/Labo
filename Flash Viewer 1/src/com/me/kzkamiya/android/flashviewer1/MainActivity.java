package com.me.kzkamiya.android.flashviewer1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.me.kzkamiya.android.download.DownloadBackgroundTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		Log.d(TAG,"onDestroy() called!");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d(TAG,"onPause() called!");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG,"onResume() called!");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d(TAG,"onStart() called!");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d(TAG,"onStop() called!");
	}

	ImageView img;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        File saveFile = this.getFileStreamPath("esz.zip");
        String saveFilePath = saveFile.getAbsolutePath();

//        DownloadBackgroundTask downloadTask = new DownloadBackgroundTask();
//        downloadTask.execute("http://10.255.250.68/esz.zip", saveFilePath);

        
//		try {
//			Bitmap bitmap = BitmapFactory.decodeStream(this.openFileInput("test.jpg"));
//		    img = (ImageView) findViewById(R.id.img);
//		    img.setImageBitmap(bitmap);
//		} catch (FileNotFoundException e) {
//			Log.getStackTraceString(e);
//			e.printStackTrace();
//		}
		Log.d(TAG,"onDestroy() called!");
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