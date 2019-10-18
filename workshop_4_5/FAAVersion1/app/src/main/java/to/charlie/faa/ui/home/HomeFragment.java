package to.charlie.faa.ui.home;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Random;

import to.charlie.faa.R;
import to.charlie.faa.model.Cat;
import to.charlie.faa.model.CatList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        CatList catList = new CatList();
        Cat[] cats = catList.getCats();

        ImageView imageView = view.findViewById(R.id.featured_image);
        imageView.setImageResource(cats[new Random().nextInt(19)].getResourceId());

        return view;
    }

}
