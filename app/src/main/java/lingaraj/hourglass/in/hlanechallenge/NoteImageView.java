package lingaraj.hourglass.in.hlanechallenge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import java.util.Locale;

/**
 * Created by lingaraj on 1/16/18.
 */

public class NoteImageView extends android.support.v7.widget.AppCompatImageView
{
  private final String TAG = "NOTEIMGVIEW";

  public NoteImageView(Context context) {
    super(context);
  }

  public NoteImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public NoteImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  public void setScaleType(ScaleType scaleType) {
    super.setScaleType(scaleType);
  }

  @Override
  public void setImageURI(@Nullable Uri uri) {
    super.setImageURI(uri);
  }

  @Override
  public void setImageMatrix(Matrix matrix) {
    super.setImageMatrix(matrix);
  }

  @Override public void setColorFilter(ColorFilter cf) {
    super.setColorFilter(cf);
  }


  public void resetColorFilter(){
    //if the custom imageview was set with grey color this will reset the filter to its original state
    this.setColorFilter(null);
    this.setImageAlpha(255);
    this.invalidate();
    Log.d(TAG,"Grey Color Filter removed");
  }

  public void setGreyScaleFilter(){
    //sets the grey color filter to the Imageview
    ColorMatrix matrix = new ColorMatrix();
    matrix.setSaturation(0);  //0 means grayscale
    ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
    this.setColorFilter(cf);
    this.setImageAlpha(128);   // 128 = 0.5
    this.invalidate();
    Log.d(TAG,"Grey Color Filter set");
  }


}
