package com.fleximo.convoygsm;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by fleximo on 05.01.16.
 */
public class CustomImageButton extends LinearLayout {

    public enum ButtonType {
        BUTTON_TYPE_ARM,
        BUTTON_TYPE_DISARM,
        BUTTON_TYPE_ALARM,
        BUTTON_TYPE_USSD,
        BUTTON_TYPE_VALET,
        BUTTON_TYPE_STATE,
        BUTTON_TYPE_GPS,
        BUTTON_TYPE_RUNCH1,
        BUTTON_TYPE_RUNCH2,
        BUTTON_TYPE_DISABLE_SENSORS,
        BUTTON_TYPE_ENABLE_SENSORS,
        BUTTON_TYPE_ENABLE_MONITORING,
        BUTTON_TYPE_DISABLE_MONITORING,
        BUTTON_TYPE_DISABLE_SIREN,
        BUTTON_TYPE_ENABLE_SIREN,
        BUTTON_TYPE_DISABLE_MICROPHONE,
        BUTTON_TYPE_ENABLE_MICROPHONE,
        BUTTON_TYPE_START_ENGINE,
        BUTTON_TYPE_STOP_ENGINE,
        BUTTON_TYPE_START_LISTEN,
    }


    ImageButton imageButton;
    TextView textView;

    public ButtonType getButtonType() {
        return m_button_type;
    }

