/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * @author roman.schaller
 *
 */
public class Attachment {

  private final long workitemId;
  private final String name;
  private Path path;

  public Attachment(long workitemId, String name) {
    this.workitemId = workitemId;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public long getWorkitemId() {
    return workitemId;
  }

  void setPath(Path path) {
    this.path = path;
  }

  public OutputStream openOutputStream() throws IOException {
    File file = path.toFile();
    if (!file.exists()) {
      file.createNewFile();
    }
    return new FileOutputStream(file);
  }

  public InputStream openInputStream() throws IOException {
    return new FileInputStream(path.toFile());
  }
}
