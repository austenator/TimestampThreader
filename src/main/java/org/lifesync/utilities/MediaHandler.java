package org.lifesync.utilities;

import org.lifesync.model.MediaDetailsWithCaption;
import org.lifesync.model.MediaFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * IN PROGRESS
 */
public class MediaHandler {
  private final Path pathToOutputFolder;
  private final Path pathToMediaFolder;
  private final MediaFile mediaFile;
  private final MediaCopier mediaCopier;

  public MediaHandler (final MediaFile mediaFile, final Path pathToMediaFolder) {
    this.mediaFile = mediaFile;
    this.pathToMediaFolder = pathToMediaFolder;
    this.pathToOutputFolder = Paths.get("src/test/TestDirectories/TestOutputFolder");
    this.mediaCopier = new MediaCopier();
  }

  public void handlePhotos() {
    Path destination;
    try {
      destination = Files.createDirectories(pathToOutputFolder.resolve("photos"));
    } catch (FileAlreadyExistsException e) {
      throw new RuntimeException("The directory exists as a file.", e);
    } catch (IOException e) {
      throw new RuntimeException("Something went wrong when creating a new output directory for photos.", e);
    }

    for (MediaDetailsWithCaption details : mediaFile.photos) {
      Path source = pathToMediaFolder.resolve(details.path);

      // Copy photo to output folder.
      mediaCopier.copy(source, destination);

      // Read in copied file.

      // Edit created by + modified by.
      System.out.println(details.path);
    }
  }
}
