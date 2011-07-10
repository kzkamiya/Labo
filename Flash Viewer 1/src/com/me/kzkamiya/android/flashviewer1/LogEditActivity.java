package com.me.kzkamiya.android.flashviewer1;

import com.me.kzkamiya.android.flashviewer1.db.StarLogDbManager;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LogEditActivity extends Activity {
	
	private EditText mLogTextView;
	private TextView mTimestampTextView;
	private StarLogDbManager mDbManager;
	private Long mLogId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_edit);
		
		mLogTextView = (EditText) findViewById(R.id.ed_starlog_text);
		mTimestampTextView = (TextView) findViewById(R.id.txt_timestamp);
		mTimestampTextView.setText("" + System.currentTimeMillis());
		
		mDbManager = new StarLogDbManager(this);
		mDbManager.open();
		
		Bundle extras = getIntent().getExtras();
		if (null != extras) {
			mLogId = extras.getLong(StarLogDbManager.ID);
		}
		
		updateViews();
	}

	private void updateViews() {
		if (null != mLogId) {
			Cursor cursor = mDbManager.fetchOne(mLogId);
			startManagingCursor(cursor);
			mTimestampTextView.setText("" + cursor.getLong(cursor.getColumnIndexOrThrow(StarLogDbManager.TIMESTAMP)));
			mLogTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(StarLogDbManager.LOG_TEXT)));
		}
	}
	
	public void onConfirmButtonClick(View view) {
		saveLogEntry();
		setResult(RESULT_OK);
		finish();
	}
	
	private void saveLogEntry() {
		String logText = mLogTextView.getText().toString();
		if (logText.length() > 0) {
			Long timestamp = 
				Long.parseLong(mTimestampTextView.getText().toString());
			
			if (null == mLogId) {
				long id = mDbManager.createLogEntry(logText, timestamp);
				if (0 < id) {
					mLogId = id;
				}
			} else {
				mDbManager.updateLogEntry(mLogId, logText, timestamp);
			}
		} else {
			mLogId = null;
		}
	}
}
