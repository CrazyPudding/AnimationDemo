package com.crazypudding.demo.animationdemo.viewanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.crazypudding.demo.animationdemo.R;

public class TranslateButton extends AppCompatActivity {

    private int mCount;
    private Button mBtnClick;
    private ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_animation);

        container = findViewById(R.id.container);

        final TextView tvCount = findViewById(R.id.tv_count);
        tvCount.setText(getString(R.string.string_click_count, 0));
        mBtnClick = findViewById(R.id.btn_click);
        mBtnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount++;
                tvCount.setText(getString(R.string.string_click_count, mCount));
            }
        });
    }

    public void startAnimation(View view) {
        Animation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0.5f,
                Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0.5f);
        animation.setDuration(600);
        animation.setFillAfter(true);

        // View 动画后改变对象的属性参数
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mBtnClick.clearAnimation();
                int translateX = container.getWidth() / 2;
                int translateY = container.getHeight() / 2;
                mBtnClick.setTranslationX(translateX);
                mBtnClick.setTranslationY(translateY);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mBtnClick.startAnimation(animation);
    }
}
