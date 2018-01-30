package io.yashshah.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import io.yashshah.inventoryapp.data.ProductContract.ProductEntry;

public class EditorActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = EditorActivity.class.getSimpleName();
    private static final int REQUEST_IMAGE_GET = 1;
    private EditText mNameEditText;
    private EditText mPriceEditText;
    private EditText mQuantityEditText;
    private EditText mSupplierEmailEditText;
    private Button mIncreaseQuantityButton;
    private Button mDecreaseQuantityButton;
    private Uri mCurrentProductUri;
    private int mQuantity;
    private int mPrice;
    private String mSupplierEmail;
    private ImageView mImageView;
    private String mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();
        mCurrentProductUri = intent.getData();

        mNameEditText = (EditText) findViewById(R.id.product_name);
        mPriceEditText = (EditText) findViewById(R.id.product_price);
        mQuantityEditText = (EditText) findViewById(R.id.product_quantity);
        mSupplierEmailEditText = (EditText) findViewById(R.id.supplier_email);

        mImageView = (ImageView) findViewById(R.id.image);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                imageIntent.setType("image/*");
                if (imageIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(imageIntent, REQUEST_IMAGE_GET);
                }
            }
        });

        mIncreaseQuantityButton = (Button) findViewById(R.id.button_increase);
        mIncreaseQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementQuantity();
            }
        });

        mDecreaseQuantityButton = (Button) findViewById(R.id.button_decrease);
        mDecreaseQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrementQuantity();
            }
        });

        if (mCurrentProductUri == null) {
            setTitle("Add a Product");
            mImageUri = "";
            mQuantity = 0;
            mPrice = 0;

            mPriceEditText.setText(String.valueOf(mPrice));
            mQuantityEditText.setText(String.valueOf(mQuantity));

        } else {
            setTitle("Edit Product");
            getLoaderManager().initLoader(0, null, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveProduct();
                break;

            case R.id.action_sell_product:
                sellProduct();
                break;

            case R.id.action_order_more:
                sendEmailToSupplier();
                break;

            case R.id.action_delete_product:
                showDeleteConfirmation();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void saveProduct() {
        String productNameString = mNameEditText.getText().toString().trim();
        String productPriceString = mPriceEditText.getText().toString().trim();
        String productQuantityString = mQuantityEditText.getText().toString().trim();
        String supplierEmailString = mSupplierEmailEditText.getText().toString().trim();

        if (TextUtils.isEmpty(productNameString) || TextUtils.isEmpty(productPriceString)
                || TextUtils.isEmpty(productQuantityString)
                || TextUtils.isEmpty(supplierEmailString) || mImageUri.equals("")) {

            Toast.makeText(this, "You must fill out all fields and select an image!", Toast.LENGTH_SHORT).show();
            return;
        }

        int productPrice = 0;
        if (!TextUtils.isEmpty(productPriceString)) {
            productPrice = Integer.parseInt(productPriceString);
        }

        int productQuantity = 0;
        if (!TextUtils.isEmpty(productQuantityString)) {
            productQuantity = Integer.parseInt(productQuantityString);
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductEntry.COLUMN_PRODUCT_NAME, productNameString);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_PRICE, productPrice);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, productQuantity);
        contentValues.put(ProductEntry.COLUMN_SUPPLIER_EMAIL, supplierEmailString);
        contentValues.put(ProductEntry.COLUMN_IMAGE_URI, mImageUri);

        if (mCurrentProductUri == null) {
            Uri uri = getContentResolver().insert(ProductEntry.CONTENT_URI, contentValues);
            if (uri == null) {
                Toast.makeText(this, "Error saving product!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Product saved successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            int rowsUpdated = getContentResolver().update(mCurrentProductUri, contentValues, null,
                    null);

            if (rowsUpdated == 0) {
                Toast.makeText(this, "Error updating product information!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Product information updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        }


    }

    private void incrementQuantity() {
        int quantity = Integer.parseInt(mQuantityEditText.getText().toString().trim());
        mQuantityEditText.setText(String.valueOf(++quantity));
    }

    private void decrementQuantity() {
        int quantity = Integer.parseInt(mQuantityEditText.getText().toString().trim());
        if (quantity == 0) {
            Toast.makeText(this, "Quantity cannot be negative!", Toast.LENGTH_SHORT).show();
        } else {
            mQuantityEditText.setText(String.valueOf(--quantity));
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRODUCT_PRICE,
                ProductEntry.COLUMN_PRODUCT_QUANTITY,
                ProductEntry.COLUMN_SUPPLIER_EMAIL,
                ProductEntry.COLUMN_IMAGE_URI
        };

        return new CursorLoader(this, mCurrentProductUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);
            int supplierEmailColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_SUPPLIER_EMAIL);
            int imageUriColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_IMAGE_URI);

            String name = cursor.getString(nameColumnIndex);
            int price = cursor.getInt(priceColumnIndex);
            int quantity = cursor.getInt(quantityColumnIndex);
            String supplierEmail = cursor.getString(supplierEmailColumnIndex);

            mQuantity = cursor.getInt(quantityColumnIndex);
            mSupplierEmail = cursor.getString(supplierEmailColumnIndex);

            mImageUri = cursor.getString(imageUriColumnIndex);
            if (mImageUri.equals("")) {
                mImageView.setImageResource(R.drawable.placeholder);
            } else {
                loadImage(Uri.parse(mImageUri));
            }

            mNameEditText.setText(name);
            mPriceEditText.setText(String.valueOf(price));
            mQuantityEditText.setText(String.valueOf(quantity));
            mSupplierEmailEditText.setText(supplierEmail);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mNameEditText.setText("");
        mPriceEditText.setText("");
        mQuantityEditText.setText("");
        mSupplierEmailEditText.setText("");
    }

    private void sellProduct() {
        if (mCurrentProductUri != null) {

            if (mQuantity == 0) {
                Toast.makeText(this, "You do not have enough quantity to sell!", Toast.LENGTH_SHORT).show();
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, --mQuantity);

                int rowsUpdated = getContentResolver().update(mCurrentProductUri, contentValues, null,
                        null);

                if (rowsUpdated == 0) {
                    Toast.makeText(this, "Error updating product information!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Product information updated successfully", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void sendEmailToSupplier() {
        if (mSupplierEmail == null || TextUtils.isEmpty(mSupplierEmail)) {
            Toast.makeText(this, "No valid supplier email found!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mSupplierEmail});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Order for extra quantity");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    private void showDeleteConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete this product?");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteProduct();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null) {
                    dialogInterface.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteProduct() {
        if (mCurrentProductUri != null) {
            int rowsDeleted = getContentResolver().delete(mCurrentProductUri, null, null);

            if (rowsDeleted == 0) {
                Toast.makeText(this, "Failed to delete product!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Product deleted successfully!", Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            mImageUri = imageUri.toString();
            loadImage(imageUri);
        }
    }

    private void loadImage(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            mImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            Toast.makeText(this, "There was a problem loading the product image!", Toast.LENGTH_SHORT).show();
            Log.e(LOG_TAG, "There was a problem loading the product image!", e);
        }
    }
}
