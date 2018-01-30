package io.yashshah.tourguide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends Fragment {


    public FoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.place_list, container, false);

        // Create an ArrayList of Places
        ArrayList<Place> places = new ArrayList<>();

        // Add the historical places to the ArrayList
        places.add(new Place(R.string.vaishali, R.string.vaishali_description));
        places.add(new Place(R.string.jj_garden, R.string.jj_garden_description));
        places.add(new Place(R.string.cafe_goodluck, R.string.cafe_goodluck_description));
        places.add(new Place(R.string.kalyan_bhel, R.string.kalyan_bhel_description));

        // Create a PlaceAdapter for the places ArrayList
        PlaceAdapter placeAdapter = new PlaceAdapter(getActivity(), places);

        // Find the ListView with the id list in the layout
        ListView list = (ListView) rootView.findViewById(R.id.list);

        // Set the adapter of the list to placeAdapter
        list.setAdapter(placeAdapter);

        return rootView;
    }

}
