package com.example.pat.aapkatrade.Home.navigation;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.pat.aapkatrade.Home.navigation.entity.CategoryHome;
import com.example.pat.aapkatrade.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.X509TrustManager;

/**
 * Created by Netforce on 7/12/2016.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter implements CompoundButton.OnCheckedChangeListener {

    private Context _context;
    private ArrayList<CategoryHome> _listDataHeader;
    private clickListner click;
    public static ToggleButton tg_button;
    ProgressDialog _progressDialog;

    public ExpandableListAdapter(Context context, ArrayList<CategoryHome> listDataHeader) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        _progressDialog = new ProgressDialog(context);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataHeader.get(groupPosition).getSubCategoryList().get(childPosititon).subCategoryName;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, parent, false);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        tg_button = (ToggleButton) convertView.findViewById(R.id.toggleButton);
        tg_button.setOnCheckedChangeListener(this);

        txtListChild.setText(childText);
        final View finalConvertView = convertView;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.itemClicked(finalConvertView, groupPosition, childPosition);
            }
        });
        if (groupPosition == 2 && childPosition == 0) {
            // tg_button.setVisibility(View.VISIBLE);

        } else {
            tg_button.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataHeader.get(groupPosition).getSubCategoryList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition).getCategoryName();
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        LayoutInflater infalInflater = (LayoutInflater) this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = infalInflater.inflate(R.layout.list_group, parent, false);
        final ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        final ImageView imageViewIcon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
        Ion.with(_context).load(_listDataHeader.get(groupPosition).getCategoryIconPath()).withBitmap().asBitmap()
                .setCallback(new FutureCallback<Bitmap>() {
                    @Override
                    public void onCompleted(Exception e, Bitmap result) {
                        imageViewIcon.setImageBitmap(result);
                    }
                });

//        if (_listDataChild.get(_listDataHeader.get(groupPosition).getCategoryName()).size() == 0) {
//            imageView.setVisibility(View.GONE);
//            final View finalConvertView = convertView;
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    click.itemClicked(finalConvertView, groupPosition, 0);
//                }
//            });
//        } else {
            if (isExpanded) {
                imageView.setImageResource(R.drawable.ic_chevron_primary);
                convertView.setBackgroundResource(R.color.dark_green);
                notifyDataSetChanged();
            } else {
                imageView.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
                notifyDataSetChanged();
            }
//        }


        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        // callSubscribedwebservice();

    }

    public interface clickListner {
        void itemClicked(View view, int groupview, int childview);
    }

    public void setClickListner(clickListner click) {
        this.click = click;
    }


    //    private void callSubscribedwebservice() {
//        SharedPreferences sharedpreferences = _context.getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
//        String emailid=sharedpreferences.getString("email",null);
//        if (ConnetivityCheck.isNetworkAvailable(_context) == true) {
//
//            if (emailid != null) {
//                _progressDialog.show();
//                String Subscribed_url = "https://netforcesales.com/eclipseexpress/web_api.php?type=subscribe&email=" + emailid;
//                Log.e("Subscribed_url", Subscribed_url);
//                setupSelfSSLCert();
//                Ion.with(_context)
//                        .load(Subscribed_url)
//                        .progressDialog(_progressDialog)
//                        .asJsonObject()
//
//                        .setCallback(new FutureCallback<JsonObject>() {
//                            public void onCompleted(Exception e, JsonObject result) {
//
//                                String status = result.get("status").getAsString();
//                                Log.e("Subscribed_url_response", status);
//
//
//                                String message = result.get("message").getAsString();
//                                if (message.contains("unsubscribed")) {
//                                    // ExpandableListAdapter.tg_button.setChecked(false);
//
//
//                                    _progressDialog.dismiss();
//                                    Log.e("toggle_off", message);
//                                } else { //ExpandableListAdapter.tg_button.setChecked(true);
//                                    _progressDialog.dismiss();
//                                    Log.e("toggle_on", message);
//
//                                }
//
//                                ShowMessage(message);
//
//
//                            }
//
//                        });
//            }
//        }
//        else {
//            ShowMessage("Their is no internet connection");
//        }
//
//    }
    private void ShowMessage(String message) {
        Toast.makeText(_context, message, Toast.LENGTH_SHORT).show();

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
//            Ion.getInstance(_context, "rest").getHttpClient().getSSLSocketMiddleware().setTrustManagers(trustmanagers);
//            Ion.getInstance(_context, "rest").getHttpClient().getSSLSocketMiddleware().setSSLContext(sslContext);
//        } catch (final NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (final KeyManagementException e) {
//            e.printStackTrace();
//        }
//    }
}