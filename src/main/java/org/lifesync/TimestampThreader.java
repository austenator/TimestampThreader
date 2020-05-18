package org.lifesync;

import org.lifesync.model.MediaFile;
import org.lifesync.utilities.JsonReader;
import org.lifesync.utilities.MediaHandler;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TimestampThreader {
  public static void main(String[] args) {
    // Currently point to single photo test folder. Can move to TestParentFolder later.
    final Path pathToParentFolder = Paths.get("src/test/TestDirectories");
    final Path pathToMediaFolder = pathToParentFolder.resolve("Test");
    final Path pathToMediaFile = pathToMediaFolder.resolve("media.json");


    // Read in JSON file.
    MediaFile mediaFile = JsonReader.read(pathToMediaFile.toString(), MediaFile.class);

    MediaHandler mediaHandler = new MediaHandler(mediaFile, pathToMediaFolder);
    mediaHandler.handlePhotos();

    System.out.println("Done.");
  }

  public static boolean isTrue() {
    return true;
  }
}
