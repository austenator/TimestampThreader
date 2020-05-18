package org.lifesync.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.OffsetDateTime;

/**
 * Handles modification of file metadata.
 */
public class MediaMetadataHandler {

  /**
   * Updates the creationTime and lastModifiedTime of the provided file (path) to the provided date-time.
   *
   * @param pathToFileToModify {@link Path} to the file to be updated.
   * @param newCreatedDatetime ISO-8601 formatted UTC offset String. (E.g "2018-02-13T10:20:12.120+00:00")
   */
  public void updateTimestamps(final Path pathToFileToModify, final String newCreatedDatetime) {
    OffsetDateTime newCreatedDateTime = OffsetDateTime.parse(newCreatedDatetime);

    try {
      Files.setAttribute(pathToFileToModify, "creationTime", FileTime.from(newCreatedDateTime.toInstant()));
      Files.setAttribute(pathToFileToModify, "lastModifiedTime", FileTime.from(newCreatedDateTime.toInstant()));
    } catch (IOException e) {
      throw new RuntimeException("Something went wrong when writing the new file attributes.", e);
    }

//    BasicFileAttributes attributes;
//    try {
//      attributes = Files.readAttributes(pathToFileToModify.toAbsolutePath(), BasicFileAttributes.class);
//    } catch (IOException e) {
//      throw new RuntimeException("Something went wrong when reading the file attributes.", e);
//    }
//    System.out.println(attributes.creationTime());
//    System.out.println(attributes.lastModifiedTime());
  }
}
