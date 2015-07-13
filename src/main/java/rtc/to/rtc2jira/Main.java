package rtc.to.rtc2jira;

import rtc.to.rtc2jira.extract.RTCExtractor;
import rtc.to.rtc2jira.storage.StorageEngine;

/**
 * @author roman.schaller
 *
 */
public class Main {

	public static void main(String[] args) throws Exception {
		try (StorageEngine storageEngine = new StorageEngine()) {
			RTCExtractor extractor = new RTCExtractor(Settings.getInstance(), storageEngine);
			extractor.extract();
		}
	}
}
