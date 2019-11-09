package to.charlie.faa.ui.cats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import to.charlie.faa.R;
import to.charlie.faa.model.Cat;

public class CatsRecyclerWithArrayAdapter extends RecyclerView.Adapter<CatsRecyclerWithArrayAdapter.ViewHolder>
{
	private final Context context;
	private Cat[] dataSet;
	private View.OnClickListener clickListener;

	CatsRecyclerWithArrayAdapter(Context context, Cat[] dataSet)
	{
		this.context = context;
		this.dataSet = dataSet;
	}


	public class ViewHolder extends RecyclerView.ViewHolder
	{
		ImageView imageView;
		TextView nameView;

		ViewHolder(View itemView)
		{
			super(itemView);
			this.imageView = itemView.findViewById(R.id.catImageView);
			this.nameView = itemView.findViewById(R.id.catNameTextView);

			itemView.setOnClickListener(clickListener);
		}

		void bindDataSet(Cat cat) {
			nameView.setText(cat.getName());
//			imageView.setImageDrawable(ContextCompat.getDrawable(context, (cat.getResourceId())));
		}
	}

	@Override
	public int getItemCount()
	{
		return dataSet.length;
	}

	@Override
	@NonNull
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_cat_item,
						parent, false);

		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder, int position)
	{
		holder.bindDataSet(dataSet[position]);
	}

	void changeDataSet(Cat[] dataSet)
	{
		this.dataSet = dataSet;
		notifyDataSetChanged();
	}

	void setOnClickListener(View.OnClickListener itemClickListener) {
		this.clickListener = itemClickListener;
	}
}
