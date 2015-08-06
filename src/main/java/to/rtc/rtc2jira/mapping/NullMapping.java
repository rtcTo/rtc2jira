/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.mapping;

import to.rtc.rtc2jira.spi.MappingAdapter;

/**
 * Use this mapping if you really want to ignore an RTC attribute.
 * 
 * @author roman.schaller
 *
 */
public class NullMapping extends MappingAdapter {

  // Does nothing. Just ignore the attribute.

}
