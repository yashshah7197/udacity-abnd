package io.yashshah.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.yashshah.inventoryapp.data.ProductContract.ProductEntry;

/**
 * Created by yashshah on 25/01/17.
 */

/**
 * Database helper for the Inventory App.
 * Manages the creation and version management of the database.
 */
public class ProductDbHelper extends SQLiteOpenHelper {

    // Name of the database
    private static final String DATABASE_NAME = "inventory.db";

    // Version number of the database
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link ProductDbHelper}
     *
     * @param context is the context of the app
     */
    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a String that holds the SQL query to create a new products table
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + ProductEntry.TABLE_NAME + "("
                + ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProductEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + ProductEntry.COLUMN_PRODUCT_PRICE + " INTEGER NOT NULL, "
                + ProductEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + ProductEntry.COLUMN_SUPPLIER_EMAIL + " TEXT NOT NULL, "
                + ProductEntry.COLUMN_IMAGE_URI + " TEXT NOT NULL);";

        // Execute the SQL query
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Database is still at version 1, so nothing to do here yet
    }
}
