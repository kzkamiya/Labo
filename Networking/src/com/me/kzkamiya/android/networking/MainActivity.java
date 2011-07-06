package com.me.kzkamiya.android.networking;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	ImageView img;

	private class BackgroudTask extends AsyncTask<String, Void, Bitmap> {
		protected Bitmap doInBackground(String ... url) {
			Bitmap bitmap = DownloadImage(url[0]);
			return bitmap;
		}
		protected void onPostExecute(Bitmap bitmap) {
			ImageView img = (ImageView)findViewById(R.id.img);
			img.setImageBitmap(bitmap);
		}
	}
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //---download an image---
//        new BackgroudTask().execute("http://211.10.14.129/img/cu_logo01.gif");
//        Bitmap bitmap = 
//        	DownloadImage("http://211.10.14.129/img/cu_logo01.gif");
//        img = (ImageView) findViewById(R.id.img);
//        img.setImageBitmap(bitmap);
        
        DownladFileSave("http://211.10.14.129/img/cu_logo01.gif");
        
        
        final Context context = this; 
        FileInputStream fileInputStream;
		try {
			fileInputStream = 
				context.openFileInput("test.jpg");
			Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
		    img = (ImageView) findViewById(R.id.img);
		    img.setImageBitmap(bitmap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
    }
    
    private InputStream OpenHttpConnection(String urlString) throws IOException 
    {
    	InputStream in = null;
    	int response = -1;
    	
    	URL url = new URL(urlString);
    	URLConnection conn = url.openConnection();
    	
    	if (!(conn instanceof HttpURLConnection))
    		throw new IOException("Not an HTTP connection");
    	try {
    		HttpURLConnection httpConn = (HttpURLConnection)conn;
    		httpConn.setAllowUserInteraction(false);
    		httpConn.setInstanceFollowRedirects(true);
    		httpConn.setRequestMethod("GET");
    		httpConn.connect();
    		response = httpConn.getResponseCode();
    		if (response == HttpURLConnection.HTTP_OK) {
    			in = httpConn.getInputStream();
    		}
    	} catch (Exception ex) {
    		throw new IOException("Error connecting");
    	}
    	return in;
    }
    
    private Bitmap DownloadImage(String URL) {
    	Bitmap bitmap = null;
    	InputStream in = null;
    	try {
    		in = OpenHttpConnection(URL);
    		bitmap = BitmapFactory.decodeStream(in);
    		in.close();
    	} catch (IOException e1) {
    		Toast.makeText(this, e1.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    		e1.printStackTrace();
    	}
    	return bitmap;
    }
    
	private boolean DownladFileSave(String URL) {
		byte[] buffer = new byte[8 * 1024];
		String filePath = "/data/data/" + this.getPackageName() + "/test.jpg";
		InputStream input = null;
		try {
			input = OpenHttpConnection(URL);
			FileOutputStream output = openFileOutput("test.jpg", Context.MODE_APPEND | Context.MODE_PRIVATE);
//new FileOutputStream(new File(filePath));
			try {
				int bytesRead;
				while ((bytesRead = input.read(buffer)) != -1) {
					output.write(buffer, 0, bytesRead);
				}
			} finally {
				output.close();
			}
		} catch (FileNotFoundException e) {
			Log.getStackTraceString(e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.getStackTraceString(e);
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				Log.getStackTraceString(e);
				e.printStackTrace();
			}
		}
		return true;
	}
    
}