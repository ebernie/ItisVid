package my.codeandroid.itisvid.log;

import java.io.UnsupportedEncodingException;

import my.codeandroid.itisvid.ItisVidConstants;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;

import android.util.Log;

import com.brightkeep.dropionotes.DroidDropNoteClient;

class DropClientRemoteLog implements RemoteLog {

	// default password and api key
	private String token = "ufcsbfmy51";
	private String apikey = "8b1594e76fa69f94fc26cb45d4b100a3761e40be";

	private DroidDropNoteClient dropClient;

	public DropClientRemoteLog() {
		super();
		dropClient = new DroidDropNoteClient(apikey);
		dropClient.setToken(token);
		Log.d(ItisVidConstants.LOGTAG, apikey + "|" + token);
	}

	public DropClientRemoteLog(String token, String apikey) {
		super();
		this.token = token;
		this.apikey = apikey;

		// api key
		dropClient = new DroidDropNoteClient(apikey);
		// token in droiddrop lingo
		dropClient.setToken(token);
		Log.d(ItisVidConstants.LOGTAG, apikey + "|" + token);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see my.codeandroid.itisvid.RemoteLog#logThis(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public void logThis(String tag, String title, String message) {
		// Write out a Note/Text contents:
		try {
			// I'm reusing the log tag as the drop
			dropClient.createNote(ItisVidConstants.LOGTAG,
					"KL TRAFFIC CAM EXCEPTION", message);
		} catch (ClientProtocolException e1) {
			Log.e(ItisVidConstants.LOGTAG, "DroidDrop token " + token
					+ "| apikey " + apikey, e1);
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			Log.e(ItisVidConstants.LOGTAG, "DroidDrop token " + token
					+ "| apikey " + apikey, e1);
			e1.printStackTrace();
		} catch (ParseException e1) {
			Log.e(ItisVidConstants.LOGTAG, "DroidDrop token " + token
					+ "| apikey " + apikey, e1);
			e1.printStackTrace();
		} catch (Exception e1) {
			Log.e(ItisVidConstants.LOGTAG, "DroidDrop token " + token
					+ "| apikey " + apikey, e1);
			e1.printStackTrace();
		}
	}

}
