package org.lifesync.utilites;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lifesync.model.MediaFile;
import org.lifesync.utilities.JsonReader;

@DisplayName("JsonReader")
public class JsonReaderTest {
  private static final String testToTestMediaFilePath = "src/test/TestDirectories/Test/media.json";

  /** Class under test. */
  JsonReader jsonReader = new JsonReader();

  @DisplayName("successfully reads in the media file")
  @Test
  void test_Read_Success() {
    MediaFile mediaFile = jsonReader.read(testToTestMediaFilePath, MediaFile.class);
    assertNotNull(mediaFile);
  }

  @DisplayName("throws an error if it can't find the file")
  @Test
  void test_Read_ThrowsFileNotFoundException() {
    RuntimeException actualException =
        assertThrows(
            RuntimeException.class, () -> jsonReader.read("non/existent/path", MediaFile.class));
    assertEquals(
        "Something went wrong when reading in the media file.", actualException.getMessage());
  }
}
