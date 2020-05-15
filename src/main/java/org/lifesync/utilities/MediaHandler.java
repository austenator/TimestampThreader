package org.lifesync.utilities;

import org.lifesync.model.MediaDetailsWithCaption;
import org.lifesync.model.MediaFile;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;

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

      // Read in copied file.
      File newPhoto = new File(destination.toString());

      PosixFileAttributes attributes;

      try {
        attributes = Files.readAttributes(destination.toAbsolutePath(), PosixFileAttributes.class);
      } catch (IOException e) {
        throw new RuntimeException("Something went wrong when reading the file attributes.", e);
      }

      // Works!
      System.out.println(attributes.creationTime());


//      MediaMetadataHandler metadataHandler = new MediaMetadataHandler();
//      metadataHandler.readMetadata(new File(source.toString()));
//      metadataHandler.readMetadata(newPhoto);


      // Edit created by + modified by.
//      System.out.println(destination.toString());
    }
  }
}
