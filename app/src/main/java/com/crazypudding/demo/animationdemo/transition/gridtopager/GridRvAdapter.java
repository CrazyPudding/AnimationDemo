package com.crazypudding.demo.animationdemo.transition.gridtopager;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.crazypudding.demo.animationdemo.R;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.crazypudding.demo.animationdemo.transition.gridtopager.ImageData.IMAGE_DRAWABLES;

public class GridRvAdapter extends RecyclerView.Adapter<GridRvAdapter.ViewHolder> {

    private RequestManager mRequestManager;
    private Fragment fragment;

    public GridRvAdapter(Fragment fragment) {
        mRequestManager = Glide.with(fragment);
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        mRequestManager.load(IMAGE_DRAWABLES[position])
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (holder.getAdapterPosition() != GridToPagerActivity.currentPosition) {
                            return false;
                        }
                        if (new AtomicBoolean().getAndSet(true)) {
                            return false;
                        }
                        fragment.startPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (holder.getAdapterPosition() != GridToPagerActivity.currentPosition) {
                            return false;
                        }
                        if (new AtomicBoolean().getAndSet(true)) {
                            return false;
                        }
                        fragment.startPostponedEnterTransition();
                        return false;
                    }
                })
                .into(holder.imageView);
        holder.imageView.setTransitionName(String.valueOf(IMAGE_DRAWABLES[position]));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment.getFragmentManager() == null) {
                    return;
                }
                ((Transition) fragment.getExitTransition()).excludeTarget(holder.cardView, true);
                GridToPagerActivity.currentPosition = holder.getAdapterPosition();
                ImageView imageView = v.findViewById(R.id.card_image);
                fragment.getFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .addSharedElement(imageView, imageView.getTransitionName())
                        .replace(R.id.fragment_container, new ImagePagerFragment(), ImagePagerFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return IMAGE_DRAWABLES.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_view);
            imageView = itemView.findViewById(R.id.card_image);
        }
    }
}
