package com.fleximo.convoygsm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by fleximo on 14.11.15.
 */
public class ConvoyPageAdapterHeader extends FragmentStatePagerAdapter {

    int m_pagesCount;
    List m_allUsersList;

    public ConvoyPageAdapterHeader(int pagesCount, List allUsersList, FragmentManager fm) {
        super(fm);
        m_pagesCount = pagesCount;
        m_allUsersList = allUsersList;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragmentHeader.newInstance(position, m_allUsersList);
    }

    @Override
    public int getCount() {
        return m_pagesCount;
    }
}
