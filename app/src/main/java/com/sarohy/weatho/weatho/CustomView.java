package com.sarohy.weatho.weatho;

import android.annotation.SuppressLint;
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
    private Context context;
    public CustomView(Context context) {
        super(context);
        this.context = context;
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        RectF rectF = RectF();
//        // Draw the shadow
//        canvas.drawOval(
//                mShadowBounds,
//                mShadowPaint
//        );

//        // Draw the label text
//        canvas.drawText(mData.get(mCurrentItem).mLabel, mTextX, mTextY, mTextPaint);

//        // Draw the pie slices
//        for (int i = 0; i < mData.size(); ++i) {
//            ClipData.Item it = mData.get(i);
//            mPiePaint.setShader(it.mShader);
//            canvas.drawArc(mBounds,
//                    360 - it.mEndAngle,
//                    it.mEndAngle - it.mStartAngle,
//                    true, mPiePaint);
//        }

        // Draw the pointer
//        canvas.drawLine(mTextX, mPointerY, mPointerX, mPointerY, mTextPaint);
        int parentWidth= context.getResources().getDisplayMetrics().widthPixels-10;
        int parentHeight= context.getResources().getDisplayMetrics().heightPixels-10;
        int width = 100;
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


        int height = 100;
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
        @SuppressLint("DrawAllocation") RectF rectF = new RectF(getPadding(),getPadding(),width-getPadding(),height-getPadding());
        Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int startValue = 6;
        if (hours>=startValue && hours<=startValue+12) {
            hours = hours - startValue;
            int value = 90-(hours*15);
            canvas.drawArc(rectF, 90 + value, 180 - (value * 2), false, p);
        }
    }
    int widthMeasureSpec, heightMeasureSpec;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(LOG_TAG, String.valueOf(widthMeasureSpec));
        Log.d(LOG_TAG, String.valueOf(heightMeasureSpec));
        this.widthMeasureSpec = widthMeasureSpec;
        this.heightMeasureSpec = heightMeasureSpec;
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public int getPadding() {
        return 12;
    }
}
