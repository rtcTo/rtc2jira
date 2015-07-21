package to.rtc.rtc2jira;

import to.rtc.rtc2jira.extract.RTCExtractor;
import to.rtc.rtc2jira.mapping.DefaultMappingRegistry;
import to.rtc.rtc2jira.mapping.DirectMapping;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author roman.schaller
 *
 */
public class Main {

	public static void main(String[] args) throws Exception {
		registerDefaultMappings();
		try (StorageEngine storageEngine = new StorageEngine()) {
			RTCExtractor extractor = new RTCExtractor(Settings.getInstance(), storageEngine);
			extractor.extract();
		}
	}

	private static void registerDefaultMappings() {
		DefaultMappingRegistry registry = DefaultMappingRegistry.getInstance();
		registry.register("summary", new DirectMapping("summary"));
		registry.register("description", new DirectMapping("description"));
		registry.register("workItemType", new DirectMapping("workItemType"));
	}
}
