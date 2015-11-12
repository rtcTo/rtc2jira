package to.rtc.rtc2jira.storage;

import static to.rtc.rtc2jira.storage.FieldNames.ID;

import java.io.Closeable;
import java.io.IOException;
import java.util.function.Consumer;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.server.OServer;
import com.orientechnologies.orient.server.OServerMain;

public class StorageEngine implements Closeable, AutoCloseable {

  private OServer server;
  private AttachmentStorage attachmentStorage;
  private String url;

  public StorageEngine() throws Exception {
    server = OServerMain.create();
    server.startup(Thread.currentThread().getContextClassLoader().getResourceAsStream("orientconf.xml"));
    server.activate();
    attachmentStorage = new AttachmentStorage();
    url = "plocal:./databases/rtc2jira";
  }

  public void setConnectionUrl(String url) {
    this.url = url;
  }

  public void withDB(Consumer<ODatabaseDocumentTx> doWithDB) {
    try (ODatabaseDocumentTx db = new ODatabaseDocumentTx(url)) {
      if (!db.exists()) {
        db.create();
        OClass workItemClass = db.getMetadata().getSchema().createClass("WORKITEM");
        workItemClass.setStrictMode(false);
        workItemClass.createProperty(ID, OType.STRING).setMandatory(true).setNotNull(true);
        workItemClass.createIndex("workitem_ID_IDX", OClass.INDEX_TYPE.UNIQUE, ID);
      } else {
        db.open("admin", "admin");
      }
      doWithDB.accept(db);
    }
  }

  public final void setField(ODocument document, Field field) {
    setFields(document, field);
  }

  public final void setFields(ODocument document, Field... fields) {
    withDB(db -> {
      for (Field field : fields) {
        document.field(field.getName(), field.getValue());
      }
      document.save();
    });
  }

  public AttachmentStorage getAttachmentStorage() {
    return attachmentStorage;
  }

  @Override
  public void close() throws IOException {
    server.shutdown();
  }
}
