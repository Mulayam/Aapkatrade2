package com.example.pat.aapkatrade.categories_tab;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.productdetail.*;
import com.mikepenz.iconics.utils.Utils;

import java.util.ArrayList;

import it.carlom.stikkyheader.core.StikkyHeaderBuilder;
import it.carlom.stikkyheader.core.animator.AnimatorBuilder;
import it.carlom.stikkyheader.core.animator.HeaderStikkyAnimator;


public class CategoryListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    CategoriesListAdapter categoriesListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<CategoriesListData> productListDatas = new ArrayList<>();
    ImageView imageView;
    Toolbar toolbar;
    boolean wrapInScrollView = true;
    Context context;
    LinearLayout linearLayout;
    private String[] spQuantity = {"-Please Select-", "Licence", "Personal"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);


        setup_data();

        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.product_list_recycler_view);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);

        categoriesListAdapter = new CategoriesListAdapter(getActivity(), productListDatas);

        mRecyclerView.setAdapter(categoriesListAdapter);

        return view;


    }

    // mRecyclerView.setNestedScrollingEnabled(false);


       /* mRecyclerView.setHasFixedSize(true);

        StikkyHeaderBuilder.stickTo(mRecyclerView)

                .setHeader(R.id.header,linearLayout)
                .minHeightHeaderDim(R.dimen.min_height_header)
                .build();
       */


    private void setup_data() {
        productListDatas.clear();
        try {
            productListDatas.add(new CategoriesListData("", "", ""));
            productListDatas.add(new CategoriesListData("", "", ""));
            productListDatas.add(new CategoriesListData("", "", ""));
            productListDatas.add(new CategoriesListData("", "", ""));
            productListDatas.add(new CategoriesListData("", "", ""));
            productListDatas.add(new CategoriesListData("", "", ""));
            productListDatas.add(new CategoriesListData("", "", ""));
            productListDatas.add(new CategoriesListData("", "", ""));

        } catch (Exception e) {

        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        StikkyHeaderBuilder.stickTo(mRecyclerView)
                .setHeader(R.id.header, (ViewGroup) getView())
                .minHeightHeader(250)
                .build();

        com.example.pat.aapkatrade.utils.Utils.populateRecyclerView(mRecyclerView);
    }


    public class IconAnimator extends HeaderStikkyAnimator {

        @Override
        public AnimatorBuilder getAnimatorBuilder() {

            View viewToAnimate = getHeader().findViewById(R.id.icon);
            Point point = new Point(50, 100); // translate to the point with coordinate (50,100);
            float scaleX = 0.5f;//scale to the 50%
            float scaleY = 0.5f; //scale to the 50%
            float fade = 0.2f;// 20% fade

            AnimatorBuilder animatorBuilder = AnimatorBuilder.create()
                    .applyScale(viewToAnimate, scaleX, scaleY)
                    .applyTranslation(viewToAnimate, point)
                    .applyFade(viewToAnimate, fade);

            return animatorBuilder;
        }
    }


}
