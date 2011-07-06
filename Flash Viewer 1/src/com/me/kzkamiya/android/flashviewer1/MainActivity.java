package com.me.kzkamiya.android.flashviewer1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.me.kzkamiya.android.download.DownloadBackgroundTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends Activity {
	ImageView img;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        File saveFile = this.getFileStreamPath("esz.zip");
        String saveFilePath = saveFile.getAbsolutePath();

        DownloadBackgroundTask downloadTask = new DownloadBackgroundTask();
        downloadTask.execute("http://10.255.250.68/esz.zip", saveFilePath);

        
//		try {
//			Bitmap bitmap = BitmapFactory.decodeStream(this.openFileInput("test.jpg"));
//		    img = (ImageView) findViewById(R.id.img);
//		    img.setImageBitmap(bitmap);
//		} catch (FileNotFoundException e) {
//			Log.getStackTraceString(e);
//			e.printStackTrace();
//		}
    }
}