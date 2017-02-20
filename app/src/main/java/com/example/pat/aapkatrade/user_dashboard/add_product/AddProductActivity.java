package com.example.pat.aapkatrade.user_dashboard.add_product;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.navigation.entity.CategoryHome;
import com.example.pat.aapkatrade.Home.navigation.entity.SubCategory;
import com.example.pat.aapkatrade.Home.registration.RegistrationActivity;
import com.example.pat.aapkatrade.Home.registration.entity.City;
import com.example.pat.aapkatrade.Home.registration.entity.State;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpCityAdapter;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpStateAdapter;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_sharedpreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.ImageUtils;
import com.example.pat.aapkatrade.general.Utils.adapter.CustomSimpleListAdapter;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.User_DashboardFragment;
import com.example.pat.aapkatrade.user_dashboard.addcompany.CompanyData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;


public class AddProductActivity extends AppCompatActivity {
    private Context context;
    private LinearLayout contentAddProduct, add_product_root_container;
    private Spinner spCompanyName, spSubCategory, spCategory, spState, spCity, spdeliverydistance;
    private String countryID = "101", stateID, cityID, companyID, categoryID, subCategoryID, deliveryDistanceID;
    private HashMap<String, String> webservice_header_type = new HashMap<>();
    private ArrayList<CategoryHome> listDataHeader = new ArrayList<>();
    private ArrayList<SubCategory> listDataChild = new ArrayList<>();
    private ArrayList<State> stateList = new ArrayList<>();
    private ArrayList<City> cityList = new ArrayList<>();
    private ArrayList<String> deliveryDistanceList = new ArrayList<>();
    private ArrayList<CompanyData> companyNames = new ArrayList<>();
    private ProgressBarHandler progressBar;
    private App_sharedpreference app_sharedpreference;
    private TextView btnUpload;
    private EditText etProductName, etDeliverLocation, etPrice, etCrossedPrice, etDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        setuptoolbar();
        initView();
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAddProductWebService();
            }
        });

    }

    private void callAddProductWebService() {

        Log.e("company result", app_sharedpreference.getsharedpref("userid", "0"));
        Ion.with(context)
                .load("http://aapkatrade.com/slim/add_product")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("user_id", app_sharedpreference.getsharedpref("userid", "0"))
                .setBodyParameter("name", AndroidUtils.getEditTextData(etProductName))
                .setBodyParameter("company_id", companyID)
                .setBodyParameter("deliverTime", "")
                .setBodyParameter("distance_id", deliveryDistanceID)
                .setBodyParameter("deliverday", "")
                .setBodyParameter("cross_price", "")
                .setBodyParameter("availablestatus_id", "")
                .setBodyParameter("short_des", "company")
                .setBodyParameter("deliveryArea", AndroidUtils.getEditTextData(etDeliverLocation))
                .setBodyParameter("country_id", countryID)
                .setBodyParameter("state_id", stateID)
                .setBodyParameter("city_id", cityID)
                .setBodyParameter("category_id", categoryID)
                .setBodyParameter("sub_cat_id", subCategoryID)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {
                        progressBar.hide();
                        Log.e("company result", result == null ? "Add Product data found null" : result.toString());

                        if (result != null && result.get("message").getAsString().equals("Product Added Successfully!"))
                        {

                            AddProductActivity.this.finish();
                        }
                        else
                        {
                            showMessage("Company Not Found");
                        }
                    }
                });

    }


    private void initView() {
        context = AddProductActivity.this;
        contentAddProduct = (LinearLayout) findViewById(R.id.contentAddProduct);
        add_product_root_container = (LinearLayout) findViewById(R.id.add_product_root_container);
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        progressBar = new ProgressBarHandler(context);
        app_sharedpreference = new App_sharedpreference(context);

        spCompanyName = (Spinner) findViewById(R.id.spCompanyName);
        spSubCategory = (Spinner) findViewById(R.id.spSubCategory);
        spCategory = (Spinner) findViewById(R.id.spCategory);
        spState = (Spinner) findViewById(R.id.spState);
        spCity = (Spinner) findViewById(R.id.spCity);
        spdeliverydistance = (Spinner) findViewById(R.id.spDeliverydistance);

        btnUpload = (TextView) findViewById(R.id.btnUpload);
        btnUpload.setText("Add");

        etProductName = (EditText) findViewById(R.id.etProductName);
        etDeliverLocation = (EditText) findViewById(R.id.etDeliverLocation);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etCrossedPrice = (EditText) findViewById(R.id.etCrossedPrice);
        etDescription = (EditText) findViewById(R.id.etDescription);

        initSpinner();
        getCompany();
        getCategory();
        getState();
        pickDeliveryLocation();
    }


    private void initSpinner() {
        CompanyData companyData = new CompanyData("Please Select Company", "-1");
        companyNames.add(companyData);
        CustomSimpleListAdapter adapter = new CustomSimpleListAdapter(context, companyNames);
        spCompanyName.setAdapter(adapter);


        State stateEntity_init = new State("-1", "Please Select State");
        stateList.add(stateEntity_init);
        SpStateAdapter spStateAdapter = new SpStateAdapter(context, stateList);
        spState.setAdapter(spStateAdapter);

        City cityEntity_init = new City("-1", "Please Select City");
        cityList.add(cityEntity_init);
        SpCityAdapter spCityAdapter = new SpCityAdapter(context, cityList);
        spCity.setAdapter(spCityAdapter);
    }

    private void getCompany() {

        Log.e("company result", app_sharedpreference.getsharedpref("userid", "0"));
        Ion.with(context)
                .load("http://aapkatrade.com/slim/dropdown")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "company")
                .setBodyParameter("id", app_sharedpreference.getsharedpref("userid", "0"))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBar.hide();
                        Log.e("company result", result == null ? "state data found" : result.toString());

                        if (result != null) {

                            JsonArray jsonResultArray = result.getAsJsonArray("result");
                            if (jsonResultArray != null) {
//                                companyNames = new ArrayList<>();
                                for (int i = 0; i < jsonResultArray.size(); i++) {
                                    JsonObject jsonObject = (JsonObject) jsonResultArray.get(i);
                                    CompanyData companyData = new CompanyData(jsonObject.get("name").getAsString(), jsonObject.get("id").getAsString());
                                    companyNames.add(companyData);
                                }
                                CustomSimpleListAdapter adapter = new CustomSimpleListAdapter(context, companyNames);
                                spCompanyName.setAdapter(adapter);


                                spCompanyName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        if(position>0){
                                            companyID = companyNames.get(position).getCompanyId();
                                        } else {
                                            showMessage("Invalid Company");
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else {
                                showMessage("Company Data Not Found");
                            }

                        } else {
                            showMessage("Company Not Found");
                        }
                    }
                });


    }


    public void getState() {
        Log.e("state result ", "getState started");
        progressBar.show();

        Ion.with(context)
                .load("http://aapkatrade.com/slim/dropdown")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "state")
                .setBodyParameter("id", countryID)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBar.hide();
                        Log.e("state result ", result == null ? "state data found" : result.toString());

                        if (result != null) {

                            JsonArray jsonResultArray = result.getAsJsonArray("result");
                            stateList = new ArrayList<>();
                            State stateEntity_init = new State("-1", "Please Select State");
                            stateList.add(stateEntity_init);

                            for (int i = 0; i < jsonResultArray.size(); i++) {
                                JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                                State stateEntity = new State(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                                stateList.add(stateEntity);
                            }
                            SpStateAdapter spStateAdapter = new SpStateAdapter(context, stateList);
                            spState.setAdapter(spStateAdapter);
                            spStateAdapter.notifyDataSetChanged();
                            spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    stateID = stateList.get(position).stateId;
                                    cityList = new ArrayList<>();
                                    if (position > 0) {
                                        getCity(stateList.get(position).stateId);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            showMessage("State Not Found");
                        }
                    }
                });
    }

    private void showMessage(String message) {
        AndroidUtils.showSnackBar(contentAddProduct, message);
    }


    public void getCity(String stateId) {
        progressBar.show();
        Ion.with(context)
                .load("http://aapkatrade.com/slim/dropdown")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "city")
                .setBodyParameter("id", stateId)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBar.hide();
                        Log.e("city result ", result == null ? "null" : result.toString());

                        if (result != null) {
                            JsonArray jsonResultArray = result.getAsJsonArray("result");

                            City cityEntity_init = new City("-1", "Please Select City");
                            cityList.add(cityEntity_init);

                            for (int i = 0; i < jsonResultArray.size(); i++) {
                                JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                                City cityEntity = new City(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                                cityList.add(cityEntity);
                            }

                            SpCityAdapter spCityAdapter = new SpCityAdapter(context, cityList);
                            spCity.setAdapter(spCityAdapter);

                            spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    cityID = cityList.get(position).cityId;
//                        if (!(Integer.parseInt(cityID) > 0)) {
//                            showmessage("Please Select City");
//                        }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            showMessage("No City Found");
                        }
                    }

                });

    }

    private void getCategory() {
        listDataChild.clear();
        listDataHeader.clear();
        progressBar.show();
        Log.e("data", "getCategory Entered");
        Ion.with(context)
                .load("http://aapkatrade.com/slim/dropdown")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "category")

                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject data) {
                        progressBar.hide();
                        Log.e("data", "getCategory result" + data.toString());
                        if (data != null) {
                            JsonObject jsonObject = data.getAsJsonObject();
                            JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");

                            listDataHeader = new ArrayList<>();
                            listDataChild.add(new SubCategory("-1", "Please Select SubCategory"));

                            listDataHeader.add(new CategoryHome("-1", "Please Select Category", "", listDataChild));
                            for (int i = 0; i < jsonResultArray.size(); i++) {
                                JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                                JsonArray json_subcategory = jsonObject1.getAsJsonArray("subcategory");

                                if (json_subcategory != null) {
                                    listDataChild = new ArrayList<>();
                                    for (int k = 0; k < json_subcategory.size(); k++) {
                                        JsonObject jsonObject_subcategory = (JsonObject) json_subcategory.get(k);
                                        SubCategory subCategory = new SubCategory(jsonObject_subcategory.get("id").getAsString(), jsonObject_subcategory.get("name").getAsString());
                                        listDataChild.add(subCategory);
                                    }
                                    CategoryHome categoryHome = new CategoryHome(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString(), jsonObject1.get("icon").getAsString(), listDataChild);
                                    listDataHeader.add(categoryHome);
                                    Log.e("data", categoryHome.toString());

                                }
                            }
                            setCategoryAdapter();
                        }

                    }
                });
    }

    private void setCategoryAdapter() {
        Log.e("data", this.listDataHeader.toString());
        CustomSimpleListAdapter categoriesAdapter = new CustomSimpleListAdapter(context, this.listDataHeader);
        spCategory.setAdapter(categoriesAdapter);


        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    categoryID = listDataHeader.get(position).getCategoryId();
                    listDataChild = new ArrayList<>();
                    listDataChild = listDataHeader.get(position).getSubCategoryList();
                    if (listDataChild.size() > 0) {
                        CustomSimpleListAdapter adapter = new CustomSimpleListAdapter(context, listDataChild);
                        spSubCategory.setAdapter(adapter);
                        spSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position>0){
                                    subCategoryID = listDataChild.get(position).subCategoryId;
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } else {
                        listDataChild = new ArrayList<>();
                        listDataChild.add(new SubCategory("0", "No SubCategory Found"));
                        CustomSimpleListAdapter adapter = new CustomSimpleListAdapter(context, listDataChild);
                        spSubCategory.setAdapter(adapter);
                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setuptoolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


    private void pickDeliveryLocation() {
        loadDistanceList();
        CustomSimpleListAdapter adapter = new CustomSimpleListAdapter(context, deliveryDistanceList);
        spdeliverydistance.setAdapter(adapter);
        spdeliverydistance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    deliveryDistanceID = String.valueOf(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadDistanceList() {
        deliveryDistanceList.add(0, "Please Select distance");
        deliveryDistanceList.add(1, "0 to 0.5 km");
        deliveryDistanceList.add(2, "0.5 to 1 km");
        deliveryDistanceList.add(3, "1 to 2 km");
        deliveryDistanceList.add(4, "2 to 3 km");
        deliveryDistanceList.add(5, "3 to 4 km");
        deliveryDistanceList.add(6, "4 to 5 km");
        deliveryDistanceList.add(7, "5 to 6 km");
        deliveryDistanceList.add(8, "6 to 7 km");
        deliveryDistanceList.add(9, "7 to 8 km");
        deliveryDistanceList.add(10, "8 to 9 km");
        deliveryDistanceList.add(11, "9 to 10 km");
        deliveryDistanceList.add(12, "10 to 11 km");
        deliveryDistanceList.add(13, "11 to 12 km");
        deliveryDistanceList.add(14, "12 to 13 km");
        deliveryDistanceList.add(15, "13 to 14 km");
        deliveryDistanceList.add(16, "14 to 15 km");
        deliveryDistanceList.add(17, "15 to 16 km");
        deliveryDistanceList.add(18, "16 to 17 km");
        deliveryDistanceList.add(19, "17 to 18 km");
        deliveryDistanceList.add(20, "18 to 19 km");
        deliveryDistanceList.add(21, "19 to 20 km");
        deliveryDistanceList.add(22, "20 to 21 km");
        deliveryDistanceList.add(23, "21 to 22 km");
        deliveryDistanceList.add(24, "22 to 23 km");
        deliveryDistanceList.add(25, "23 to 24 km");
        deliveryDistanceList.add(26, "24 to 25 km");
        deliveryDistanceList.add(27, "25 to 26 km");
        deliveryDistanceList.add(28, "26 to 27 km");
        deliveryDistanceList.add(29, "27 to 28 km");
        deliveryDistanceList.add(30, "28 to 29 km");
        deliveryDistanceList.add(31, "29 to 30 km");
    }



    void picPhoto() {
        String str[] = new String[]{"Camera", "Gallery", "PDF Files"};
        new AlertDialog.Builder(this).setItems(str,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        performImgPicAction(which);
                    }
                }).show();
    }

    void performImgPicAction(int which) {
        Intent in;
        if (which == 1) {
            in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(in, "Select profile picture"), 11);
        } else if (which == 2) {


            new MaterialFilePicker()
                    .withActivity(this)
                    .withRequestCode(1)
                    .withFilter(Pattern.compile(".*\\.pdf$"))
                    .withFilterDirectories(false)
                    .withHiddenFiles(true)
                    .start();


        } else {
            in = new Intent();
            in.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(Intent.createChooser(in, "Select profile picture"), 11);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("hi", "requestCode : "+requestCode+"result code : "+resultCode );

        try {
            if (requestCode == 1) {
                Log.e("hi", " if else if 1 " );
                String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                File file = new File(filePath);
                if(!filePath.equals("result_file_path")) {
                 /*   if (isCompIncorp) {
                        previewPDFLayout.setVisibility(View.VISIBLE);
                        previewPDF.setImageDrawable(ContextCompat.getDrawable(RegistrationActivity.this, R.drawable.pdf));
                        compIncorpFile = file;
                        isCompIncorp = false;
                    } else {
                        previewImageLayout.setVisibility(View.VISIBLE);
                        circleImageView.setImageDrawable(ContextCompat.getDrawable(RegistrationActivity.this, R.drawable.pdf));
                        docFile = file;
                    }*/
                }
                Log.e("hi", "pdf file path : " + file.getAbsolutePath() + "\n" + filePath);
            } else if (requestCode == 11) {
                Log.e("hi", " if else if 2 " );
                BitmapFactory.Options option = new BitmapFactory.Options();
                option.inDither = false;
                option.inPurgeable = true;
                option.inInputShareable = true;
                option.inTempStorage = new byte[32 * 1024];
                option.inPreferredConfig = Bitmap.Config.RGB_565;
                if (Build.VERSION.SDK_INT < 19) {
//                    Uri selectedImageURI = data.getData();

//                    imageForPreview = BitmapFactory.decodeFile(getFilesDir().getPath(), option);
                } else {
                    if (data.getData() != null) {

                        ParcelFileDescriptor pfd;
                        try {
                            pfd = getContentResolver()
                                    .openFileDescriptor(data.getData(), "r");
                            if (pfd != null) {
                                FileDescriptor fileDescriptor = pfd
                                        .getFileDescriptor();

//                                imageForPreview = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, option);
                            }
                            pfd.close();


                        } catch (FileNotFoundException e) {
                            Log.e("FileNotFoundException", e.toString());
                        } catch (IOException e) {
                            Log.e("IOException", e.toString());
                        }
                    } else {
//                        imageForPreview = (Bitmap) data.getExtras().get("data");
                        Log.e("data_not_found", "data_not_found");
                    }

                }
                try {
//                    previewImageLayout.setVisibility(View.VISIBLE);
//                    Log.e("doc", "***START.****** ");
//                    if (ImageUtils.sizeOf(imageForPreview) > 2048) {
//                        Log.e("doc", "if doc file path 1");
//                        circleImageView.setImageBitmap(ImageUtils.resize(imageForPreview, imageForPreview.getHeight() / 2, imageForPreview.getWidth() / 2));
//                        docFile = getFile(ImageUtils.resize(imageForPreview, imageForPreview.getHeight() / 2, imageForPreview.getWidth() / 2));
//                        Log.e("doc", "if doc file path"+docFile.getAbsolutePath());
//                    } else {
//                        circleImageView.setImageBitmap(imageForPreview);
//                        Log.e("doc", " else doc file path 1");
//                        docFile = getFile(imageForPreview);
//                        Log.e("doc", " else doc file path"+docFile.getAbsolutePath());
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
