package my.codeandroid.itisvid.activity;

import my.codeandroid.itisvid.ItisVidConstants;
import my.codeandroid.itisvid.R;
import my.codeandroid.itisvid.db.DatabaseHelper;
import my.codeandroid.itisvid.db.FavCam;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ListFavCamsActivity extends ListActivity {

	private static final int MENU_RM_FAV = Menu.FIRST;
	private static final int MENU_ITEM_HOME = Menu.FIRST + 1;
	private static final int MENU_ITEM_ALL = Menu.FIRST + 2;
	private static final int MENU_WAP = Menu.FIRST + 3;

	private DatabaseHelper db = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (db == null) {
			db = new DatabaseHelper(getApplicationContext(),
					ItisVidConstants.DATABASE_NAME, null,
					ItisVidConstants.DATABASE_VERSION);
		}
		Cursor favs = db.getFavourites();

		startManagingCursor(favs);

		ListAdapter adapter = new SimpleCursorAdapter(this,
		// Use a template that displays a text view
				android.R.layout.simple_list_item_1,
				// Give the cursor to the list adatper
				favs,
				// Map the NAME column in the people database to...
				new String[] { FavCam.CAM_NAME },
				// The "text1" view defined in the XML template
				new int[] { android.R.id.text1 });
		setListAdapter(adapter);

		ListView listView = getListView();
		listView.setTextFilterEnabled(false);
		listView.setLongClickable(true);
		listView.setCacheColorHint(0);
		registerForContextMenu(listView);

		if (adapter.isEmpty()) {
			final Toast t = Toast.makeText(getApplicationContext(),
					"No favorites", Toast.LENGTH_LONG);
			t.show();
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, MENU_RM_FAV, 0, "Remove from favorites");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case MENU_RM_FAV:
			Log.i(ItisVidConstants.LOGTAG, "onLongListItemClick id=" + info.id);
			db.delete(info.id);
			Toast t = Toast.makeText(info.targetView.getContext(),
					"Removed entry from favourites", Toast.LENGTH_SHORT);
			t.show();
			Cursor favs = db.getFavourites();
			
			startManagingCursor(favs);
			
			ListView listView = getListView();
			listView.setTextFilterEnabled(false);
			listView.setLongClickable(true);
			listView.setCacheColorHint(0);
			registerForContextMenu(listView);

			ListAdapter adapter = new SimpleCursorAdapter(this,
			// Use a template that displays a text view
					android.R.layout.simple_list_item_1,
					// Give the cursor to the list adatper
					favs,
					// Map the NAME column in the people database to...
					new String[] { FavCam.CAM_NAME },
					// The "text1" view defined in the XML template
					new int[] { android.R.id.text1 });
			setListAdapter(adapter);

			if (adapter.isEmpty()) {
				final Toast t2 = Toast.makeText(getApplicationContext(),
						"No favorites", Toast.LENGTH_LONG);
				t2.show();
			}
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (db != null) {
			db.close();
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		try {
			Log.i(ItisVidConstants.LOGTAG,
					"Attempting to display item with id: " + id);
			Log.d(ItisVidConstants.LOGTAG, "Uri of row: " + id);
			String url = db.getSelectedCamUrl(id);
			Intent tostart = new Intent(Intent.ACTION_VIEW);
			tostart.setData(Uri.parse(url));
			startActivity(tostart);
		} catch (ActivityNotFoundException e) {
			Toast t = Toast.makeText(v.getContext(),
					"Unable to play video. No content handler found ...",
					Toast.LENGTH_SHORT);
			t.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_WAP, 0, R.string.menu_wap)
		.setIcon(R.drawable.world_go);
		menu.add(0, MENU_ITEM_HOME, 0, R.string.menu_home).setIcon(
				R.drawable.house);
		menu.add(0, MENU_ITEM_ALL, 0, R.string.menu_all).setIcon(
				R.drawable.webcam_s);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_WAP:
			ListFavCamsActivity.this.finish();
			startActivity(new Intent().setClass(getApplicationContext(),
					ShowItisWapActivity.class));
			return true;
		case MENU_ITEM_HOME:
			ListFavCamsActivity.this.finish();
			return true;
		case MENU_ITEM_ALL:
			ListFavCamsActivity.this.finish();
			startActivity(new Intent().setClass(getApplicationContext(),
					ListAllCamsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
