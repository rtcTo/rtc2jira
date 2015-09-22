/*
 * Copyright (c) 2014 BISON Schweiz AG, All Rights Reserved.
 */

package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Radio button type adapter
 *
 * @author gustaf.hansen
 */
public class ArchivedTypeAdapter extends XmlAdapter<Object, Boolean> {



  @Override
  public Boolean unmarshal(Object item) throws Exception {
    return item == null ? Boolean.FALSE : Boolean.TRUE;
  }

  @Override
  public Object marshal(Boolean item) throws Exception {
    return item.booleanValue() ? JiraRadioItem.YES : null;
  }
}
