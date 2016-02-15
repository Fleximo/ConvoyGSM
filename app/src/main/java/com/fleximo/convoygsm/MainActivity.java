package com.fleximo.convoygsm;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.wearable.view.DotsPageIndicator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int NEWUSER_REQUEST = 1;
    public static int currentUserId = 0;

    public ConvoyDBHelper dbAdapter = new ConvoyDBHelper(this);

    private ImageButton imgBtnUsers;
    private ImageButton imgBtnSettings;

    private ViewPager pagerHeader;
    private ViewPager pagerBody;
    private PagerAdapter pagerHeaderAdapter;
    private PagerAdapter pagerBodyAdapter;
    private RadioGroup rbGroup;
    private ArrayList<RadioButton> radioButtons = new ArrayList<>();
    private int m_current_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Upgrade DV
        //dbAdapter.onUpgrade(dbAdapter.getWritableDatabase(), 1, 2);
        //Initialization
        init();
        setListeners();
        updatePageAdapters();

        //Start Activity
        Cursor cursor = dbAdapter.getWritableDatabase().rawQuery("SELECT  * FROM users", null);
        if (cursor.getCount() == 0) {
            Intent intent = new Intent(this, CreateFirstUser.class);
            startActivityForResult(intent, NEWUSER_REQUEST);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {
            if (requestCode == NEWUSER_REQUEST && data != null) {
                String simCardPhoneNumber = data.getStringExtra(CreateFirstUser.TEXT_PHONE);
                String PIN = data.getStringExtra(CreateFirstUser.TEXT_PIN);
                dbAdapter.createUser(new User("0", "Car", simCardPhoneNumber, PIN));
                updatePageAdapters();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        updatePageAdapters();
    }

    private void init() {
        imgBtnUsers = (ImageButton) findViewById(R.id.imgBtnUsers);
        imgBtnSettings = (ImageButton) findViewById(R.id.imgBtnSettings);
        pagerHeader = (ViewPager) findViewById(R.id.pagerHeader);
        rbGroup = (RadioGroup) findViewById(R.id.rbGroup);

        m_current_page = 0;
    }

    private void setListeners()
    {
        imgBtnUsers.setOnClickListener
        (   new View.OnClickListener()
            {
               @Override
               public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Users.class);
                startActivity(intent);
               }
            }
        );

        imgBtnSettings.setOnClickListener
        (   new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
                }
            }
        );

        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkedButtonId = -1;
                final int buttonsCount = radioButtons.size();

                for(int i = 0; i < buttonsCount; ++i) {
                    if(checkedId == radioButtons.get(i).getId())
                        checkedButtonId = i;
                }

                if(checkedButtonId != -1)
                    pagerBody.setCurrentItem(checkedButtonId);
            }
        });
    }



    private void updatePageAdapters() {
        Cursor pageAdapterCursor = dbAdapter.getWritableDatabase().rawQuery("SELECT  * FROM users", null);
        pagerHeaderAdapter = new ConvoyPageAdapterHeader(pageAdapterCursor.getCount(), dbAdapter.getAllUsers(), getSupportFragmentManager());
        pagerHeader.setAdapter(pagerHeaderAdapter);

        ViewPager.OnPageChangeListener header_page_change_listener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentUserId = position + 1;   //In SQLite id begins from 1
                pagerBody = (ViewPager) findViewById(R.id.pagerBody);
                pagerBodyAdapter = new ConvoyPageAdapterBody(currentUserId, dbAdapter, getSupportFragmentManager());
                pagerBody.setAdapter(pagerBodyAdapter);
                m_current_page = position;

                clearRadioGroup(rbGroup);
                final int pagesCount = pagerBodyAdapter.getCount();
                for(int i = 0; i < pagesCount; i++) {
                    RadioButton rb = new RadioButton(MainActivity.this);
                    rbGroup.addView(rb);
                    radioButtons.add(rb);
                }

                //Body page listener
                ViewPager.OnPageChangeListener body_page_change_listener = new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        radioButtons.get(position).setChecked(true);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                };
                pagerBody.addOnPageChangeListener(body_page_change_listener);
                if(!radioButtons.isEmpty())
                    radioButtons.get(0).setChecked(true);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        };


        pagerHeader.addOnPageChangeListener(header_page_change_listener);
        header_page_change_listener.onPageSelected(m_current_page);
    }

    private void clearRadioGroup(RadioGroup rg) {
        int count = rg.getChildCount();
        if(count>0) {
            for (int i=count-1;i>=0;i--) {
                View o = rg.getChildAt(i);
                if (o instanceof RadioButton) {
                    rg.removeViewAt(i);
                }
            }
        }
        radioButtons.clear();
    }
}
