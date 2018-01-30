package io.yashshah.tourguide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yashshah on 31/12/16.
 */

public class PlaceAdapter extends ArrayAdapter<Place> {

    public PlaceAdapter(Context context, List<Place> places) {
        super(context, 0, places);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the current listView Item
        View listViewItem = convertView;
        // If the view is null, inflate the view
        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item, parent, false);
        }

        // Get the current Place object in the listView
        Place currentPlace = getItem(position);

        // Find the ImageView with the id image in the listViewItem
        ImageView imageView = (ImageView) listViewItem.findViewById(R.id.image);

        // If the current place has an image, set the image
        if (currentPlace.hasImage()) {
            imageView.setImageResource(currentPlace.getImageResourceID());
            imageView.setVisibility(View.VISIBLE);
        } else {
            // Otherwise hide the imageView
            imageView.setVisibility(View.GONE);
        }

        // Find the TextView with the id place_name in the listViewItem
        TextView placeName = (TextView) listViewItem.findViewById(R.id.place_name);
        // Set the text of the placeName TextView to the name of the current place
        placeName.setText(getContext().getResources().getString(currentPlace.getPlaceNameResourceID()));

        // Find the TextView with the id place_description in the listViewItem
        TextView placeDescription = (TextView) listViewItem.findViewById(R.id.place_description);
        // Set the text of the placeDescription TextView to the description of the current place
        placeDescription.setText(getContext().getResources().
                getString(currentPlace.getPlaceDescriptionResourceID()));

        return listViewItem;
    }
}
