/*
 * Copyright (c) 2014 BISON Schweiz AG, All Rights Reserved.
 */

package to.rtc.rtc2jira.exporter.jira.entities;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Radio button type adapter
 *
 * @author gustaf.hansen
 */
public class ArchivedTypeAdapter extends XmlAdapter<ArchivedTypeAdapter.JiraRadioItem, Boolean> {



  @Override
  public Boolean unmarshal(JiraRadioItem item) throws Exception {
    return item == null ? Boolean.FALSE : Boolean.TRUE;
  }

  @Override
  public JiraRadioItem marshal(Boolean item) throws Exception {
    return item.booleanValue() ? JiraRadioItem.YES : null;
  }

  @XmlRootElement
  public static class JiraRadioItem {
    private String value;

    static JiraRadioItem YES = new JiraRadioItem(true);
    static JiraRadioItem NONE = new JiraRadioItem(false);

    JiraRadioItem(boolean archived) {
      value = archived ? "Yes" : "None";
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }
}
