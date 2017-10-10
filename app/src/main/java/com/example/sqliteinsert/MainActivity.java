package com.example.sqliteinsert;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqliteinsert.data.UserContract;
import com.example.sqliteinsert.data.UserInfoProvider;

public class MainActivity extends AppCompatActivity {
    EditText Name, Pass;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name= (EditText) findViewById(R.id.editName);
        Pass= (EditText) findViewById(R.id.editPass);
/*        Cursor cursorContacts = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI ,
                null , null , null , null);
        Toast.makeText(this , "Number of contacts: " + cursorContacts.getCount(),Toast.LENGTH_LONG).show();*/
    }

    public void addUser(View view)
    {
        Toast.makeText(this,"Running", Toast.LENGTH_LONG).show();
        String t1 = Name.getText().toString();
        String t2 = Pass.getText().toString();
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntity.USER_NAME , t1);
        values.put(UserContract.UserEntity.USER_PWD , t1);
        Uri newUri = getContentResolver().insert(UserContract.UserEntity.CONTENT_URI, values);
        long identity = ContentUris.parseId(newUri);
        //long identity = helper.insertData(t1,t2);
        context = getApplicationContext();
        if(identity == 0)
        {
            Message.message(context,"Unsuccessful");
        } else
        {
            Message.message(context,"Successful");
        }
    }
}
