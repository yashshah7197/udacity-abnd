package io.yashshah.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by yashshah on 14/01/17.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    // URL from which we want to fetch the Book data
    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        // Check if the Query URL is not null, if it is, return null
        if (mUrl == null) {
            return null;
        }

        // Fetch Book data from the internet and return a List of Book objects
        return QueryUtils.fetchBooksData(mUrl);
    }
}
