package com.fleximo.convoygsm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    Button btn_Settings_Save;
    Button btn_Settings_Cancel;

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
    }

    private void setListeners() {
        btn_Settings_Save.setOnClickListener(this);
        btn_Settings_Cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btn_Settings_Save) {
            finish();
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
