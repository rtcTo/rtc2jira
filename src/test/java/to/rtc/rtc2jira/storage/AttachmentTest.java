package to.rtc.rtc2jira.storage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class AttachmentTest {

  @Rule
  public TemporaryFolder _tempFolder = new TemporaryFolder();
  private Attachment attachment;


  @Before
  public void setUp() throws Exception {
    attachment = new Attachment(10L, "anyName");
  }

  @Test
  public void testOpenOutputStream_WhenFileDoesntExist_CreateAndOpenOutputStream()
      throws IOException {
    String filename = "nonExistingFile";
    Path file = Paths.get(_tempFolder.getRoot().toURI()).resolve(filename);
    attachment.setPath(file);
    assertFalse(Files.exists(file));
    try (OutputStream outputstream = attachment.openOutputStream()) {
      assertNotNull(outputstream);
    }
    assertTrue(Files.exists(file));
  }

  @Test
  public void testOpenOutputStream_WhenFileExist_OpenOutputStream() throws IOException {
    Path file = _tempFolder.newFile("blabla.whatAnIdioticFileExtension").toPath();
    assertTrue(Files.exists(file));
    attachment.setPath(file);
    try (OutputStream outputstream = attachment.openOutputStream()) {
      assertNotNull(outputstream);
    }
    assertTrue(Files.exists(file));
  }
}
