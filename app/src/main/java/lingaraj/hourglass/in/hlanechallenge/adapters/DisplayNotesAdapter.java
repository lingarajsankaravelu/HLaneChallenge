package lingaraj.hourglass.in.hlanechallenge.adapters;

/**
 * Created by lingaraj on 1/17/18.
 */

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lingaraj.hourglass.in.hlanechallenge.R;
import lingaraj.hourglass.in.hlanechallenge.activities.DisplayNotesActivity;
import lingaraj.hourglass.in.hlanechallenge.databinding.CardNotesBinding;
import lingaraj.hourglass.in.hlanechallenge.model.Note;

public class DisplayNotesAdapter extends RecyclerView.Adapter<DisplayNotesAdapter.ViewHolder> {

  private List<String> images = new ArrayList<String>();
  private NewNoteImageAdapter image_adapter = new NewNoteImageAdapter();
  private List<Long> keyset;
  private Map<Long,Note> notes = new HashMap<>();
  private DisplayNotesActivity.CardClick card_click;
  private DisplayNotesActivity.DeleteClick delete_click;
  private Context mContext;

  public Long getRecordId(int position) {
     return   this.keyset.get(position);
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    CardNotesBinding binding;
    public ViewHolder(View v) {
      super(v);
      binding = DataBindingUtil.bind(v);
    }
  }

  public DisplayNotesAdapter(Context context,DisplayNotesActivity.CardClick cardClick,DisplayNotesActivity.DeleteClick deleteClick) {
    //empty constructor for the adapter
    this.card_click = cardClick;
    this.delete_click = deleteClick;
    this.mContext = context;
  }

  @Override
  public DisplayNotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    // create a new view
    View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.card_notes, parent, false);
    // set the view's size, margins, paddings and layout parameters
    v.setOnClickListener(this.card_click);
    ImageView delete = (ImageView) v.findViewById(R.id.delete);
    delete.setOnClickListener(this.delete_click);
    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(DisplayNotesAdapter.ViewHolder holder, int position) {
    Long key = this.keyset.get(position);
    Note record = this.notes.get(key);
      holder.binding.title.setText(record.getTitle());
      holder.binding.content.setText(record.getText());
      String creadted_text = "Created At"+" "+record.getCreatedAt();
      holder.binding.createdAt.setText(creadted_text);
      handleImages(holder,record.getImages());
   }

  private void handleImages(ViewHolder holder, List<String> images) {
    if (images.size()>0){
      NewNoteImageAdapter images_adapter = new NewNoteImageAdapter();
      holder.binding.imagesRecyclerview.setNestedScrollingEnabled(true);
      holder.binding.imagesRecyclerview.setHasFixedSize(true);
      holder.binding.imagesRecyclerview.setLayoutManager(new LinearLayoutManager(this.mContext,LinearLayoutManager.HORIZONTAL,false));
      holder.binding.imagesRecyclerview.setAdapter(images_adapter);
      images_adapter.setNewData(images);
      holder.binding.imagesRecyclerview.setVisibility(View.VISIBLE);
    }
    else {
      holder.binding.imagesRecyclerview.setVisibility(View.GONE);
    }

  }

  public void setNewData(Map<Long,Note> notes_data)
  {
    this.notes = notes_data;
    this.keyset = new ArrayList<>(this.notes.keySet());
    notifyDataSetChanged();
  }


  @Override
  public int getItemCount() {

    return this.notes.size();
  }


}
