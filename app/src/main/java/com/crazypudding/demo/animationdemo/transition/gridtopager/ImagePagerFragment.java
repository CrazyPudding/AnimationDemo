package com.crazypudding.demo.animationdemo.transition.gridtopager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crazypudding.demo.animationdemo.R;

import java.util.List;
import java.util.Map;

public class ImagePagerFragment extends Fragment {
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewPager = (ViewPager) inflater.inflate(R.layout.fragment_image_pager, container, false);

        viewPager.setAdapter(new ImagePagerAdapter(this));
        viewPager.setCurrentItem(GridToPagerActivity.currentPosition);
        GridToPagerActivity.shouldScrollToPos = true;
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                GridToPagerActivity.shouldScrollToPos = true;
                GridToPagerActivity.currentPosition = position;
            }
        });

        prepaerTransitions();

        if (savedInstanceState == null) {
            postponeEnterTransition();
        }

        return viewPager;
    }

    private void prepaerTransitions() {
        Transition transition = TransitionInflater.from(getContext()).inflateTransition(R.transition.image_shared_element_transition);
        setSharedElementEnterTransition(transition);

        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                Fragment currentFragment = (Fragment) viewPager.getAdapter().instantiateItem(viewPager, GridToPagerActivity.currentPosition);
                View view = currentFragment.getView();
                if (view == null) {
                    return;
                }

                sharedElements.put(names.get(0), view.findViewById(R.id.image_view));
            }
        });
    }
}
