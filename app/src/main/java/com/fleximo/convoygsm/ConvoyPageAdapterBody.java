package com.fleximo.convoygsm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;
import java.util.Vector;

/**
 * Created by fleximo on 14.11.15.
 */
public class ConvoyPageAdapterBody extends FragmentStatePagerAdapter {

    int m_pages_count;
    int m_user_id_in_db;
    static final int BUTOONS_PER_PAGE = 4;
    private ConvoyDBHelper m_db_helper;

    public ConvoyPageAdapterBody(int user_id_in_db, ConvoyDBHelper db_helper, FragmentManager fm) {
        super(fm);

        m_user_id_in_db = user_id_in_db;
        m_db_helper = db_helper;
        m_pages_count = getPagesCount();
    }

    @Override
    public Fragment getItem(int body_page_id) {
        return PageFragmentBody.newInstance(m_user_id_in_db, body_page_id, m_db_helper);
    }

    @Override
    public int getCount() {
        return m_pages_count;
    }

    private int getPagesCount() {
        int enabled_elements_cnt = 0;
        List<User> users =  m_db_helper.getAllUsers();

        User user = null;
        if(!users.isEmpty())
            user = users.get(MainActivity.currentUserId - 1);
        else
            return 0;

        if(user.getArm())
            ++enabled_elements_cnt;
        if(user.getDisarm())
            ++enabled_elements_cnt;
        if(user.getAlarm())
            ++enabled_elements_cnt;
        if(user.getUssd())
            ++enabled_elements_cnt;
        if(user.getValet())
            ++enabled_elements_cnt;
        if(user.getState())
            ++enabled_elements_cnt;
        if(user.getGps())
            ++enabled_elements_cnt;
        if(user.getRunch1())
            ++enabled_elements_cnt;
        if(user.getRunch2())
            ++enabled_elements_cnt;
        if(user.getDisableSensors())
            ++enabled_elements_cnt;
        if(user.getEnableSensors())
            ++enabled_elements_cnt;
        if(user.getEnableMonitoring())
            ++enabled_elements_cnt;
        if(user.getDisableMonitoring())
            ++enabled_elements_cnt;
        if(user.getDisableSiren())
            ++enabled_elements_cnt;
        if(user.getEnableSiren())
            ++enabled_elements_cnt;
        if(user.getDisableMicrophone())
            ++enabled_elements_cnt;
        if(user.getEnableMicrophone())
            ++enabled_elements_cnt;
        if(user.getStartEngine())
            ++enabled_elements_cnt;
        if(user.getStopEngine())
            ++enabled_elements_cnt;
        if(user.getStartListen())
            ++enabled_elements_cnt;

        int pages_count = enabled_elements_cnt/BUTOONS_PER_PAGE;
        if(enabled_elements_cnt % BUTOONS_PER_PAGE > 0)
            ++pages_count;

        return  pages_count;
    }
}
