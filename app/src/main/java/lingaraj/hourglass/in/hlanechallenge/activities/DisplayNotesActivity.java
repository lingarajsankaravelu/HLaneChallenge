package lingaraj.hourglass.in.hlanechallenge.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import lingaraj.hourglass.in.hlanechallenge.HouseLaneApp;
import lingaraj.hourglass.in.hlanechallenge.R;
import lingaraj.hourglass.in.hlanechallenge.adapters.DisplayNotesAdapter;
import lingaraj.hourglass.in.hlanechallenge.databinding.ActivityDisplayNoteBinding;
import lingaraj.hourglass.in.hlanechallenge.model.NotesData;

/**
 * Created by lingaraj on 1/17/18.
 */

public class DisplayNotesActivity extends AppCompatActivity {
  private final String TAG = "DISPNOTEACT";
  ActivityDisplayNoteBinding activity_binding;
  private HouseLaneApp app;
  private NotesData notes_data;
  private DisplayNotesAdapter mAdapter;
  private int NEW_NOTE_ADD_REQUEST_CODE = 100;
  private boolean notify_adapter = false;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display_note);
    activity_binding = DataBindingUtil.setContentView(this, R.layout.activity_display_note);
    activity_binding.fabAddNote.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
         launchNewNoteActivity();
      }
    });
    setInitialViews();
    showLoading();
    setData();
  }

  private void launchNewNoteActivity() {
    Intent intent = new Intent(getApplicationContext(),NewNoteActivity.class);
    startActivityForResult(intent,NEW_NOTE_ADD_REQUEST_CODE);
  }

  @Override protected void onResume() {
    super.onResume();
    if (notify_adapter){
      mAdapter.setNewData(app.getNotesData().getNotesMap());
      notify_adapter = false;
    }
  }

  private void setData() {
    notes_data = new NotesData();
    this.app.setNotesData(notes_data);
    if (notes_data.getNotesMap().size() > 0) {
      mAdapter = new DisplayNotesAdapter(DisplayNotesActivity.this, new CardClick(), new DeleteClick());
      activity_binding.recyclerviewNotes.setNestedScrollingEnabled(true);
      activity_binding.recyclerviewNotes.setHasFixedSize(true);
      activity_binding.recyclerviewNotes.setLayoutManager(
          new GridLayoutManager(DisplayNotesActivity.this, 1));
      //activity_binding.recyclerviewNotes.setLayoutManager(new LinearLayoutManager(this.mContext,LinearLayoutManager.HORIZONTAL,false));
      activity_binding.recyclerviewNotes.setAdapter(mAdapter);
      mAdapter.setNewData(notes_data.getNotesMap());
      showDataContainer();
    }
    else {
      showNoData();
    }
  }

  private void showNoData() {
    activity_binding.progress.setVisibility(View.INVISIBLE);
    activity_binding.errorMessageView.setText(getString(R.string.no_data));
    activity_binding.fabAddNote.setVisibility(View.VISIBLE);
    if (activity_binding.viewSwitcher.getDisplayedChild() != 0) {
      activity_binding.viewSwitcher.showPrevious();
    }
    Log.d(TAG, "Showing no data");
  }

  private void setInitialViews() {
    setSupportActionBar(activity_binding.toolbar);
    app = (HouseLaneApp) getApplication();
    if (getSupportActionBar() != null) {
      getSupportActionBar().setTitle(getString(R.string.notes));
    }
  }

  private void showLoading() {
    activity_binding.fabAddNote.setVisibility(View.INVISIBLE);
    activity_binding.progress.setVisibility(View.VISIBLE);
    activity_binding.errorMessageView.setText(getString(R.string.message_common_loading));
    if (activity_binding.viewSwitcher.getDisplayedChild() != 0) {
      activity_binding.viewSwitcher.showPrevious();
    }
    Log.d(TAG, "Showing Loading");
  }

  private void showDataContainer() {
    activity_binding.fabAddNote.setVisibility(View.VISIBLE);
    if (activity_binding.viewSwitcher.getDisplayedChild() != 1) {
      activity_binding.viewSwitcher.showNext();
    }
    Log.d(TAG, "Showing Data Container");
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode==NEW_NOTE_ADD_REQUEST_CODE){
      if (resultCode==RESULT_OK){
        notify_adapter = true;
        Log.d(TAG,"Result okay success");
      }

    }

  }

  public class CardClick implements View.OnClickListener {

    @Override public void onClick(View v) {
      Log.d(TAG, "Card Clicked");
      int position = activity_binding.recyclerviewNotes.getChildAdapterPosition(v);
      Long record_id = mAdapter.getRecordId(position);
      Intent intent = new Intent(getApplicationContext(),NoteFullViewActivity.class);
      intent.putExtra("record_id",record_id);
      startActivity(intent);
    }
  }

  public class DeleteClick implements View.OnClickListener {

    @Override public void onClick(View v) {
      // Log.d(TAG,"Delete clicked");
      int position = activity_binding.recyclerviewNotes.getChildAdapterPosition((View) v.getParent().getParent().getParent());
      Long delete_record_id = mAdapter.getRecordId(position);
      notes_data.deleteNote(delete_record_id);
      app.setNotesData(notes_data);
      mAdapter.setNewData(notes_data.getNotesMap());
      mAdapter.notifyDataSetChanged();
    }
  }

}





