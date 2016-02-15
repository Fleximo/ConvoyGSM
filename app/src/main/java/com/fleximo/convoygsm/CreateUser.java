package com.fleximo.convoygsm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class CreateUser extends AppCompatActivity implements View.OnClickListener {

    public static final int PICK_CONTACT_REQUEST = 1;

    public final static String TEXT_NAME = "TEXT_NAME";
    public final static String TEXT_PHONE = "TEXT_PHONE";
    public final static String TEXT_PIN = "TEXT_PIN";
    public final static String TEXT_VERIFICATION = "TEXT_VERIFICATION";

    EditText et_CreateUser_UserName;
    EditText et_CreateUser_SimCardPhone;
    EditText et_CreateUser_PinCode;
    EditText et_CreateUser_VerificationCode;

    Button btn_CreateUser_Create;
    Button btn_CreateUser_Cancel;

    ImageButton imgbtn_CreateUser_Phone;
    ImageButton imgbtn_CreateUser_PinCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        init();
        setListeners();
    }

    private void init() {
        et_CreateUser_UserName = (EditText) findViewById(R.id.et_CreateUser_UserName);
        et_CreateUser_SimCardPhone = (EditText) findViewById(R.id.et_CreateUser_SimCardPhone);
        et_CreateUser_PinCode = (EditText) findViewById(R.id.et_CreateUser_PinCode);
        et_CreateUser_VerificationCode = (EditText) findViewById(R.id.et_CreateUser_VerificationCode);

        btn_CreateUser_Create = (Button) findViewById(R.id.btn_CreateUser_Create);
        btn_CreateUser_Cancel = (Button) findViewById(R.id.btn_CreateUser_Cancel);

        imgbtn_CreateUser_Phone = (ImageButton) findViewById(R.id.imgbtn_CreateUser_Phone);
        imgbtn_CreateUser_PinCode = (ImageButton) findViewById(R.id.imgbtn_CreateUser_PinCode);
    }

    private void setListeners() {
        btn_CreateUser_Create.setOnClickListener(this);
        btn_CreateUser_Cancel.setOnClickListener(this);
        imgbtn_CreateUser_Phone.setOnClickListener(this);
        imgbtn_CreateUser_PinCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_CreateUser_Create: {
                onCreate();
                break;
            }

            case R.id.btn_CreateUser_Cancel: {
                onCancel();
                break;
            }

            case R.id.imgbtn_CreateUser_Phone: {
                if (ContextCompat.checkSelfPermission(CreateUser.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                    pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                    startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
                } else { // Permission NOT GRANTED

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(CreateUser.this, Manifest.permission.READ_CONTACTS)) {
                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed, we can request the permission.
                        ActivityCompat.requestPermissions(CreateUser.this,
                                new String[]{Manifest.permission.READ_CONTACTS},
                                PICK_CONTACT_REQUEST);
                    }
                }
                break;
            }

            case R.id.imgbtn_CreateUser_PinCode: {
                et_CreateUser_PinCode.setText("0000");
                Toast.makeText(CreateUser.this, "Default PIN: 0000 was set", Toast.LENGTH_LONG).show();
                break;
            }
        }
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

                et_CreateUser_SimCardPhone.setText(number);
            }
        }
    }

    private void onCreate() {
        if(isAllFieldsCorrect()) {
            returnResultCreatetUser();
            finish();
        }
    }

    private void onCancel() {
        finish();
    }

    private boolean isAllFieldsCorrect() {
        if (isUserNameCorrect() &&
            isPINCorrect())
            return true;
        else
            return false;
    }

    private boolean isUserNameCorrect() {
        if(!et_CreateUser_UserName.getText().toString().isEmpty())
            return true;
        else
            return false;
    }

    private boolean isSIMNumberCorrect() {
        String phoneNumber = et_CreateUser_SimCardPhone.getText().toString();
        if(phoneNumber.length() != 13)
        {
            return false;
        }
        else if(!phoneNumber.substring(0, 4).equals("+380"))
        {
            return false;
        }

        return true;
    }

    private boolean isPINCorrect() {
        if(et_CreateUser_PinCode.getText().toString().length() == 4)
            return true;
        else
            return false;
    }

    private boolean isVerificationCorrect() {
        String code = et_CreateUser_VerificationCode.getText().toString();

        if(code.length() != 5)
            return false;

        if((code.charAt(0) != '*') || ((code.charAt(code.length()-1) != '#')))
            return false;

        String subcode = code.substring(1, 4);

        return isInteger(subcode);
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
//        if (str.charAt(0) == '-') {
//            if (length == 1) {
//                return false;
//            }
//            i = 1;
//        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    private void returnResultCreatetUser() {
        Intent intent = new Intent();
        intent.putExtra(TEXT_NAME, et_CreateUser_UserName.getText().toString());
        intent.putExtra(TEXT_PHONE, et_CreateUser_SimCardPhone.getText().toString());
        intent.putExtra(TEXT_PIN, et_CreateUser_PinCode.getText().toString());
        intent.putExtra(TEXT_VERIFICATION, et_CreateUser_VerificationCode.getText().toString());
        setResult(RESULT_OK, intent);
    }
}
