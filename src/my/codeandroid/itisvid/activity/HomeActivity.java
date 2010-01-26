/**
 * 
 */
package my.codeandroid.itisvid.activity;

import my.codeandroid.itisvid.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * @author bernie
 * 
 */
public class HomeActivity extends Activity implements OnClickListener {

	// Menu item ids
	private static final int MENU_ITEM_ABOUT = Menu.FIRST;
	private static final int MENU_ITEM_LICENSE = Menu.FIRST + 1;
	private static final int MENU_HELP = Menu.FIRST + 3;
	private static final int MENU_WAP = Menu.FIRST + 4;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		ImageButton allCamBtn = (ImageButton) findViewById(R.id.allcam);
		ImageButton favCamBtn = (ImageButton) findViewById(R.id.favcams);
		allCamBtn.setOnClickListener(this);
		favCamBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int clickId = v.getId();
		switch (clickId) {
		case R.id.allcam:
			startActivity(new Intent().setClass(v.getContext(),
					ListAllCamsActivity.class));
			break;
		case R.id.favcams:
			 startActivity(new Intent().setClass(v.getContext(),
			 ListFavCamsActivity.class));
			break;

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_WAP, 0, R.string.menu_wap)
				.setIcon(R.drawable.world_go);
		menu.add(0, MENU_HELP, 0, R.string.menu_help).setIcon(R.drawable.help);
		menu.add(0, MENU_ITEM_ABOUT, 0, R.string.menu_about).setIcon(
				R.drawable.information);
		menu.add(0, MENU_ITEM_LICENSE, 0, R.string.menu_license).setIcon(
				R.drawable.lock_break);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch (item.getItemId()) {
		case MENU_ITEM_ABOUT:
			builder.setMessage(R.string.menu_about_content).setTitle(
					R.string.menu_about).setCancelable(true).setIcon(
					R.drawable.information).setPositiveButton(
					R.string.label_done, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			builder.create().show();
			return true;
		case MENU_ITEM_LICENSE:
			builder.setMessage(R.string.menu_license_content).setTitle(
					R.string.menu_license).setCancelable(true).setIcon(
					R.drawable.lock_break).setPositiveButton(
					R.string.label_done, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			builder.create().show();
			return true;
		case MENU_HELP:
			builder.setMessage(R.string.menu_help_content).setTitle(
					R.string.menu_help).setCancelable(true).setIcon(
					R.drawable.help).setPositiveButton(R.string.label_done,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			builder.create().show();
			return true;
		case MENU_WAP:
			startActivity(new Intent().setClass(getApplicationContext(),
					ShowItisWapActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
