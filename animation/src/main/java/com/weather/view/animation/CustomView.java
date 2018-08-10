package com.weather.view.animation;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

public class CustomView extends FrameLayout {
    ImageView [] clouds = new ImageView[9];
    ImageView sumOrMoon, clearMoon;
    FrameLayout backgroud;
    AnimationSet animationSetSunOrMoon = new AnimationSet(false);
    private Random r = new Random(123456789L);
    private int viewWidth;
    private int viewHeight;
    int amount=1000, size=0, speed=0;
    private int weathericon = 1;
    public CustomView(Context context,int weather) {
        super(context);
        weathericon = weather;
        init();
    }
    View v;
    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyCustomElement, 0, 0);
        try {
            weathericon = (int) ta.getInt(R.styleable.MyCustomElement_weatherIcon, -1);
            if (weathericon!=-1){
                init();
            }
        } finally {
            ta.recycle();
        }

    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    // 11
    private void init() {
        v = inflate(getContext(), R.layout.custom_view, this);

        findViews(v);
        for(int i=0;i<9;i++){
            clouds[i].setVisibility(VISIBLE);
        }
        sumOrMoon.setVisibility(VISIBLE);
        backgroud.setBackgroundColor(getResources().getColor(R.color.background_light));
        animateCloudAndSun();
        switch (weathericon){
            case 1:  //Sunny
                hideClouds();
                break;
            case 2:  //Sunny
                hideClouds();
                break;
            case 3: // Partly Cloudy
                showLessCloud();
                break;
            case 4:  // Mostly Cloudy and sun
                break;
            case 5:
                showLessCloud();
                amount = 5;
                makeWind();
                break;
            case 6:
                break;
            case 7: // Mostly Cloud, no sun
                sumOrMoon.setVisibility(GONE);
                break;
            case 8: //Dark cloud and no sun
                backgroud.setBackgroundColor(getResources().getColor(R.color.background_dark));
                sumOrMoon.setVisibility(GONE);
                darkClouds();
                break;
            case 11: //Fog
                hideClouds();
                break;
            case 12://Shower
                sumOrMoon.setVisibility(GONE);
                amount = 800;
                makeRain(Animation.INFINITE);
                break;
            case 13://Mostly cloudy and shower
                displaySunOrMoonThenRain(2000,700);
                break;
            case 14://Partly sunny and shower
                showLessCloud();
                displaySunOrMoonThenRain(4000,500);
                break;
            case 15://Storm
                backgroud.setBackgroundColor(getResources().getColor(R.color.background_dark));
                sumOrMoon.setVisibility(GONE);
                darkClouds();
                amount = 1200;
                makeRain(Animation.INFINITE);
                makeFlashes(getResources().getColor(R.color.background_dark));
                break;
            case 16://Mostly cloudy and storm
                displaySunOrMoonThenStorm(2000,700,getResources().getColor(R.color.background_light));
                break;
            case 17://Partly sunny and storm
                showLessCloud();
                displaySunOrMoonThenStorm(4000,500,getResources().getColor(R.color.background_light));
                break;
            case 18://Rain
                sumOrMoon.setVisibility(GONE);
                darkClouds();
                amount = 1500;
                makeRain(Animation.INFINITE);
                break;
            case 19://Snow
                amount = 700;
                sumOrMoon.setVisibility(GONE);
                makeSnow(Animation.INFINITE);
                break;
            case 22://Snow
                amount = 700;
                sumOrMoon.setVisibility(GONE);
                makeSnow(Animation.INFINITE);
                break;
            case 23://Mostly Cloudy and Snow
                displaySunOrMoonThenSnow(4000,700);
                break;
            case 20 ://Mostly Cloudy and Snow
                displaySunOrMoonThenSnow(4000,700);
                break;
            case 21://Mostly Cloudy and Snow
                displaySunOrMoonThenSnow(4000,700);
                break;
            case 26: // freezing rain
                sumOrMoon.setVisibility(GONE);
                amount = 1200;
                makeRain(Animation.INFINITE);
                break;
            case 25://Rain and snow
                sumOrMoon.setVisibility(GONE);
                amount = 500;
            case 29://Rain and snow
                sumOrMoon.setVisibility(GONE);
                amount = 500;
                makeSnow(Animation.INFINITE);
                amount = 300;
                makeRain(Animation.INFINITE);
                break;
            case 30:// Hot
                hideClouds();
                break;
            case 31: // Cold
                amount = 15;
                makeWind();
                amount = 300;
                makeRain(5);
                break;
            case 32://Wind
                amount = 50;
                makeWind();
                break;
            default:
                backgroud.setBackgroundColor( getResources().getColor(R.color.background_dark));
                sumOrMoon.setImageResource(R.drawable.ic_moon_circle);
                darkClouds();
                break;
        }
        switch (weathericon) {
            case 33: //Clear Night
                hideClouds();
                sumOrMoon.setVisibility(GONE);
                clearMoon.setVisibility(VISIBLE);
                clearMoonAnimation();
                break;
            case 34: //Clear Night
                hideClouds();
                sumOrMoon.setVisibility(GONE);
                clearMoon.setVisibility(VISIBLE);
                clearMoonAnimation();
                break;
            case 35:  // Partly Cloudy
                showLessCloud();
                break;
            case 36: // Partly Cloudy
                showLessCloud();
                break;
            case 37:
                showLessCloud();
                amount = 5;
                makeWind();
                break;
            case 38: // Mostly Cloudy and sun
                break;
            case 39: // Partly Cloudy and shower
                showLessCloud();
                displaySunOrMoonThenRain(3000,500);
                break;
            case 40: // Mostly Cloud and shower
                displaySunOrMoonThenRain(2000,700);
                break;
            case 41: // Partly Cloudy and storm
                showLessCloud();
                displaySunOrMoonThenStorm(3000,500,getResources().getColor(R.color.background_dark));
                break;
            case 42: // Mostly Cloud and storm
                displaySunOrMoonThenStorm(2000,700,getResources().getColor(R.color.background_dark));
                break;
            case 44://  Mostly Cloud and Snow
                displaySunOrMoonThenSnow(2000,500);
                break;
            case 43://  Mostly Cloud and Snow
                displaySunOrMoonThenSnow(2000,500);
                break;
        }
    }

    private void hideClouds() {
        for(int i=0;i<9;i++){
            clouds[i].setVisibility(GONE);
        }
    }

    private void showLessCloud() {
        for(int i=6;i<9;i++){
            clouds[i].setVisibility(GONE);
        }
    }

    private void displaySunOrMoonThenRain(final int fadeOutTime,final int amountOfRain) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(fadeOutTime);
        fadeOut.setRepeatMode(Animation.RESTART);
        fadeOut.setRepeatCount(1);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                sumOrMoon.setVisibility(GONE);
                amount = amountOfRain;
                makeRain(Animation.INFINITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animationSetSunOrMoon.addAnimation(fadeOut);
    }
    private void displaySunOrMoonThenStorm(final int fadeOutTime, final int amountOfRain, final int color) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(fadeOutTime);
        fadeOut.setRepeatMode(Animation.RESTART);
        fadeOut.setRepeatCount(1);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                sumOrMoon.setVisibility(GONE);
                amount = amountOfRain;
                makeRain(Animation.INFINITE);
                makeFlashes(color);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animationSetSunOrMoon.addAnimation(fadeOut);
    }
    private void displaySunOrMoonThenSnow(final int fadeOutTime, final int amountOfRain) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(fadeOutTime);
        fadeOut.setRepeatMode(Animation.RESTART);
        fadeOut.setRepeatCount(1);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                sumOrMoon.setVisibility(GONE);
                amount = amountOfRain;
                makeSnow(Animation.INFINITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animationSetSunOrMoon.addAnimation(fadeOut);
    }
    private void darkClouds() {
        for(int i=0;i<9;i++){
            if (clouds[i].getTag().toString().equals("1"))
                clouds[i].setImageResource(R.drawable.ic_cloud_circle_night_big_light);
            else
                clouds[i].setImageResource(R.drawable.ic_cloud_circle_night_big_dark);
            if (i==0||i==2||i==8){
                if (clouds[i].getTag().toString().equals("1"))
                    clouds[i].setImageResource(R.drawable.ic_cloud_circle_night_light);
                else
                    clouds[i].setImageResource(R.drawable.ic_cloud_circle_night_dark);
            }
        }
    }
    private void animateCloudAndSun() {
        TranslateAnimation mAnimation1 = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.02f);
        mAnimation1.setDuration(800);
        mAnimation1.setRepeatCount(-1);
        mAnimation1.setRepeatMode(Animation.REVERSE);
        mAnimation1.setInterpolator(new LinearInterpolator());

        TranslateAnimation mAnimation2 = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.02f);
        mAnimation2.setDuration(1000);
        mAnimation2.setRepeatCount(-1);
        mAnimation2.setRepeatMode(Animation.REVERSE);
        mAnimation2.setInterpolator(new LinearInterpolator());



        TranslateAnimation mAnimation3 = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.02f);
        mAnimation2.setDuration(1300);
        mAnimation2.setRepeatCount(-1);
        mAnimation2.setRepeatMode(Animation.REVERSE);
        mAnimation2.setInterpolator(new LinearInterpolator());


        for(int i=0;i<3;i++){
            clouds[i].setAnimation(mAnimation1);
        }
        for(int i=3;i<6;i++){
            clouds[i].setAnimation(mAnimation2);
        }
        for(int i=6;i<9;i++){
            clouds[i].setAnimation(mAnimation3);
        }
        animationSetSunOrMoon.reset();
        Animation rotateAnimation = new RotateAnimation(0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotateAnimation.setDuration(2500);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
        animationSetSunOrMoon.addAnimation(rotateAnimation);
        sumOrMoon.startAnimation(animationSetSunOrMoon);
    }
    private void findViews(View v) {
        this.clouds[0] = v.findViewById(R.id.iv_cloud1);
        this.clouds[1] = v.findViewById(R.id.iv_cloud2);
        this.clouds[2] = v.findViewById(R.id.iv_cloud3);
        this.clouds[3] = v.findViewById(R.id.iv_cloud4);
        this.clouds[4] = v.findViewById(R.id.iv_cloud5);
        this.clouds[5] = v.findViewById(R.id.iv_cloud6);
        this.clouds[6] = v.findViewById(R.id.iv_cloud7);
        this.clouds[7] = v.findViewById(R.id.iv_cloud8);
        this.clouds[8] = v.findViewById(R.id.iv_cloud9);
        this.sumOrMoon = v.findViewById(R.id.iv_sun);
        this.clearMoon = v.findViewById(R.id.iv_clear_moon);
        this.backgroud = v.findViewById(R.id.rl_background);

    }
    public void clearMoonAnimation(){
        Animation rotateAnimation = new RotateAnimation(0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotateAnimation.setDuration(25000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setRepeatMode(Animation.RESTART);

        clearMoon.setAnimation(rotateAnimation);
    }
    public void makeWind(){
        final int height =  Resources.getSystem().getDisplayMetrics().heightPixels;
        int unit=1;
        for ( int i =0;i<amount;i++){
            float yOffsetVal = r.nextFloat()*(1f - -1f)+-1f;
            Log.d("Tested", String.valueOf(yOffsetVal));
            TranslateAnimation translateAnimation = new TranslateAnimation(
                    TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                    TranslateAnimation.RELATIVE_TO_PARENT, 1f,
                    TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                    TranslateAnimation.RELATIVE_TO_PARENT, yOffsetVal);
            translateAnimation.setDuration(2300+unit);
            translateAnimation.setRepeatCount(-1);
            translateAnimation.setRepeatMode(Animation.RESTART);
            translateAnimation.setInterpolator(new LinearInterpolator());
            final int startingYVal = r.nextInt(height)+1;
            final ImageView imageView = new ImageView(this.getContext());
            int rand = r.nextInt(2-0)+1;
            switch (rand){
                case 1:
                    imageView.setImageResource(R.drawable.ic_leaf);
                   break;
                case 2:
                    imageView.setImageResource(R.drawable.ic_leaf_m);


            }
            imageView.setX(0);
            imageView.setY(startingYVal);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50);
            imageView.setLayoutParams(layoutParams);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    int i1 = r.nextInt(height)+1;
                    imageView.setX(0);
                    imageView.setY(i1);
                }
            });
            imageView.startAnimation(translateAnimation);
            this.addView(imageView);
            unit=unit+50;
        }
    }
    public void makeRain(int repeatCount){
        int width =  Resources.getSystem().getDisplayMetrics().widthPixels;
        int height =  Resources.getSystem().getDisplayMetrics().heightPixels;
        int unit=1;
        for ( int i =0;i<amount;i++){
            Animation fadeOut = new AlphaAnimation(1, 0);
            fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
            fadeOut.setStartOffset(1000);
            fadeOut.setDuration(1000+unit);
            fadeOut.setRepeatMode(Animation.RESTART);
            fadeOut.setRepeatCount(repeatCount);
            AnimationSet animation = new AnimationSet(false);//change to false
            animation.addAnimation(fadeOut);
            TranslateAnimation mAnimation2 = new TranslateAnimation(
                    TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                    TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                    TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                    TranslateAnimation.RELATIVE_TO_PARENT, 1f);
            mAnimation2.setDuration(2300+unit);
            mAnimation2.setRepeatCount(-1);
            mAnimation2.setRepeatMode(Animation.RESTART);
            mAnimation2.setInterpolator(new LinearInterpolator());
            animation.addAnimation(mAnimation2);
            int i1 = r.nextInt(width - 0 + 1)+0;
            int i2 = r.nextInt(height - 0 + 1);
            ImageView imageView = new ImageView(this.getContext());
            imageView.setImageResource(R.drawable.ic_drop);
            imageView.setX(i1);

            imageView.setY(i2);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(25, 50);
            imageView.setLayoutParams(layoutParams);
            imageView.setAnimation(animation);
            this.addView(imageView);
            unit=unit+50;
        }

    }
    public void makeSnow(int repeatCount){
        int width =  Resources.getSystem().getDisplayMetrics().widthPixels;
        int height =  Resources.getSystem().getDisplayMetrics().heightPixels;
        int unit=1;
        for ( int i =0;i<amount;i++){
            Animation fadeOut = new AlphaAnimation(1, 0);
            fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
            fadeOut.setStartOffset(1000);
            fadeOut.setDuration(1000+unit);
            fadeOut.setRepeatMode(Animation.RESTART);
            fadeOut.setRepeatCount(repeatCount);
            AnimationSet animation = new AnimationSet(false);//change to false
            animation.addAnimation(fadeOut);
            TranslateAnimation mAnimation2 = new TranslateAnimation(
                    TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                    TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                    TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                    TranslateAnimation.RELATIVE_TO_PARENT, 1f);
            mAnimation2.setDuration(2300+unit);
            mAnimation2.setRepeatCount(-1);
            mAnimation2.setRepeatMode(Animation.RESTART);
            mAnimation2.setInterpolator(new LinearInterpolator());
            animation.addAnimation(mAnimation2);
            int i1 = r.nextInt(width - 0 + 1)+0;
            int i2 = r.nextInt(height - 0 + 1);
            ImageView imageView = new ImageView(this.getContext());
            imageView.setImageResource(R.drawable.ic_snow);
            imageView.setX(i1);

            imageView.setY(i2);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(15, 30);
            imageView.setLayoutParams(layoutParams);
            imageView.setAnimation(animation);
            this.addView(imageView);
            unit=unit+50;
        }

    }
    public void makeFlashes(int backgroundColor){
        int count=2;
        final ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(backgroud,
                "backgroundColor",
                new ArgbEvaluator(),backgroundColor,
                0xffffffff);
        backgroundColorAnimator.setDuration(300);
        backgroundColorAnimator.setRepeatMode(ValueAnimator.RESTART);
        backgroundColorAnimator.setStartDelay(1000);
        backgroundColorAnimator.setRepeatCount(ValueAnimator.INFINITE);
        backgroundColorAnimator.addListener(new Animator.AnimatorListener() {
            int count=0;
            int flashLimt=5;
            int limit= r.nextInt(flashLimt - 0 + 1)+1;
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {



            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                count=count+1;


                if(count>=limit){
                    backgroundColorAnimator.setStartDelay(5000);

                    count=0;
                    limit= r.nextInt(flashLimt - 0 + 1)+1;

                }

                backgroundColorAnimator.start();
            }
        });
        backgroundColorAnimator.start();


    }
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        viewWidth = xNew;
        viewHeight = yNew;
    }
    public void setWeatherIcon(int weatherIcon) {
        weathericon = weatherIcon;
        Log.d("Tested No", String.valueOf(weathericon));
        init();
    }
}
