package io.yashshah.booklisting;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Book>> {

    // Loader ID
    private static final int BOOK_LOADER_ID = 7;
    // Sample URL to query the Books data from
    private static final String
            BASE_BOOKS_QUERY_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    // Custom ListView adapter for the Book objects
    private BooksAdapter mBooksAdapter;

    // Final Query URL
    private String queryUrl = null;

    // User's query term
    private String queryTerm = null;

    // TextView that will be shown when the ListView is empty
    private TextView emptyView;

    // ProgressBar that will be shown while the data is being fetched
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        // Set the BooksAdapter object's source as the List of Book objects
        mBooksAdapter = new BooksAdapter(this, new ArrayList<Book>());

        // Find the ListView with the id list in the layout
        ListView listView = (ListView) findViewById(R.id.list);

        // Set the ListView to use the BooksAdapter as its adapter
        listView.setAdapter(mBooksAdapter);

        // Find the ProgressBar with the id progress in the layout
        progressBar = (ProgressBar) findViewById(R.id.progress);
        // Hide the ProgressBar
        progressBar.setVisibility(View.GONE);

        // Find the EditText with the id query_term in the layout
        final EditText queryTextEdit = (EditText) findViewById(R.id.query_term);

        // Find the Button with the id search in the layout
        Button search = (Button) findViewById(R.id.search);

        // Set OnClickListener for the search button
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If there is no internet connection, do nothing and just return
                if (!isConnected()) {
                    return;
                }

                // Hide the emptyView
                emptyView.setVisibility(View.GONE);
                // Show the ProgressBar
                progressBar.setVisibility(View.VISIBLE);

                // Get the user's query term from queryTextEdit
                queryTerm = queryTextEdit.getText().toString();

                // Create the query URL by concatenating the base url and the query term
                queryUrl = BASE_BOOKS_QUERY_URL + queryTerm;

                // Restart the loader so a fresh load can be done
                getLoaderManager().restartLoader(BOOK_LOADER_ID, null, BooksActivity.this);
            }
        });

        // Find the TextEdit with the id empty_view in the layout
        emptyView = (TextView) findViewById(R.id.empty_view);

        // Set the ListView to use the emptyView TextEdit as its empty view
        listView.setEmptyView(emptyView);

        // Initialize the loader
        getLoaderManager().initLoader(BOOK_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        // Create a new Loader for the give URL
        return new BookLoader(BooksActivity.this, queryUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // Hide the ProgressBar
        progressBar.setVisibility(View.GONE);

        // Clear the existing BooksAdapter adapter data
        mBooksAdapter.clear();

        // Check if the List of books is not null and not empty
        if (books != null && !books.isEmpty()) {
            // Add the new Books data to the BooksAdapter
            mBooksAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        // Loader reset, clear the existing BooksAdapter adapter data
        mBooksAdapter.clear();
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
}
