package com.fleximo.convoygsm;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CreateFirstUser extends AppCompatActivity {

    public static final int PICK_CONTACT_REQUEST = 1;
    public static final String TEXT_PHONE = "USER_PHONE_NUMBER";
    public static final String TEXT_PIN = "USER_PIN";

    private EditText etSimCardPhone;
    private EditText etPinCode;
    private TextView tvNext;
    ImageButton imgbtn_CreateFirstUser_Phone;
    ImageButton imgbtn_CreateFirstUser_PinCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_first_user);

        init();
        setListeners();
    }

    @Override
    public void onBackPressed() {
        //Do not kill activity on Back Button press
    }

    private void init() {
        etSimCardPhone = (EditText) findViewById(R.id.etSimCardPhone);
        etPinCode = (EditText) findViewById(R.id.etPinCode);
        tvNext = (TextView) findViewById(R.id.tvNext);
        imgbtn_CreateFirstUser_Phone = (ImageButton) findViewById(R.id.imgbtn_CreateFirstUser_Phone);
        imgbtn_CreateFirstUser_PinCode = (ImageButton) findViewById(R.id.imgbtn_CreateFirstUser_PinCode);
    }

    private void setListeners() {
        //PIN
        etPinCode.setOnKeyListener
                (
                        new EditText.OnKeyListener() {
                            @Override
                            public boolean onKey(View view, int key_code, KeyEvent key_event) {
                                // TODO Auto-generated method stub
                                if (key_event.getAction() == KeyEvent.ACTION_DOWN && key_event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                                    if (correctPhoneAndPINFormat()) {
                                        returnResultCreateFirstUser();
                                        finish();
                                    }
                                    return true;
                                }
                                return false;
                            }
                        }
                );
        //NEXT
        tvNext.setOnClickListener
                (
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (correctPhoneAndPINFormat()) {
                                    returnResultCreateFirstUser();
                                    finish();
                                }
                            }
                        }
                );
        //PHONE BUTTON
        imgbtn_CreateFirstUser_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ContextCompat.checkSelfPermission(CreateFirstUser.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                    pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                    startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
                } else { // Permission NOT GRANTED

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(CreateFirstUser.this, Manifest.permission.READ_CONTACTS)) {
                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed, we can request the permission.
                        ActivityCompat.requestPermissions(CreateFirstUser.this,
                                new String[]{Manifest.permission.READ_CONTACTS},
                                PICK_CONTACT_REQUEST);
                    }
                }
            }
        });
        //PIN BUTTON
        imgbtn_CreateFirstUser_PinCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPinCode.setText("0000");
                Toast.makeText(CreateFirstUser.this, "Default PIN: 0000 was set", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check which request it is that we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);

                etSimCardPhone.setText(number);
            }
        }
    }

    private boolean correctPhoneAndPINFormat()
    {
//        if(etSimCardPhone.getText().toString().length() != 13)
//        {
//            Toast.makeText(this, "Phone number length does not match", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        else if(!etSimCardPhone.getText().toString().substring(0, 4).equals("+380"))
//        {
//            Toast.makeText(this, "Incorrect country number, should be: +380...", Toast.LENGTH_SHORT).show();
//            return false;
//        }

        if(etPinCode.getText().toString().length() != 4)
        {
            Toast.makeText(this, "Incorrect PIN format", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void returnResultCreateFirstUser() {
        Intent intent = new Intent();
        intent.putExtra(TEXT_PHONE, etSimCardPhone.getText().toString());
        intent.putExtra(TEXT_PIN, etPinCode.getText().toString());
        setResult(RESULT_OK, intent);
    }
}
