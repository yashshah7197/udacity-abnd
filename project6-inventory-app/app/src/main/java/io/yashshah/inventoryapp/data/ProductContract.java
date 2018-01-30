package io.yashshah.inventoryapp.data;

/**
 * Created by yashshah on 25/01/17.
 */


import android.net.Uri;
import android.provider.BaseColumns;

/**
 * API Contract for the Inventory App
 */
public class ProductContract {

    // Content Authority for the ProductProvider
    public static final String CONTENT_AUTHORITY = "io.yashshah.inventoryapp";

    // Base Content Uri which the apps will use to contact the ProductProvider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // The path for the products table which is to be appended to the base uri
    public static final String PATH_PRODUCTS = "products";

    // Make the constructor private to prevent anyone for instantiating this class
    private ProductContract() {
    }

    /**
     * Inner class that defines the constant values for the products table
     * Each entry in the table represents a single product
     */
    public static final class ProductEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS);

        // Name of the table for the products
        public static final String TABLE_NAME = "products";

        // Column name for the name of the product. Type TEXT.
        public static final String COLUMN_PRODUCT_NAME = "name";

        // Column name for the price of the product. Type INTEGER.
        public static final String COLUMN_PRODUCT_PRICE = "price";

        // Column name for the quantity of the product. TYPE INTEGER.
        public static final String COLUMN_PRODUCT_QUANTITY = "quantity";

        // Column name for the supplier email for the product. TYPE TEXT.
        public static final String COLUMN_SUPPLIER_EMAIL = "supplierEmail";

        public static final String COLUMN_IMAGE_URI = "imageUri";
    }
}
