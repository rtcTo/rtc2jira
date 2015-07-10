/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package rtc2jira;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author roman.schaller
 *
 */
public class Settings {

  private static final String RTC_URL = "rtc.url";
  private static final String RTC_USER = "rtc.user";
  private static final String RTC_PASSWORD = "rtc.password";
  private static final String RTC_PROJECTAREA = "rtc.projectarea";

  private static final Settings instance = new Settings();

  private final Properties props;

  private Settings() {
    props = new Properties();

    try {
      props.load(new FileReader("settings.properties"));
    } catch (IOException e) {
      System.err.println("Please create your settings.properties out of the settings.properties.example");
      throw new RuntimeException(e);
    }
  }

  public static String getRtcUrl() {
    return instance.props.getProperty(RTC_URL);
  }

  public static String getRtcUser() {
    return instance.props.getProperty(RTC_USER);
  }

  public static String getRtcPassword() {
    return instance.props.getProperty(RTC_PASSWORD);
  }

  public static String getRtcProjectarea() {
    return instance.props.getProperty(RTC_PROJECTAREA);
  }

}
