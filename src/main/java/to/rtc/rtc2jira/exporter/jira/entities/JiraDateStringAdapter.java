/*
 * Copyright (c) 2014 BISON Schweiz AG, All Rights Reserved.
 */

package to.rtc.rtc2jira.exporter.jira.entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Trend enum jaxb adapter
 *
 * @author gustaf.hansen
 */
public class JiraDateStringAdapter extends XmlAdapter<String, Date> {

  // N.B. this works for timezones without a colon separating hours and minutes (e.g. +0200), which
  // currently is the case with datetime strings returned by Jira
  public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");


  @Override
  public Date unmarshal(String dateAsString) throws Exception {
    if (dateAsString.length() == 10) {
      return DATE_FORMAT.parse(dateAsString);
    } else {
      return DATE_TIME_FORMAT.parse(dateAsString);
    }
  }

  @Override
  public String marshal(Date date) throws Exception {
    Calendar myCal = new GregorianCalendar();
    myCal.setTime(date);
    return javax.xml.bind.DatatypeConverter.printDateTime(myCal);
  }

}
