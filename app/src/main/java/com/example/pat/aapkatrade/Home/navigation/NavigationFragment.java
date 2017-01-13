package com.example.pat.aapkatrade.Home.navigation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.example.pat.aapkatrade.R;

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
    String Fname,Lname,Dob;

    public static final String PREFS_NAME = "call_recorder";
    private SharedPreferences loginPreferences;
    List<String> categoryids;
    List<String> categoryname;


    public static SharedPreferences.Editor loginPrefsEditor;
    private Context context;
    TextView footer;
    RelativeLayout header;

    TextView textViewName,textViewmobile_no,textView_email;
    private ImageView imageViewGB;
    private ExpandableListView expListView;
    private ImageView edit_profile_imgview;
    private ExpandableListAdapter listAdapter;
    private ArrayList<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    ProgressDialog _progressDialog;

    public NavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_navigation, container, false);
        _progressDialog=new ProgressDialog(context);
        initView();



        return view;
    }





    private void initView() {
        //prepare textviewdata
        categoryname=new ArrayList<>();
        categoryids=new ArrayList<>();


        //sharedprefrance
        loginPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        prepareListData();
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

//circle imageview


        //ImagePicker.setMinQuality(600, 600);




        // preparing list data



        listAdapter = new ExpandableListAdapter(context, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);

        listAdapter.setClickListner(this);
    }


    private void Showmessage(String message) {


        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
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



    public void setdata(String username, String mobno, String email,String Lastname,String dob) {
        Fname=username;
        Lname=Lastname;
        Dob=dob;

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
            //showMessage("groupbiew: " + groupview + "\nchildview: " + childview);
            if((groupview==1)&(childview==0))
            {
                //Intent i=new Intent(getActivity(), ProductListActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //startActivity(i);

            }
            else if(groupview==0)
            {
              //  Intent i=new Intent(getActivity(), ProductListActivity.class);
                //startActivity(i);
            }
            else if(groupview==2 &childview==1)
            {
               // Intent i=new Intent(getActivity(),ProductListActivity.class);
                //startActivity(i);
            }
            else if(groupview==2 &childview==0)
            {
                // callSubscribedwebservice();
            }
            else if(groupview==5)
            {
               // Intent i=new Intent(getActivity(),ProductListActivity.class);
               // startActivity(i);
            }

            listAdapter.notifyDataSetChanged();
            mDrawerLayout.closeDrawers();


        } catch (Exception e) {
Log.e("Exception",e.toString());
        }
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

//    public  void setupSelfSSLCert() {
//        final Trust trust = new Trust();
//        final TrustManager[] trustmanagers = new TrustManager[]{trust};
//        SSLContext sslContext;
//        try {
//            sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, trustmanagers, new SecureRandom());
//            Ion.getInstance(context, "rest").getHttpClient().getSSLSocketMiddleware().setTrustManagers(trustmanagers);
//            Ion.getInstance(context, "rest").getHttpClient().getSSLSocketMiddleware().setSSLContext(sslContext);
//        } catch (final NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (final KeyManagementException e) {
//            e.printStackTrace();
//        }
//    }




    private void prepareListData() {



            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();

            // Adding child data
            listDataHeader.add("Groceries");
            listDataHeader.add("Restaurant");
            listDataHeader.add("Vegetables");
            listDataHeader.add("Sweet shops");
            listDataHeader.add("Tailors");
            listDataHeader.add("Barber");

            // Adding child data
            List<String> top250 = new ArrayList<String>();
            top250.add("Woman's Clothings");
            top250.add("Man's Clothings");
            top250.add("Electronics");
            top250.add("Home and Garden");
            top250.add("Jwellery and Health");
            top250.add("Automotive");
            top250.add("Beauty and Health");
            top250.add("Toys, Kids and Baby");
            top250.add("Bags and Shoes");
            top250.add("Sports and Outdoor");
            top250.add("Phone and Accessories");
            top250.add("Computer and Networking");
            top250.add("VIEW ALL CATEGORIES");

            List<String> Settings_data = new ArrayList<String>();
            Settings_data.add("Groceries");
            Settings_data.add("Restaurant");

            List<String> account = new ArrayList<String>();
            List<String> ratethisapp = new ArrayList<String>();
            List<String> help_center = new ArrayList<String>();
            List<String> share_app = new ArrayList<String>();

            listDataChild.put(listDataHeader.get(0), Settings_data); // Header, Child data
            listDataChild.put(listDataHeader.get(1), Settings_data); // Header, Child data
            listDataChild.put(listDataHeader.get(2), Settings_data);
            listDataChild.put(listDataHeader.get(3), Settings_data); // Header, Child data
            listDataChild.put(listDataHeader.get(4), Settings_data); // Header, Child data
            listDataChild.put(listDataHeader.get(5), Settings_data);
        }






    }










