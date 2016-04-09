package com.fleximo.convoygsm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    public static final String LANGUAGE = "USER_LANGUAGE";

    Button btn_Settings_Save;
    Button btn_Settings_Cancel;
    Spinner spin_Settings_Languages;

    ImageButton imgbtn_Settings_info;
    TextView tv_Settings_info;

    private static SharedPreferences m_sharedPreferences = MainActivity.m_sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        init();
        setListeners();
    }

    private void init() {
        btn_Settings_Save = (Button) findViewById(R.id.btn_Settings_Save);
        btn_Settings_Cancel = (Button) findViewById(R.id.btn_Settings_Cancel);
        spin_Settings_Languages = (Spinner) findViewById(R.id.spin_Settings_Languages);

        imgbtn_Settings_info = (ImageButton) findViewById(R.id.imgbtn_Settings_info);
        tv_Settings_info = (TextView) findViewById(R.id.tv_Settings_info);

        ArrayAdapter<String> inputSystArmDisarm = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.arr_Settings_Languages));
        spin_Settings_Languages.setAdapter(inputSystArmDisarm);
    }

    private void setListeners() {
        btn_Settings_Save.setOnClickListener(this);
        btn_Settings_Cancel.setOnClickListener(this);
        imgbtn_Settings_info.setOnClickListener(this);
        tv_Settings_info.setOnClickListener(this);

        String language = m_sharedPreferences.getString(LANGUAGE, "ru");

        if(language.equals("ru"))
            spin_Settings_Languages.setSelection(1);
        else if(language.equals("en"))
            spin_Settings_Languages.setSelection(2);
        else if(language.equals("ua"))
            spin_Settings_Languages.setSelection(0);




//        spin_Settings_Languages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                Locale locale = null;
//                String language = null;
//
//                if (position == 0) {
//                    language = new String("ua");
//                    locale = new Locale(language);
//                } else if (position == 1) {
//                    language = new String("ru");
//                    locale = new Locale(language);
//                } else if (position == 2) {
//                    language = new String("en");
//                    locale = new Locale(language);
//                } else {
//                    language = new String("ru");
//                    locale = new Locale(language);
//                }
//
//                Locale.setDefault(locale);
//                Configuration config = new Configuration();
//                config.locale = locale;
//                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//
//                //m_sharedPreferences = MainActivity.m_sharedPreferences;
//                SharedPreferences.Editor ed = MainActivity.m_sharedPreferences.edit();
//                ed.putString(LANGUAGE, language);
//                ed.commit();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // your code here
//            }
//
//        });
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btn_Settings_Save) {
            int position = spin_Settings_Languages.getSelectedItemPosition();

            Locale locale = null;
            String language = null;

            if (position == 0) {
                language = new String("ua");
                locale = new Locale(language);
            } else if (position == 1) {
                language = new String("ru");
                locale = new Locale(language);
            } else if (position == 2) {
                language = new String("en");
                locale = new Locale(language);
            } else {
                language = new String("ru");
                locale = new Locale(language);
            }

            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

            SharedPreferences.Editor ed = MainActivity.m_sharedPreferences.edit();
            ed.putString(LANGUAGE, language);
            ed.commit();

            finish();
        }

        if(v.getId() == R.id.imgbtn_Settings_info || v.getId() == R.id.tv_Settings_info) {
            Intent intent = new Intent(this, Info.class);
            startActivityForResult(intent, 0);
        }

        if(v.getId() == R.id.btn_Settings_Cancel) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
