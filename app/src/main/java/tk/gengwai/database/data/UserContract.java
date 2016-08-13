package tk.gengwai.database.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by danielchan303 on 22/7/2016.
 */
//public final class UserContract {
//    public static final String CONTENT_AUTHORITY = "tk.gengwai.database";
//    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
//    public static final String PATH_CUSTOMER = "Customer";
//    public static final String PATH_ORDER = "Order";

public class UserContract {

    public static final String CONTENT_AUTHORITY = "tk.gengwai.database";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_CUSTOMER = CustomerEntry.TABLE_NAME;
    public static final String PATH_ORDER = CustomerEntry.TABLE_NAME;

    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY;

    public static final class CustomerEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CUSTOMER).build();

        public static Uri buildContentUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CUSTOMER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CUSTOMER;

        public static final String TABLE_NAME = "Customer";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_PHONE_NO = "PhoneNum";
    }

    public static abstract class OrderEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ORDER).build();

        public static Uri buildContentUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CUSTOMER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CUSTOMER;

        public static final String TABLE_NAME = "CustomerOrder";
        public static final String CUSTOMER_ID = "CustomerId";
        public static final String COLUMN_DESCRIPTION = "Description";
        public static final String COLUMN_QUANTITY = "Quantity";
    }
}
