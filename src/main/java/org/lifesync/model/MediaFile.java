package org.lifesync.model;

import java.util.List;

public class MediaFile {
  public List<MediaDetailsWithCaption> stories;
  public List<MediaDetailsWithCaption> photos;
  public List<MediaDetailsWithCaption> videos;
  public List<MediaDetailsWithActiveFlag> profile;
  public List<MediaDetailsBasic> direct;
}
