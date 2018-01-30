package io.yashshah.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import io.yashshah.inventoryapp.data.ProductContract.ProductEntry;

/**
 * Created by yashshah on 01/02/17.
 */

public class ProductCursorAdapter extends CursorAdapter {

    private static final String LOG_TAG = ProductCursorAdapter.class.getSimpleName();

    /**
     * Constructs a new {@link ProductCursorAdapter} object
     *
     * @param context is the context of the app
     * @param c       is the cursor from which to fetch the data
     */
    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    /**
     * Makes a blank ListView item without binding the data to the views
     *
     * @param context   is the context of the app
     * @param cursor    is the cursor from which to load the data
     * @param viewGroup is the parent to which the view is attatched to
     * @return the newly created ListView item
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_product, viewGroup, false);
    }

    /**
     * Binds the Product data to the views in the ListView item
     *
     * @param view    is the existing view to be used/reused
     * @param context is the context of the app
     * @param cursor  is the cursor from which we want to load the data
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find the views in the ListView item that we want to modify
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);

        // Fetch the column indices of the columns that we want to fetch the data from from cursor
        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        int idColumnIndex = cursor.getColumnIndex(ProductEntry._ID);

        final long id = cursor.getInt(idColumnIndex);
        final int quantity = cursor.getInt(quantityColumnIndex);

        Button sellButton = (Button) view.findViewById(R.id.sell);
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity >= 1) {
                    Uri productUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id);
                    int updatedQuantity = quantity - 1;

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, updatedQuantity);

                    int rowsUpdated = context.getContentResolver().update(productUri, contentValues, null, null);

                    if (rowsUpdated == 0) {
                        Toast.makeText(context, "There was an error selling the item", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Product sold successfully!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Not enough quantity to sell!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        String quantityString = "Quantity: " + String.valueOf(cursor.getInt(quantityColumnIndex));
        String priceString = "Price: " + String.valueOf(cursor.getInt(priceColumnIndex)) + " USD";

        // Update the selected views with the data from the cursor
        nameTextView.setText(cursor.getString(nameColumnIndex));
        quantityTextView.setText(quantityString);
        priceTextView.setText(String.valueOf(priceString));
    }
}
