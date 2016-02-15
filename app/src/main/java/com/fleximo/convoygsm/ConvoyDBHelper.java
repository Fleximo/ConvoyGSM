package com.fleximo.convoygsm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by fleximo on 15.11.15.
 */
public class ConvoyDBHelper extends SQLiteOpenHelper {

    // database version
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "ConvoyGSMDB";
    //table name
    private static final String table_Name = "users";
    //table columns
    private static final String user_ID = "id";
    private static final String user_Name = "name";
    private static final String SIMCardPhone = "sim_card_phone";
    private static final String PIN = "pin";
    private static final String verifCode = "ver_code";
    private static final String replyNumber = "reply_num";
//    private static final String listenTime = "listen_time";
//    private static final String depositCode = "deposit_code";
//    private static final String locationCode = "location_code";
//    private static final String speakerVolume = "speaker_volume";
//    private static final String micVolume = "mic_volume";
//    private static final String sensors = "sensors";
//    private static final String sendPeriod = "send_period";
    private static final String arm = "arm";
    private static final String disarm = "disarm";
    private static final String alarm = "alarm";
    private static final String ussd = "ussd";
    private static final String valet = "valet";
    private static final String state = "state";
    private static final String gps = "gps";
    private static final String runch1 = "runch1";
    private static final String runch2 = "runch2";
    private static final String disableSensors = "disable_sensors";
    private static final String enableSensors = "enable_sensors";
    private static final String enableMonitoring = "enable_monit";
    private static final String disableMonitoring = "disable_monit";
    private static final String disableSiren = "disable_siren";
    private static final String enableSiren = "enable_siren";
    private static final String disableMicrophone = "disable_mic";
    private static final String enableMicrophone = "enable_mic";
    private static final String startEngine = "start_eng";
    private static final String stopEngine = "stop_eng";
    private static final String startListen = "start_listen";

    private static final String[] COLUMNS =
    {
            user_ID,
            user_Name,
            SIMCardPhone,
            PIN,
            verifCode,
            replyNumber,
//            listenTime,
//            depositCode,
//            locationCode,
//            speakerVolume,
//            micVolume,
//            sensors,
//            sendPeriod,
            arm,
            disarm,
            alarm,
            ussd,
            valet,
            state,
            gps,
            runch1,
            runch2,
            disableSensors,
            enableSensors,
            enableMonitoring,
            disableMonitoring,
            disableSiren,
            enableSiren,
            disableMicrophone,
            enableMicrophone,
            startEngine,
            stopEngine,
            startListen,
    };


