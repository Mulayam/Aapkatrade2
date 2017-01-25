package com.example.pat.aapkatrade.Home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.example.pat.aapkatrade.Home.banner_home.viewpageradapter_home;
import com.example.pat.aapkatrade.Home.latestproduct.latestproductadapter;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.user_dashboard.User_DashboardFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements View.OnClickListener {

    Context context;
    private View upAllSale;
    int currentPage=0;
    private com.example.pat.aapkatrade.Home.DashboardFragment homeFragment;
    LinearLayout viewpagerindicator;
    private RecyclerView recyclerViewAllSale, recyclerViewTrendingStyle, recyclerViewEclipseCollection, recyclerViewExpressDeal, recyclerViewBestSelling;
    private LinearLayoutManager llManagerAllSale, llManagerTrendingStyle, llManagerEclipseCollection, llManagerExpressDeal, llManagerBestSelling;
    ArrayList<CommomData> commomDatas = new ArrayList<>();
    ArrayList<CommomData> commomDatas_latestdeals = new ArrayList<>();
    ArrayList<CommomData> commomDatas_newarrival = new ArrayList<>();
    ArrayList<CommomData> commomDatas_hotdeals = new ArrayList<>();
    private CommomAdapter commomAdapter;
   public latestproductadapter latestproductadapter;
    int position=0;
    private int dotsCount;
    private int[] tabColors;
    private List<Integer> imageIdList;
    User_DashboardFragment user_dashboardFragment;
    private ImageView[] dots;
    private Handler mHandler;
    public static final int DELAY = 5000;
    AHBottomNavigationAdapter  bottom_menuAdapter;
    //private StikkyHeaderBuilder stikkyHeader;
    private Intent intent;
    AppCompatButton discover_category;
    TextView viewall_expressdeals,viewall_bestselling,viewall_expresscollection,viewall_trendingstyles,viewall_allsale;
    View v1,v2;
    ViewPager vp;
    ScrollView scrollView;
    AHBottomNavigation bottomNavigation;
    Timer banner_timer=new Timer();
    CoordinatorLayout coordinatorLayout;

    viewpageradapter_home viewpageradapter;
    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard_new, container, false);


        vp=(ViewPager)view.findViewById(R.id.viewpager_custom) ;
        context = getActivity();
        //llManagerAllSale,llManagerTrendingStyle,llManagerEclipseCollection,llManagerExpressDeal,llManagerBestSelling;
        llManagerAllSale = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        llManagerTrendingStyle = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        llManagerEclipseCollection = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        llManagerExpressDeal = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        llManagerBestSelling = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        viewpagerindicator=(LinearLayout)view.findViewById(R.id.viewpagerindicator);
        latestproductadapter=new latestproductadapter(context,commomDatas);

        initializeview(view);
        setupView(view);
        setupviewpager();
        setuprecyclers(view);

      //  GridLayoutManager gridLayoutManager=new GridLayoutManager(context,3);
        return view;
    }



    private void setupviewpager() {

        imageIdList = new ArrayList<>();
        imageIdList.add(R.drawable.banner_home);
        imageIdList.add(R.drawable.banner_home);
        imageIdList.add(R.drawable.banner_home);
        imageIdList.add(R.drawable.banner_home);






        viewpageradapter  = new viewpageradapter_home(getActivity(), null);
        vp.setAdapter(viewpageradapter);
        vp.setCurrentItem(currentPage);
        setUiPageViewController();

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == viewpageradapter.getCount() - 1) {
                    currentPage = 0;
                }
                vp.setCurrentItem(currentPage++, true);
            }
        };


        banner_timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 0, 3000);














        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                try {
                    for (int i = 0; i < dotsCount; i++) {
                        dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_item));
                    }

                    dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
                }
                catch (Exception e)
                {
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initializeview(View v) {


        v1=(View)v.findViewById(R.id.previous) ;
        v2=(View)v.findViewById(R.id.next) ;
       // discover_category=(AppCompatButton)v.findViewById(R.id.buttonDiscover);
       // viewall_expressdeals=(TextView)v.findViewById(R.id.textView65);
        viewall_bestselling=(TextView)v.findViewById(R.id.textView66);
        //viewall_expresscollection=(TextView)v.findViewById(R.id.textView67);
        viewall_trendingstyles=(TextView)v.findViewById(R.id.textView68);
        viewall_allsale=(TextView)v.findViewById(R.id.textView69);
//        viewall_expressdeals.setOnClickListener(this);
        viewall_bestselling.setOnClickListener(this);
//        viewall_expresscollection.setOnClickListener(this);
        viewall_trendingstyles.setOnClickListener(this);
        viewall_allsale.setOnClickListener(this);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=recyclerViewBestSelling.getChildCount();
                if(position!=0)
               position=position-1;
                Log.e("position previous",""+position);
                //llManagerBestSelling.scrollToPositionWithOffset(position,0);

                recyclerViewBestSelling.smoothScrollToPosition(position);
            }
        });
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position=position+1;
                //position=position-1;
               // llManagerBestSelling.scrollToPositionWithOffset(position,-0);


                recyclerViewBestSelling.smoothScrollToPosition(position);
                Log.e("position previous",""+position);
            }
        });

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


    private void setuprecyclers(View view) {





        //setupExpressDeal(view);
        setupBestSelling(view);
        setupEclipseCollection(view);
        setupTrendingStyles(view);
        setupAllSale(view);
    }

    private void setupAllSale(View view) {

        commomDatas_latestdeals.clear();
        for(int i=0;i<20;i++) {
            commomDatas_latestdeals.add(new CommomData("Latest Product", "Latest Deals", "", "http://administrator.aapkatrade.com/public/upload/220/nyc-pie-gurgaon-625_625x350_41460295362.jpg"));
        }
        commomAdapter = new CommomAdapter(context, commomDatas_latestdeals);
        recyclerViewAllSale = (RecyclerView) view.findViewById(R.id.recyclerAllSale);
        recyclerViewAllSale.setLayoutManager(llManagerAllSale);
        recyclerViewAllSale.setAdapter(commomAdapter);


    }

    private void setupTrendingStyles(View view) {



        commomDatas_hotdeals.clear();
        for(int i=0;i<20;i++) {
            commomDatas_hotdeals.add(new CommomData("Latest Product", "Latest Deals", "", "http://administrator.aapkatrade.com/public/upload/noimg.jpg"));

        }
        commomAdapter = new CommomAdapter(context, commomDatas_hotdeals);
        recyclerViewTrendingStyle = (RecyclerView) view.findViewById(R.id.recyclerTrendingStyle);
        recyclerViewTrendingStyle.setLayoutManager(llManagerTrendingStyle);
        recyclerViewTrendingStyle.setAdapter(commomAdapter);

    }

    private void setupEclipseCollection(View view) {

        commomDatas_newarrival.clear();
        for(int i=0;i<2;i++) {
            commomDatas_newarrival.add(new CommomData("Latest Product", "Latest Deals", "", "http://administrator.aapkatrade.com/public/upload/noimg.jpg"));
        }
        commomAdapter = new CommomAdapter(context, commomDatas_newarrival);
        recyclerViewEclipseCollection = (RecyclerView) view.findViewById(R.id.recyclerEclipseExpressCollection);
        recyclerViewEclipseCollection.setLayoutManager(llManagerEclipseCollection);
        recyclerViewEclipseCollection.setAdapter(commomAdapter);

    }

    private void setupBestSelling(View view) {

        commomDatas.clear();
        for(int i=0;i<20;i++) {
            commomDatas.add(new CommomData("Latest Product", "Latest Deals", "", "http://administrator.aapkatrade.com/public/upload/noimg.jpg"));
        }
        commomAdapter = new CommomAdapter(context, commomDatas);

        recyclerViewBestSelling = (RecyclerView) view.findViewById(R.id.recyclerBestSelling);
        recyclerViewBestSelling.setLayoutManager(llManagerBestSelling);
        recyclerViewBestSelling.setAdapter(latestproductadapter);
        recyclerViewBestSelling.getAdapter().notifyDataSetChanged();


    }






//    private void setupExpressDeal(View view) {
//        recyclerViewExpressDeal = (RecyclerView) view.findViewById(R.id.recyclerExpressDeal);
//        recyclerViewExpressDeal.setLayoutManager(llManagerExpressDeal);
//        recyclerViewExpressDeal.setAdapter(commomAdapter);
//    }

    private void setupView(View view) {
        scrollView= (ScrollView) view.findViewById(R.id.scrollView);
//        view.findViewById(R.id.relativeLayoutSearch).setOnClickListener(this);
//        view.findViewById(R.id.buttonDiscover).setOnClickListener(this);
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

    private void setUiPageViewController() {

        dotsCount = viewpageradapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_item));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            viewpagerindicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        banner_timer.cancel();
    }



    private void replaceFragment(Fragment newFragment, String tag)
    {

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.drawer_layout, newFragment, tag).addToBackStack(null);
        transaction.commit();
    }








}
