/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.storage;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author roman.schaller
 *
 */
public class AttachmentStorage {
  private Path basePath = Paths.get("attachments");

  public AttachmentStorage() throws IOException {
    Files.createDirectories(basePath);
  }

  public Attachment createAttachment(long workitemId, String name) {
    Attachment attachment = new Attachment(workitemId, name);
    String storeName = attachment.getWorkitemId() + "_" + attachment.getName();
    attachment.setPath(basePath.resolve(storeName));
    return attachment;
  }

  public List<Attachment> readAttachments(long workitemId) throws IOException {
    String prefix = workitemId + "_";
    List<Attachment> attachments = new ArrayList<>();
    try (DirectoryStream<Path> fileListStream = Files.newDirectoryStream(basePath)) {
      fileListStream.forEach(p -> {
        String filename = p.getFileName().toString();
        if (filename.startsWith(prefix)) {
          String realName = filename.substring(prefix.length());
          Attachment att = new Attachment(workitemId, realName);
          att.setPath(p);
          attachments.add(att);
        }
      });
    }
    return attachments;
  }

}
