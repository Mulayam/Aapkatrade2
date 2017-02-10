package com.example.pat.aapkatrade.productdetail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.pat.aapkatrade.Home.banner_home.viewpageradapter_home;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.user_dashboard.address.AddressActivity;
import com.google.gson.JsonObject;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import github.nisrulz.stackedhorizontalprogressbar.StackedHorizontalProgressBar;



public class ProductDetail extends AppCompatActivity
{

    LinearLayout viewpagerindicator;
    Spinner spinner;
    private TextView buyProductButton;
    int primary_pts = 3;
    int secondary_pts = 7;
    int max = 10;
    private List<Integer> imageIdList;
    int currentPage=0;
    StackedHorizontalProgressBar progressbarFive,progressbarFour,progressbarThree, progressbarTwo,progressbarOne;
    ViewPager vp;
    viewpageradapter_home viewpageradapter;
    private int dotsCount;
    private ImageView[] dots;
    Timer banner_timer=new Timer();
    RelativeLayout relativeBuyNow;
    LinearLayout linearProductDetail;



    TextView tvProductName,tvProPrice,tvStock,tvDuration,tvDiscription,tvSpecification;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_detail);

        setuptoolbar();

        setup_layout();

        setupviewpager();

        initView();


        get_data();

       /* buyProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDetail.this, AddressActivity.class));
            }
        });
       */



    }

    private void get_data()
    {

        Ion.with(getApplicationContext())
                .load("http://aapkatrade.com/slim/product_detail/2")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "product_detail")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {

                        JsonObject jsonObject = result.getAsJsonObject();

                        String   product_name = jsonObject.get("name").getAsString();
                        String   product_price = jsonObject.get("price").getAsString();
                        String   deliverday = jsonObject.get("deliverday").getAsString();
                        String   description = jsonObject.get("short_des").getAsString();
                        String specification = jsonObject.get("").getAsString();

                    }


                });

    }


    private void setUiPageViewController()
    {

        dotsCount = viewpageradapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getApplicationContext());
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


    private void setupviewpager()
    {

        imageIdList = new ArrayList<>();
        imageIdList.add(R.drawable.banner_home);
        imageIdList.add(R.drawable.banner_home);
        imageIdList.add(R.drawable.banner_home);
        imageIdList.add(R.drawable.banner_home);


        viewpageradapter  = new viewpageradapter_home(getApplicationContext(), null);
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
            public void onPageSelected(int position)
            {

                try
                {
                    for (int i = 0; i < dotsCount; i++)
                    {
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



    private void setup_layout()
    {

        linearProductDetail = (LinearLayout) findViewById(R.id.linearProductDetail);

        tvProductName = (TextView) findViewById(R.id.tvProductName);

        tvProPrice = (TextView) findViewById(R.id.tvProPrice);

        tvStock = (TextView) findViewById(R.id.tvStock);

        tvDuration = (TextView) findViewById(R.id.tvDuration);

        tvDiscription = (TextView) findViewById(R.id.tvDiscription);

        tvSpecification = (TextView) findViewById(R.id.tvSpecification);

        relativeBuyNow = (RelativeLayout) findViewById(R.id.relativeBuyNow);

        vp=(ViewPager)  findViewById(R.id.viewpager_custom) ;

        viewpagerindicator=(LinearLayout)findViewById(R.id.viewpagerindicator);

        progressbarFive = (StackedHorizontalProgressBar) findViewById(R.id.progressbarFive);
        progressbarFive.setMax(max);
        progressbarFive.setProgress(10);
        progressbarFive.setSecondaryProgress(0);


        progressbarFour = (StackedHorizontalProgressBar) findViewById(R.id.progressbarFour);
        progressbarFour.setMax(max);
        progressbarFour.setProgress(6);
        progressbarFour.setSecondaryProgress(4);


        progressbarThree = (StackedHorizontalProgressBar) findViewById(R.id.progressbarThree);
        progressbarThree.setMax(max);
        progressbarThree.setProgress(3);
        progressbarThree.setSecondaryProgress(7);


        progressbarTwo = (StackedHorizontalProgressBar) findViewById(R.id.progressbarTwo);
        progressbarTwo.setMax(max);
        progressbarTwo.setProgress(8);
        progressbarTwo.setSecondaryProgress(2);


        progressbarOne = (StackedHorizontalProgressBar) findViewById(R.id.progressbarOne);
        progressbarOne.setMax(max);
        progressbarOne.setProgress(7);
        progressbarOne.setSecondaryProgress(3);

        relativeBuyNow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent i = new Intent(ProductDetail.this, AddressActivity.class);
                startActivity(i);

            }
        });


    }



    private void initView()
    {
        String[] ITEMS = {"1", "2", "3", "4", "5", "6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.spQuantity);
        spinner.setAdapter(adapter);

    }



    private void setuptoolbar()
    {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(null);

       // getSupportActionBar().setIcon(R.drawable.home_logo);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

}
