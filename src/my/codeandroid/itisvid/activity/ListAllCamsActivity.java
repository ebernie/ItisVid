package my.codeandroid.itisvid.activity;

import java.io.UnsupportedEncodingException;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;

import com.brightkeep.dropionotes.DroidDropNoteClient;

import my.codeandroid.itisvid.ItisVidConstants;
import my.codeandroid.itisvid.R;
import my.codeandroid.itisvid.db.DatabaseHelper;
import my.codeandroid.itisvid.db.FavCam;
import my.codeandroid.itisvid.log.RemoteLog;
import my.codeandroid.itisvid.log.RemoteLogFactory;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

/**
 * @author bernie
 * 
 */
public class ListAllCamsActivity extends ListActivity {

	private static final int MENU_ADD_FAV = Menu.FIRST;
	private static final int MENU_ITEM_HOME = Menu.FIRST + 1;
	private static final int MENU_ITEM_FAV = Menu.FIRST + 2;
	private static final int MENU_WAP = Menu.FIRST + 3;

	private DatabaseHelper db = null;

	private static final String[] LOCATIONS = {
			"Federal Highway near Seri Setia",
			"Federal Highway near Golf Club Subang",
			"Federal Highway near PJ Hilton",
			"Federal Highway near Angkasapuri",
			"KL-Seremban Expressway near Petronas TPM",
			"KL-Seremban Expressway near Desa Petaling",
			"Jalan Damansara near Muzium Negara",
			"Jalan Tun Razak near Bulatan Kg Pandan",
			"Jalan Tun Razak near Istana Budaya",
			"Jalan Tun Razak near Bulatan Pudu",
			"Jalan Tun Razak near Tabung Haji",
			"Jalan Kinabalu near Bulatan Dato' Onn", "MRR2 near Kesas Highway",
			"MRR2 near Jejambat Jln Kuari", "MRR2 near Tesco Ampang",
			"MRR2 near Bukit Antarabangsa", "MRR2 near Taman Midah",
			"Jalan Duta near Jalan Semantan (Sprint Highway)",
			"Jalan Istana near Jalan Bellamy",
			"Jalan Ampang near Kedutaan Perancis",
			"Highway Bukit Jalil near Taman Bukit Jalil",
			"Jalan Kuching near Bulatan Segambut",
			"Jalan Kuching near Hentian Putra",
			"Jalan Raja Laut near Bangunan DBKL", "Jalan Pahang near HKL",
			"Jalan Pahang near Persiaran Titiwangsa junction",
			"Jalan Pudu near Chinese Maternity Hospital",
			"Jalan Raja Chulan near Pavilion",
			"Jalan Sultan Ismail near Sheraton Imperial",
			"Jalan Loke Yew near Bulatan Cheras",
			"Jalan Syed Putra near exit Jalan Robson",
			"Jalan Yew near Bulatan Sg Besi", "Salak Highway" };

	private static final String[] CAM_URLS = {
			"rtsp://218.208.74.27:8554/live008",
			"rtsp://218.208.74.28:8554/live001",
			"rtsp://218.208.74.27:8554/live007",
			"rtsp://218.208.74.27:8554/live006",
			"rtsp://218.208.74.26:8554/live8",
			"rtsp://218.208.74.27:8554/live001",
			"rtsp://218.208.74.26:8554/live006",
			"rtsp://218.208.74.26:8554/live003",
			"rtsp://124.82.147.149:8554/live001",
			"rtsp://218.208.74.26:8554/live004",
			"rtsp://218.208.74.26:8554/live002",
			"rtsp://218.208.74.28:8554/live008",
			"rtsp://218.208.74.29:8554/live007",
			"rtsp://218.208.74.29:8554/live008",
			"rtsp://218.208.74.30:8554/live001",
			"rtsp://218.208.74.30:8554/live002",
			"rtsp://218.208.74.30:8554/live007",
			"rtsp://218.208.74.29:8554/live004",
			"rtsp://218.208.74.26:8554/live007",
			"rtsp://218.208.74.29:8554/live006",
			"rtsp://218.208.74.27:8554/live003",
			"rtsp://218.208.74.28:8554/live006",
			"rtsp://218.208.74.28:8554/live007",
			"rtsp://218.208.74.30:8554/live006",
			"rtsp://218.208.74.28:8554/live003",
			"rtsp://218.208.74.30:8554/live005",
			"rtsp://218.208.74.30:8554/live007",
			"rtsp://218.208.74.30:8554/live008",
			"rtsp://218.208.74.28:8554/live002",
			"rtsp://218.208.74.29:8554/live002",
			"rtsp://218.208.74.27:8554/live5",
			"rtsp://124.82.147.149:8554/live004",
			"rtsp://218.208.74.27:8554/live004" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, LOCATIONS));
		ListView listView = getListView();
		listView.setTextFilterEnabled(false);
		listView.setLongClickable(true);
		listView.setCacheColorHint(0);
		registerForContextMenu(listView);

		if (db == null) {
			db = new DatabaseHelper(this.getApplicationContext(),
					ItisVidConstants.DATABASE_NAME, null,
					ItisVidConstants.DATABASE_VERSION);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, MENU_ADD_FAV, 0, "Add to favorites");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case MENU_ADD_FAV:
			Log.i(ItisVidConstants.LOGTAG, "onLongListItemClick id=" + info.id);

			ContentValues vals = new ContentValues();
			vals.put(FavCam.CAM_NAME, LOCATIONS[(int) info.id]);
			vals.put(FavCam.CAM_URL, CAM_URLS[(int) info.id]);

			db.insertFavourites(vals);
			Toast t = Toast.makeText(info.targetView.getContext(),
					"Added entry to favourites", Toast.LENGTH_SHORT);
			t.show();
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
			startIntent(CAM_URLS[position]);
		} catch (ActivityNotFoundException e) {
			Toast t = Toast.makeText(v.getContext(),
					"Unable to play video. No content handler found ...",
					Toast.LENGTH_SHORT);
			t.show();
		} catch (Exception e) {
			RemoteLog logger = RemoteLogFactory.getRemoteLogger();
			logger.logThis(ItisVidConstants.LOGTAG, ItisVidConstants.LOG_TITLE,
					e.getMessage());
		}
	}

	private void startIntent(String url) {
		Intent tostart = null;
		tostart = new Intent(Intent.ACTION_VIEW);
		tostart.setData(Uri.parse(url));
		startActivity(tostart);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_WAP, 0, R.string.menu_wap)
				.setIcon(R.drawable.world_go);
		menu.add(0, MENU_ITEM_HOME, 0, R.string.menu_home).setIcon(
				R.drawable.house);
		menu.add(0, MENU_ITEM_FAV, 0, R.string.menu_fav).setIcon(
				R.drawable.heart_s);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_WAP:
			ListAllCamsActivity.this.finish();
			startActivity(new Intent().setClass(getApplicationContext(),
					ShowItisWapActivity.class));
			return true;
		case MENU_ITEM_HOME:
			ListAllCamsActivity.this.finish();
			return true;
		case MENU_ITEM_FAV:
			ListAllCamsActivity.this.finish();
			startActivity(new Intent().setClass(getApplicationContext(),
					ListFavCamsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}