package to.charlie.faa.ui.home;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;
import java.util.Random;

import to.charlie.faa.R;
import to.charlie.faa.model.Cat;
import to.charlie.faa.model.RecentCatsViewModel;
import to.charlie.faa.model.util.ResourceUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment
{


	public HomeFragment()
	{
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
// Inflate the layout for this fragment
		final View view = inflater.inflate(R.layout.fragment_home, container, false);
		RecentCatsViewModel catViewModel = ViewModelProviders.of(this).get(RecentCatsViewModel.class);

		final Random rand = new Random();

		final ImageView featuredImage = view.findViewById(R.id.featured_image);

		LiveData<List<Cat>> recentCats = catViewModel.getRecentCats();

		recentCats.observe(HomeFragment.this, new Observer<List<Cat>>()
		{
			@Override
			public void onChanged(@Nullable final List<Cat> cats)
			{
				// Randomly select one of the cats
				if (cats != null && cats.size() > 0)
				{
					Bitmap bitmap = ResourceUtil.getAssetImageAsBitmap(featuredImage.getContext(), cats.get(rand.nextInt(cats.size() - 1)).getImagePath());
					if (bitmap != null)
					{
						featuredImage.setImageBitmap(bitmap);
					}
				}
			}
		});


		return view;

	}
}
