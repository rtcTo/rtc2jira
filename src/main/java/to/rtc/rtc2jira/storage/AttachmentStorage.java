/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

/**
 * @author roman.schaller
 *
 */
public class AttachmentStorage {
  private Path basePath = Paths.get("attachements");

  public AttachmentStorage() throws IOException {
    Files.createDirectories(basePath);
  }

  public void storeAttachment(Attachment attachment, InputStream in) throws IOException {
    String storeName = attachment.getWorkitemId() + "_" + attachment.getName();
    attachment.setPath(basePath.resolve(storeName));
    IOUtils.copy(in, attachment.getOutputStream());
  }

  public List<Attachment> readAttachments(long workitemId) throws IOException {
    String prefix = workitemId + "_";
    List<Attachment> attachments = new ArrayList<>();
    try (DirectoryStream<Path> fileListStream = Files.newDirectoryStream(basePath)) {
      fileListStream.forEach(p -> {
        if (p.getFileName().toString().startsWith(prefix)) {
          String realName = p.getFileName().toString().substring(prefix.length());
          Attachment att = new Attachment(workitemId, realName);
          att.setPath(p);
          attachments.add(att);
        }
      });
    }
    return attachments;
  }

}
