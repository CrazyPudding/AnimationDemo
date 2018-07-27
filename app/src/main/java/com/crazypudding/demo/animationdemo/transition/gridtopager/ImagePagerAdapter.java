package com.crazypudding.demo.animationdemo.transition.gridtopager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import static com.crazypudding.demo.animationdemo.transition.gridtopager.ImageData.IMAGE_DRAWABLES;

public class ImagePagerAdapter extends FragmentStatePagerAdapter {

    public ImagePagerAdapter(Fragment fragment) {
        super(fragment.getChildFragmentManager());
    }

    @Override
    public Fragment getItem(int position) {
        return ImageFragment.newInstance(IMAGE_DRAWABLES[position]);
    }

    @Override
    public int getCount() {
        return IMAGE_DRAWABLES.length;
    }
}
