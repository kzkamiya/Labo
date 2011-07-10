package com.me.kzkamiya.android.flashviewer1;

import java.io.InputStream;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.QuickContactBadge;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class StaffActivity extends ListActivity {
	
	static final String[] CONTACTS_FIELDS = new String[] {
		Contacts._ID,
		Contacts.LOOKUP_KEY,
		Contacts.DISPLAY_NAME,
		Contacts.PHOTO_ID
	};
	
	static final int FIELD_INDEX_ID = 0;
	static final int FIELD_INDEX_LOOKUP_KEY = 1;
	static final int FIELD_INDEX_DISPLAY_NAME = 2;
	static final int FIELD_INDEX_PHOTO_ID = 3;

	private ContentResolver mContentResolver;

	static final class StaffEntryTag {
		public TextView nameTextView;
		public QuickContactBadge badge;
	}
	
	private final class StaffCursorAdapter extends ResourceCursorAdapter {

		public StaffCursorAdapter(Context context, int layout, Cursor c) {
			super(context, layout, c);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			View view = super.newView(context, cursor, parent);
			StaffEntryTag tag = new StaffEntryTag();
			tag.nameTextView = (TextView) view.findViewById(R.id.txt_staff_name);
			tag.badge = (QuickContactBadge) view.findViewById(R.id.qcb_staff_badge);
			view.setTag(tag);
			return view;
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			StaffEntryTag tag = (StaffEntryTag)view.getTag();
			tag.nameTextView.setText(cursor.getString(FIELD_INDEX_DISPLAY_NAME));
			long id = cursor.getLong(FIELD_INDEX_ID);
			String key = cursor.getString(FIELD_INDEX_LOOKUP_KEY);
			tag.badge.assignContactUri(Contacts.getLookupUri(id, key));
			Uri contactUri =
				ContentUris.withAppendedId(Contacts.CONTENT_URI, id);
			InputStream imgStream =
				Contacts.openContactPhotoInputStream(mContentResolver, contactUri);
			if (null == imgStream) {
				tag.badge.setImageResource(R.drawable.icon);
			} else {
				Bitmap imgBitmap = BitmapFactory.decodeStream(imgStream);
				tag.badge.setImageBitmap(imgBitmap);
			}
			
		}

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.staff);

		String selection = "((" + Contacts.DISPLAY_NAME + " NOT NULL) AND ( " + Contacts.DISPLAY_NAME + " != ''))";
		mContentResolver = getContentResolver();
		Cursor cursor = mContentResolver.query(
				Contacts.CONTENT_URI, 
				CONTACTS_FIELDS, 
				selection,
				null, 
				Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC"
				);
		startManagingCursor(cursor);
		
		StaffCursorAdapter adapter = 
			new StaffCursorAdapter(this, R.layout.staff_entry, cursor);
		setListAdapter(adapter);
		
// Simple Adapter
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//				android.R.layout.simple_list_item_1, getResources()
//						.getStringArray(R.array.contactNames));
//		setListAdapter(adapter);

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

	public void onContactsButtonClick(View view) {
		Intent intent = new Intent(Intent.ACTION_VIEW,
				android.provider.ContactsContract.Contacts.CONTENT_URI);
		startActivity(intent);
	}
}