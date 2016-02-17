package com.fleximo.convoygsm;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import static com.fleximo.convoygsm.CustomImageButton.*;

/**
 * Created by fleximo on 14.11.15.
 */
public class PageFragmentBody extends Fragment {

    private final static int MY_PERMISSIONS_REQUEST_SEND_SMS = 12;
    private final static int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 13;

    private final static int BUTTON_TOP_LEFT = 0;
    private final static int BUTTON_TOP_RIGHT = 1;
    private final static int BUTTON_BOTTOM_LEFT = 2;
    private final static int BUTTON_BOTTOM_RIGHT = 3;

    private final static int BUTTONS_PER_PAGE = 4;
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    private int m_page_number;
    private int m_user_id_number_in_db;
    private int backColor;
    private static ConvoyDBHelper m_db_helper;
    private Vector<CustomImageButton> m_buttons;

    RelativeLayout ll_FragmentBody_Hor1;
    RelativeLayout ll_FragmentBody_Hor2;
    LinearLayout m_linear_layout_top;
    LinearLayout m_linear_layout_bottom;

    CustomImageButton m_pressed_image_button = null;

    static PageFragmentBody newInstance(int user_id_number_in_db, int body_page_id, ConvoyDBHelper db_helper) {
        //Set DB helper
        m_db_helper = db_helper;
        //Set bundle
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, body_page_id);

        PageFragmentBody pageFragmentBody = new PageFragmentBody();
        pageFragmentBody.setArguments(arguments);
        pageFragmentBody.setUserDBId(user_id_number_in_db);

        return pageFragmentBody;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_page_number = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

