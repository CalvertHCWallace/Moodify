// MoodBoardAdapter.java

package ca.calvert.moodify.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ca.calvert.moodify.R;
import ca.calvert.moodify.models.MoodBoard;

// Adapter for managing MoodBoards in a RecyclerView.
public class MoodBoardAdapter extends RecyclerView.Adapter<MoodBoardAdapter.ViewHolder> {

    // List of MoodBoards to display in the RecyclerView.
    private List<MoodBoard> moodBoards;

    // Constructor for the adapter. Initializes with a list of MoodBoards.
    public MoodBoardAdapter(List<MoodBoard> moodBoards) {
        this.moodBoards = moodBoards;
    }

    // Creates new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mood_board_item, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MoodBoard moodBoard = moodBoards.get(position);

        // If the moodBoard is not null, set the name and switch status, otherwise handle appropriately.
        if (moodBoard != null) {
            holder.moodBoardName.setText(moodBoard.getName());
            holder.moodBoardSwitch.setChecked(moodBoard.isPublic());
        } else {
            // Handle null moodBoard appropriately, e.g., show a loading indicator
        }
    }

    // Returns the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return moodBoards.size();
    }

    // ViewHolder class for MoodBoard items
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView moodBoardName;
        Switch moodBoardSwitch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moodBoardName = itemView.findViewById(R.id.mood_board_name);
            moodBoardSwitch = itemView.findViewById(R.id.mood_board_switch);
        }
    }

    // Updates the list of MoodBoards and notifies the adapter to redraw the layout
    public void setMoodBoards(List<MoodBoard> moodBoards) {
        this.moodBoards = moodBoards;
        notifyDataSetChanged();
    }
}
