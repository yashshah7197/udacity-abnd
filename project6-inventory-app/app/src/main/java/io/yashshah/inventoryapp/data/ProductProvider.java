package io.yashshah.inventoryapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import io.yashshah.inventoryapp.data.ProductContract.ProductEntry;

/**
 * Created by yashshah on 25/01/17.
 */

/**
 * {@link ContentProvider} for the Inventory App.
 */
public class ProductProvider extends ContentProvider {

    // Tag for the log messages
    private static final String LOG_TAG = ProductProvider.class.getSimpleName();

    // UriMatcher code for the products table
    private static final int PRODUCTS = 786;

    // UriMatcher code for a single entry in the products table
    private static final int PRODUCTS_ID = 420;

    // UriMatcher is an object which will map a URI to a corresponding code
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class
    static {

        /**
         * The content Uri of the form "content://io.yashshah.inventoryapp/products" is mapped to
         * the integer constant {@link PRODUCTS} for the UriMatcher to compare later
         */
        sUriMatcher.addURI(ProductContract.CONTENT_AUTHORITY, ProductContract.PATH_PRODUCTS,
                PRODUCTS);

        /**
         * The content Uri of the form "content://io.yashshah.inventoryapp/products/#" is mapped to
         * the integer constant {@link PRODUCTS_ID} for the UriMatcher to compare later. "#" is a
         * wildcard which will be substituted by an integer
         */
        sUriMatcher.addURI(ProductContract.CONTENT_AUTHORITY, ProductContract.PATH_PRODUCTS + "/#",
                PRODUCTS_ID);
    }

    // Database helper for creation and version management of database
    private ProductDbHelper mDbHelper;

