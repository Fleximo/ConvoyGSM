package com.fleximo.convoygsm;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class Users extends AppCompatActivity implements View.OnClickListener {

    public static String EDIT_USER_ITEM_ID = "EDIT_USER_ITEM_ID";

    public static final int ADD_USER_REQUEST = 3;

    LinearLayout llAllUsers;
    ImageButton imgBtnAddUser;
    TextView tvAddUser;

    public ConvoyDBHelper dbAdapter = new ConvoyDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        init();
        setListeners();
    }

    private void init () {
        llAllUsers = (LinearLayout) findViewById(R.id.llAllUsers);
        List<User> allUsersList = dbAdapter.getAllUsers();
        int id = 0;
        for (User user: allUsersList) {
            UserItem item = new UserItem(this, null);
            item.setOnClickListener(this);
            item.setUserName(user.getName());
            item.setId(id);
            llAllUsers.addView(item);
            ++id;
        }

        //ImageButton
        imgBtnAddUser = (ImageButton) findViewById(R.id.imgBtnAddUser);
        tvAddUser = (TextView) findViewById(R.id.tvAddUser);
    }

    private void setListeners() {
        imgBtnAddUser.setOnClickListener(this);
        tvAddUser.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        llAllUsers.removeAllViews();

        llAllUsers = (LinearLayout) findViewById(R.id.llAllUsers);
        List<User> allUsersList = dbAdapter.getAllUsers();
        for (User user: allUsersList) {
            UserItem item = new UserItem(this, null);
            item.setOnClickListener(this);
            item.setUserName(user.getName());
            item.setId(user.getId());
            llAllUsers.addView(item);
        }

        llAllUsers.invalidate();
        llAllUsers.postInvalidate();
    }

    @Override
    public void onClick(View v) {

        if((v.getId() == R.id.imgBtnAddUser) || (v.getId() == R.id.tvAddUser)) {
            Intent intent = new Intent(this, CreateUser.class);
            startActivityForResult(intent, ADD_USER_REQUEST);
            return;
        }

        UserItem item = (UserItem) v;
        if(item != null) {
            Intent intent = new Intent(this, EditUserActivity.class);
            intent.putExtra(EDIT_USER_ITEM_ID, item.getID());
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_USER_REQUEST && data != null) {
                String simCardPhoneNumber = data.getStringExtra(CreateUser.TEXT_PHONE);
                String PIN = data.getStringExtra(CreateUser.TEXT_PIN);
                Cursor cursor = dbAdapter.getWritableDatabase().rawQuery("SELECT  * FROM users", null);
                User user = new User(new Integer(cursor.getCount()).toString(), "User " + cursor.getCount(), simCardPhoneNumber, PIN);
                user.setName(data.getStringExtra(CreateUser.TEXT_NAME));
                user.setVerifyCode(data.getStringExtra(CreateUser.TEXT_VERIFICATION));
                dbAdapter.createUser(user);
                llAllUsers.refreshDrawableState();
            }
        }
    }
}
