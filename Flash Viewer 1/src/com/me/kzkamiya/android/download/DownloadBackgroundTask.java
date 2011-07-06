package com.me.kzkamiya.android.download;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.os.AsyncTask;
import android.util.Log;

public class DownloadBackgroundTask extends AsyncTask<String, Void, InputStream> {
	private String filePath;
	private String fileUrl;
	protected InputStream doInBackground(String ... url) {
		fileUrl = url[0];
		filePath = url[1];
		InputStream inputstream = null;
		try {
			inputstream = DownloadUtil.openHttpConnection(fileUrl);
		} catch (IOException e) {
			Log.getStackTraceString(e);
			e.printStackTrace();
		}
		return inputstream;
	}
	protected void onPostExecute(InputStream inputstream) {
		if (inputstream == null) {
			Log.d(this.getClass().getName(), "Download Error. " + fileUrl);
			return;
		}
		
		byte[] buffer = new byte[8 * 1024];
		// Save File
		try {
			FileOutputStream output = new FileOutputStream(filePath, true);
			try {
				int bytesRead;
				while ((bytesRead = inputstream.read(buffer)) != -1) {
					output.write(buffer, 0, bytesRead);
				}
			} finally {
				output.close();
			}
			
			if (filePath.toLowerCase().endsWith("zip")) {
				String[] list = filePath.split("/");
				if (list != null && list.length >= 1) {
					StringBuilder sb = new StringBuilder("/");
					for(int i=0; i < list.length-1; i++) {
						sb.append(list[i]).append("/");
					}
					String dest = sb.toString();
					DownloadUtil.unzip(filePath, dest);
				}
			}
			
		} catch (FileNotFoundException e) {
			Log.getStackTraceString(e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.getStackTraceString(e);
			e.printStackTrace();
		} finally {
			try {
				inputstream.close();
			} catch (IOException e) {
				Log.getStackTraceString(e);
				e.printStackTrace();
			}
		}
	}
}
