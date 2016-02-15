package com.fleximo.convoygsm;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

/**
 * Created by fleximo on 14.11.15.
 */
public class PageFragmentHeader extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    int pageNumber;
    int backColor;
    List<User> m_allUsersList;

    static PageFragmentHeader newInstance(int page, List allUsersList) {
        PageFragmentHeader pageFragmentHeader = new PageFragmentHeader();
        pageFragmentHeader.setAllUsersList(allUsersList);
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragmentHeader.setArguments(arguments);
        return pageFragmentHeader;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

        Random rnd = new Random();
        backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_header, null);

        TextView tvPage = (TextView) view.findViewById(R.id.tvPage);
        String userName = m_allUsersList.get(pageNumber).getName();
        tvPage.setText(userName);
        tvPage.setBackgroundColor(backColor);

        return view;
    }

    private void setAllUsersList(List allUsersList) {
        m_allUsersList = allUsersList;
    }
}
