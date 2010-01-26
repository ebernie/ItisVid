package my.codeandroid.itisvid.activity;

import my.codeandroid.itisvid.ItisVidConstants;
import my.codeandroid.itisvid.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ShowItisWapActivity extends Activity {

	private static final String ITIS_WAP_URL = "http://www.itis.com.my/atis/wap";

	private WebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		if (webview == null) {
			webview = new WebView(this);
			webview.getSettings().setJavaScriptEnabled(true);
			final Activity activity = this;
			webview.setWebChromeClient(new WebChromeClient() {
				public void onProgressChanged(WebView view, int progress) {
					activity.setProgress(progress * 1000);
				}
			});
			webview.setWebViewClient(new WebViewClient() {
				public void onReceivedError(WebView view, int errorCode,
						String description, String failingUrl) {
					Toast.makeText(activity, "Oh no! " + description,
							Toast.LENGTH_SHORT).show();
				}
			});

		}

		webview.loadUrl(ITIS_WAP_URL);
		setContentView(webview);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.i(ItisVidConstants.LOGTAG, "Registering key down");
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			Log.i(ItisVidConstants.LOGTAG, "KEYCODE_BACK registered");
			webview.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, 0, 0, R.string.menu_exit).setIcon(R.drawable.door_out);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.menu_exit_content).setTitle(
				R.string.menu_exit).setCancelable(false).setIcon(
				R.drawable.exclamation).setPositiveButton(R.string.label_yes,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						ShowItisWapActivity.this.finish();
					}
				}).setNegativeButton(R.string.label_no,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		builder.create().show();
		return true;
	}

}
