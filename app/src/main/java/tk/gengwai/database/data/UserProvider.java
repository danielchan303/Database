package tk.gengwai.database.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.Nullable;

/**
 * Created by danielchan303 on 12/8/2016.
 */
public class UserProvider extends ContentProvider {

    private sqlHelper mOpenHelper;

    static final int JOIN = 0;
    static final int CUSTOMER = 100;
    static final int ORDER = 200;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String authority = UserContract.CONTENT_AUTHORITY;

    static {
        sUriMatcher.addURI(authority,null, JOIN);
        sUriMatcher.addURI(authority, UserContract.PATH_CUSTOMER, CUSTOMER);
        sUriMatcher.addURI(authority, UserContract.PATH_ORDER, ORDER);
    }

    private Cursor buildCustomerQuery() {
        return mOpenHelper.getReadableDatabase().query(UserContract.PATH_CUSTOMER, null, null, null, null, null, null);
    }

    private Cursor buildOrderQuery() {
        return mOpenHelper.getReadableDatabase().query(UserContract.PATH_ORDER, null, null, null, null, null, null);
    }

    private static final SQLiteQueryBuilder sWeatherByLocationSettingQueryBuilder;

    static {
        sWeatherByLocationSettingQueryBuilder = new SQLiteQueryBuilder();
        sWeatherByLocationSettingQueryBuilder.setTables(
                UserContract.PATH_CUSTOMER + " INNER JOIN " + UserContract.PATH_ORDER +
                        " ON " + UserContract.PATH_CUSTOMER + "." + UserContract.CustomerEntry._ID +
                        " = " + UserContract.PATH_ORDER + "." + UserContract.OrderEntry.CUSTOMER_ID
        );
    }

    private Cursor buildJoinQuery() {
        return sWeatherByLocationSettingQueryBuilder.query(mOpenHelper.getReadableDatabase(), null, null, null, null, null, null);
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new sqlHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case JOIN:
                return UserContract.CONTENT_TYPE;
            case CUSTOMER:
                return UserContract.CustomerEntry.CONTENT_TYPE;
            case ORDER:
                return UserContract.CustomerEntry.CONTENT_TYPE;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case JOIN:
            {
                retCursor = buildJoinQuery();
                break;
            }
            case CUSTOMER:
            {
                retCursor = buildCustomerQuery();
                break;
            }
            case ORDER:
            {
                retCursor = buildOrderQuery();
                break;
            }
            default:
            {
                return null;
            }
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match){
            case CUSTOMER:
            {
                long _id = db.insert(UserContract.PATH_CUSTOMER, null, contentValues);
                if (_id != -1) {
                    returnUri = UserContract.CustomerEntry.buildContentUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case ORDER:
            {
                long _id = db.insert(UserContract.PATH_ORDER, null, contentValues);
                if (_id != -1) {
                    returnUri = UserContract.OrderEntry.buildContentUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
            {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
        db.close();
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }
}
