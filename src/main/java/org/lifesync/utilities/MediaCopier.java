package org.lifesync.utilities;

import java.io.IOException;
import java.nio.file.*;

/** Copies media files. */
public class MediaCopier {
  /**
   * Copies the file at source to a new file at the destination.
   *
   * @param source The path of the file to copy.
   * @param destination The path of the copied file to create.
   */
  public void copy(final Path source, final Path destination) {
    if (Files.exists(destination)) {
      return;
    }

    try {
      Files.copy(
          source,
          destination,
          StandardCopyOption.REPLACE_EXISTING,
          StandardCopyOption.COPY_ATTRIBUTES);
    } catch (UnsupportedOperationException e) {
      throw new RuntimeException("Invalid copy option. (Shouldn't be thrown).", e);
    } catch (DirectoryNotEmptyException e) {
      throw new RuntimeException("The destination directory is not empty.", e);
    } catch (IOException e) {
      throw new RuntimeException(
          "A problem occurred while copying "
              + source.toString()
              + " to "
              + destination.toString()
              + ".",
          e);
    }
  }
}
