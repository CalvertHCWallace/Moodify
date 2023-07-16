// ImageAdapter.java

package ca.calvert.moodify.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ca.calvert.moodify.R;
import ca.calvert.moodify.models.Image;

// Adapter for managing images in a RecyclerView.
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    // List of images to display in the RecyclerView.
    private List<Image> images = new ArrayList<>();

    // Creates new views (invoked by the layout manager)
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Image currentImage = images.get(position);
        holder.titleTextView.setText(currentImage.getTitle());
        Picasso.get().load(currentImage.getImageURL()).into(holder.imageView);
    }

    // Returns the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return images.size();
    }

    // Updates the list of images and notifies the adapter to redraw the layout
    public void setImages(List<Image> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    // ViewHolder class for image items
    class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleTextView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            titleTextView = itemView.findViewById(R.id.text_view_title);
        }
    }
}
