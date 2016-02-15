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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by fleximo on 05.01.16.
 */
public class CustomImageButton extends ImageButton {

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


    //ImageButton imageButton;
    //TextView textView;
    public ButtonType getButtonType() {
        return m_button_type;
    }

    public CustomImageButton(Context context, ButtonType button_type) {
        super(context);

//        setOrientation(VERTICAL);
//        imageButton = new ImageButton(mContext);
//        textView = new TextView(mContext);
//        addView(imageButton);
//        addView(textView);

        mContext = context;
        m_button_type = button_type;
        initilizeImage();

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
            setImageResource(R.drawable.ic_button_arm);
            //textView.setText("Bla-Bla");
        }
        else if(m_button_type == ButtonType.BUTTON_TYPE_DISARM)
            setImageResource(R.drawable.ic_button_disarm);
        else if(m_button_type == ButtonType.BUTTON_TYPE_ALARM)
            setImageResource(R.drawable.ic_button_panic);
        else if(m_button_type == ButtonType.BUTTON_TYPE_USSD)
            setImageResource(R.drawable.ic_button_deposit);
        else if(m_button_type == ButtonType.BUTTON_TYPE_VALET)
            setImageResource(R.drawable.ic_button_valet);
        else if(m_button_type == ButtonType.BUTTON_TYPE_STATE)
            setImageResource(R.drawable.ic_button_status);
        else if(m_button_type == ButtonType.BUTTON_TYPE_GPS)
            setImageResource(R.drawable.ic_button_gps);
        else if(m_button_type == ButtonType.BUTTON_TYPE_RUNCH1)
            setImageResource(R.drawable.ic_button_channel1);
        else if(m_button_type == ButtonType.BUTTON_TYPE_RUNCH2)
            setImageResource(R.drawable.ic_button_channel2);
        else if(m_button_type == ButtonType.BUTTON_TYPE_DISABLE_SENSORS)
            setImageResource(R.drawable.ic_button_sensor_disabled);
        else if(m_button_type == ButtonType.BUTTON_TYPE_ENABLE_SENSORS)
            setImageResource(R.drawable.ic_button_sensor_enabled);
        else if(m_button_type == ButtonType.BUTTON_TYPE_ENABLE_MONITORING)
            setImageResource(R.drawable.ic_button_tracking_enabled);
        else if(m_button_type == ButtonType.BUTTON_TYPE_DISABLE_MONITORING)
            setImageResource(R.drawable.ic_button_tracking_disabled);
        else if(m_button_type == ButtonType.BUTTON_TYPE_DISABLE_SIREN)
            setImageResource(R.drawable.ic_button_siren_off);
        else if(m_button_type == ButtonType.BUTTON_TYPE_ENABLE_SIREN)
            setImageResource(R.drawable.ic_button_siren_on);
        else if(m_button_type == ButtonType.BUTTON_TYPE_DISABLE_MICROPHONE)
            setImageResource(R.drawable.ic_button_speaker_off);
        else if(m_button_type == ButtonType.BUTTON_TYPE_ENABLE_MICROPHONE)
            setImageResource(R.drawable.ic_button_speaker_on);
        else if(m_button_type == ButtonType.BUTTON_TYPE_START_ENGINE)
            setImageResource(R.drawable.ic_button_start_engine);
        else if(m_button_type == ButtonType.BUTTON_TYPE_STOP_ENGINE)
            setImageResource(R.drawable.ic_button_stop_engine);
        else if(m_button_type == ButtonType.BUTTON_TYPE_START_LISTEN)
            setImageResource(R.drawable.ic_button_listening_mode);
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
