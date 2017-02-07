package com.example.pat.aapkatrade.Home.navigation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.pat.aapkatrade.categories_tab.CategoryListActivity;
import com.example.pat.aapkatrade.general.App_sharedpreference;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.X509TrustManager;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends Fragment implements View.OnClickListener, ExpandableListAdapter.clickListner
{


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

    App_sharedpreference app_sharedpreference;
    public static final String PREFS_NAME = "call_recorder";
   // private SharedPreferences loginPreferences;
    List<String> categoryids;

    List<String> categoryname;


    private Context context;
    TextView footer;
    RelativeLayout header;

    TextView textViewName,emailid;
    private ImageView imageViewGB;
    private ExpandableListView expListView;
    private ImageView edit_profile_imgview;
    private ExpandableListAdapter listAdapter;
    private ArrayList<CategoryHome> listDataHeader;
    public
    ProgressDialog _progressDialog;
    private static  String shared_pref_name = "aapkatrade";


    public NavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_navigation, container, false);
        _progressDialog = new ProgressDialog(context);
        app_sharedpreference=new App_sharedpreference(getActivity());
        initView(view);

        return view;
    }


    private void initView(View view)
    {
     //   sharedPreferences = getActivity().getSharedPreferences(shared_pref_name, MODE_PRIVATE);
        //prepare textviewdata
        categoryname = new ArrayList<>();
        categoryids = new ArrayList<>();
        //sharedprefrance
       // loginPreferences = getActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        textViewName=(TextView)view.findViewById(R.id.welcome_guest) ;
        emailid=(TextView)view.findViewById(R.id.tv_email) ;
       // loginPrefsEditor = loginPreferences.edit();
        prepareListData();
        expListView = (ExpandableListView) this.view.findViewById(R.id.lvExp);



        if(app_sharedpreference.getsharedpref("username","notlogin")!=null) {
            String Username = app_sharedpreference.getsharedpref("username", "not");
            String Emailid = app_sharedpreference.getsharedpref("emailid", "not");
            if (Username.equals("not")) {
                Log.e("Shared_pref2","null"+Username);
            } else {

                setdata(Username,Emailid);
            }
        }
        else{
            Log.e("Shared_pref1","null");
        }

        //circle imageview

        //ImagePicker.setMinQuality(600, 600);
        // preparing list data

    }


    private void Showmessage(String message)
    {
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
        SharedPreferences sharePreference = context.getSharedPreferences(preFile, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePreference.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharePreference = context.getSharedPreferences(preFile, MODE_PRIVATE);
        return sharePreference.getString(preferenceName, defaultValue);
    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

    }


    private void showMessage(String clicked) {
        Toast.makeText(context, clicked, Toast.LENGTH_SHORT).show();
    }


    public void setdata(String username, String email) {
        Fname = username;

Log.e("Username",username);
        textViewName.setText(username);
emailid.setText(email);


    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void itemClicked(View view, int groupview, int childview)
    {

        try {

            Intent i = new Intent(getActivity(), CategoryListActivity.class);
            startActivity(i);
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }


      /*  CategoryListFragment categoryListFragment = new CategoryListFragment();
        replaceFragment(categoryListFragment,null);
        mDrawerLayout.closeDrawer(Gravity.LEFT);
*/

    }


    private void replaceFragment(Fragment newFragment, String tag) {

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.drawer_layout, newFragment, tag).addToBackStack(tag);
        transaction.commit();
    }



    private static class Trust implements X509TrustManager {

        /**
         * {@inheritDoc}
         */
        @Override
        public void checkClientTrusted(final X509Certificate[] chain, final String authType)
                throws CertificateException {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void checkServerTrusted(final X509Certificate[] chain, final String authType)
                throws CertificateException {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

    }

    private void prepareListData() {
        getCategory();

//            listDataHeader = new ArrayList<String>();
//            listDataChild = new HashMap<String, List<String>>();
//            listDataHeader.add("Home");
//            listDataHeader.add("Automobile");
//            listDataHeader.add("Barber");
//            listDataHeader.add("Dairy Product");
//            listDataHeader.add("Electronics Repair");
//            listDataHeader.add("Flower Shops");
//            listDataHeader.add("Funeral Places");
//
//            // Adding child data
//            List<String> top250 = new ArrayList<String>();
//            top250.add("Woman's Clothings");
//            top250.add("Man's Clothings");
//            top250.add("Electronics");
//            top250.add("Home and Garden");
//            top250.add("Jwellery and Health");
//            top250.add("Automotive");
//            top250.add("Beauty and Health");
//            top250.add("Toys, Kids and Baby");
//            top250.add("Bags and Shoes");
//            top250.add("Sports and Outdoor");
//            top250.add("Phone and Accessories");
//            top250.add("Computer and Networking");
//            top250.add("VIEW ALL CATEGORIES");
//
//            List<String> Settings_data = new ArrayList<String>();
//            Settings_data.add("Groceries");
//            Settings_data.add("Restaurant");
//        List<String> home = new ArrayList<String>();
//
//            List<String> account = new ArrayList<String>();
//            List<String> ratethisapp = new ArrayList<String>();
//            List<String> help_center = new ArrayList<String>();
//            List<String> share_app = new ArrayList<String>();
//            listDataChild.put(listDataHeader.get(0), home);
//        //listDataChild = new HashMap<String, List<String>>();
////            for (int i = 0; i < listDataHeader.size(); i++) {
////                listDataChild.put(listDataHeader.get(i), Settings_data);
////            }
//            listDataChild.put(listDataHeader.get(1), Settings_data); // Header, Child data
//            listDataChild.put(listDataHeader.get(2), Settings_data); // Header, Child data
//            listDataChild.put(listDataHeader.get(3), Settings_data);
//            listDataChild.put(listDataHeader.get(4), Settings_data); // Header, Child data
//            listDataChild.put(listDataHeader.get(5), Settings_data); // Header, Child data
//            listDataChild.put(listDataHeader.get(6), Settings_data);
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
                                }
                                set_expandable_adapter_data();
                            }
                    }

                });

    }


    private ArrayList<SubCategory> getSubCategoryArrayList(String categoryId)
    {
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










