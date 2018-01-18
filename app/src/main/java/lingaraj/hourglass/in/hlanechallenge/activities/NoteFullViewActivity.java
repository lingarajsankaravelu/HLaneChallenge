package lingaraj.hourglass.in.hlanechallenge.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import lingaraj.hourglass.in.hlanechallenge.HouseLaneApp;
import lingaraj.hourglass.in.hlanechallenge.R;
import lingaraj.hourglass.in.hlanechallenge.adapters.NewNoteImageAdapter;
import lingaraj.hourglass.in.hlanechallenge.databinding.ActivitySingleNoteSelectionBinding;
import lingaraj.hourglass.in.hlanechallenge.model.Note;
import lingaraj.hourglass.in.hlanechallenge.model.NotesData;

/**
 * Created by lingaraj on 1/18/18.
 */

public class NoteFullViewActivity extends AppCompatActivity {

  private final String TAG = "NOTEFULLVIEW";
  private HouseLaneApp app;
  private NotesData notes_data;
  private ActivitySingleNoteSelectionBinding activity_binding;
  private NewNoteImageAdapter mAdapter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_single_note_selection);
    activity_binding = DataBindingUtil.setContentView(NoteFullViewActivity.this,R.layout.activity_single_note_selection);
    app = (HouseLaneApp) getApplication();
    notes_data = app.getNotesData();
    Long record_id = getIntent().getLongExtra("record_id",0);
    Note note = this.notes_data.getNotesMap().get(record_id);
    setData(note);
  }

  private void setData(Note record) {
    activity_binding.title.setText(record.getTitle());
    activity_binding.content.setText(record.getText());
    String created_time = "Created At:"+" "+record.getCreatedAt();
    activity_binding.createdAt.setText(created_time);
    if (record.getImages().size()>0){
      //contains images use adapter to display data
      mAdapter = new NewNoteImageAdapter();
      activity_binding.imagesRecyclerview.setNestedScrollingEnabled(true);
      activity_binding.imagesRecyclerview.setHasFixedSize(true);
      activity_binding.imagesRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
      activity_binding.imagesRecyclerview.setAdapter(mAdapter);
      mAdapter.setNewData(record.getImages());
      activity_binding.imagesRecyclerview.setVisibility(View.VISIBLE);
    }
    else {
      activity_binding.imagesRecyclerview.setVisibility(View.GONE);
    }
    Log.d(TAG,"Data set on activity");

  }
}
