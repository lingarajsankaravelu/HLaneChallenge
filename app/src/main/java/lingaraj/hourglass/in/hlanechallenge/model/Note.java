package lingaraj.hourglass.in.hlanechallenge.model;

import java.util.List;

/**
 * Created by lingaraj on 1/16/18.
 */

public class Note {
  private long id;
  private String title;
  private String text;
  private List<String> images;
  private String createdAt;

  public Note(long record_id, String title, String text, String created_at, List<String> images) {
    this.id = record_id;
    this.title = title;
    this.text = text;
    this.createdAt = created_at;
    this.images = images;
  }


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public List<String> getImages() {
    return images;
  }

  public void setImages(List<String> images) {
    this.images = images;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
