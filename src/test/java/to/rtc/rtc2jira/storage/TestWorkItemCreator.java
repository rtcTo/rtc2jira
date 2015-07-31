package to.rtc.rtc2jira.storage;

import java.util.concurrent.atomic.AtomicReference;

import com.orientechnologies.orient.core.record.impl.ODocument;

public class TestWorkItemCreator {

  public static ODocument createWorkItem(int id, StorageEngine storage) {
    final AtomicReference<ODocument> reference = new AtomicReference<>();
    storage.withDB(db -> {
      ODocument doc = new ODocument("WorkItem");
      doc.field("ID", id);
      doc.save();
      reference.set(doc);
    });
    return reference.get();
  }

}
