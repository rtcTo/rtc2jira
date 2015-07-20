package to.rtc.rtc2jira.exporter;

public interface Exporter {

	default boolean isConfigured() {
		return false;
	}

	void export() throws Exception;

}
