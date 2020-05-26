package org.lifesync.utilities;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;

/** Reads in Json files. */
public class JsonReader {
  /**
   * Reads in a Json file and converts it to an object of the given class type.
   *
   * @param pathToFile the path from the root of the project ("src/.../") to the json file.
   * @param typeOfFile the expected class type of the json file to create an object of.
   * @param <T> a gson acceptable class type
   * @return An object populated with the details of the json file.
   */
  public <T> T read(final String pathToFile, final Class<T> typeOfFile) {
    T jsonFile;

    try (FileReader reader = new FileReader(pathToFile)) {
      jsonFile = new Gson().fromJson(reader, typeOfFile);
    } catch (IOException e) {
      throw new RuntimeException("Something went wrong when reading in the media file.", e);
    }
    return jsonFile;
  }
}
