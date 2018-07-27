package com.crazypudding.demo.animationdemo.transition.gridtopager;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.crazypudding.demo.animationdemo.R;

public class ImageFragment extends Fragment {
    private static final String KEY_IMG_RES = "com.crazypudding.demo.animationdemo.transition.key.img";

    public static Fragment newInstance(@DrawableRes int drawableRes) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_IMG_RES, drawableRes);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ImageView iv = view.findViewById(R.id.image_view);
        Bundle arguments = getArguments();
        int drawableRes = arguments.getInt(KEY_IMG_RES);

        iv.setTransitionName(String.valueOf(drawableRes));

        Glide.with(this)
                .load(drawableRes)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (getParentFragment() == null) {
                            return false;
                        }
                        getParentFragment().startPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (getParentFragment() == null) {
                            return false;
                        }
                        getParentFragment().startPostponedEnterTransition();
                        return false;
                    }
                })
                .into(iv);
        return view;
    }

}
