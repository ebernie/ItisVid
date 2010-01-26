/**
 * 
 */
package my.codeandroid.itisvid.db;

import my.codeandroid.itisvid.ItisVidConstants;
import android.net.Uri;

/**
 * @author bernie
 *
 */
public class FavCam {
	
    public static final Uri CONTENT_URI = Uri.parse("content://" + ItisVidConstants.AUTHORITY + "/camfavs");
	
	public static final String CAM_URL = "cam_url";
	
	public static final String CAM_NAME = "cam_name";
	
	private final String camUrl;
	
	private final String camName;

	public String getCamUrl() {
		return camUrl;
	}

	public String getCamName() {
		return camName;
	}

	public FavCam(String camUrl, String camName) {
		super();
		this.camUrl = camUrl;
		this.camName = camName;
	}

	@Override
	public String toString() {
		return "FavCam [camName=" + camName + ", camUrl=" + camUrl + "]";
	}
	
}
