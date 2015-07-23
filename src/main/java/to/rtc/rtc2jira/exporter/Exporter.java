package to.rtc.rtc2jira.exporter;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.storage.StorageEngine;

public interface Exporter {

  void initialize(Settings settings, StorageEngine engine);

  default boolean isConfigured() {
    return false;
  }

  void export() throws Exception;

}
