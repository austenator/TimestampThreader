package org.lifesync.modules;

import com.google.inject.AbstractModule;

/** Google Guice Module */
public class TimestampThreaderModule extends AbstractModule {
  private final String pathToMediaFolder;

  public TimestampThreaderModule(final String pathToMediaFolder) {
    this.pathToMediaFolder = pathToMediaFolder;
  }

  @Override
  protected void configure() {
    bindConstant().annotatedWith(PathToMediaFolder.class).to(pathToMediaFolder);
  }
}
