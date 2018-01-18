package lingaraj.hourglass.in.hlanechallenge.model;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.sql.language.Select;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lingaraj.hourglass.in.hlanechallenge.database.NotesTable;

/**
 * Created by lingaraj on 1/16/18.
 */

public class NotesData {
  private final String TAG = "NOTESDATA";
  private Map<Long,Note> notesMap = new HashMap<>();

  public Map<Long, Note> getNotesMap() {
    return notesMap;
  }


  public NotesData(){
    fetchOfflineNotes();
  }

  private void fetchOfflineNotes() {
    //fetch notes which were saved in db and populate it to views once came back.
    long count = new Select().count().from(NotesTable.class).count();
    if (count>0){
      List<NotesTable> records = new Select().from(NotesTable.class).queryList();
      for (NotesTable record:records) {
        Note note = null;
        if (record.getImages()!=null && !record.getImages().isEmpty()){
          //if the images string is not empty which mean the current note contain images , convert back to list using gson
          Type typeIndicatorForGson = new TypeToken<List<String>>() {}.getType();
          List<String> images = new Gson().fromJson(record.getImages(),typeIndicatorForGson);
          note = new Note(record.getId(),record.getTitle(),record.getText(),record.getCreatedAt(),images);
        }
        else {
          //if the image string is empty
          note = new Note(record.getId(),record.getTitle(),record.getText(),record.getCreatedAt(),new ArrayList<String>());

        }
        notesMap.put(record.getId(),note);
      }
    }
  }

  public void addNote(String title,String text,String created_at,List<String> images){
    //add a new note map to both db and notesmap
    long record_id = saveToNotesTable(title,text,created_at,images);
    Note note = new Note(record_id,title,text,created_at,images);
    notesMap.put(record_id,note);
    Log.d(TAG,"Note added to notes list");
  }

  public void deleteNote(long note_id){
    // delete a note from both db and notesmap
    removeFromDB(note_id);
    notesMap.remove(note_id);
    Log.d(TAG,"Record Removed from map:"+note_id);
  }

  private void removeFromDB(long note_id) {
    NotesTable record = new Select().from(NotesTable.class).where("id=?",note_id).querySingle();
    record.delete();
    Log.d(TAG,"Record removed from Db:"+note_id);
  }

  private long saveToNotesTable(String title, String text, String created_at, List<String> images) {
    //saves Newly created record to Notes Table and return the long id of the record so that it can be used with the list data as well
    NotesTable record = new NotesTable();
    if (images.size()>0){
      //an images have been added to the note
      record.setImages(new Gson().toJson(images));
    }
    record.setCreatedAt(created_at);
    record.setTitle(title);
    record.setText(text);
    record.setCreatedAt(created_at);
    record.save();
    Log.d(TAG,"Record added to notes table:"+title);
    return record.getId();
  }






}
