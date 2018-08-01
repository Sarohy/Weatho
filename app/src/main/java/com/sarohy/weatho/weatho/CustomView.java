package com.sarohy.weatho.weatho;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;


public class CustomView extends View {
    private static final String LOG_TAG = CustomView.class.getSimpleName();

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width;
        if(getLayoutParams().width== ViewGroup.LayoutParams.WRAP_CONTENT)
        {
            width = 40;
        }
        else if((getLayoutParams().width== ViewGroup.LayoutParams.MATCH_PARENT)||(getLayoutParams().width== ViewGroup.LayoutParams.FILL_PARENT))
        {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        else
            width = getLayoutParams().width;


        int height;
        if(getLayoutParams().height== ViewGroup.LayoutParams.WRAP_CONTENT)
        {
            height = 100;
        }
        else if((getLayoutParams().height== ViewGroup.LayoutParams.MATCH_PARENT)||(getLayoutParams().height== ViewGroup.LayoutParams.FILL_PARENT))
        {
            height = MeasureSpec.getSize(heightMeasureSpec);
        }
        else
            height = getLayoutParams().height;

        Log.d(LOG_TAG, String.valueOf(getLayoutParams().height));
        Log.d(LOG_TAG, String.valueOf(getLayoutParams().width));
        Paint p = new Paint();
        p.setColor(Color.YELLOW);
        canvas.drawCircle( width/2, height/2, width/2-getPadding(), p);
        p.setColor(Color.GRAY);
        RectF rectF = new RectF(getPadding(),getPadding(),width-getPadding(),height-getPadding());
        Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int startValue = 6;
        if (hours>=startValue && hours<=startValue+12) {
            hours = hours - startValue;
            int value = 90-(hours*15);
            canvas.drawArc(rectF, 90 + value, 180 - (value * 2), false, p);
        }
    }
    private int widthMeasureSpec, heightMeasureSpec;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(LOG_TAG, String.valueOf(widthMeasureSpec));
        Log.d(LOG_TAG, String.valueOf(heightMeasureSpec));
        this.widthMeasureSpec = widthMeasureSpec;
        this.heightMeasureSpec = heightMeasureSpec;
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int getPadding() {
        return 12;
    }
}
