package org.lifesync.utilities;

import com.google.inject.Inject;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.lifesync.model.MediaDetailsWithCaption;
import org.lifesync.model.MediaFile;
import org.lifesync.modules.PathToMediaFolder;

/** Handles processing different types of media sections in the {@link MediaFile} */
public class MediaHandler {
  private final Path pathToOutputFolder;
  private final Path pathToMediaFolder;
  private final MediaFile mediaFile;
  private final MediaCopier mediaCopier;
  private final MediaMetadataHandler mediaMetadataHandler;

  /**
   * Constructor.
   *
   * @param stringPathToMediaFolder The string path that is the injected constant of where the media
   *     is.
   * @param jsonReader The reader for json files.
   * @param mediaCopier The media copier.
   * @param mediaMetadataHandler The media metadata handler.
   */
  @Inject
  public MediaHandler(
      @PathToMediaFolder final String stringPathToMediaFolder,
      final JsonReader jsonReader,
      final MediaCopier mediaCopier,
      final MediaMetadataHandler mediaMetadataHandler) {
    final Path pathToMediaFolder = Path.of(stringPathToMediaFolder);
    final Path pathToMediaFile = pathToMediaFolder.resolve("media.json");
    this.pathToOutputFolder = Paths.get("src/test/TestDirectories/TestOutputFolder");
    this.pathToMediaFolder = pathToMediaFolder;
    this.mediaFile = jsonReader.read(pathToMediaFile.toString(), MediaFile.class);
    this.mediaCopier = mediaCopier;
    this.mediaMetadataHandler = mediaMetadataHandler;
  }

  /** Handles the 'stories' section. */
  public void handleStories() {
    // TODO > Combine with handlePhotos.
    Path storiesDirectory = createDirectory("stories");
    for (MediaDetailsWithCaption details : mediaFile.stories) {
      Path source = pathToMediaFolder.resolve(details.path);
      String fileName = source.getFileName().toString();
      Path destination = storiesDirectory.resolve(fileName);

      // Copy photo to output folder.
      mediaCopier.copy(source, destination);

      // Modify newly created file's created date time with the details.
      mediaMetadataHandler.updateTimestamps(destination, details.taken_at);
    }
  }

  /** Handles the 'photos' section. */
  public void handlePhotos() {
    Path destination = createDirectory("photos");
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

  /**
   * Creates a directory in the output folder with the given name.
   *
   * @param directoryName The name of the directory to create.
   * @return The {@link Path} to the newly created directory.
   */
  private Path createDirectory(final String directoryName) {
    Path destination;
    try {
      destination = Files.createDirectories(pathToOutputFolder.resolve(directoryName));
    } catch (FileAlreadyExistsException e) {
      System.out.println("The directory exists as a file.");
      throw new RuntimeException("The directory exists as a file.", e);
    } catch (IOException e) {
      throw new RuntimeException(
          "Something went wrong when creating a new output directory for photos.", e);
    }
    return destination;
  }
}
