package to.rtc.rtc2jira.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

  public static final ODocument getRTCWorkItem(StorageEngine engine, String workItemId) {
    final ODocument[] result = new ODocument[1];
    result[0] = null;
    engine.withDB(db -> {
      OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<ODocument>("select * from WorkItem");
      List<ODocument> queryResults = db.query(query);
      if (queryResults.size() > 0) {
        result[0] = queryResults.get(0);
      }
    });
    return result[0];
  }


  /**
   * Retrieves the fieldValue from a given document with a name
   * 
   * @param document - document where to query the field
   * @param fieldname - name of field to query
   * @param fallbackValue - returns this value - in case there is no field with provided field name
   * @return fieldValue (or fallbackValue if fieldValue is null)
   */
  @SuppressWarnings("unchecked")
  public static final <T> T getField(ODocument document, String fieldname, T fallbackValue) {
    T fieldValue = (T) Optional.ofNullable(document.field(fieldname)).orElse(fallbackValue);
    return fieldValue;
  }


}
