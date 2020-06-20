package org.lifesync.utilities;

import com.google.inject.Inject;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.lifesync.model.MediaDetailsBasic;
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
    this.pathToOutputFolder = Paths.get("/Users/austen/Documents/Social Media Archives/InstagramThreadedOutput");
//    this.pathToOutputFolder = Paths.get("src/test/TestDirectories/TestOutputFolder");
    this.pathToMediaFolder = pathToMediaFolder;
    this.mediaFile = jsonReader.read(pathToMediaFile.toString(), MediaFile.class);
    this.mediaCopier = mediaCopier;
    this.mediaMetadataHandler = mediaMetadataHandler;
  }

  /** Handles the 'stories' section. */
  public void handleStories() {
    // TODO > Combine with handlePhotos.
    Path storiesDirectory = createDirectory("stories");
    handleBasicMedia(storiesDirectory, mediaFile.stories);
  }

  /** Handles the 'photos' section. */
  public void handlePhotos() {
    Path photosDirectory = createDirectory("photos");
    handleBasicMedia(photosDirectory, mediaFile.photos);
  }

  /** Handles the 'direct' section. */
  public void handleDirect() {
    Path directDirectory = createDirectory("direct");
    handleBasicMedia(directDirectory, mediaFile.direct);
  }

  /** Handles the 'videos' section. */
  public void handleVideos() {
    Path videosDirectory = createDirectory("videos");
    handleBasicMedia(videosDirectory, mediaFile.videos);
  }

  /** Handles the 'profile' section. */
  public void handleProfile() {
    Path profileDirectory = createDirectory("profile");
    handleBasicMedia(profileDirectory, mediaFile.profile);
  }

  /**
   * Generically copies and updates timestamps of all specified media.
   * @param outputDirectory The sub-directory to place all updated media into inside of the output directory.
   * @param mediaFileSection The section of the media file to iterate over and process.
   * @param <T> Basic details class.
   */
  private <T extends MediaDetailsBasic> void handleBasicMedia(Path outputDirectory, List<T> mediaFileSection) {
    for (MediaDetailsBasic details : mediaFileSection) {
      Path source = pathToMediaFolder.resolve(details.path);
      String fileName = source.getFileName().toString();
      Path destination = outputDirectory.resolve(fileName);

      try {
        // Copy photo to output folder.
        mediaCopier.copy(source, destination);
      } catch (IOException e) {
        // Skip the current file. Instagram didn't include ALL the downloaded data.
        continue;
      }

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
