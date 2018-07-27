package com.crazypudding.demo.animationdemo.transition.gridtopager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.crazypudding.demo.animationdemo.R;

public class GridToPagerActivity extends AppCompatActivity {
    private static final String KEY_POSITION = "com.crazypudding.demo.animationdemo.transition.position";
    private static final String KEY_SCROLL="shouldScroll";
    public static int currentPosition;
    public static boolean shouldScrollToPos = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_to_pager);

        if (savedInstanceState != null) {
            shouldScrollToPos = savedInstanceState.getBoolean(KEY_SCROLL);
            currentPosition = savedInstanceState.getInt(KEY_POSITION, 0);
            return;
        } else {
            shouldScrollToPos = true;
            currentPosition = 0;
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new GridFragment(), GridFragment.class.getSimpleName())
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_POSITION, currentPosition);
        outState.putBoolean(KEY_SCROLL, shouldScrollToPos);
    }
}
