package org.lifesync;

import org.lifesync.model.MediaFile;
import org.lifesync.utilities.JsonReader;

import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
  public static void main(String[] args) {
    // Currently point to single photo test folder. Can move to TestParentFolder later.
    final Path pathToParent = Paths.get("src/test/TestDirectories/Test");
    final Path pathToMediaFile = pathToParent.resolve("media.json");

    // Read in JSON file.
    MediaFile mediaFile = JsonReader.read(pathToMediaFile.toString(), MediaFile.class);

    System.out.println("Austen!");
  }

  public static boolean isTrue() {
    return true;
  }
}