    /**
     * Initialize the provider and create the database helper object
     */
    @Override
    public boolean onCreate() {
        mDbHelper = new ProductDbHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection,
     * selection arguments, and sort order.
     */
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        // Get an instance of a SQLiteDatabase using the ProductDbHelper so that we can read
        // from the database
        SQLiteDatabase sqLiteDatabase = mDbHelper.getReadableDatabase();

        // The cursor that will hold the result of the query
        Cursor cursor;

        // See what code the UriMatcher matches to
        int match = sUriMatcher.match(uri);

        switch (match) {
            case PRODUCTS:
                // For the PRODUCTS code, query the database with the given projection, selection,
                // selectionArgs and sortOrder and store the result in a cursor object
                cursor = sqLiteDatabase.query(ProductEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;

            case PRODUCTS_ID:
                // For the PRODUCTS_ID code, parse the id from the uri and set it as the selection
                selection = ProductEntry._ID + "=?";
                // Each element of selectionArgs will replace every '?' in the selection
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                // Query the database with the given projection with the selection and selectionArgs
                // and store the result in a cursor object
                cursor = sqLiteDatabase.query(ProductEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;

            default:
                // No URI match was found, throw an exception saying that the URI query was
                // unsuccessful
                throw new IllegalArgumentException("Cannot query URI " + uri);
        }

        // Set the notification URI on the cursor
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the result of the query as a cursor
        return cursor;
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        String name = contentValues.getAsString(ProductEntry.COLUMN_PRODUCT_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Product requires a name!");
        }

        Integer price = contentValues.getAsInteger(ProductEntry.COLUMN_PRODUCT_PRICE);
        if (price == null) {
            throw new IllegalArgumentException("Product requires a valid price!");
        }

        Integer quantity = contentValues.getAsInteger(ProductEntry.COLUMN_PRODUCT_QUANTITY);
        if (quantity == null) {
            throw new IllegalArgumentException("Product requires a valid quantity!");
        }

        String supplierEmail = contentValues.getAsString(ProductEntry.COLUMN_SUPPLIER_EMAIL);
        if (supplierEmail == null) {
            throw new IllegalArgumentException("Product requires a valid supplier email!");
        }

        String imageUri = contentValues.getAsString(ProductEntry.COLUMN_IMAGE_URI);
        if (imageUri == null) {
            throw new IllegalArgumentException("Product requires a valid image URI");
        }

        // Get an instance of the SQLiteDatabase using the ProductDbHelper so that we can add new
        // products to the database
        SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();

        // Integer variable for id of the newly inserted row in the table
        long newRowId;

        // See what code the UriMatcher matches the uri to
        int match = sUriMatcher.match(uri);

        switch (match) {
            case PRODUCTS:
                // Insert the new product into the database and get the new row id as the result
                newRowId = sqLiteDatabase.insert(ProductEntry.TABLE_NAME, null, contentValues);
                break;

            default:
                // The insertion method is not supported for this URI
                // Throw an exception telling the user the same
                throw new IllegalArgumentException("Insertion not supported for URI " + uri);
        }

        if (newRowId == -1) {
            // The new row id is -1 which means the insertion was unsuccessful
            // Print an error in the logs and return a null value
            Log.e(LOG_TAG, "Failed to insert new product in " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for this URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Insertion was successful, return the uri of the newly inserted product
        return ContentUris.withAppendedId(uri, newRowId);
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        // Get an instance of the SQLiteDatabase using the ProductDbHelper so that we can delete the
        // product(s) from the database
        SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();

        // Integer variable for the number of rows affected due to deletion
        int rowsAffected;

        // See what code the UriMatcher matches the Uri to
        int match = sUriMatcher.match(uri);

        switch (match) {
            case PRODUCTS:
                // For the PRODUCTS code, perform a delete query with the given selection and
                // selectionArgs and store the number of rows affected as an integer
                rowsAffected = sqLiteDatabase.delete(ProductEntry.TABLE_NAME, selection,
                        selectionArgs);
                break;

            case PRODUCTS_ID:
                // For the PRODUCTS_ID code, parse the id from the uri and set it as the selection
                selection = ProductEntry._ID + "=?";
                // Each element of selectionArgs will replace every '?' in the selection
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                // Perform a delete query on the given uri and with the selection and
                // selectionArgs and store the number of rows affected as an integer
                rowsAffected = sqLiteDatabase.delete(ProductEntry.TABLE_NAME, selection,
                        selectionArgs);
                break;

            default:
                // No URI match was found, throw an exception saying that the URI query was
                // unsuccessful
                throw new IllegalArgumentException("Could not delete product from URI " + uri);
        }

        if (rowsAffected == 0) {
            // No rows were deleted which means the deletion was unsuccessful
            // Print an error in the logs and return 0
            Log.e(LOG_TAG, "Failed to delete product with URI " + uri);
            return 0;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        // Return the number of rows affected
        return rowsAffected;
    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {

        // Get an instance of the SQLiteDatabase using the ProductDbHelper so that we can update the
        // product in the database
        SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();

        // Integer variable for the number of rows affected due to update operation
        int rowsAffected;

        // See what code the UriMatcher matches the uri to
        int match = sUriMatcher.match(uri);

        switch (match) {
            case PRODUCTS:
                // Perform an update query with the given ContentValues, selection and selectionArgs
                // and store the result as an integer indicating the number of rows affected
                rowsAffected = sqLiteDatabase.update(ProductEntry.TABLE_NAME, contentValues,
                        selection, selectionArgs);
                break;

            case PRODUCTS_ID:
                // For the PRODUCTS_ID code, parse the id from the uri and set it as the selection
                selection = ProductEntry._ID + "=?";
                // Each element of selectionArgs will replace every '?' in the selection
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                // Perform the update query with the given ContentValues, the selection and
                // selectionArgs and return the number of rows affected as the result
                rowsAffected = sqLiteDatabase.update(ProductEntry.TABLE_NAME, contentValues,
                        selection, selectionArgs);
                break;

            default:
                // No URI match was found, throw an exception saying that the URI query was
                // unsuccessful
                throw new IllegalArgumentException("Failed to update product in URI " + uri);
        }

        if (rowsAffected == 0) {
            // No rows were updated which means that the deletion was unsuccessful
            // Print an error in the logs and return 0
            Log.e(LOG_TAG, "Failed to update row with URI " + uri);
            return 0;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        // Return the number of rows affected
        return rowsAffected;
    }
}
