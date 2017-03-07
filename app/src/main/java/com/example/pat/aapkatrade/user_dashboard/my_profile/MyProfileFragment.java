package com.example.pat.aapkatrade.user_dashboard.my_profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;


/**
 * Created by PPC09 on 03-Mar-17.
 */

public class MyProfileFragment extends Fragment {

    public int stepNumber;
    public CardView step1aLayout, step1bLayout, step1cLayout, step2Layout, step3Layout;


    public static MyProfileFragment newInstance(int page, boolean isLast) {
        Bundle args = new Bundle();
        args.putInt("page", page);
        if (isLast)
            args.putBoolean("isLast", true);
        MyProfileFragment fragment = new MyProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Log.e("page", "init"+stepNumber);

        step1aLayout = (CardView) view.findViewById(R.id.step1aLayout);
        step1bLayout = (CardView) view.findViewById(R.id.step1bLayout);
        step1cLayout = (CardView) view.findViewById(R.id.step1cLayout);
        step2Layout = (CardView) view.findViewById(R.id.step2Layout);
        step3Layout = (CardView) view.findViewById(R.id.step3Layout);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final int page = getArguments().getInt("page", 50);
        stepNumber = page;

        Log.e("page", "page number"+stepNumber+(step1aLayout==null));
        if(stepNumber == 0){
            step1aLayout.setVisibility(View.VISIBLE);
            step1bLayout.setVisibility(View.VISIBLE);
            step1cLayout.setVisibility(View.VISIBLE);
            step2Layout.setVisibility(View.GONE);
            step3Layout.setVisibility(View.GONE);
        } else  if(stepNumber == 1){
            step1aLayout.setVisibility(View.GONE);
            step1bLayout.setVisibility(View.GONE);
            step1cLayout.setVisibility(View.GONE);
            step2Layout.setVisibility(View.VISIBLE);
            step3Layout.setVisibility(View.GONE);
        }else  if(stepNumber == 2){
            step1aLayout.setVisibility(View.GONE);
            step1bLayout.setVisibility(View.GONE);
            step1cLayout.setVisibility(View.GONE);
            step2Layout.setVisibility(View.GONE);
            step3Layout.setVisibility(View.VISIBLE);
        }
    }
}
