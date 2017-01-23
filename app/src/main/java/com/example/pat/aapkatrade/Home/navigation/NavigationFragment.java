package com.example.pat.aapkatrade.Home.navigation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pat.aapkatrade.Home.navigation.entity.CategoryHome;
import com.example.pat.aapkatrade.Home.navigation.entity.SubCategory;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.categories_tab.CategoriesTabActivity;
import com.example.pat.aapkatrade.general.ConnetivityCheck;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.productdetail.CategoryListFragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.X509TrustManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends Fragment implements View.OnClickListener, ExpandableListAdapter.clickListner {


    public static final String preFile = "textFile";
    public static final String userKey = "key";
    private static final String TAG = "gcm_tag";
    private static int POSITION = 0;
    public static ActionBarDrawerToggle mDrawerToggle;
    public static DrawerLayout mDrawerLayout;
    boolean mUserLearnedDrawer;
    private static final int IMAGE_PICKER_SELECT = 999;
    boolean mFromSavedInstance;
    View view;
    String Fname, Lname, Dob;
    private int lastExpandedPosition = -1;
    public static final String PREFS_NAME = "call_recorder";
    private SharedPreferences loginPreferences;
    List<String> categoryids;
    CategoryListFragment productListActivity;
    List<String> categoryname;
    private Snackbar snackbar;


    public static SharedPreferences.Editor loginPrefsEditor;
    private Context context;
    TextView footer;
    RelativeLayout header;

    TextView textViewName, textViewmobile_no, textView_email;
    private ImageView imageViewGB;
    private ExpandableListView expListView;
    private ImageView edit_profile_imgview;
    private ExpandableListAdapter listAdapter;
    private ArrayList<CategoryHome> listDataHeader;
    ProgressDialog _progressDialog;

    public NavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_navigation, container, false);
        _progressDialog = new ProgressDialog(context);

        initView();

        return view;
    }


    private void initView() {
        //prepare textviewdata
        categoryname = new ArrayList<>();
        categoryids = new ArrayList<>();
        //sharedprefrance
        loginPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        prepareListData();
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        //circle imageview

        //ImagePicker.setMinQuality(600, 600);
        // preparing list data

    }


    private void Showmessage(String message) {


        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), userKey, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstance = true;
        }
    }

    public void setup(int id, final DrawerLayout drawer, Toolbar toolbar) {
        view = getActivity().findViewById(id);

        mDrawerLayout = drawer;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.drawer_open, R
                .string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                hideSoftKeyboard(getActivity());
                mDrawerLayout.setScrimColor(Color.TRANSPARENT);
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    savedInstances(getActivity(), userKey, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();

            }

        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDrawerLayout.closeDrawers();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public static void savedInstances(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharePreference = context.getSharedPreferences(preFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePreference.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharePreference = context.getSharedPreferences(preFile, Context.MODE_PRIVATE);
        return sharePreference.getString(preferenceName, defaultValue);
    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

    }


    private void showMessage(String clicked) {
        Toast.makeText(context, clicked, Toast.LENGTH_SHORT).show();
    }


    public void setdata(String username, String mobno, String email, String Lastname, String dob) {
        Fname = username;
        Lname = Lastname;
        Dob = dob;

        textViewName.setText(username);
        textViewmobile_no.setText(mobno);
        textView_email.setText(email);


    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void itemClicked(View view, int groupview, int childview) {
        try {
            Intent i = new Intent(getActivity(), CategoriesTabActivity.class);
            startActivity(i);
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
    }


    private void replaceFragment(Fragment newFragment, String tag) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.drawer_layout, newFragment, tag).addToBackStack(tag);
        transaction.commit();
    }

    private void setup_productlist_Fragment() {
        if (productListActivity == null) {
            productListActivity = new CategoryListFragment();
        }
        String tagName = productListActivity.getClass().getName();
        replaceFragment(productListActivity, tagName);
    }

    private void prepareListData() {
//        if(ConnetivityCheck.isNetworkAvailable(getContext())){
            getCategory();
//        }else{
//            snackbar = Snackbar.make(getView().findViewById(R.id.snakBar), "Please Check your Network Connection", Snackbar.LENGTH_LONG);
//            snackbar.setActionTextColor(getResources().getColor(android.R.color.holo_red_dark));
//            snackbar.setAction("Retry", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    getCategory();
//                }
//            });
//            snackbar.show();
//        }
    }


    private void getCategory() {
//        dialog.show();
        Ion.with(getContext())
                .load("http://aapkatrade.com/slim/dropdown")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "category")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            JsonObject jsonObject = result.getAsJsonObject();
                            JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");
                            listDataHeader = new ArrayList<>();
                            for (int i = 0; i < jsonResultArray.size(); i++) {
                                JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                                CategoryHome categoryHome = new CategoryHome(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString(), jsonObject1.get("icon").getAsString());
                                categoryHome.setSubCategoryList(getSubCategoryArrayList(categoryHome.getCategoryId()));
                                listDataHeader.add(categoryHome);
                                Log.e("hi", categoryHome.getCategoryName());
                            }
                            set_expandable_adapter_data();
//                        dialog.hide();
                        }
                        else{
                            Log.e("Exception_webservice",e.toString());

                        }
                    }
                });
    }


    private ArrayList<SubCategory> getSubCategoryArrayList(String categoryId) {
        final ArrayList<SubCategory> listDataChild = new ArrayList<>();
        Ion.with(getContext())
                .load("http://aapkatrade.com/slim/dropdown")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "subcategory")
                .setBodyParameter("id", categoryId)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            JsonObject jsonObject = result.getAsJsonObject();
                            JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");
                            if (jsonResultArray != null) {
                                for (int i = 0; i < jsonResultArray.size(); i++) {
                                    JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                                    SubCategory subCategory = new SubCategory(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                                    listDataChild.add(subCategory);
                                }
                            }
                        }
                    }
                });
        return listDataChild;
    }

    private void set_expandable_adapter_data() {

        if (listDataHeader.size() != 0) {
            listAdapter = new ExpandableListAdapter(context, listDataHeader);
            // setting list adapter
            expListView.setAdapter(listAdapter);

            expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    if (lastExpandedPosition != -1
                            && groupPosition != lastExpandedPosition) {
                        expListView.collapseGroup(lastExpandedPosition);
                    }
                    lastExpandedPosition = groupPosition;
                }
            });
            listAdapter.setClickListner(this);
        }
    }


}










