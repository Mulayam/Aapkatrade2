package com.example.pat.aapkatrade.productdetail;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.address.AddressActivity;
import com.google.gson.JsonArray;
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
    int max = 10;
    private ArrayList<String> imageList;
    int currentPage=0;
    StackedHorizontalProgressBar progressbarFive,progressbarFour,progressbarThree, progressbarTwo,progressbarOne;
    ViewPager vp;
    ProductViewPagerAdapter viewpageradapter;
    private int dotsCount;
    private ImageView[] dots;
    Timer banner_timer=new Timer();
    RelativeLayout relativeBuyNow,RelativeProductDetail;
    LinearLayout linearProductDetail;
    TextView tvProductName,tvProPrice,tvCrossPrice,tvDuration,tvDiscription,tvSpecification,tvQuatity;
    ProgressBarHandler progress_handler;
    String product_id;
    ImageView imgViewPlus,imgViewMinus;
    int quantity_value=1;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_detail);

         Intent intent= getIntent();

         Bundle b = intent.getExtras();

          product_id = b.getString("product_id");

          setuptoolbar();

          setup_layout();

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
        relativeBuyNow.setVisibility(View.INVISIBLE);
        linearProductDetail.setVisibility(View.INVISIBLE);
        progress_handler.show();

        Ion.with(getApplicationContext())
                .load("http://aapkatrade.com/slim/product_detail/"+product_id)
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "product_detail")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {

                        if(result!= null)
                        {

                            Log.e("result---------",result.toString());

                            JsonObject jsonObject = result.getAsJsonObject();

                            JsonObject json_result = jsonObject.getAsJsonObject("result");

                            JsonArray jsonArray_image = json_result.getAsJsonArray("product_images");

                            System.out.println("jsonArray_image------"+jsonArray_image.toString());

                            for (int i =0; i<jsonArray_image.size(); i++)
                            {
                                JsonObject jsonimage = (JsonObject) jsonArray_image.get(i);

                                String image_url = jsonimage.get("image_url").getAsString();

                                System.out.println("image_url---------"+image_url);

                                imageList.add(image_url);
                            }

                            String   product_name = json_result.get("name").getAsString();

                            String   product_price = json_result.get("price").getAsString();

                            String   product_cross_price = json_result.get("cross_price").getAsString();

                            String   description = json_result.get("short_des").getAsString();

                            String   duration = json_result.get("deliverday").getAsString();

                            tvProductName.setText(product_name);
                            tvProPrice.setText("\u20A8"+"."+" "+product_price);
                            tvCrossPrice.setText("\u20A8"+"."+" "+product_cross_price);
                            tvDiscription.setText(description);
                            tvDuration.setText(duration);

                            setupviewpager();

                            progress_handler.hide();

                            linearProductDetail.setVisibility(View.VISIBLE);
                            relativeBuyNow.setVisibility(View.VISIBLE);



                        }
                        else
                        {


                            progress_handler.hide();
                            linearProductDetail.setVisibility(View.INVISIBLE);
                            relativeBuyNow.setVisibility(View.INVISIBLE);


                        }


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

        viewpageradapter  = new ProductViewPagerAdapter(getApplicationContext(), imageList);
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

        progress_handler = new ProgressBarHandler(this);

        imageList = new ArrayList<>();

        RelativeProductDetail = (RelativeLayout) findViewById(R.id.RelativeProductDetail);

        linearProductDetail = (LinearLayout) findViewById(R.id.linearProductDetail);

        tvProductName = (TextView) findViewById(R.id.tvProductName);

        tvProPrice = (TextView) findViewById(R.id.tvProPrice);

        tvCrossPrice = (TextView) findViewById(R.id.tvCrossPrice);

        tvCrossPrice.setPaintFlags(tvCrossPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        tvDuration = (TextView) findViewById(R.id.tvDuration);

        tvDiscription = (TextView) findViewById(R.id.tvDiscription);

        tvSpecification = (TextView) findViewById(R.id.tvSpecification);

        tvQuatity = (TextView) findViewById(R.id.tvQuatity);

        imgViewPlus = (ImageView) findViewById(R.id.imgViewPlus);

        imgViewPlus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                quantity_value = quantity_value +1;

                tvQuatity.setText(String.valueOf(quantity_value));


            }
        });

        imgViewMinus = (ImageView) findViewById(R.id.imgViewMinus);

        imgViewMinus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(quantity_value == 1){

                    tvQuatity.setText(String.valueOf(quantity_value));
                }
                else
                {
                    quantity_value = quantity_value - 1;

                    tvQuatity.setText(String.valueOf(quantity_value));
                }

            }
        });


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
