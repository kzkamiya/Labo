package com.me.kzkamiya.android.flashviewer1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import com.me.kzkamiya.android.download.DownloadBackgroundTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
	
	// Image view object
	ImageView img;
	// Sensor manager
	SensorManager mSensorManager;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        File saveFile = this.getFileStreamPath("esz.zip");
        String saveFilePath = saveFile.getAbsolutePath();

        DownloadBackgroundTask downloadTask = new DownloadBackgroundTask();
        downloadTask.execute("http://10.255.250.68/esz.zip", saveFilePath);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensorList.size() > 0) {
        	mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_UI);
        	
        } else {
        	markNotAvailable(new int[] {R.id.txt_axis_x, R.id.txt_axis_y, R.id.txt_axis_z});
        }
        
//		try {
//			Bitmap bitmap = BitmapFactory.decodeStream(this.openFileInput("test.jpg"));
//		    img = (ImageView) findViewById(R.id.img);
//		    img.setImageBitmap(bitmap);
//		} catch (FileNotFoundException e) {
//			Log.getStackTraceString(e);
//			e.printStackTrace();
//		}
    }

    //-----------------
    // Helper methods
    //-----------------
	private void markNotAvailable(int[] is) {
		for(int i = is.length; --i >= 0;) {
			markNotAvailable(is[i]);
			
		}
 		
	}

	private void markNotAvailable(int id) {
		TextView textView = (TextView)findViewById(id);
		textView.setText(R.string.txt_sensor_na);
	}
	
	private void writeValue(int[] is, float[] values) {
		for(int i = is.length; --i >= 0;) {
			writeValue(is[i], values[i]);
			
		}
 		
	}

	private void writeValue(int viewId, float value) {
		TextView textView = (TextView)findViewById(viewId);
		textView.setText("" + value);
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// do noting
		
	}

	public void onSensorChanged(SensorEvent event) {
		writeValue(new int[] {
				R.id.txt_axis_x,
				R.id.txt_axis_y,
				R.id.txt_axis_z
		}, new float[] {
				event.values[0],
				event.values[1],
				event.values[2]
		});
	}
    public void onNavButtonClick(View view) {
    	Intent intent = null;
    	switch (view.getId()) {
    	case R.id.btn_sensors:
    		intent = new Intent(this, MainActivity.class);
    		break;
    	case R.id.btn_staff:
    		intent = new Intent(this, StaffActivity.class);
    		break;
    	case R.id.btn_starlog:
    		intent = new Intent(this, StarLogActivity.class);
    		break;
    	}
    	startActivity(intent);
    }
}