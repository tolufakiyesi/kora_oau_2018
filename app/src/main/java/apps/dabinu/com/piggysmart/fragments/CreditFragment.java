package apps.dabinu.com.piggysmart.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apps.dabinu.com.piggysmart.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreditFragment extends Fragment {


    public CreditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_credit, container, false);
    }

}
