package io.yashshah.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yashshah on 04/01/17.
 */

public class BooksAdapter extends ArrayAdapter<Book> {

    /**
     * @param context is the Context
     * @param books   is the List of books to be displayed in the ListView
     */
    public BooksAdapter(Context context, List<Book> books) {
        super(context, 0, books);
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
            viewHolder.authors = (TextView) listViewItem.findViewById(R.id.authors);
            viewHolder.categories = (TextView) listViewItem.findViewById(R.id.categories);
            viewHolder.pageCount = (TextView) listViewItem.findViewById(R.id.page_count);

            // Associate the view with the ViewHolder
            listViewItem.setTag(viewHolder);

        } else {
            // The current ListView item is not null
            // Get the ViewHolder data from the view
            viewHolder = (ViewHolder) listViewItem.getTag();
        }

        // Get the current book
        Book currentBook = getItem(position);

        // Display the title of the current book
        viewHolder.title.setText(currentBook.getTitle());

        // Display the authors of the current book
        String authorsString = getContext().getString(R.string.authors) + " " + currentBook.getAuthors();
        viewHolder.authors.setText(authorsString);

        // Display the categories of the current book
        String categoryString = getContext().getString(R.string.category) + " "
                + currentBook.getCategories();
        viewHolder.categories.setText(categoryString);

        // If the page count of the current book is 0
        if (currentBook.getPageCount() == 0) {
            // Display the page count information as 'Unknown'
            String countUnknownString = getContext().getString(R.string.page_count) + " " +
                    getContext().getString(R.string.unknown);
            viewHolder.pageCount.setText(countUnknownString);
        } else {
            // Display the page count of the current book
            viewHolder.pageCount.setText(String.valueOf(
                    getContext().getString(R.string.page_count) + " " + currentBook.getPageCount()));
        }

        // Return the whole view
        return listViewItem;
    }

    // ViewHolder class to cache or hold the views for later reference
    private static class ViewHolder {
        private TextView title;
        private TextView authors;
        private TextView categories;
        private TextView pageCount;
    }
}
