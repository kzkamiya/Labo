package com.me.kzkamiya.android;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.webkit.WebView;

public class WebView1 extends Activity {
	
	WebView webView;
	
	final class JavaScriptInterface {
		private static final String KEY_HIGH_SCORE = "HIGH_SCORE";

		JavaScriptInterface() {}
		
		public int getScreenWidth() {
			return webView.getWidth();
		}
		
		public int getScreenHeight() {
			// -5 pixcels to prevent vertical scrolling.
			return webView.getHeight() - 5;
		}
		
		public int getHighScore() {
			SharedPreferences preferences = getPreferences(MODE_WORLD_WRITEABLE);
			return preferences.getInt(KEY_HIGH_SCORE, 0);
		}
		
		public void setHighScore(int value) {
			SharedPreferences preferences = getPreferences(MODE_PRIVATE);
			Editor editor = preferences.edit();
			editor.putInt(KEY_HIGH_SCORE, value);
			editor.commit();
		}
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.loadUrl("file:///android_asset/index.html");
        webView.addJavascriptInterface(new JavaScriptInterface(), "android");
        webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        setContentView(webView);
    }
}