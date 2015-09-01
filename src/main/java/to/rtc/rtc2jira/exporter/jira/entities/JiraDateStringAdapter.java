/*
 * Copyright (c) 2014 BISON Schweiz AG, All Rights Reserved.
 */

package to.rtc.rtc2jira.exporter.jira.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Trend enum jaxb adapter
 *
 * @author gustaf.hansen
 */
public class JiraDateStringAdapter extends XmlAdapter<String, Date> {

  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");


  @Override
  public Date unmarshal(String dateAsString) throws Exception {
    return DATE_FORMAT.parse(dateAsString);
  }

  @Override
  public String marshal(Date date) throws Exception {
    return DATE_FORMAT.format(date);
  }

}