        Random rnd = new Random();
        backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_body, null);

        createButtons();

        ll_FragmentBody_Hor1 = (RelativeLayout) view.findViewById(R.id.ll_FragmentBody_Hor1);
        m_linear_layout_top = new LinearLayout(getContext());
        addButton(BUTTON_TOP_LEFT);
        addButton(BUTTON_TOP_RIGHT);
        ll_FragmentBody_Hor1.addView(m_linear_layout_top);

        ll_FragmentBody_Hor2 = (RelativeLayout) view.findViewById(R.id.ll_FragmentBody_Hor2);
        m_linear_layout_bottom = new LinearLayout(getContext());
        addButton(BUTTON_BOTTOM_LEFT);
        addButton(BUTTON_BOTTOM_RIGHT);
        ll_FragmentBody_Hor2.addView(m_linear_layout_bottom);

        return view;
    }

    private void setUserDBId(int user_id_number_in_db) {
        m_user_id_number_in_db = user_id_number_in_db;
    }

    private void createButtons() {

        List<User> users =  m_db_helper.getAllUsers();
        User user = users.get(m_user_id_number_in_db - 1);

        CustomImageButton custom_button = null;
        View.OnClickListener listener =  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomImageButton button = (CustomImageButton) v;
                generateSMS(button);
            }
        };

        m_buttons = new Vector<CustomImageButton>();
        if(user.getArm()) {
            custom_button = new CustomImageButton(getContext(), ButtonType.BUTTON_TYPE_ARM);
            custom_button.setOnClickListener(listener);
            m_buttons.add(custom_button);
        }
        if(user.getDisarm()) {
            custom_button = new CustomImageButton(getContext(), ButtonType.BUTTON_TYPE_DISARM);
            custom_button.setOnClickListener(listener);
            m_buttons.add(custom_button);
        }
        if(user.getAlarm()) {
            custom_button = new CustomImageButton(getContext(), ButtonType.BUTTON_TYPE_ALARM);
            custom_button.setOnClickListener(listener);
            m_buttons.add(custom_button);
        }
        if(user.getUssd()) {
            custom_button = new CustomImageButton(getContext(), ButtonType.BUTTON_TYPE_USSD);
            custom_button.setOnClickListener(listener);
            m_buttons.add(custom_button);
        }
        if(user.getValet()) {
            custom_button = new CustomImageButton(getContext(), ButtonType.BUTTON_TYPE_VALET);
            custom_button.setOnClickListener(listener);
            m_buttons.add(custom_button);
        }
        if(user.getState()) {
            custom_button = new CustomImageButton(getContext(), ButtonType.BUTTON_TYPE_STATE);
            custom_button.setOnClickListener(listener);
            m_buttons.add(custom_button);
        }
        if(user.getGps()) {
            custom_button = new CustomImageButton(getContext(), ButtonType.BUTTON_TYPE_GPS);
            custom_button.setOnClickListener(listener);
            m_buttons.add(custom_button);
        }
        if(user.getRunch1()) {
            custom_button = new CustomImageButton(getContext(), ButtonType.BUTTON_TYPE_RUNCH1);
            custom_button.setOnClickListener(listener);
            m_buttons.add(custom_button);
        }
        if(user.getRunch2()) {
            custom_button = new CustomImageButton(getContext(), ButtonType.BUTTON_TYPE_RUNCH2);
            custom_button.setOnClickListener(listener);
            m_buttons.add(custom_button);
        }
        if(user.getDisableSensors()) {
            custom_button = new CustomImageButton(getContext(), ButtonType.BUTTON_TYPE_DISABLE_SENSORS);
            custom_button.setOnClickListener(listener);
            m_buttons.add(custom_button);
        }
        if(user.getEnableSensors()) {
            custom_button = new CustomImageButton(getContext(), ButtonType.BUTTON_TYPE_ENABLE_SENSORS);
            custom_button.setOnClickListener(listener);
            m_buttons.add(custom_button);
        }
        if(user.getEnableMonitoring()) {
            custom_button = new CustomImageButton(getContext(), ButtonType.BUTTON_TYPE_ENABLE_MONITORING);
            custom_button.setOnClickListener(listener);
            m_buttons.add(custom_button);
        }
        if(user.getDisableMonitoring()) {
            custom_button = new CustomImageButton(getContext(), ButtonType.BUTTON_TYPE_DISABLE_MONITORING);
            custom_button.setOnClickListener(listener);
            m_buttons.add(custom_button);
        }
        if(user.getDisableSiren()) {
            custom_button = new CustomImageButton(getContext(), ButtonType.BUTTON_TYPE_DISABLE_SIREN);
            custom_button.setOnClickListener(listener);
            m_buttons.add(custom_button);
        }
        if(user.getEnableSiren()) {
            custom_button = new CustomImageButton(getContext(), ButtonType.BUTTON_TYPE_ENABLE_SIREN);
            custom_button.setOnClickListener(listener);
            m_buttons.add(custom_button);
        }
        if(user.getDisableMicrophone()) {
            custom_button = new CustomImageButton(getContext(), ButtonType.BUTTON_TYPE_DISABLE_MICROPHONE);
            custom_button.setOnClickListener(listener);
            m_buttons.add(custom_button);
        }
        if(user.getEnableMicrophone()) {
            custom_button = new CustomImageButton(getContext(), ButtonType.BUTTON_TYPE_ENABLE_MICROPHONE);
            custom_button.setOnClickListener(listener);
            m_buttons.add(custom_button);
        }
        if(user.getStartEngine()) {
            custom_button = new CustomImageButton(getContext(), ButtonType.BUTTON_TYPE_START_ENGINE);
            custom_button.setOnClickListener(listener);
            m_buttons.add(custom_button);
        }
        if(user.getStopEngine()) {
            custom_button = new CustomImageButton(getContext(), ButtonType.BUTTON_TYPE_STOP_ENGINE);
            custom_button.setOnClickListener(listener);
            m_buttons.add(custom_button);
        }
        if(user.getStartListen()) {
            custom_button = new CustomImageButton(getContext(), ButtonType.BUTTON_TYPE_START_LISTEN);
            custom_button.setOnClickListener(listener);
            m_buttons.add(custom_button);
        }
    }

    private void addButton(int button) {

        try {
            if (button == BUTTON_TOP_LEFT) {
                CustomImageButton button_view = m_buttons.get(BUTTON_TOP_LEFT + (m_page_number) * BUTTONS_PER_PAGE);
                m_linear_layout_top.addView(button_view);
            } else if (button == BUTTON_TOP_RIGHT) {
                CustomImageButton button_view = m_buttons.get(BUTTON_TOP_RIGHT + (m_page_number) * BUTTONS_PER_PAGE);
                m_linear_layout_top.addView(button_view);
            } else if (button == BUTTON_BOTTOM_LEFT) {
                CustomImageButton button_view = m_buttons.get(BUTTON_BOTTOM_LEFT + (m_page_number) * BUTTONS_PER_PAGE);
                m_linear_layout_bottom.addView(button_view);
            } else if (button == BUTTON_BOTTOM_RIGHT) {
                CustomImageButton button_view = m_buttons.get(BUTTON_BOTTOM_RIGHT + (m_page_number) * BUTTONS_PER_PAGE);
                m_linear_layout_bottom.addView(button_view);
            }
        }
        catch (ArrayIndexOutOfBoundsException except) {

        }
    }

    private void generateSMS(CustomImageButton button) {

        m_pressed_image_button = button;

            boolean sms_granted = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
            boolean read_phone_granted = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
            if (sms_granted && read_phone_granted) {
                sendSMS();
            }
            else { // Permission NOT GRANTED
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.SEND_SMS)) {
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE},
                            MY_PERMISSIONS_REQUEST_SEND_SMS);
                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
    }

    private String getMessageText() {

        if(m_pressed_image_button == null)
            return "";

        List<User> users =  m_db_helper.getAllUsers();
        User user = users.get(m_user_id_number_in_db - 1);

        String text = "PIN:";
        text += user.getPIN();
        text += " ";
        ButtonType buttonType = m_pressed_image_button.getButtonType();

        if(buttonType == ButtonType.BUTTON_TYPE_ARM) {
            text += "ARM";
        }
        else if(buttonType == ButtonType.BUTTON_TYPE_DISARM) {
            text += "DISARM";
        }
        else if(buttonType == ButtonType.BUTTON_TYPE_ALARM) {
            text += "ALARM";
        }
        else if(buttonType == ButtonType.BUTTON_TYPE_USSD) {
            text += "USSD " + user.getVerifyCode();
        }
        else if(buttonType == ButtonType.BUTTON_TYPE_VALET) {
            text += "VALET";
        }
        else if(buttonType == ButtonType.BUTTON_TYPE_STATE) {
            text += "STATE?";
        }
        else if(buttonType == ButtonType.BUTTON_TYPE_GPS) {
            text += "GPS?";
        }
        else if(buttonType == ButtonType.BUTTON_TYPE_RUNCH1) {
            text += "RUNCH1";
        }
        else if(buttonType == ButtonType.BUTTON_TYPE_RUNCH2) {
            text += "RUNCH2";
        }
        else if(buttonType == ButtonType.BUTTON_TYPE_DISABLE_SENSORS) {
            text += "SENSOR 0";
        }
        else if(buttonType == ButtonType.BUTTON_TYPE_ENABLE_SENSORS) {
            text += "SENSOR 1";
        }
        else if(buttonType == ButtonType.BUTTON_TYPE_ENABLE_MONITORING) {
            text += "MONITOR 0";
        }
        else if(buttonType == ButtonType.BUTTON_TYPE_DISABLE_MONITORING) {
            text += "MONITOR 1";
        }
        else if(buttonType == ButtonType.BUTTON_TYPE_DISABLE_SIREN) {
            text += "SIREN 1 0";
        }
        else if(buttonType == ButtonType.BUTTON_TYPE_ENABLE_SIREN) {
            text += "SIREN 1 1";
        }
        else if(buttonType == ButtonType.BUTTON_TYPE_DISABLE_MICROPHONE) {
            text += "MIC 0";
        }
        else if(buttonType == ButtonType.BUTTON_TYPE_ENABLE_MICROPHONE) {
            text += "MIC 1";
        }
        else if(buttonType == ButtonType.BUTTON_TYPE_START_ENGINE) {
            text += "START";
        }
        else if(buttonType == ButtonType.BUTTON_TYPE_STOP_ENGINE) {
            text += "STOP";
        }
        else if(buttonType == ButtonType.BUTTON_TYPE_START_LISTEN) {
            text += "LISTEN";
        }

        return text;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSMS();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void sendSMS() {
        try {
            List<User> users =  m_db_helper.getAllUsers();
            User user = users.get(m_user_id_number_in_db - 1);

            SmsManager smsManager = SmsManager.getDefault();
            String phone = user.getPhoneSIMCard();
            String message = getMessageText();
            smsManager.sendTextMessage(phone, null, message, null, null);

            Toast.makeText(getContext(), "SMS Sent!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getContext(),
                    "SMS faild, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}
