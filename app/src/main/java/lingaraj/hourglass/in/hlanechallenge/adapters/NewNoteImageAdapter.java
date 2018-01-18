package lingaraj.hourglass.in.hlanechallenge.adapters;

/**
 * Created by lingaraj on 1/16/18.
 */

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import lingaraj.hourglass.in.hlanechallenge.R;
import lingaraj.hourglass.in.hlanechallenge.databinding.LayoutNewNoteAdapterBinding;

/**
 * Created by lingaraj on 5/10/16.
 */


public class NewNoteImageAdapter extends RecyclerView.Adapter<NewNoteImageAdapter.ViewHolder> {

  private List<String> images = new ArrayList<String>();

  public static class ViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    LayoutNewNoteAdapterBinding binding;
    public ViewHolder(View v) {
      super(v);
      binding = DataBindingUtil.bind(v);
     }
  }

  public NewNoteImageAdapter() {
    //empty constructor for the adapter
  }

  @Override
  public NewNoteImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    // create a new view
    View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.layout_new_note_adapter, parent, false);
    // set the view's size, margins, paddings and layout parameters
    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(NewNoteImageAdapter.ViewHolder holder, int position) {
    String image_path_string = images.get(position);
    holder.binding.imageView.setImageURI(Uri.parse(image_path_string));
  }

  public void setNewData(List<String> images)
  {
    this.images = images;
    notifyDataSetChanged();
  }


  @Override
  public int getItemCount() {
    return this.images.size();
  }


}
