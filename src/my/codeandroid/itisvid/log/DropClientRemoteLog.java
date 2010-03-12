package my.codeandroid.itisvid.log;

import java.io.UnsupportedEncodingException;

import my.codeandroid.itisvid.ItisVidConstants;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;

import com.brightkeep.dropionotes.DroidDropNoteClient;

class DropClientRemoteLog implements RemoteLog {
	
	//default password and token
	private String password = "kltrafficcam";
	private String token = "8b1594e76fa69f94fc26cb45d4b100a3761e40be";

	private DroidDropNoteClient dropClient;
	
	public DropClientRemoteLog() {
		super();
		dropClient = new DroidDropNoteClient(token);
		dropClient.setToken(password);
	}

	public DropClientRemoteLog(String password, String token) {
		super();
		this.password = password;
		this.token = token;
		
		dropClient = new DroidDropNoteClient(
				"8b1594e76fa69f94fc26cb45d4b100a3761e40be");
		dropClient.setToken("kltrafficcam");
	}
	
	/* (non-Javadoc)
	 * @see my.codeandroid.itisvid.RemoteLog#logThis(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void logThis(String tag, String title, String message) {
		// Write out a Note/Text contents:
		try {
			dropClient.createNote(ItisVidConstants.LOGTAG,
					"KL TRAFFIC CAM EXCEPTION", message);
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
