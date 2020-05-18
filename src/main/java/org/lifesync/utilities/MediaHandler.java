package org.lifesync.utilities;

import org.lifesync.model.MediaDetailsWithCaption;
import org.lifesync.model.MediaFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Handles processing different types of media sections in the {@link MediaFile}
 */
public class MediaHandler {
  private final Path pathToOutputFolder;
  private final Path pathToMediaFolder;
  private final MediaFile mediaFile;
  private final MediaCopier mediaCopier;
  private final MediaMetadataHandler metadataHandler;

  /**
   * Constructor.
   * @param mediaFile The media file to process.
   * @param pathToMediaFolder The path to the folder containing the original media.
   */
  public MediaHandler (final MediaFile mediaFile, final Path pathToMediaFolder) {
    this.mediaFile = mediaFile;
    this.pathToMediaFolder = pathToMediaFolder;
    this.pathToOutputFolder = Paths.get("src/test/TestDirectories/TestOutputFolder");
    this.mediaCopier = new MediaCopier();
    this.metadataHandler = new MediaMetadataHandler();
  }

  /**
   * Handles the 'photos' section.
   */
  public void handlePhotos() {
    Path destination;
    try {
      destination = Files.createDirectories(pathToOutputFolder.resolve("photos"));
    } catch (FileAlreadyExistsException e) {
      System.out.println("The directory exists as a file.");
      throw new RuntimeException("The directory exists as a file.", e);
    } catch (IOException e) {
      throw new RuntimeException("Something went wrong when creating a new output directory for photos.", e);
    }

    for (MediaDetailsWithCaption details : mediaFile.photos) {
      Path source = pathToMediaFolder.resolve(details.path);
      String fileName = source.getFileName().toString();
      destination = destination.resolve(fileName);

      // Copy photo to output folder.
      mediaCopier.copy(source, destination);

      // Modify newly created file's created date time with the details.
      metadataHandler.updateTimestamps(destination, details.taken_at);
    }
  }
}