    public ConvoyDBHelper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_USERS_TABLE =
        "CREATE TABLE " + table_Name + " ( " +
        user_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        user_Name + " TEXT, " +
        SIMCardPhone + " TEXT, " +
        PIN + " TEXT, " +
        verifCode + " TEXT, " +
        replyNumber + " TEXT, " +
//        listenTime + " TEXT, " +
//        depositCode + " TEXT, " +
//        locationCode + " TEXT, " +
//        speakerVolume + " TEXT, " +
//        micVolume + " TEXT, " +
//        sensors + " TEXT, " +
//        sendPeriod + " TEXT, " +
        arm + " TEXT, " +
        disarm + " TEXT, " +
        alarm + " TEXT, " +
        ussd + " TEXT, " +
        valet + " TEXT, " +
        state + " TEXT, " +
        gps + " TEXT, " +
        runch1 + " TEXT, " +
        runch2 + " TEXT, " +
        disableSensors + " TEXT, " +
        enableSensors + " TEXT, " +
        enableMonitoring + " TEXT, " +
        disableMonitoring + " TEXT, " +
        disableSiren + " TEXT, " +
        enableSiren + " TEXT, " +
        disableMicrophone + " TEXT, " +
        enableMicrophone + " TEXT, " +
        startEngine + " TEXT, " +
        stopEngine + " TEXT, " +
        startListen + " TEXT )";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop books table if already exists
        db.execSQL("DROP TABLE IF EXISTS " + table_Name);
        this.onCreate(db);
    }

    public void createUser(User user) {
        // get reference of the BookDB database
        SQLiteDatabase db = this.getWritableDatabase();
        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(user_Name, user.getName());
        values.put(SIMCardPhone, user.getPhoneSIMCard());
        values.put(PIN, user.getPIN());
        values.put(verifCode, user.getVerifyCode());
        values.put(replyNumber, user.getReplyNumber());
        values.put(arm, user.getArm().toString());
        values.put(disarm, user.getDisarm().toString());
        values.put(alarm, user.getAlarm().toString());
        values.put(ussd, user.getUssd().toString());
        values.put(valet, user.getValet().toString());
        values.put(state, user.getState().toString());
        values.put(gps, user.getGps().toString());
        values.put(runch1, user.getRunch1().toString());
        values.put(runch2, user.getRunch2().toString());
        values.put(disableSensors, user.getDisableSensors().toString());
        values.put(enableSensors, user.getEnableSensors().toString());
        values.put(enableMonitoring, user.getEnableMonitoring());
        values.put(disableMonitoring, user.getDisableMonitoring().toString());
        values.put(disableSiren, user.getDisableSiren().toString());
        values.put(enableSiren, user.getEnableSiren().toString());
        values.put(disableMicrophone, user.getDisableMicrophone().toString());
        values.put(enableMicrophone, user.getEnableMicrophone().toString());
        values.put(startEngine, user.getStartEngine().toString());
        values.put(stopEngine, user.getStopEngine().toString());
        values.put(startListen, user.getStartListen().toString());

        // insert book
        db.insert(table_Name, null, values);
        // close database transaction
        db.close();
    }

    public User readBook(int id) {
        // get reference of the BookDB database
        SQLiteDatabase db = this.getReadableDatabase();
        // get book query
        Cursor cursor = db.query(table_Name, // a. table
                COLUMNS, " id = ?", new String[]{String.valueOf(id)}, null, null, null, null);

        // if results !=null, parse the first one
        if (cursor != null)
        cursor.moveToFirst();

        User user = new User();
        user.setId(Integer.parseInt(cursor.getString(0)));
        user.setName(cursor.getString(1));
        user.setPhoneSIMCard(cursor.getString(2));
        user.setPIN(cursor.getString(3));
        user.setVerifyCode(cursor.getString(4));
        user.setReplyNumber(cursor.getString(5));
        user.setArm(Boolean.valueOf(cursor.getString(6)));
        user.setDisarm(Boolean.valueOf(cursor.getString(7)));
        user.setAlarm(Boolean.valueOf(cursor.getString(8)));
        user.setUssd(Boolean.valueOf(cursor.getString(9)));
        user.setValet(Boolean.valueOf(cursor.getString(10)));
        user.setState(Boolean.valueOf(cursor.getString(11)));
        user.setGps(Boolean.valueOf(cursor.getString(12)));
        user.setRunch1(Boolean.valueOf(cursor.getString(13)));
        user.setRunch2(Boolean.valueOf(cursor.getString(14)));
        user.setDisableSensors(Boolean.valueOf(cursor.getString(15)));
        user.setEnableSensors(Boolean.valueOf(cursor.getString(16)));
        user.setEnableMonitoring(Boolean.valueOf(cursor.getString(17)));
        user.setDisableMonitoring(Boolean.valueOf(cursor.getString(18)));
        user.setDisableSiren(Boolean.valueOf(cursor.getString(19)));
        user.setEnableSiren(Boolean.valueOf(cursor.getString(20)));
        user.setDisableMicrophone(Boolean.valueOf(cursor.getString(21)));
        user.setEnableMicrophone(Boolean.valueOf(cursor.getString(22)));
        user.setStartEngine(Boolean.valueOf(cursor.getString(23)));
        user.setStopEngine(Boolean.valueOf(cursor.getString(24)));
        user.setStartListen(Boolean.valueOf(cursor.getString(25)));

        return user;
    }

    public List getAllUsers() {
        List users = new LinkedList();
        // select book query
        String query = "SELECT  * FROM " + table_Name;
        // get reference of the BookDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // parse all results
        User user = null;
        if (cursor.moveToFirst()) {
            do {
                user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setPhoneSIMCard(cursor.getString(2));
                user.setPIN(cursor.getString(3));
                user.setVerifyCode(cursor.getString(4));
                user.setReplyNumber(cursor.getString(5));
                user.setArm(Boolean.valueOf(cursor.getString(6)));
                user.setDisarm(Boolean.valueOf(cursor.getString(7)));
                user.setAlarm(Boolean.valueOf(cursor.getString(8)));
                user.setUssd(Boolean.valueOf(cursor.getString(9)));
                user.setValet(Boolean.valueOf(cursor.getString(10)));
                user.setState(Boolean.valueOf(cursor.getString(11)));
                user.setGps(Boolean.valueOf(cursor.getString(12)));
                user.setRunch1(Boolean.valueOf(cursor.getString(13)));
                user.setRunch2(Boolean.valueOf(cursor.getString(14)));
                user.setDisableSensors(Boolean.valueOf(cursor.getString(15)));
                user.setEnableSensors(Boolean.valueOf(cursor.getString(16)));
                user.setEnableMonitoring(Boolean.valueOf(cursor.getString(17)));
                user.setDisableMonitoring(Boolean.valueOf(cursor.getString(18)));
                user.setDisableSiren(Boolean.valueOf(cursor.getString(19)));
                user.setEnableSiren(Boolean.valueOf(cursor.getString(20)));
                user.setDisableMicrophone(Boolean.valueOf(cursor.getString(21)));
                user.setEnableMicrophone(Boolean.valueOf(cursor.getString(22)));
                user.setStartEngine(Boolean.valueOf(cursor.getString(23)));
                user.setStopEngine(Boolean.valueOf(cursor.getString(24)));
                user.setStartListen(Boolean.valueOf(cursor.getString(25)));
                // Add book to books
                users.add(user);
            } while (cursor.moveToNext());
        }
        return users;
    }

    public int updateUser(User user) {
        // get reference of the BookDB database
        SQLiteDatabase db = this.getWritableDatabase();
        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(user_Name, user.getName());
        values.put(SIMCardPhone, user.getPhoneSIMCard());
        values.put(PIN, user.getPIN());
        values.put(verifCode, user.getVerifyCode());
        values.put(replyNumber, user.getReplyNumber());
        values.put(arm, user.getArm().toString());
        values.put(disarm, user.getDisarm().toString());
        values.put(alarm, user.getAlarm().toString());
        values.put(ussd, user.getUssd().toString());
        values.put(valet, user.getValet().toString());
        values.put(state, user.getState().toString());
        values.put(gps, user.getGps().toString());
        values.put(runch1, user.getRunch1().toString());
        values.put(runch2, user.getRunch2().toString());
        values.put(disableSensors, user.getDisableSensors().toString());
        values.put(enableSensors, user.getEnableSensors().toString());
        values.put(enableMonitoring, user.getEnableMonitoring().toString());
        values.put(disableMonitoring, user.getDisableMonitoring().toString());
        values.put(disableSiren, user.getDisableSiren().toString());
        values.put(enableSiren, user.getEnableSiren().toString());
        values.put(disableMicrophone, user.getDisableMicrophone().toString());
        values.put(enableMicrophone, user.getEnableMicrophone().toString());
        values.put(startEngine, user.getStartEngine().toString());
        values.put(stopEngine, user.getStopEngine().toString());
        values.put(startListen, user.getStartListen().toString());
        // update
        int i = db.update(table_Name, values, user_ID + " = ?", new String[] { String.valueOf(user.getId()) });
        db.close();
        return i;
    }

    // Deleting single book
    public void deleteUser(User user) {
        // get reference of the BookDB database
        SQLiteDatabase db = this.getWritableDatabase();
        // delete book
        db.delete(table_Name, user_ID + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

}
