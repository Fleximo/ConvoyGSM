package com.fleximo.convoygsm;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by fleximo on 15.11.15.
 */
public class UserItem extends LinearLayout {

    private int m_id;
    private String userName;
    private TextView tv;
    private ImageButton imgBtn;

    public UserItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        init();
    }

    public void setId(int id ) {
        m_id = id;
    }

    public void setUserName(String usrName) {
        userName = usrName;
        tv.setText(usrName);
    }

    public int getID() { return m_id; }

    private void init() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        //TextView Creation
        tv = new TextView(getContext());
        tv.setText(userName);
        tv.setLayoutParams(params);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.header_text_size));
        //ImageButton creation
        imgBtn = new ImageButton(getContext());
        imgBtn.setBackgroundColor(Color.TRANSPARENT);
        imgBtn.setImageResource(R.drawable.ic_account_circle_white_24dp);
        imgBtn.setLayoutParams(params);
        //Views adding
        addView(imgBtn);
        addView(tv);
    }
}
