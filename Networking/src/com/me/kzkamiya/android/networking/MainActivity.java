package com.me.kzkamiya.android.networking;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	ImageView img;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //---download an image---
        Bitmap bitmap = 
        	DownloadImage("http://211.10.14.129/img/cu_logo01.gif");
        img = (ImageView) findViewById(R.id.img);
        img.setImageBitmap(bitmap);
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
    
}