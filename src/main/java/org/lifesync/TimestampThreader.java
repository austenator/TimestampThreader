package org.lifesync;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.lifesync.modules.TimestampThreaderModule;
import org.lifesync.utilities.MediaHandler;

/** Main class that threads timestamps with media. */
public class TimestampThreader {
  public static void main(String[] args) {
    // Currently point to single photo test folder. Can move to TestParentFolder later.
    final Path pathToParentFolder = Paths.get("src/test/TestDirectories");
    final Path pathToMediaFolder = pathToParentFolder.resolve("Test");

    Injector injector =
        Guice.createInjector(new TimestampThreaderModule(pathToMediaFolder.toString()));
    MediaHandler mediaHandler = injector.getInstance(MediaHandler.class);

    //    mediaHandler.handlePhotos();
    mediaHandler.handleStories();
    System.out.println("Done.");
  }

  public static boolean isTrue() {
    return true;
  }
}
