/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.storage;

import static java.nio.file.Files.createFile;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.newOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * @author roman.schaller
 *
 */
public class Attachment {

  static final public String EXPORTED_ATTACHMENTS_PROPERTY = "exportedAttachments";

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

  public Path getPath() {
    return path;
  }

  public OutputStream openOutputStream() throws IOException {
    if (!exists(path)) {
      createFile(path);
    }
    return newOutputStream(path);
  }

  public InputStream openInputStream() throws IOException {
    return newInputStream(path);
  }
}
