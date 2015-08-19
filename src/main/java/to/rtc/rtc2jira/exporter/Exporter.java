package to.rtc.rtc2jira.exporter;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.storage.StorageEngine;

import com.orientechnologies.orient.core.record.impl.ODocument;

public interface Exporter {

  void initialize(Settings settings, StorageEngine engine) throws Exception;

  default boolean isConfigured() {
    return false;
  }

  void createOrUpdateItem(ODocument item) throws Exception;

}
