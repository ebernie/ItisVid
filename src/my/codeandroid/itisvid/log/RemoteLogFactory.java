package my.codeandroid.itisvid.log;

public class RemoteLogFactory {

	private static RemoteLog LOG;

	private RemoteLogFactory() {

	}

	public synchronized static final RemoteLog getRemoteLogger(String password,
			String token) {

		if (LOG == null) {
			LOG = new DropClientRemoteLog(password, token);
		}
		return LOG;
	}

	public synchronized static final RemoteLog getRemoteLogger() {

		if (LOG == null) {
			LOG = new DropClientRemoteLog();
		}
		return LOG;
	}

}
