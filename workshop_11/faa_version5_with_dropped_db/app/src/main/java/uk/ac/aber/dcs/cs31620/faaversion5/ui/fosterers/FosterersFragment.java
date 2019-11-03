package uk.ac.aber.dcs.cs31620.faaversion5.ui.fosterers;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.aber.dcs.cs31620.faaversion5.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FosterersFragment extends Fragment {


    public FosterersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fosterers, container, false);
    }

}
