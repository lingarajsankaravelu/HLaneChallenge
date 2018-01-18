package lingaraj.hourglass.in.hlanechallenge;

import android.app.Application;
import com.raizlabs.android.dbflow.config.FlowManager;
import lingaraj.hourglass.in.hlanechallenge.model.NotesData;

/**
 * Created by lingaraj on 1/16/18.
 */

public class HouseLaneApp extends Application
{

  private NotesData notesData;

  public NotesData getNotesData() {
    return notesData;
  }

  public void setNotesData(NotesData notesData) {
    this.notesData = notesData;
  }

  private final String TAG = "HOUSELANEAPP";
  @Override public void onCreate() {
    super.onCreate();
    FlowManager.init(this);
  }

}
