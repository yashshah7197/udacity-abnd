package io.yashshah.newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Article>> {

    // Loader ID
    private static final int LOADER_ID = 10;

    // Sample URL to query the Books data from
    private static String SAMPLE_QUERY_URL =
            "https://content.guardianapis.com/technology?api-key=test";

    // Custom ListView adapter for the Article objects
    private ArticleAdapter mArticleAdapter;

    // SwipeRefreshLayout for pull to refresh
    private SwipeRefreshLayout swipeRefreshLayout;

    // TextView for the ListView empty view
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the mArticleAdapter object's source as a new ArrayList of Article objects
        mArticleAdapter = new ArticleAdapter(this, new ArrayList<Article>());

        // Find the ListView with the id list in the layout
        ListView listView = (ListView) findViewById(R.id.list);

        // Set the ListView to use the mArticleAdapter as its adapter
        listView.setAdapter(mArticleAdapter);

        // Set the OnItemCLickListener for the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Open the web page associated with the article
                openWebPage(mArticleAdapter.getItem(i).getUrl());
            }
        });

        // Find the TextView with the id empty in the layout
        emptyView = (TextView) findViewById(R.id.empty);

        // Set the empty view of the ListView
        listView.setEmptyView(emptyView);

        // Find the SwipeRefreshLayout with the id swipe_refresh_layout in the layout
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        // Set the OnClickListener for the SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // A refresh event occurred, clear the existing mArticleAdapter data
                mArticleAdapter.clear();

                // If there is no network connection
                if (!isConnected()) {
                    // Update the emptyView text
                    emptyView.setText(getString(R.string.no_internet));

                    // Set the SwipeRefreshLayout refreshing status to false
                    swipeRefreshLayout.setRefreshing(false);

                    return;
                }

                // Update the emptyView text
                emptyView.setText(getString(R.string.no_data));

                // Restart the loader to reload the data
                getLoaderManager().restartLoader(LOADER_ID, null, MainActivity.this);
            }
        });

        // Set the SwipeRefreshLayout status to refreshing, as we will be loading the data
        swipeRefreshLayout.setRefreshing(true);

        // Initialize the loader
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle) {
        // Create a new Loader for the given URL
        return new ArticleLoader(this, SAMPLE_QUERY_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {
        // Load complete, set the refreshing status of the SwipeRefreshLayout to false
        swipeRefreshLayout.setRefreshing(false);

        // Clear the existing BooksAdapter adapter data
        mArticleAdapter.clear();

        // Check if the List of books is not null and not empty
        if (articles != null && !articles.isEmpty()) {
            // Add the new Books data to the BooksAdapter
            mArticleAdapter.addAll(articles);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        swipeRefreshLayout.setRefreshing(true);
        // Loader reset, clear the existing BooksAdapter adapter data
        mArticleAdapter.clear();
    }

    /**
     * This function checks whether the device has internet connectivity or not
     *
     * @return Returns false if the device doesn't have internet connection, otherwise returns true
     */
    private boolean isConnected() {
        // Get the Connectivity system service
        ConnectivityManager connectivityManager = (ConnectivityManager)
                this.getSystemService(CONNECTIVITY_SERVICE);

        // Get the network info from the Connectivity Manager
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // Return true if the network info is not null and if the network is connected
        // or is attempting to connect to the internet
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if there is network connectivity each time the onStart() method is called
        // If there is no network connectivity, set the emptyView of the ListView to tell the user
        // that there is no internet connection
        if (isConnected()) {
            emptyView.setText(getString(R.string.no_data));
        } else {
            emptyView.setText(getString(R.string.no_internet));
        }
    }

    /**
     * Helper method to open a web URL using an explicit intent
     *
     * @param url is the URL which we want to open
     */
    public void openWebPage(String url) {
        // If the URL is null, do nothing
        if (url == null) {
            return;
        }

        // Create a Uri from the given String url
        Uri webpage = Uri.parse(url);

        // Create an explicit intent to open the URL
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        // Check if there is an app that can support this kind of intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the intent
            startActivity(intent);
        }
    }
}
