package uk.ac.aber.dcs.cs31620.faaversion5.ui.home;


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

import uk.ac.aber.dcs.cs31620.faaversion5.R;
import uk.ac.aber.dcs.cs31620.faaversion5.model.Cat;
import uk.ac.aber.dcs.cs31620.faaversion5.model.RecentCatsViewModel;
import uk.ac.aber.dcs.cs31620.faaversion5.model.util.ResourceUtil;

/**
 * UI fragment to display the home screen of the FAA app including the featured inmate.
 *
 * @author Chris Loftus
 * @version 2 1/09/2018.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecentCatsViewModel catViewModel = ViewModelProviders.of(this).get(RecentCatsViewModel.class);

        final Random rand = new Random();

        final ImageView featuredImage = view.findViewById(R.id.featured_image);

        LiveData<List<Cat>> recentCats = catViewModel.getRecentCats();

        recentCats.observe(HomeFragment.this, new Observer<List<Cat>>() {
            @Override
            public void onChanged(@Nullable final List<Cat> cats) {
                // Randomly select one of the cats
                if (cats != null && cats.size() > 0) {
                    Bitmap bitmap = ResourceUtil.getAssetImageAsBitmap(featuredImage.getContext(),
                            cats.get(rand.nextInt(cats.size() - 1)).getImagePath());
                    if (bitmap != null) {
                        featuredImage.setImageBitmap(bitmap);
                    }
                }
            }
        });


        return view;
    }

}
