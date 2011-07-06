package com.me.kzkamiya.android.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DownloadUtil {

	public static InputStream openHttpConnection(String urlString)
			throws IOException {
		InputStream in = null;
		int response = -1;

		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();

		if (!(conn instanceof HttpURLConnection))
			throw new IOException("Not an HTTP connection");
		try {
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setAllowUserInteraction(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestMethod("GET");
			httpConn.setReadTimeout(60000);
			httpConn.setConnectTimeout(60000);
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

	public static void unzip(String src, String dest) {

		final int BUFFER_SIZE = 4096;

		BufferedOutputStream bufferedOutputStream = null;
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(src);
			ZipInputStream zipInputStream = new ZipInputStream(
					new BufferedInputStream(fileInputStream));
			ZipEntry zipEntry;

			while ((zipEntry = zipInputStream.getNextEntry()) != null) {

				String zipEntryName = zipEntry.getName();
				File file = new File(dest + zipEntryName);

				if (file.exists()) {

				} else {
					if (zipEntry.isDirectory()) {
						file.mkdirs();
					} else {
						byte buffer[] = new byte[BUFFER_SIZE];
						FileOutputStream fileOutputStream = new FileOutputStream(
								file);
						bufferedOutputStream = new BufferedOutputStream(
								fileOutputStream, BUFFER_SIZE);
						int count;

						while ((count = zipInputStream.read(buffer, 0,
								BUFFER_SIZE)) != -1) {
							bufferedOutputStream.write(buffer, 0, count);
						}

						bufferedOutputStream.flush();
						bufferedOutputStream.close();
					}
				}
			}
			zipInputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
