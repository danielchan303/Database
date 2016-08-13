package tk.gengwai.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tk.gengwai.database.data.UserContract;
import tk.gengwai.database.data.sqlHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // InsertBtn Action
        Button insertRowBtn = (Button) findViewById(R.id.insertRowBtn);
        insertRowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Declare Input Field
                EditText nameField = (EditText) findViewById(R.id.nameField);
                EditText phoneNumField = (EditText) findViewById(R.id.phoneNumField);

                // Get the text from Input Field
                String name = nameField.getText().toString();
                String phoneNum = phoneNumField.getText().toString();

                // Toast Msg
                String toastMsg = "Name: " + name + " Phone Number: " + phoneNum;
                Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();

                // Insert by using the content provider
                ContentValues contentValues = new ContentValues();
                contentValues.put(UserContract.CustomerEntry.COLUMN_NAME, name);
                contentValues.put(UserContract.CustomerEntry.COLUMN_PHONE_NO, phoneNum);

                ContentResolver resolver = getContentResolver();
                try {
                    Uri returnUri = resolver.insert(UserContract.CustomerEntry.CONTENT_URI, contentValues);
                    Log.i("InsertSucces", returnUri.toString());
                } catch (android.database.SQLException e1) {
                    Log.e("InsertionError", e1.toString());
                }
            }
        });

        // QueryBtn Action
        Button queryBtn = (Button) findViewById(R.id.queryBtn);
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentResolver resolver = getContentResolver();
                Cursor cursor = resolver.query(UserContract.CustomerEntry.CONTENT_URI, null,null,null,null);

                while (cursor.moveToNext()) {
                    int nameId = cursor.getColumnIndex(UserContract.CustomerEntry.COLUMN_NAME);
                    int phoneNumId = cursor.getColumnIndex(UserContract.CustomerEntry.COLUMN_PHONE_NO);

                    Log.i("Query", "Name: " + cursor.getString(nameId) + " PhoneNum: " + cursor.getString(phoneNumId));
                }
                cursor.close();
            }
        });
    }
}
