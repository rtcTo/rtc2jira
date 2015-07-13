package rtc.to.rtc2jira.storage;

import java.io.Closeable;
import java.io.IOException;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.server.OServer;
import com.orientechnologies.orient.server.OServerMain;

public class StorageEngine implements Closeable {

	private OServer server;

	public StorageEngine() throws Exception {
		server = OServerMain.create();
		server.startup(Thread.currentThread().getContextClassLoader().getResourceAsStream("orientconf.xml"));
		server.activate();
		prepareDB();
	}

	private void prepareDB() {
		try (ODatabaseDocumentTx db = new ODatabaseDocumentTx("plocal:./databases/rtc2jira")) {
			if (!db.exists()) {
				db.create();
			} else {
				db.open("admin", "admin");
			}
		}
	}

	@Override
	public void close() throws IOException {
		server.shutdown();
	}
}
