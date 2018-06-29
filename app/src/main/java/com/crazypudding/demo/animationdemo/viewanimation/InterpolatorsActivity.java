package com.crazypudding.demo.animationdemo.viewanimation;

import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.PathInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.crazypudding.demo.animationdemo.R;

public class InterpolatorsActivity extends AppCompatActivity {

    private static final String[] INTERPOLATORS = {
            "Accelerate", "Decelerate", "Accelerate/Decelerate",
            "Anticipate", "Overshoot", "Anticipate/Overshoot",
            "Bounce", "CycleInterpolator", "LinearInterpolator", "PathInterpolator"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intepolators);

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, INTERPOLATORS));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView target = findViewById(R.id.target);
                View targetParent = (View) target.getParent();
                Animation a = new TranslateAnimation(0.0f,
                        targetParent.getWidth() - target.getWidth() - targetParent.getPaddingLeft() - targetParent.getPaddingRight(), 0.0f, 0.0f);
                a.setDuration(1500);
                a.setRepeatCount(Animation.INFINITE);
                a.setRepeatMode(Animation.RESTART);
                a.setStartOffset(300);

                switch (position) {
                    case 0:
                        a.setInterpolator(AnimationUtils.loadInterpolator(getApplicationContext(), android.R.anim.accelerate_interpolator));
                        break;
                    case 1:
                        a.setInterpolator(AnimationUtils.loadInterpolator(getApplicationContext(), android.R.anim.decelerate_interpolator));
                        break;
                    case 2:
                        a.setInterpolator(AnimationUtils.loadInterpolator(getApplicationContext(), android.R.anim.accelerate_decelerate_interpolator));
                        break;
                    case 3:
                        a.setInterpolator(AnimationUtils.loadInterpolator(getApplicationContext(), android.R.anim.anticipate_interpolator));
                        break;
                    case 4:
                        a.setInterpolator(AnimationUtils.loadInterpolator(getApplicationContext(), android.R.anim.overshoot_interpolator));
                        break;
                    case 5:
                        a.setInterpolator(AnimationUtils.loadInterpolator(getApplicationContext(), android.R.anim.anticipate_overshoot_interpolator));
                        break;
                    case 6:
                        a.setInterpolator(AnimationUtils.loadInterpolator(getApplicationContext(), android.R.anim.bounce_interpolator));
                        break;
                    case 7:
                        a.setInterpolator(AnimationUtils.loadInterpolator(getApplicationContext(), android.R.anim.cycle_interpolator));
                        break;
                    case 8:
                        a.setInterpolator(AnimationUtils.loadInterpolator(getApplicationContext(), android.R.anim.linear_interpolator));
                        break;
                    case 9:
                        Path path = new Path();
                        path.lineTo(0.25f, 0.25f);
                        path.moveTo(0.25f, 0.5f);
                        path.lineTo(1f, 1f);
                        a.setInterpolator(new PathInterpolator(path));
                }

                target.startAnimation(a);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
