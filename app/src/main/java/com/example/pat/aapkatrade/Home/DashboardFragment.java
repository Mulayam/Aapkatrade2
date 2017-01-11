package com.example.pat.aapkatrade.Home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pat.aapkatrade.R;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements View.OnClickListener {

    Context context;
    private View upAllSale;
    ScrollView scrollView;
    private RecyclerView recyclerViewAllSale, recyclerViewTrendingStyle, recyclerViewEclipseCollection, recyclerViewExpressDeal, recyclerViewBestSelling;
    private LinearLayoutManager llManagerAllSale, llManagerTrendingStyle, llManagerEclipseCollection, llManagerExpressDeal, llManagerBestSelling;
    ArrayList<CommomData> commomDatas = new ArrayList<>();
    private CommomAdapter commomAdapter;
    //private StikkyHeaderBuilder stikkyHeader;
    private Intent intent;
    AppCompatButton discover_category;
    TextView viewall_expressdeals,viewall_bestselling,viewall_expresscollection,viewall_trendingstyles,viewall_allsale;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard_new, container, false);
        context = getActivity();
        //llManagerAllSale,llManagerTrendingStyle,llManagerEclipseCollection,llManagerExpressDeal,llManagerBestSelling;
        llManagerAllSale = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        llManagerTrendingStyle = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        llManagerEclipseCollection = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        llManagerExpressDeal = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        llManagerBestSelling = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        commomAdapter = new CommomAdapter(context, commomDatas);
        setupDummyData();
        initializeview(view);
        setupView(view);
        setuprecyclers(view);
      //  GridLayoutManager gridLayoutManager=new GridLayoutManager(context,3);
        return view;
    }

    private void initializeview(View v) {
        discover_category=(AppCompatButton)v.findViewById(R.id.buttonDiscover);
        viewall_expressdeals=(TextView)v.findViewById(R.id.textView65);
        viewall_bestselling=(TextView)v.findViewById(R.id.textView66);
        viewall_expresscollection=(TextView)v.findViewById(R.id.textView67);
        viewall_trendingstyles=(TextView)v.findViewById(R.id.textView68);
        viewall_allsale=(TextView)v.findViewById(R.id.textView69);
        viewall_expressdeals.setOnClickListener(this);
        viewall_bestselling.setOnClickListener(this);
        viewall_expresscollection.setOnClickListener(this);
        viewall_trendingstyles.setOnClickListener(this);
        viewall_allsale.setOnClickListener(this);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
//        stikkyHeader = StikkyHeaderBuilder.stickTo(scrollView);
//        stikkyHeader.setHeader(R.id.header, (ViewGroup) getView())
//                .minHeightHeaderDim(R.dimen.min_height_header)
//                .animator(new ParallaxStikkyAnimator())
//                .build();
    }
    private void setupDummyData() {
        for (int i = 0; i < 20; i++) {
            commomDatas.add(new CommomData("", "", "", ""));
        }
    }

    private void setuprecyclers(View view) {
        setupExpressDeal(view);
        setupBestSelling(view);
        setupEclipseCollection(view);
        setupTrendingStyles(view);
        setupAllSale(view);
    }

    private void setupAllSale(View view) {
        recyclerViewAllSale = (RecyclerView) view.findViewById(R.id.recyclerAllSale);
        recyclerViewAllSale.setLayoutManager(llManagerAllSale);
        recyclerViewAllSale.setAdapter(commomAdapter);

    }

    private void setupTrendingStyles(View view) {
        recyclerViewTrendingStyle = (RecyclerView) view.findViewById(R.id.recyclerTrendingStyle);
        recyclerViewTrendingStyle.setLayoutManager(llManagerTrendingStyle);
        recyclerViewTrendingStyle.setAdapter(commomAdapter);
    }

    private void setupEclipseCollection(View view) {
        recyclerViewEclipseCollection = (RecyclerView) view.findViewById(R.id.recyclerEclipseExpressCollection);
        recyclerViewEclipseCollection.setLayoutManager(llManagerEclipseCollection);
        recyclerViewEclipseCollection.setAdapter(commomAdapter);
    }

    private void setupBestSelling(View view) {
        recyclerViewBestSelling = (RecyclerView) view.findViewById(R.id.recyclerBestSelling);
        recyclerViewBestSelling.setLayoutManager(llManagerBestSelling);
        recyclerViewBestSelling.setAdapter(commomAdapter);
    }

    private void setupExpressDeal(View view) {
        recyclerViewExpressDeal = (RecyclerView) view.findViewById(R.id.recyclerExpressDeal);
        recyclerViewExpressDeal.setLayoutManager(llManagerExpressDeal);
        recyclerViewExpressDeal.setAdapter(commomAdapter);
    }

    private void setupView(View view) {
        scrollView= (ScrollView) view.findViewById(R.id.scrollView);
        view.findViewById(R.id.relativeLayoutSearch).setOnClickListener(this);
        view.findViewById(R.id.buttonDiscover).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.buttonDiscover:
//                showMessage("Clicked");
//                intent=new Intent(context, DiscoverActivity.class);
//                startActivity(intent);
//                ((AppCompatActivity)context).overridePendingTransition(R.anim.enter, R.anim.exit);
//                break;
//
//            case R.id.relativeLayoutSearch:
//                intent=new Intent(context, SearchActivity.class);
//                startActivity(intent);
//                ((AppCompatActivity)context).overridePendingTransition(R.anim.enter, R.anim.exit);
//                break;
//            case R.id.textView65:
//                Intent intent = new Intent(context, CategoryActivity.class);
//                getActivity().startActivity(intent);
//                ((AppCompatActivity)context).overridePendingTransition(R.anim.enter, R.anim.exit);
//                break;
//            case R.id.textView66:
//                Intent intent2 = new Intent(context, CategoryActivity.class);
//                getActivity().startActivity(intent2);
//                ((AppCompatActivity)context).overridePendingTransition(R.anim.enter, R.anim.exit);
//                break;
//            case R.id.textView67:
//                Intent intent3 = new Intent(context, CategoryActivity.class);
//                getActivity().startActivity(intent3);
//                ((AppCompatActivity)context).overridePendingTransition(R.anim.enter, R.anim.exit);
//                break;
//            case R.id.textView68:
//                Intent intent4 = new Intent(context, CategoryActivity.class);
//                getActivity().startActivity(intent4);
//                ((AppCompatActivity)context).overridePendingTransition(R.anim.enter, R.anim.exit);
//                break;
//            case R.id.textView69:
//                Intent intent5 = new Intent(context, CategoryActivity.class);
//                getActivity().startActivity(intent5);
//                ((AppCompatActivity)context).overridePendingTransition(R.anim.enter, R.anim.exit);
//                break;




        }
    }

    private void showMessage(String clicked) {
        Toast.makeText(context, clicked, Toast.LENGTH_SHORT).show();
    }


}
