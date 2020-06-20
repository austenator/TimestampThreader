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
  public void copy(final Path source, final Path destination) throws IOException {
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
    }
  }
}
