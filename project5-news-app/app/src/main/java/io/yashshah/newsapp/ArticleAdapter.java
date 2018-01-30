package io.yashshah.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yashshah on 18/01/17.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {

    /**
     * @param context  is the Context
     * @param articles is the List of articles to be displayed in the ListView
     */
    public ArticleAdapter(Context context, List<Article> articles) {
        super(context, 0, articles);
    }

    /**
     * @param position    is the position of the current item
     * @param convertView is the current ListView item
     * @param parent      is the parent ViewGroup
     * @return Returns the entire ListView item after updating all fields
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the current ListView item
        View listViewItem = convertView;
        // Declare a ViewHolder
        ViewHolder viewHolder;

        // If the current ListView item is null
        if (listViewItem == null) {

            // Inflate the view
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

            // Create a new ViewHolder
            viewHolder = new ViewHolder();

            // Hold the views inside the ViewHolder
            viewHolder.title = (TextView) listViewItem.findViewById(R.id.title);
            viewHolder.section = (TextView) listViewItem.findViewById(R.id.section);

            // Associate the view with the ViewHolder
            listViewItem.setTag(viewHolder);

        } else {
            // The current ListView item is not null
            // Get the ViewHolder data from the view
            viewHolder = (ViewHolder) listViewItem.getTag();
        }

        // Get the current article
        Article currentArticle = getItem(position);

        // Display the title of the current article
        viewHolder.title.setText(currentArticle.getTitle());
        // Display the authors of the current article
        viewHolder.section.setText(currentArticle.getSection());

        // Return the whole view
        return listViewItem;
    }

    // ViewHolder class to cache or hold the views for later reference
    private static class ViewHolder {
        private TextView title;
        private TextView section;
    }
}
