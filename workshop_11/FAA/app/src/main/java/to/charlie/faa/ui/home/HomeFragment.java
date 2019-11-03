package to.charlie.faa.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Date;

import to.charlie.faa.R;
import to.charlie.faa.datasourcce.FaaRepository;

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
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		Date endDate = Calendar.getInstance().getTime();
		Calendar past = Calendar.getInstance();
		past.add(Calendar.DATE, -30);
		new FaaRepository(getActivity().getApplication()).getRecentCatsSync(past.getTime(), endDate);

//        CatList catList = new CatList();
//        Cat[] cats = catList.getCats();

		ImageView imageView = view.findViewById(R.id.featured_image);
//        imageView.setImageResource(cats[new Random().nextInt(19)].getResourceId());

		return view;
	}

}
