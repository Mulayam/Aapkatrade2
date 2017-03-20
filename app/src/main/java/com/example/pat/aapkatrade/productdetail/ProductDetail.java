package com.example.pat.aapkatrade.productdetail;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.CheckPermission;
import com.example.pat.aapkatrade.general.LocationManager_check;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.map.GoogleMapActivity;
import com.example.pat.aapkatrade.service_enquiry.ServiceEnquiry;
import com.example.pat.aapkatrade.user_dashboard.address.AddressActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import github.nisrulz.stackedhorizontalprogressbar.StackedHorizontalProgressBar;



public class ProductDetail extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{

    LinearLayout viewpagerindicator,linearlayoutShare,linearlayoutLocation;
    Spinner spinner;
    int max = 10;
    private ArrayList<String> imageList;
    int currentPage=0;
    private int isStartDate = -1;
    ServiceEnquiry serviceEnquiry;
    private String date;
    StackedHorizontalProgressBar progressbarFive,progressbarFour,progressbarThree, progressbarTwo,progressbarOne;
    ViewPager vp;
    ProductViewPagerAdapter viewpageradapter;
    private int dotsCount;
    private ImageView[] dots;
    Timer banner_timer=new Timer();
    RelativeLayout relativeBuyNow,RelativeProductDetail,relativeRateReview;
    LinearLayout linearProductDetail;
    TextView tvProductName,tvProPrice,tvCrossPrice,tvDuration,tvDiscription,tvSpecification,tvQuatity;
    ProgressBarHandler progress_handler;
    String product_id,product_location;
    ImageView imgViewPlus,imgViewMinus;
    int quantity_value=1;
    String productlocation;
    EditText firstName,quantity,price,mobile,email,etEndDate,etStatDate,description;
    TextView tvServiceBuy;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_detail);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);


          Intent intent= getIntent();

          Bundle b = intent.getExtras();

          product_id = b.getString("product_id");

          Log.e("product_id",product_id);

          product_location=b.getString("product_location");

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
        Log.e("data_productdeatil","http://aapkatrade.com/slim/product_detail/"+product_id);

        Ion.with(getApplicationContext())
                .load("http://aapkatrade.com/slim/product_detail/"+product_id)
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
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

                            String enquiry = json_result.get("enquiry").getAsString();

                            productlocation=json_result.get("city_name").getAsString()+","+ json_result.get("state_name").getAsString()+","+ json_result.get("country_name").getAsString();

                            if (enquiry.equals("1"))
                            {

                                tvServiceBuy.setText("Buy Now");


                            }
                            else
                            {

                                tvServiceBuy.setText("Service Enquiry");
                            }




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

        viewpageradapter  = new ProductViewPagerAdapter(ProductDetail.this, imageList);
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

        relativeRateReview = (RelativeLayout) findViewById(R.id.relativeRateReview);

        linearlayoutShare = (LinearLayout) findViewById(R.id.linearlayoutShare);

        linearlayoutLocation = (LinearLayout) findViewById(R.id.linearlayoutLocation);

        tvServiceBuy = (TextView) findViewById(R.id.tvServiceBuy);

        relativeRateReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        linearlayoutLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean permission_status = CheckPermission.checkPermissions(ProductDetail.this);


                if (permission_status)

                {
                    LocationManager_check locationManagerCheck = new LocationManager_check(
                            ProductDetail.this);
                    Location location = null;
                    if (locationManagerCheck.isLocationServiceAvailable())
                    {


                        Intent intent = new Intent(ProductDetail.this, GoogleMapActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        intent.putExtra("product_location", productlocation);
                        ProductDetail.this.startActivity(intent);



                    }
                    else {
                        locationManagerCheck.createLocationServiceError(ProductDetail.this);
                    }

                }





            }
        });


        linearlayoutShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = "Text I want to share.";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));

            }
        });

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


                /*    FragmentManager fm = getSupportFragmentManager();
                ServiceEnquiry dialogFragment = new ServiceEnquiry ();
                dialogFragment.show(fm, "Sample Fragment");*/

                //   final RelativeLayout root = new RelativeLayout(ProductDetail.this);
                // root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                // creating the fullscreen dialog

                if (tvServiceBuy.getText().toString().equals("Buy Now"))
                {

                            Intent i = new Intent(ProductDetail.this, AddressActivity.class);
                            startActivity(i);

                }
                else
                {
                    //FragmentManager fm = getSupportFragmentManager();
                    ServiceEnquiry dialogFragment = new ServiceEnquiry (ProductDetail.this);
                    dialogFragment.show();
//
//                     dialog = new Dialog(ProductDetail.this);
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setContentView(R.layout.fragment_service_enquiry);
//
//                    Button imgCLose = (Button) dialog.findViewById(R.id.imgCLose);
//
//                    firstName  = (EditText) dialog.findViewById(R.id.edtFName);
//
//                    quantity = (EditText) dialog.findViewById(R.id.edtQuantity);
//
//                    price = (EditText) dialog.findViewById(R.id.edtPrice);
//
//                    mobile = (EditText) dialog.findViewById(R.id.edtMobile);
//
//                    email = (EditText) dialog.findViewById(R.id.edtEmail);
//
//                    etEndDate = (EditText) dialog.findViewById(R.id.etEndDate);
//
//                    etStatDate = (EditText) dialog.findViewById(R.id.etStartDate) ;
//
//                    description = (EditText) dialog.findViewById(R.id.edtDescription);
//
//                    Button submit = (Button) dialog.findViewById(R.id.buttonSubmit) ;
//
//                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
//                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                    //dialog.show();
//
//                    submit.setOnClickListener(new View.OnClickListener()
//                    {
//                        @Override
//                        public void onClick(View v)
//                        {
//
//
//                        }
//                    });
//
//
//                    imgCLose.setOnClickListener(new View.OnClickListener()
//                    {
//                        @Override
//                        public void onClick(View v)
//                        {
//
//                            dialog.hide();
//                        }
//                    });
//                    GradientDrawable shape =  new GradientDrawable();
//                    shape.setCornerRadius( 8 );
//                    shape.setColor(ContextCompat.getColor(ProductDetail.this, R.color.orange));
//                    dialog.findViewById(R.id.buttonSubmit).setBackgroundSolid(shape);
//                    GradientDrawable shape2 =  new GradientDrawable();
//                    shape2.setCornerRadius( 8 );
//                    shape2.setColor(ContextCompat.getColor(ProductDetail.this, R.color.green));
//                    dialog.findViewById(R.id.rl_service_enquiry).setBackgroundSolid(shape2);
//
//                    etStatDate.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            Log.e("etStartDate","clicked");
//
//                            isStartDate = 0;
//                            openCalender(etStatDate);
//                        }
//                    });
//                    etEndDate.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Log.e("etStartDate","clicked");
//
//                            isStartDate = 1;
//                            openCalender(etEndDate);
//                        }
//                    });
//
//
//
//
//

                }

            }
        });

    }

    private void openCalender(EditText editText) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                ProductDetail.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setMaxDate(now);
        if(isStartDate == 1){
            if(editText.getText()!=null || editText.getText().toString().length()>=8)
                dpd.setMinDate(AndroidUtils.stringToCalender(editText.getText().toString()));
        }
        dpd.show(getFragmentManager(), "DatePickerDialog");
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
        getMenuInflater().inflate(R.menu.menu_map, menu);
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        showDate(year, monthOfYear + 1, dayOfMonth);
    }


    private void showDate(int year, int month, int day) {
        date = (new StringBuilder()).append(year).append("-").append(month).append("-").append(day).toString();
        if(isStartDate == 0){
            etStatDate.setText(date);
        } else if(isStartDate == 1){
            etEndDate.setText(date);
        }
    }
}
