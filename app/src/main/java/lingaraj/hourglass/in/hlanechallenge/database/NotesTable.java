package lingaraj.hourglass.in.hlanechallenge.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by lingaraj on 1/16/18.
 */
@Table(databaseName = HLaneDatabase.NAME, tableName = NotesTable.NAME)
public class NotesTable extends BaseModel {

  public static final String NAME = "note_table";

  @PrimaryKey(autoincrement = true)
  @Column
  private long id;

  @Column
  private String title;

  @Column
  private String text;

  @Column
  private String images;

  @Column
  private String createdAt;

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

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getImages() {
    return images;
  }

  public void setImages(String images) {
    this.images = images;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }
}
