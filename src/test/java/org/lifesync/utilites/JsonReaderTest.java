package org.lifesync.utilites;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lifesync.model.MediaFile;
import org.lifesync.utilities.JsonReader;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JsonReader")
public class JsonReaderTest {
  private static final String testToTestMediaFilePath = "src/test/TestDirectories/Test/media.json";

  @DisplayName("successfully reads in the media file")
  @Test
  void test_Read_Success() {
    MediaFile mediaFile = JsonReader.read(testToTestMediaFilePath, MediaFile.class);
    assertNotNull(mediaFile);
  }

  @DisplayName("throws an error if it can't find the file")
  @Test
  void test_Read_ThrowsFileNotFoundException() {
    RuntimeException actualException =
        assertThrows(RuntimeException.class, () -> JsonReader.read("non/existent/path", MediaFile.class));
    assertEquals("Something went wrong when reading in the media file.", actualException.getMessage());
  }
}
