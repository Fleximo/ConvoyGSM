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
public class SettingsItem extends LinearLayout {

    public enum SettingId {
        SETTING_ID_NONE,
        SETTING_ID_ARM,
        SETTING_ID_DISARM,
        SETTING_ID_ALARM,
        SETTING_ID_USSD,
        SETTING_ID_VALET,
        SETTING_ID_STATE,
        SETTING_ID_GPS,
        SETTING_ID_RUNCH1,
        SETTING_ID_RUNCH2,
        SETTING_ID_DISABLE_SENSORS,
        SETTING_ID_ENABLE_SENSORS,
        SETTING_ID_ENABLE_MONITORING,
        SETTING_ID_DISABLE_MONITORING,
        SETTING_ID_DISABLE_SIREN,
        SETTING_ID_ENABLE_SIREN,
        SETTING_ID_DISABLE_MICROPHONE,
        SETTING_ID_ENABLE_MICROPHONE,
        SETTING_ID_START_ENGINE,
        SETTING_ID_STOP_ENGINE,
        SETTING_ID_START_LISTEN,
    }

    private int m_id = 0;
    private SettingId m_settingId = SettingId.SETTING_ID_NONE;

    private TextView m_tv;
    private ImageButton m_imgBtn;

    private boolean m_active;
    private String m_settingName;

    public SettingsItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        init();
    }

    public void setId(int id ) {m_id = id;}
    //public int getID() { return m_id; }

    public void setSettingName(String usrName) {
        m_settingName = usrName;
        m_tv.setText(usrName);
    }

    public SettingId getSettingId() {
        return m_settingId;
    }
    public void setSettingId(SettingId m_settingId) {
        this.m_settingId = m_settingId;
    }

    public boolean getActive() { return m_active; }

    public void setActive(boolean active) {
        m_active = active;

        if(active)
            m_imgBtn.setImageResource(R.drawable.ic_done_white_24dp);
        else
            m_imgBtn.setImageResource(R.drawable.ic_clear_white_24dp);

    }

    private void init() {
        m_active = false;
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        //TextView Creation
        m_tv = new TextView(getContext());
        m_tv.setText(m_settingName);
        m_tv.setLayoutParams(params);
        m_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.header_text_size));
        //ImageButton creation
        m_imgBtn = new ImageButton(getContext());
        m_imgBtn.setBackgroundColor(Color.TRANSPARENT);
        m_imgBtn.setImageResource(R.drawable.ic_clear_white_24dp);
        m_imgBtn.setLayoutParams(params);
        //Views adding
        addView(m_imgBtn);
        addView(m_tv);
    }
}
