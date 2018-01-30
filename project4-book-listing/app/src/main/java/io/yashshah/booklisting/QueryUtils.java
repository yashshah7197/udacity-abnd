package io.yashshah.booklisting;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yashshah on 04/01/17.
 */


/**
 * Helper methods to extract Books data from a JSON response
 */
public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * @return Returns a List of Books from the sample JSON response
     */
    public static List<Book> extractBooksFromJSON(String jsonResponse) {

        // Create a new ArrayList of books
        List<Book> books = new ArrayList<>();

        try {

            // Create a new JSONObject from the sample JSON response
            JSONObject baseJSONResponse = new JSONObject(jsonResponse);

            // Extract the JSONArray associated with the key 'items'
            // which represents a list of books
            JSONArray items = baseJSONResponse.getJSONArray("items");

            // For each book in the items array, create a Book object
            for (int i = 0; i < items.length(); ++i) {

                // Get the book at the position i in the array
                JSONObject item = items.getJSONObject(i);

                // Extract the JSONObject associated with the key 'volumeInfo'
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                // Extract the value for the key called 'title'
                String title = volumeInfo.getString("title");

                // Extract the value for the key called 'pageCount'
                int pageCount = volumeInfo.optInt("pageCount");

                // Extract the values from the JSONArray associated with the key 'authors'
                // If the array is null, set the author to be 'No author found'
                String authors = "No author found";
                JSONArray authorsArray = volumeInfo.optJSONArray("authors");
                if (authorsArray != null) {
                    authors = extractAuthorInfo(authorsArray);
                }

                // Extract the values from the JSONArray associated with the key 'categories'
                // If the array is null, set the author to be 'Uncategorized'
                String categories = "Uncategorized";
                JSONArray categoriesArray = volumeInfo.optJSONArray("categories");
                if (categoriesArray != null) {
                    categories = extractCategoriesInfo(categoriesArray);
                }

                // Add a new Book object to the list of Books
                books.add(new Book(title, authors, categories, pageCount));
            }

        } catch (JSONException e) {
            // If an error or JSONException occurs in the above try block,
            // catch the exception here so the app doesn't crash.
            // Print an error message in the logs
            Log.e(LOG_TAG, "There was a problem parsing the JSON response!", e);
        }

        // Return the entire list of books
        return books;
    }

    /**
     * @param authorsArray is the JSONArray containing strings of one or more authors
     * @return Returns a String with the names of authors separated by a comma
     * @throws JSONException which will be caught and handled in the calling function
     *                       extractBooksFromJSON()
     */
    private static String extractAuthorInfo(JSONArray authorsArray) throws JSONException {
        // Create a new StringBuilder object
        StringBuilder authors = new StringBuilder();

        // For each item in the authorsArray, append the string to the authors StringBuilder object
        for (int i = 0; i < authorsArray.length(); ++i) {
            // If we are at the last element in the array
            if (i == authorsArray.length() - 1) {
                // Append the author name at position i and then append a full stop
                authors.append(authorsArray.getString(i)).append(".");
            } else {
                // Append the author name at position i and then append a comma
                authors.append(authorsArray.getString(i)).append(", ");
            }
        }

        // Return the String of comma separated authors
        return authors.toString();
    }

    /**
     * @param categoriesArray is the JSONArray containing strings of one or more categories
     * @return Returns a String with the names of categories separated by a comma
     * @throws JSONException which will be caught and handled in the calling function
     *                       extractBooksFromJSON()
     */
    private static String extractCategoriesInfo(JSONArray categoriesArray) throws JSONException {
        // Create a new StringBuilder object
        StringBuilder categories = new StringBuilder();

        // For each item in the categoriesArray, append the string to the categories StringBuilder
        // object
        for (int i = 0; i < categoriesArray.length(); ++i) {
            // If we are at the last element in the array
            if (i == categoriesArray.length() - 1) {
                // Append the category name at position i and then append a full stop
                categories.append(categoriesArray.getString(i)).append(".");
            } else {
                // Append the category name at position i and then append a comma
                categories.append(categoriesArray.getString(i)).append(", ");
            }
        }

        // Return the String of comma separated authors
        return categories.toString();
    }

    /**
     * This method creates a URL object from a URL given in String form
     *
     * @param stringURL is the URL in String form that we want to convert to a proper URL
     * @return Returns a URL object which is created from stringURL
     */
    private static URL createURL(String stringURL) {
        // Declare URL object and set it to null
        URL url = null;

        // try to create the URL from stringURL
        // If there is an error or exception, catch it and display in logs so that the app does not
        // crash
        try {
            // Create a new URL object from stringURL and store it in the url variable
            url = new URL(stringURL);
        } catch (MalformedURLException e) {
            // There is an error while creating the URL, display it in the logs
            Log.e(LOG_TAG, "There was an error creating the URL", e);
        }

        // Return the URL object
        return url;
    }

    /**
     * This method makes an HttpRequest, reads the response stream and
     * converts it into a JSON String
     *
     * @param url is the URL to which we want to make the HttpRequest and get the response from
     * @return Returns the JSON response in String form
     */
    private static String makeHttpRequest(URL url) throws IOException {

        // Declare a JSON response String object and set it to an empty String
        String jsonResponse = "";

        // If the URL to which we want to make the HttpRequest is null, return the empty String
        if (url == null) {
            return jsonResponse;
        }

        // Declare a new HttpURLConnection object and set it to null
        HttpURLConnection urlConnection = null;
        // Declare a new InputStream object and set it to null
        InputStream inputStream = null;

        // try to make the request, get the response and convert it into a JSON response String
        // If there is an error or exception, display it in the logs so that the app doesn't crash
        try {

            // Open a connection to the URL which we want to make the HttpRequest to
            urlConnection = (HttpURLConnection) url.openConnection();

            // Set the request method of the request
            // We want to only read the JSON response and not update it so we set it to GET
            urlConnection.setRequestMethod("GET");

            // Set the time in milliseconds after which we must stop trying to read the response
            urlConnection.setReadTimeout(10000);

            // Set the time in milliseconds after which we must stop trying to connect to the URL
            urlConnection.setConnectTimeout(15000);

            // Connect to the URL
            urlConnection.connect();

            // If the response code is 200
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get the input stream that we get back
                inputStream = urlConnection.getInputStream();
                // Convert the input stream into a String
                jsonResponse = readFromStream(inputStream);

            } else {
                // The response code was not 200, display the response code as an error code
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            // There was an error or exception, display it in the logs so that the app does not
            // crash
            Log.e(LOG_TAG, "There was a problem getting the JSON response!", e);
        } finally {

            // If there is an existing urlConnection, disconnect it.
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            // If there is an existing input stream, close it.
            if (inputStream != null) {
                inputStream.close();
            }
        }

        // Return the JSON response in String form
        return jsonResponse;
    }

    /**
     * @param inputStream is the input stream received as response
     * @return Returns the input stream converted to a String
     * @throws IOException which will be caught and handled in the calling function
     *                     makeHttpRequest(URL url)
     */
    private static String readFromStream(InputStream inputStream) throws IOException {

        // Create a new StringBuilder object
        StringBuilder jsonResponse = new StringBuilder();

        // If the received input stream is not null
        if (inputStream != null) {

            // Create a new InputStreamReader object to read the input stream and converts it to
            // a UTF-8 String
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream, Charset.forName("UTF-8"));

            // Create a new BufferedReader object which wraps around the InputStreamReader to help
            // convert the input stream to a String
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // Read a line from the BufferedReader
            String line = bufferedReader.readLine();
            // Check if the line is not null
            while (line != null) {
                // Append the read line to the StringBuilder
                jsonResponse.append(line);
                // Read the next line from the BufferedReader
                line = bufferedReader.readLine();
            }
        }

        // Convert the StringBuilder object to a String and return it
        return jsonResponse.toString();
    }

    /**
     * This method requests, extracts and returns a List of Book items from the URL given in
     * String form
     *
     * @param stringURL is the URL which we want to fetch the JSON response from
     * @return Returns a List of Book items
     */
    public static List<Book> fetchBooksData(String stringURL) {

        // Create a URL object from the String stringURL
        URL url = createURL(stringURL);

        // Declare a new String for the JSON response and set it to null
        String jsonResponse = null;

        // try to make the HttpRequest to the URL, get the response as a String
        // If there is an error, catch it and display it in logs so that the app does not crash
        try {
            // Set the jsonResponse String object to use the JSON response from the HttpRequest
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            // There was an error, display it in the logs
            Log.e(LOG_TAG, "There was a problem fetching the books data.", e);
        }

        // return the List of books extracted from the JSON response
        return extractBooksFromJSON(jsonResponse);
    }

}
