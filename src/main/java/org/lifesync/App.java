package org.lifesync;

import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
  public static void main(String[] args) {
    // Currently point to single photo test folder. Can move to TestParentFolder later.
    final Path pathToParent = Paths.get("src/test/TestDirectories/Test");
    final Path pathToMediaFile = pathToParent.resolve("media.json");


    System.out.println(pathToMediaFile.toString());
  }

  public static boolean isTrue() {
    return true;
  }
}