    public CustomImageButton(Context context, ButtonType button_type) {
        super(context);

        mContext = context;
        setOrientation(VERTICAL);
        imageButton = new ImageButton(mContext);
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        addView(imageButton);
        addView(textView);
        m_button_type = button_type;
        initilizeImage();
        //Create listener for transfering click event to the father
        View.OnClickListener listener =  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout = (LinearLayout)v.getParent();
                linearLayout.performClick();
            }
        };
        textView.setOnClickListener(listener);
        imageButton.setOnClickListener(listener);
    }

    public CustomImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initilizeImage();
    }

    private void initilizeImage() {
        //Text paint
        m_text_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        m_text_paint.setTextSize(50);
        m_text_paint.setStyle(Paint.Style.STROKE);

        //Path
        mClippingPath = new Path();

        //Top left coordinates of image. Give appropriate values depending on the position you wnat image to be placed
        mPivotX = getScreenGridUnit();
        mPivotY = 0;

        //Adjust the image size to support different screen sizes
//        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.circle);
//        int imageWidth = (int) (getScreenGridUnit() * 30);
//        int imageHeight = (int) (getScreenGridUnit() * 30);
//        mBitmap = Bitmap.createScaledBitmap(bitmap, imageWidth, imageHeight, false);

        if(m_button_type == ButtonType.BUTTON_TYPE_ARM) {
            imageButton.setImageResource(R.drawable.ic_button_arm);
            textView.setText(mContext.getString(R.string.str_Buttons_Arm));
        }
        else if(m_button_type == ButtonType.BUTTON_TYPE_DISARM) {
            imageButton.setImageResource(R.drawable.ic_button_disarm);
            textView.setText(mContext.getString(R.string.str_Buttons_Disarm));
        }
        else if(m_button_type == ButtonType.BUTTON_TYPE_ALARM) {
            imageButton.setImageResource(R.drawable.ic_button_panic);
            textView.setText(mContext.getString(R.string.str_Buttons_Alarm));
        }
        else if(m_button_type == ButtonType.BUTTON_TYPE_USSD) {
            imageButton.setImageResource(R.drawable.ic_button_deposit);
            textView.setText(mContext.getString(R.string.str_Buttons_USSD));
        }
        else if(m_button_type == ButtonType.BUTTON_TYPE_VALET) {
            imageButton.setImageResource(R.drawable.ic_button_valet);
            textView.setText(mContext.getString(R.string.str_Buttons_Valet));
        }
        else if(m_button_type == ButtonType.BUTTON_TYPE_STATE) {
            imageButton.setImageResource(R.drawable.ic_button_status);
            textView.setText(mContext.getString(R.string.str_Buttons_State));
        }
        else if(m_button_type == ButtonType.BUTTON_TYPE_GPS) {
            imageButton.setImageResource(R.drawable.ic_button_gps);
            textView.setText(mContext.getString(R.string.str_Buttons_GPS));
        }
        else if(m_button_type == ButtonType.BUTTON_TYPE_RUNCH1) {
            imageButton.setImageResource(R.drawable.ic_button_channel1);
            textView.setText(mContext.getString(R.string.str_Buttons_Channel1));
        }
        else if(m_button_type == ButtonType.BUTTON_TYPE_RUNCH2) {
            imageButton.setImageResource(R.drawable.ic_button_channel2);
            textView.setText(mContext.getString(R.string.str_Buttons_Channel2));
        }
        else if(m_button_type == ButtonType.BUTTON_TYPE_DISABLE_SENSORS) {
            imageButton.setImageResource(R.drawable.ic_button_sensor_disabled);
            textView.setText(mContext.getString(R.string.str_Buttons_DisableSensors));
        }
        else if(m_button_type == ButtonType.BUTTON_TYPE_ENABLE_SENSORS) {
            imageButton.setImageResource(R.drawable.ic_button_sensor_enabled);
            textView.setText(mContext.getString(R.string.str_Buttons_EnableSensors));
        }
        else if(m_button_type == ButtonType.BUTTON_TYPE_ENABLE_MONITORING) {
            imageButton.setImageResource(R.drawable.ic_button_tracking_enabled);
            textView.setText(mContext.getString(R.string.str_Buttons_EnableMonitoring));
        }
        else if(m_button_type == ButtonType.BUTTON_TYPE_DISABLE_MONITORING) {
            imageButton.setImageResource(R.drawable.ic_button_tracking_disabled);
            textView.setText(mContext.getString(R.string.str_Buttons_DisableMonitoring));
        }
        else if(m_button_type == ButtonType.BUTTON_TYPE_DISABLE_SIREN) {
            imageButton.setImageResource(R.drawable.ic_button_siren_off);
            textView.setText(mContext.getString(R.string.str_Buttons_DisableSiren));
        }
        else if(m_button_type == ButtonType.BUTTON_TYPE_ENABLE_SIREN) {
            imageButton.setImageResource(R.drawable.ic_button_siren_on);
            textView.setText(mContext.getString(R.string.str_Buttons_EnableSiren));
        }
        else if(m_button_type == ButtonType.BUTTON_TYPE_DISABLE_MICROPHONE) {
            imageButton.setImageResource(R.drawable.ic_button_speaker_off);
            textView.setText(mContext.getString(R.string.str_Buttons_DisableMicrophone));
        }
        else if(m_button_type == ButtonType.BUTTON_TYPE_ENABLE_MICROPHONE) {
            imageButton.setImageResource(R.drawable.ic_button_speaker_on);
            textView.setText(mContext.getString(R.string.str_Buttons_EnableMicrophone));
        }
        else if(m_button_type == ButtonType.BUTTON_TYPE_START_ENGINE) {
            imageButton.setImageResource(R.drawable.ic_button_start_engine);
            textView.setText(mContext.getString(R.string.str_Buttons_StartEngine));
        }
        else if(m_button_type == ButtonType.BUTTON_TYPE_STOP_ENGINE) {
            imageButton.setImageResource(R.drawable.ic_button_stop_engine);
            textView.setText(mContext.getString(R.string.str_Buttons_StopEngine));
        }
        else if(m_button_type == ButtonType.BUTTON_TYPE_START_LISTEN) {
            imageButton.setImageResource(R.drawable.ic_button_listening_mode);
            textView.setText(mContext.getString(R.string.str_Buttons_StartListen));
        }
    }

    private float getScreenGridUnit() {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels / 32;
    }

    private ButtonType m_button_type;
    private Path mClippingPath;
    private Paint m_text_paint;
    private Context mContext;
    private Bitmap mBitmap;
    private float mPivotX;
    private float mPivotY;
}
