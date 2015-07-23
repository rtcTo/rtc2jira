package to.rtc.rtc2jira.storage;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;


public class AttachmentStorageTest {


  @Test
  public void storeAndReadAttachment() throws Exception {
    AttachmentStorage storage = new AttachmentStorage();
    long workitemId = 54285L;

    storeExitAndOti(storage, workitemId);
    List<Attachment> readAttachments = storage.readAttachments(workitemId);

    Attachment exitPng =
        readAttachments.stream().filter(a -> a.getName().equals("exit.png")).findAny().get();
    Attachment otiJpg =
        readAttachments.stream().filter(a -> a.getName().equals("oti.jpg")).findAny().get();

    assertTrue(IOUtils.contentEquals(exitPng.openInputStream(), getExitPngInputStream()));
    assertTrue(IOUtils.contentEquals(otiJpg.openInputStream(), getOtiJpgInputStream()));
  }

  private void storeExitAndOti(AttachmentStorage storage, long workitemId) throws IOException {
    try (InputStream in = getExitPngInputStream()) {
      Attachment exit = storage.createAttachment(workitemId, "exit.png");
      try (OutputStream out = exit.openOutputStream()) {
        IOUtils.copy(in, out);
      }
    }

    try (InputStream in = getOtiJpgInputStream()) {
      Attachment oti = storage.createAttachment(54285L, "oti.jpg");
      try (OutputStream out = oti.openOutputStream()) {
        IOUtils.copy(in, out);
      }
    }
  }

  private InputStream getExitPngInputStream() {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    return classLoader.getResourceAsStream("attachmentStorage/exit.png");
  }

  private InputStream getOtiJpgInputStream() {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    return classLoader.getResourceAsStream("attachmentStorage/oti.jpg");
  }
}
