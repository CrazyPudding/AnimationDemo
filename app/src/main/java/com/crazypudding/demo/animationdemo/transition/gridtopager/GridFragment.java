package com.crazypudding.demo.animationdemo.transition.gridtopager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crazypudding.demo.animationdemo.R;

import java.util.List;
import java.util.Map;

import static com.crazypudding.demo.animationdemo.transition.gridtopager.GridToPagerActivity.shouldScrollToPos;

public class GridFragment extends Fragment {
    private RecyclerView mRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_grid, container, false);
        mRecyclerView.setAdapter(new GridRvAdapter(this));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    shouldScrollToPos = false;
                }
            }
        });
        mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (!shouldScrollToPos) {
                    return;
                }
                mRecyclerView.removeOnLayoutChangeListener(this);
                final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
                View viewAtPosition = layoutManager.findViewByPosition(GridToPagerActivity.currentPosition);
                if (viewAtPosition == null || layoutManager.isViewPartiallyVisible(viewAtPosition, false, true)) {
                    mRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            layoutManager.scrollToPosition(GridToPagerActivity.currentPosition);
                            shouldScrollToPos = true;
                        }
                    });
                }
            }
        });

        prepareTransitions();
        postponeEnterTransition();
        return mRecyclerView;
    }

    private void prepareTransitions() {
        Transition exitTransition = TransitionInflater.from(getContext()).inflateTransition(R.transition.grid_exit_transition);
        setExitTransition(exitTransition);

        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                RecyclerView.ViewHolder selectedViewHolder = mRecyclerView.findViewHolderForAdapterPosition(GridToPagerActivity.currentPosition);
                if (selectedViewHolder == null || selectedViewHolder.itemView == null) {
                    return;
                }

                sharedElements.put(names.get(0), selectedViewHolder.itemView.findViewById(R.id.card_image));
            }
        });
    }
}
