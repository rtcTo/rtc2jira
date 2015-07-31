package to.rtc.rtc2jira.storage;

import java.util.ArrayList;
import java.util.List;

import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

public class StorageQuery {

  public static final List<ODocument> getRTCWorkItems(StorageEngine engine) {
    final List<ODocument> result = new ArrayList<>();
    engine.withDB(db -> {
      OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<ODocument>("select * from WorkItem");
      List<ODocument> queryResults = db.query(query);
      result.addAll(queryResults);
    });
    return result;
  }


}
