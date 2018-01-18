package lingaraj.hourglass.in.hlanechallenge.activities;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import lingaraj.hourglass.in.hlanechallenge.Helpers;
import lingaraj.hourglass.in.hlanechallenge.HouseLaneApp;
import lingaraj.hourglass.in.hlanechallenge.R;
import lingaraj.hourglass.in.hlanechallenge.adapters.NewNoteImageAdapter;
import lingaraj.hourglass.in.hlanechallenge.databinding.ActivityNewNoteBinding;
import lingaraj.hourglass.in.hlanechallenge.model.NotesData;

/**
 * Created by lingaraj on 1/16/18.
 * @ Screen 2
 */

public class NewNoteActivity extends AppCompatActivity
{
  private final String TAG = "NEWNOTEACT";
  private NotesData notesData;
  private HouseLaneApp app;
  private String createdAt;
  private ActivityNewNoteBinding activity_binding;
  private final int IMAGE_PICK_CODE = 111;
  private List<String> images = new ArrayList<String>();
  private NewNoteImageAdapter mAdapter;
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_note);
    activity_binding = DataBindingUtil.setContentView(this,R.layout.activity_new_note);
    setInitialView();

    activity_binding.fabAddImage.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Log.d(TAG,"fab button clicked");
        launchGalleryImagePicker();
      }
    });
  }



  private void setInitialView() {
    setSupportActionBar(activity_binding.toolbar);
    createdAt = Helpers.noteCreatedAt();
    if (getSupportActionBar()!=null){
       getSupportActionBar().setTitle(getString(R.string.created_at));
       getSupportActionBar().setSubtitle(createdAt);
    }
    app = (HouseLaneApp) getApplication();
    notesData = app.getNotesData();
    mAdapter = new NewNoteImageAdapter();
    activity_binding.noteImagesRecyclerview.setNestedScrollingEnabled(true);
    activity_binding.noteImagesRecyclerview.setHasFixedSize(true);
    activity_binding.noteImagesRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
    activity_binding.noteImagesRecyclerview.setAdapter(mAdapter);

  }

  @Override protected void onPause() {
    super.onPause();
    app.setNotesData(notesData);
  }

  private void launchGalleryImagePicker(){
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(Intent.createChooser(intent, "Select Picture"),IMAGE_PICK_CODE);
    Log.d(TAG,"Gallery Image picker launched");
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode==RESULT_OK && data!=null){
      if (requestCode==IMAGE_PICK_CODE){
      //  String image_uri_string = data.getDataString();
        String path = Helpers.getPath(getApplicationContext(),data.getData());
        Log.d(TAG,"Choosen Image Uri String:"+path);
       // Uri uri = Uri.parse(image_uri_string);
       // Log.d(TAG,"convert Uri:"+uri.toString());

        images.add(path);
        setAdapterData();
      }
    }
  }

  private void setAdapterData() {
    if (mAdapter.getItemCount()>0){
      mAdapter.notifyDataSetChanged();
    }
    else {
      mAdapter.setNewData(images);
    }
    showRecyclerView();
   Log.d(TAG,"Adapter Data Updated");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_save,menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case R.id.menu_save:
        if (isValidData()){
            addNote();
            Snackbar.make(activity_binding.rootLayout,getString(R.string.note_saved),Snackbar.LENGTH_SHORT).show();
            Intent intent = new Intent();
            setResult(RESULT_OK,intent);
           finish();

        }
        else {
          Snackbar.make(activity_binding.rootLayout,getString(R.string.validation_error),Snackbar.LENGTH_SHORT).show();
        }
           break;
    }
    return super.onOptionsItemSelected(item);
  }

  private void addNote() {
    String title = activity_binding.noteTitle.getText().toString();
    String content = activity_binding.noteContent.getText().toString();
    notesData.addNote(title,content,createdAt,images);
  }

  private void showRecyclerView() {
       activity_binding.noteImagesRecyclerview.setVisibility(View.VISIBLE);
  }

  private boolean isValidData(){
    if (activity_binding.noteTitle.getText().toString().isEmpty() || activity_binding.noteTitle.getText().toString().equals(getString(R.string.note_title))){
       return false;
    }
    else if (activity_binding.noteContent.getText().toString().isEmpty() || activity_binding.noteContent.getText().toString().equals(getString(R.string.note_content))){
      return false;
    }
    else {

      return true;
    }
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    app.setNotesData(notesData);
  }

  public String getRealPathFromURI( Uri contentUri) {
    Cursor cursor = null;
    try {
      String[] proj = { MediaStore.Images.Media.DATA };
      cursor = getApplicationContext().getContentResolver().query(contentUri,  proj, null, null, null);
      int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
      cursor.moveToFirst();
      return cursor.getString(column_index);
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }
}
