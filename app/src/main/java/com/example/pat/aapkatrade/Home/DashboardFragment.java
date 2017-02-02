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
import android.support.v7.widget.GridLayoutManager;
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
import com.example.pat.aapkatrade.Home.aboutus.AboutUsFragment;
import com.example.pat.aapkatrade.Home.banner_home.viewpageradapter_home;
import com.example.pat.aapkatrade.Home.latestproduct.latestproductadapter;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.SpacesItemDecoration;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.User_DashboardFragment;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements View.OnClickListener
{

    Context context;
    private View upAllSale;
    int currentPage=0;
    private com.example.pat.aapkatrade.Home.DashboardFragment homeFragment;
    LinearLayout viewpagerindicator;
    private RecyclerView recyclerViewAllSale, recyclerViewTrendingStyle, recyclerViewEclipseCollection, recyclerViewExpressDeal, recyclerViewBestSelling;
    private LinearLayoutManager llManagerAllSale, llManagerTrendingStyle, llManagerEclipseCollection, llManagerExpressDeal, llManagerBestSelling;
    ArrayList<CommomData> commomDatas = new ArrayList<>();
    ArrayList<CommomData> commomDatas_latestpost= new ArrayList<>();
    ArrayList<CommomData> commomDatas_latestupdate = new ArrayList<>();
    ArrayList<CommomData> commomDatas_hotdeals = new ArrayList<>();
    private CommomAdapter commomAdapter;
    public latestproductadapter latestproductadapter;
    SpacesItemDecoration itemDecoration;
    int position=0;
ProgressBarHandler progress_handler;
    private int dotsCount;
    private int[] tabColors;
    private List<Integer> imageIdList;
    User_DashboardFragment user_dashboardFragment;
    AboutUsFragment aboutUsFragment;
    private ImageView[] dots;
    ImageView home_ads;
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
    GridLayoutManager gridLayoutManager;

    viewpageradapter_home viewpageradapter;
    CircularProgressView progressView;
    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard_new, container, false);
        progress_handler=new ProgressBarHandler(getActivity());
        coordinatorLayout=(CoordinatorLayout)view.findViewById(R.id.coordination_home);
        coordinatorLayout.setVisibility(View.INVISIBLE);

        home_ads=(ImageView)view.findViewById(R.id.home_ads) ;
        home_ads.setImageResource(R.drawable.ic_home_ads_banner);

