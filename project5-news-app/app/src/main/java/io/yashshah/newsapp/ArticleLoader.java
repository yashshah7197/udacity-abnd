package io.yashshah.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by yashshah on 18/01/17.
 */

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    // URL from which we want to fetch the Article data
    private String mUrl;

    public ArticleLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        // Check if the Query URL is not null, if it is, return null
        if (mUrl == null) {
            return null;
        }

        // Fetch Book data from the internet and return a List of Article objects
        return QueryUtils.fetchArticleData(mUrl);
    }
}
