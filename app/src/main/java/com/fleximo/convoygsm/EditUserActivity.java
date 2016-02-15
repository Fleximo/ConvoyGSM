package com.fleximo.convoygsm;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class EditUserActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int PICK_CONTACT_REQUEST = 1;

    Button btn_EditUser_Save;
    Button btn_EditUser_Delete;

    EditText et_EditUser_UserName;
    EditText et_EditUser_SimCardPhone;
    EditText et_EditUser_PinCode;
    EditText et_EditUser_VerificationCode;

    ImageButton imgbtn_EditUser_Phone;
    ImageButton imgbtn_CreateUser_PinCode;

    User m_user;
    LinearLayout ll_EditUser_Body;
    public ConvoyDBHelper dbAdapter = new ConvoyDBHelper(this);

    boolean m_arm;
    boolean m_disarm;
    boolean m_alarm;
    boolean m_ussd;
    boolean m_valet;
    boolean m_state;
    boolean m_gps;
    boolean m_runch1;
    boolean m_runch2;
    boolean m_disableSensors;
    boolean m_enableSensors;
    boolean m_enableMonitoring;
    boolean m_disableMonitoring;
    boolean m_disableSiren;
    boolean m_enableSiren;
    boolean m_disableMicrophone;
    boolean m_enableMicrophone;
    boolean m_startEngine;
    boolean m_stopEngine;
    boolean m_startListen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        Intent intent = getIntent();
        int userId = intent.getIntExtra(Users.EDIT_USER_ITEM_ID, 1);

        init();
        setListeners();
        constructActivity(userId);
    }

    private void init() {
        btn_EditUser_Save = (Button) findViewById(R.id.btn_EditUser_Save);
        btn_EditUser_Delete = (Button) findViewById(R.id.btn_EditUser_Delete);
        ll_EditUser_Body = (LinearLayout) findViewById(R.id.ll_EditUser_Body);

        et_EditUser_UserName = (EditText) findViewById(R.id.et_EditUser_UserName);
        et_EditUser_SimCardPhone = (EditText) findViewById(R.id.et_EditUser_SimCardPhone);
        et_EditUser_PinCode = (EditText) findViewById(R.id.et_EditUser_PinCode);
        et_EditUser_VerificationCode = (EditText) findViewById(R.id.et_EditUser_VerificationCode);

        imgbtn_EditUser_Phone = (ImageButton) findViewById(R.id.imgbtn_EditUser_Phone);
        imgbtn_CreateUser_PinCode = (ImageButton) findViewById(R.id.imgbtn_EditUser_PinCode);
    }

    private void setListeners() {
        btn_EditUser_Save.setOnClickListener(this);
        btn_EditUser_Delete.setOnClickListener(this);
        imgbtn_EditUser_Phone.setOnClickListener(this);
        imgbtn_CreateUser_PinCode.setOnClickListener(this);
    }

    private void constructActivity(int userID) {
        m_user = dbAdapter.readBook(userID);

        //User
        et_EditUser_UserName.setText(m_user.getName());
        et_EditUser_SimCardPhone.setText(m_user.getPhoneSIMCard());
        et_EditUser_PinCode.setText(m_user.getPIN());
        et_EditUser_VerificationCode.setText(m_user.getVerifyCode());

        //Arm
        SettingsItem settingsItemArm = new SettingsItem(this, null);
        settingsItemArm.setOnClickListener(this);
        m_arm = m_user.getArm();
        settingsItemArm.setActive(m_arm);
        settingsItemArm.setSettingId(SettingsItem.SettingId.SETTING_ID_ARM);
        settingsItemArm.setSettingName("Arm");
        ll_EditUser_Body.addView(settingsItemArm);
        //Disarm
        SettingsItem settingsItemDisarm = new SettingsItem(this, null);
        settingsItemDisarm.setOnClickListener(this);
        m_disarm = m_user.getDisarm();
        settingsItemDisarm.setActive(m_disarm);
        settingsItemDisarm.setSettingId(SettingsItem.SettingId.SETTING_ID_DISARM);
        settingsItemDisarm.setSettingName("Disarm");
        ll_EditUser_Body.addView(settingsItemDisarm);
        //Alarm
        SettingsItem settingsItemAlarm = new SettingsItem(this, null);
        settingsItemAlarm.setOnClickListener(this);
        m_alarm = m_user.getAlarm();
        settingsItemAlarm.setActive(m_alarm);
        settingsItemAlarm.setSettingId(SettingsItem.SettingId.SETTING_ID_ALARM);
        settingsItemAlarm.setSettingName("Alarm");
        ll_EditUser_Body.addView(settingsItemAlarm);
        //USSD
        SettingsItem settingsItemUSSD = new SettingsItem(this, null);
        settingsItemUSSD.setOnClickListener(this);
        m_ussd = m_user.getUssd();
        settingsItemUSSD.setActive(m_ussd);
        settingsItemUSSD.setSettingId(SettingsItem.SettingId.SETTING_ID_USSD);
        settingsItemUSSD.setSettingName("USSD");
        ll_EditUser_Body.addView(settingsItemUSSD);
        //Valet
        SettingsItem settingsItemValet = new SettingsItem(this, null);
        settingsItemValet.setOnClickListener(this);
        m_valet = m_user.getValet();
        settingsItemValet.setActive(m_valet);
        settingsItemValet.setSettingId(SettingsItem.SettingId.SETTING_ID_VALET);
        settingsItemValet.setSettingName("Valet");
        ll_EditUser_Body.addView(settingsItemValet);
        //State
        SettingsItem settingsItemState = new SettingsItem(this, null);
        settingsItemState.setOnClickListener(this);
        m_state = m_user.getState();
        settingsItemState.setActive(m_state);
        settingsItemState.setSettingId(SettingsItem.SettingId.SETTING_ID_STATE);
        settingsItemState.setSettingName("State");
        ll_EditUser_Body.addView(settingsItemState);
        //GPS
        SettingsItem settingsItemGPS = new SettingsItem(this, null);
        settingsItemGPS.setOnClickListener(this);
        m_gps = m_user.getGps();
        settingsItemGPS.setActive(m_gps);
        settingsItemGPS.setSettingId(SettingsItem.SettingId.SETTING_ID_GPS);
        settingsItemGPS.setSettingName("GPS");
        ll_EditUser_Body.addView(settingsItemGPS);
        //Runch1
        SettingsItem settingsItemRunch1 = new SettingsItem(this, null);
        settingsItemRunch1.setOnClickListener(this);
        m_runch1 = m_user.getRunch1();
        settingsItemRunch1.setActive(m_runch1);
        settingsItemRunch1.setSettingId(SettingsItem.SettingId.SETTING_ID_RUNCH1);
        settingsItemRunch1.setSettingName("Runch1");
        ll_EditUser_Body.addView(settingsItemRunch1);
        //Runch2
        SettingsItem settingsItemRunch2 = new SettingsItem(this, null);
        settingsItemRunch2.setOnClickListener(this);
        m_runch2 = m_user.getRunch2();
        settingsItemRunch2.setActive(m_runch2);
        settingsItemRunch2.setSettingId(SettingsItem.SettingId.SETTING_ID_RUNCH2);
        settingsItemRunch2.setSettingName("Runch2");
        ll_EditUser_Body.addView(settingsItemRunch2);
        //Disable sensors
        SettingsItem settingsItemDisableSensors = new SettingsItem(this, null);
        settingsItemDisableSensors.setOnClickListener(this);
        m_disableSensors = m_user.getDisableSensors();
        settingsItemDisableSensors.setActive(m_disableSensors);
        settingsItemDisableSensors.setSettingId(SettingsItem.SettingId.SETTING_ID_DISABLE_SENSORS);
        settingsItemDisableSensors.setSettingName("Disable sensors");
        ll_EditUser_Body.addView(settingsItemDisableSensors);
        //Enable sensors
        SettingsItem settingsItemEnableSensors = new SettingsItem(this, null);
        settingsItemEnableSensors.setOnClickListener(this);
        m_enableSensors = m_user.getEnableSensors();
        settingsItemEnableSensors.setActive(m_enableSensors);
        settingsItemEnableSensors.setSettingId(SettingsItem.SettingId.SETTING_ID_ENABLE_SENSORS);
        settingsItemEnableSensors.setSettingName("Enable sensors");
        ll_EditUser_Body.addView(settingsItemEnableSensors);
        //Enable monitoring
        SettingsItem settingsItemEnableMonitoring = new SettingsItem(this, null);
        settingsItemEnableMonitoring.setOnClickListener(this);
        m_enableMonitoring = m_user.getEnableMonitoring();
        settingsItemEnableMonitoring.setActive(m_enableMonitoring);
        settingsItemEnableMonitoring.setSettingId(SettingsItem.SettingId.SETTING_ID_ENABLE_MONITORING);
        settingsItemEnableMonitoring.setSettingName("Enable monitoring");
        ll_EditUser_Body.addView(settingsItemEnableMonitoring);
        //Disable monitoring
        SettingsItem settingsItemDisableMonitoring = new SettingsItem(this, null);
        settingsItemDisableMonitoring.setOnClickListener(this);
        m_disableMonitoring = m_user.getDisableMonitoring();
        settingsItemDisableMonitoring.setActive(m_disableMonitoring);
        settingsItemDisableMonitoring.setSettingId(SettingsItem.SettingId.SETTING_ID_DISABLE_MONITORING);
        settingsItemDisableMonitoring.setSettingName("Disable monitoring");
        ll_EditUser_Body.addView(settingsItemDisableMonitoring);
        //Disable siren
        SettingsItem settingsItemDisableSiren = new SettingsItem(this, null);
        settingsItemDisableSiren.setOnClickListener(this);
        m_disableSiren = m_user.getDisableSiren();
        settingsItemDisableSiren.setActive(m_disableSiren);
        settingsItemDisableSiren.setSettingId(SettingsItem.SettingId.SETTING_ID_DISABLE_SIREN);
        settingsItemDisableSiren.setSettingName("Disable siren");
        ll_EditUser_Body.addView(settingsItemDisableSiren);
        //Enable siren
        SettingsItem settingsItemEnableSiren = new SettingsItem(this, null);
        settingsItemEnableSiren.setOnClickListener(this);
        m_enableSiren = m_user.getEnableSiren();
        settingsItemEnableSiren.setActive(m_enableSiren);
        settingsItemEnableSiren.setSettingId(SettingsItem.SettingId.SETTING_ID_ENABLE_SIREN);
        settingsItemEnableSiren.setSettingName("Enable siren");
        ll_EditUser_Body.addView(settingsItemEnableSiren);
        //Disable microphone
        SettingsItem settingsItemDisableMicrophone = new SettingsItem(this, null);
        settingsItemDisableMicrophone.setOnClickListener(this);
        m_disableMicrophone = m_user.getDisableMicrophone();
        settingsItemDisableMicrophone.setActive(m_disableMicrophone);
        settingsItemDisableMicrophone.setSettingId(SettingsItem.SettingId.SETTING_ID_DISABLE_MICROPHONE);
        settingsItemDisableMicrophone.setSettingName("Disable microphone");
        ll_EditUser_Body.addView(settingsItemDisableMicrophone);
        //Enable microphone
        SettingsItem settingsItemEnableMicrophone = new SettingsItem(this, null);
        settingsItemEnableMicrophone.setOnClickListener(this);
        m_enableMicrophone = m_user.getEnableMicrophone();
        settingsItemEnableMicrophone.setActive(m_enableMicrophone);
        settingsItemEnableMicrophone.setSettingId(SettingsItem.SettingId.SETTING_ID_ENABLE_MICROPHONE);
        settingsItemEnableMicrophone.setSettingName("Enable microphone");
        ll_EditUser_Body.addView(settingsItemEnableMicrophone);
        //Start engine
        SettingsItem settingsItemStartEngine = new SettingsItem(this, null);
        settingsItemStartEngine.setOnClickListener(this);
        m_startEngine = m_user.getStartEngine();
        settingsItemStartEngine.setActive(m_startEngine);
        settingsItemStartEngine.setSettingId(SettingsItem.SettingId.SETTING_ID_START_ENGINE);
        settingsItemStartEngine.setSettingName("Enable microphone");
        ll_EditUser_Body.addView(settingsItemStartEngine);
        //Stop engine
        SettingsItem settingsItemStopEngine = new SettingsItem(this, null);
        settingsItemStopEngine.setOnClickListener(this);
        m_stopEngine = m_user.getStopEngine();
        settingsItemStopEngine.setActive(m_stopEngine);
        settingsItemStopEngine.setSettingId(SettingsItem.SettingId.SETTING_ID_STOP_ENGINE);
        settingsItemStopEngine.setSettingName("Stop engine");
        ll_EditUser_Body.addView(settingsItemStopEngine);
        //Start listen
        SettingsItem settingsItemStartListen = new SettingsItem(this, null);
        settingsItemStartListen.setOnClickListener(this);
        m_startListen = m_user.getStartListen();
        settingsItemStartListen.setActive(m_startListen);
        settingsItemStartListen.setSettingId(SettingsItem.SettingId.SETTING_ID_START_LISTEN);
        settingsItemStartListen.setSettingName("Start listen");
        ll_EditUser_Body.addView(settingsItemStartListen);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btn_EditUser_Save) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.str_SaveUserAlert_Text)
                    .setPositiveButton(R.string.str_DeleteUserAlert_OK, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            save();
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.str_DeleteUserAlert_Cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Current behavior - nothing to do.
                        }
                    });
            // Create the AlertDialog object and show it
            builder.create().show();
        }

        if(v.getId() == R.id.btn_EditUser_Delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.str_DeleteUserAlert_Text)
                    .setPositiveButton(R.string.str_DeleteUserAlert_OK, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dbAdapter.deleteUser(m_user);
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.str_DeleteUserAlert_Cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Current behavior - nothing to do.
                        }
                    });
            // Create the AlertDialog object and show it
            builder.create().show();
        }


        if(v.getId() == R.id.imgbtn_EditUser_Phone) {
            if (ContextCompat.checkSelfPermission(EditUserActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
            } else { // Permission NOT GRANTED

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(EditUserActivity.this, Manifest.permission.READ_CONTACTS)) {
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(EditUserActivity.this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            PICK_CONTACT_REQUEST);
                }
            }
        }

        if(v.getId() == R.id.imgbtn_EditUser_PinCode) {
            et_EditUser_PinCode.setText("0000");
            Toast.makeText(EditUserActivity.this, "Default PIN: 0000 was set", Toast.LENGTH_LONG).show();
        }


        //Handle user settings
        try {
            SettingsItem settingsItem = (SettingsItem) v;
            if (settingsItem != null) {
                SettingsItem.SettingId id = settingsItem.getSettingId();
                if (id == SettingsItem.SettingId.SETTING_ID_ARM) {
                    m_arm = !m_arm;
                    settingsItem.setActive(m_arm);
                }
                if (id == SettingsItem.SettingId.SETTING_ID_DISARM) {
                    m_disarm = !m_disarm;
                    settingsItem.setActive(m_disarm);
                }
                if (id == SettingsItem.SettingId.SETTING_ID_ALARM) {
                    m_alarm = !m_alarm;
                    settingsItem.setActive(m_alarm);
                }
                if (id == SettingsItem.SettingId.SETTING_ID_USSD) {

                    if(isVerificationCorrect()) {
                        m_ussd = !m_ussd;
                        settingsItem.setActive(m_ussd);
                    } else {
                        Toast.makeText(EditUserActivity.this, "Verification code incorrect! Please enter *XXX#", Toast.LENGTH_LONG).show();
                    }
                }
                if (id == SettingsItem.SettingId.SETTING_ID_VALET) {
                    m_valet = !m_valet;
                    settingsItem.setActive(m_valet);
                }
                if (id == SettingsItem.SettingId.SETTING_ID_STATE) {
                    m_state = !m_state;
                    settingsItem.setActive(m_state);
                }
                if (id == SettingsItem.SettingId.SETTING_ID_GPS) {
                    m_gps = !m_gps;
                    settingsItem.setActive(m_gps);
                }
                if (id == SettingsItem.SettingId.SETTING_ID_RUNCH1) {
                    m_runch1 = !m_runch1;
                    settingsItem.setActive(m_runch1);
                }
                if (id == SettingsItem.SettingId.SETTING_ID_RUNCH2) {
                    m_runch2 = !m_runch2;
                    settingsItem.setActive(m_runch2);
                }
                if (id == SettingsItem.SettingId.SETTING_ID_DISABLE_SENSORS) {
                    m_disableSensors = !m_disableSensors;
                    settingsItem.setActive(m_disableSensors);
                }
                if (id == SettingsItem.SettingId.SETTING_ID_ENABLE_SENSORS) {
                    m_enableSensors = !m_enableSensors;
                    settingsItem.setActive(m_enableSensors);
                }
                if (id == SettingsItem.SettingId.SETTING_ID_ENABLE_MONITORING) {
                    m_enableMonitoring = !m_enableMonitoring;
                    settingsItem.setActive(m_enableMonitoring);
                }
                if (id == SettingsItem.SettingId.SETTING_ID_DISABLE_MONITORING) {
                    m_disableMonitoring = !m_disableMonitoring;
                    settingsItem.setActive(m_disableMonitoring);
                }
                if (id == SettingsItem.SettingId.SETTING_ID_DISABLE_SIREN) {
                    m_disableSiren = !m_disableSiren;
                    settingsItem.setActive(m_disableSiren);
                }
                if (id == SettingsItem.SettingId.SETTING_ID_ENABLE_SIREN) {
                    m_enableSiren = !m_enableSiren;
                    settingsItem.setActive(m_enableSiren);
                }
                if (id == SettingsItem.SettingId.SETTING_ID_DISABLE_MICROPHONE) {
                    m_disableMicrophone = !m_disableMicrophone;
                    settingsItem.setActive(m_disableMicrophone);
                }
                if (id == SettingsItem.SettingId.SETTING_ID_ENABLE_MICROPHONE) {
                    m_enableMicrophone = !m_enableMicrophone;
                    settingsItem.setActive(m_enableMicrophone);
                }
                if (id == SettingsItem.SettingId.SETTING_ID_START_ENGINE) {
                    m_startEngine = !m_startEngine;
                    settingsItem.setActive(m_startEngine);
                }
                if (id == SettingsItem.SettingId.SETTING_ID_STOP_ENGINE) {
                    m_stopEngine = !m_stopEngine;
                    settingsItem.setActive(m_stopEngine);
                }
                if (id == SettingsItem.SettingId.SETTING_ID_START_LISTEN) {
                    m_startListen = !m_startListen;
                    settingsItem.setActive(m_startListen);
                }
            }
        }
        catch (ClassCastException exc) {
            return;
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

                et_EditUser_SimCardPhone.setText(number);
            }
        }
    }

    private void save() {
        m_user.setName(et_EditUser_UserName.getText().toString());
        m_user.setPhoneSIMCard(et_EditUser_SimCardPhone.getText().toString());
        m_user.setPIN(et_EditUser_PinCode.getText().toString());
        m_user.setVerifyCode(et_EditUser_VerificationCode.getText().toString());

        m_user.setArm(m_arm);
        m_user.setDisarm(m_disarm);
        m_user.setAlarm(m_alarm);
        m_user.setUssd(m_ussd);
        m_user.setValet(m_valet);
        m_user.setState(m_state);
        m_user.setGps(m_gps);
        m_user.setRunch1(m_runch1);
        m_user.setRunch2(m_runch2);
        m_user.setDisableSensors(m_disableSensors);
        m_user.setEnableSensors(m_enableSensors);
        m_user.setEnableMonitoring(m_enableMonitoring);
        m_user.setDisableMonitoring(m_disableMonitoring);
        m_user.setDisableSiren(m_disableSiren);
        m_user.setEnableSiren(m_enableSiren);
        m_user.setDisableMicrophone(m_disableMicrophone);
        m_user.setEnableMicrophone(m_enableMicrophone);
        m_user.setStartEngine(m_startEngine);
        m_user.setStopEngine(m_stopEngine);
        m_user.setStartListen(m_startListen);

        dbAdapter.updateUser(m_user);
        finish();
    }

    private boolean isVerificationCorrect() {
        String code = et_EditUser_VerificationCode.getText().toString();

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
}