//                .load("http://aapkatrade.com/laraveldemo/public/image/demo/slider/3.jpg");
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
        recyclerViewAllSale = (RecyclerView) view.findViewById(R.id.recyclerlatestpost);
        recyclerViewAllSale.setLayoutManager(llManagerEclipseCollection);










        initializeview(view);
        setupView(view);
        setupviewpager();
        setuprecyclers(view);

        recyclerViewEclipseCollection = (RecyclerView) view.findViewById(R.id.recyclerlatestupdate);


         gridLayoutManager=new GridLayoutManager (context,2);
        recyclerViewEclipseCollection.setLayoutManager(gridLayoutManager);
        recyclerViewEclipseCollection.setHasFixedSize(true);

        return view;
    }






    private void setupviewpager()
    {

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


        get_home_data();

    }

    public void get_home_data()
    {
        progress_handler.show();
        coordinatorLayout.setVisibility(View.INVISIBLE);

        Ion.with(getActivity())
                .load("http://aapkatrade.com/slim/home")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("city_id", "")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {
                        if(result ==null)
                        {

                        }
                        else
                        {
                            Log.e("data===============",result.toString());


                            JsonObject jsonResult = result.getAsJsonObject("result");

                            JsonArray latest_post = jsonResult.getAsJsonArray("latest_post");

                            JsonArray latest_update = jsonResult.getAsJsonArray("latest_update");

                            System.out.println("jsonArray---------"+latest_post.toString());

                            for(int i = 0; i<latest_post.size(); i++)
                            {

                                JsonObject jsonObject_latest_post = (JsonObject) latest_post.get(i);

                                System.out.println("jsonArray jsonObject2"+jsonObject_latest_post.toString());

                                String product_id = jsonObject_latest_post.get("id").getAsString();

                                String product_name = jsonObject_latest_post.get("prodname").getAsString();

                                commomDatas_latestpost.add(new CommomData(product_id, product_name, "", "http://administrator.aapkatrade.com/public/upload/220/nyc-pie-gurgaon-625_625x350_41460295362.jpg"));

                            }

                            commomAdapter = new CommomAdapter(context, commomDatas_latestpost,"list","latestdeals");
                            recyclerViewAllSale.setAdapter(commomAdapter);
                            commomAdapter.notifyDataSetChanged();


                            for(int i = 0; i<latest_update.size(); i++)
                            {

                                JsonObject jsonObject_latest_update = (JsonObject) latest_post.get(i);

                                System.out.println("jsonArray jsonObject2"+jsonObject_latest_update.toString());

                                String update_product_id = jsonObject_latest_update.get("id").getAsString();

                                String update_product_name = jsonObject_latest_update.get("prodname").getAsString();

                                commomDatas_latestupdate.add(new CommomData(update_product_id, update_product_name, "", "http://administrator.aapkatrade.com/public/upload/220/nyc-pie-gurgaon-625_625x350_41460295362.jpg"));

                            }

                            commomAdapter = new CommomAdapter(context, commomDatas_latestupdate,"gridtype","latestupdate");
                            recyclerViewEclipseCollection.setAdapter(commomAdapter);
                            commomAdapter.notifyDataSetChanged();
                            coordinatorLayout.setVisibility(View.VISIBLE);
                            progress_handler.hide();

                        }


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


    private void setuprecyclers(View view)
    {

        //setupExpressDeal(view);
        setupBestSelling(view);
        setupEclipseCollection(view);
        setupTrendingStyles(view);
        setupAllSale(view);
    }

    private void setupAllSale(View view) {

       /* commomDatas_latestdeals.clear();
        for(int i=0;i<20;i++) {
            commomDatas_latestdeals.add(new CommomData("Latest Product", "Latest Deals", "", "http://administrator.aapkatrade.com/public/upload/220/nyc-pie-gurgaon-625_625x350_41460295362.jpg"));
        }*/

    }

    private void setupTrendingStyles(View view)
    {

        commomDatas_hotdeals.clear();
        for(int i=0;i<3;i++) {
            commomDatas_hotdeals.add(new CommomData("Latest Product", "Latest Deals", "", "http://administrator.aapkatrade.com/public/upload/noimg.jpg"));

        }
        commomAdapter = new CommomAdapter(context, commomDatas_hotdeals,"list","hotdeals");


        recyclerViewTrendingStyle = (RecyclerView) view.findViewById(R.id.recyclerhotdeals);

        recyclerViewTrendingStyle.setLayoutManager(gridLayoutManager);
        recyclerViewTrendingStyle.setAdapter(commomAdapter);

    }

    private void setupEclipseCollection(View view) {

       /* commomDatas_latestupdate.clear();
        for(int i=0;i<2;i++) {
            commomDatas_latestupdate.add(new CommomData("Latest Product", "Latest Deals", "", "http://administrator.aapkatrade.com/public/upload/noimg.jpg"));
        }*/



    }

    private void setupBestSelling(View view)
    {

        commomDatas.clear();

        for(int i=0;i<3;i++) {
      
        

            commomDatas.add(new CommomData("Latest Product", "Latest Deals", "", "http://administrator.aapkatrade.com/public/upload/noimg.jpg"));
        }
        commomAdapter = new CommomAdapter(context, commomDatas,"list","hotdeals");
       // itemDecoration=new SpacesItemDecoration(2,3);

        recyclerViewBestSelling = (RecyclerView) view.findViewById(R.id.recyclerBestSelling);
        recyclerViewBestSelling.setLayoutManager(gridLayoutManager);
        //recyclerViewBestSelling.addItemDecoration(itemDecoration);
        recyclerViewBestSelling.setAdapter(latestproductadapter);
        recyclerViewBestSelling.getAdapter().notifyDataSetChanged();


    }




//    private void setupExpressDeal(View view) {
//        recyclerViewExpressDeal = (RecyclerView) view.findViewById(R.id.recyclerExpressDeal);
//        recyclerViewExpressDeal.setLayoutManager(llManagerExpressDeal);
//        recyclerViewExpressDeal.setAdapter(commomAdapter);
//    }

    private void setupView(View view)
    {
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

    private void setUiPageViewController()
    {

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
