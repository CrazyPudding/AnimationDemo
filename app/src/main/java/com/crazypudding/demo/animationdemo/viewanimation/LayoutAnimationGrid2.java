package com.crazypudding.demo.animationdemo.viewanimation;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.crazypudding.demo.animationdemo.R;

import java.util.ArrayList;
import java.util.List;

public class LayoutAnimationGrid2 extends AppCompatActivity {
    private List<ResolveInfo> mAppIcons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadAppIcons();

        setContentView(R.layout.activity_layout_animation_grid);

        GridLayoutAnimationController controller = new GridLayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.wave_scale));
        controller.setRowDelay(0.75f);
        controller.setColumnDelay(0.0f);
        controller.setDirectionPriority(GridLayoutAnimationController.PRIORITY_NONE);

        GridView gridView = findViewById(R.id.grid);
        gridView.setLayoutAnimation(controller);
        gridView.setAdapter(new AppsAdapter());
    }

    private void loadAppIcons() {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        mAppIcons = getPackageManager().queryIntentActivities(intent, 0);
    }

    class AppsAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return Math.min(32, mAppIcons.size());
        }

        @Override
        public Object getItem(int position) {
            return mAppIcons.get(position % mAppIcons.size());
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ResolveInfo resolveInfo = mAppIcons.get(position % mAppIcons.size());
            ImageView img = new ImageView(LayoutAnimationGrid2.this);
            img.setImageDrawable(resolveInfo.activityInfo.loadIcon(getPackageManager()));
            img.setScaleType(ImageView.ScaleType.FIT_CENTER);
            int w = (int) (36 * getResources().getDisplayMetrics().density + 0.5f);
            img.setLayoutParams(new GridView.LayoutParams(w, w));
            return img;
        }
    }
}
