package org.lifesync.utilities;

import com.google.inject.Inject;
import org.lifesync.model.MediaDetailsWithCaption;
import org.lifesync.model.MediaFile;
import org.lifesync.modules.PathToMediaFolder;

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
  private final MediaMetadataHandler mediaMetadataHandler;

  /**
   * Constructor.
   * @param pathToMediaFolder The injected constant of where the media is.
   * @param jsonReader The reader for json files.
   * @param mediaCopier The media copier.
   * @param mediaMetadataHandler The media metadata handler.
   */
  @Inject
  public MediaHandler (
      @PathToMediaFolder final Path pathToMediaFolder,
      final JsonReader jsonReader,
      final MediaCopier mediaCopier,
      final MediaMetadataHandler mediaMetadataHandler
  ) {
    final Path pathToMediaFile = pathToMediaFolder.resolve("media.json");
    this.pathToOutputFolder = Paths.get("src/test/TestDirectories/TestOutputFolder");
    this.pathToMediaFolder = pathToMediaFolder;
    this.mediaFile = jsonReader.read(pathToMediaFile.toString(), MediaFile.class);
    this.mediaCopier = mediaCopier;
    this.mediaMetadataHandler = mediaMetadataHandler;
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
      mediaMetadataHandler.updateTimestamps(destination, details.taken_at);
    }
  }
}
