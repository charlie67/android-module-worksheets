package uk.ac.aber.dcs.cs31620.faaversion6.ui.cats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.aber.dcs.cs31620.faaversion6.R;
import uk.ac.aber.dcs.cs31620.faaversion6.model.Cat;
import uk.ac.aber.dcs.cs31620.faaversion6.model.util.ResourceUtil;

/**
 * Recycler view that uses a a CursorAdapter. Based on code from https://stackoverflow.com/a/27732748
 *
 * @author Chris Loftus
 * @version 2 25/07/2019.
 */

public class CatsRecyclerWithListAdapter extends RecyclerView.Adapter<CatsRecyclerWithListAdapter.ViewHolder> {
    private final Context context;
    private List<Cat> dataSet;
    private View.OnClickListener clickListener;

    /**
     * Initialises and overrides a CursorAdapter to wrap up and manage a database Cursor
     *
     * @param context Current application context
     */
    CatsRecyclerWithListAdapter(Context context) {
        this.context = context;
    }

    /**
     * Provides a reference to the views for each data item. Caches as much as possible.
     * Complex data items may need more than one view per item, and
     * you provide access to all the views for a data item in a view holder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameView;

        ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.catImageView);

            nameView = itemView.findViewById(R.id.catNameTextView);
            itemView.setOnClickListener(clickListener);
        }

        /**
         * This is where the view is bound to data within the data
         *
         * @param cat The current data item
         */
        void bindDataSet(Cat cat) {
            nameView.setText(cat.getName());
            // Have to now obtain the image from the assets folder
            // and assign it to the ImageView.
            // It is not possible to do this by binding to the src attribute in the layout file
            // so we have to do it programmatically here.

            ResourceUtil.loadImageBitmap(context, cat.getImagePath(), imageView);

        }
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     *
     * @return The size of the dataset. The layout manager needs to know.
     */
    @Override
    public int getItemCount() {
        if (dataSet != null) {
            return dataSet.size();
        } else {
            return 0;
        }
    }

    /**
     * Called by the RecyclerView asking for a ViewHolder object
     *
     * @param parent   The parent view (e.g. GridLayout)
     * @param viewType An id to identify the kind of view to inflate, e.g. where different
     *                 kinds of view are displayed for different items in the list: by default 0
     * @return The new ViewHolder object that will be cached by the RecyclerView
     */
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recycler_view_cat_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Replace the contents of a view
     *
     * @param holder   The current view being displayed
     * @param position The position of that view in the grid being displayed
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        // Delegate the binding to the ViewHolder
        if (dataSet != null) {
            holder.bindDataSet(dataSet.get(position));
        }
    }

    /**
     * If the data set is reset, then we need to let the adapter know.
     *
     * @param dataSet The updated dataSet
     */
    void changeDataSet(List<Cat> dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    /**
     * Allows clicks events to be caught
     *
     * @param itemClickListener The provided listener invoked when someone clicks on an item in the grid
     */
    void setOnClickListener(View.OnClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
}
