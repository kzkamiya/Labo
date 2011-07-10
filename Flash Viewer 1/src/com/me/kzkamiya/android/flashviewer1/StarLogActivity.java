package com.me.kzkamiya.android.flashviewer1;

import com.me.kzkamiya.android.flashviewer1.db.StarLogDbManager;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class StarLogActivity extends ListActivity {
	
	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_DELETE = 1;
	private static final int ACTIVITY_EDIT = 3;

	private StarLogDbManager mDbManager;
	private Cursor mCursor;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.starlog_options, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add_log_entry:
			Intent intent = new Intent(this, LogEditActivity.class);
			startActivityForResult(intent, ACTIVITY_CREATE);
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.star_log);

		ArrayAdapter<String> adapter = 
			new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,
					getResources().getStringArray(R.array.log_entries));
		setListAdapter(adapter);
		
		registerForContextMenu(getListView());
		
		mDbManager = new StarLogDbManager(this);
		mDbManager.open();
		
		updateList();
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		updateList();
	}

	private void updateList() {
		mCursor = mDbManager.fetchAll();
		startManagingCursor(mCursor);
		
		String[] from = new String[] {StarLogDbManager.TIMESTAMP, 
				StarLogDbManager.LOG_TEXT};
		int[] to = new int[] {android.R.id.text1, android.R.id.text2};
		
		SimpleCursorAdapter logEntries = new SimpleCursorAdapter(this,
				android.R.layout.simple_expandable_list_item_2, mCursor, from, to);
		setListAdapter(logEntries);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, LogEditActivity.class);
		intent.putExtra(StarLogDbManager.ID, id);
		startActivityForResult(intent, ACTIVITY_EDIT);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_delete_log_entry:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			mDbManager.deleteLogEntry(info.id);
			updateList();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.starlog_entry_context, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
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