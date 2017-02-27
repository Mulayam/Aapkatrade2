package com.example.pat.aapkatrade.productdetail;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.pat.aapkatrade.R;
import com.koushikdutta.ion.Ion;
import java.util.ArrayList;

/**
 * Created by PPC16 on 10-Feb-17.
 */




public class ProductViewPagerAdapter extends PagerAdapter
{

     Context mContext;
    ArrayList<String> imageurl = new ArrayList<>();


    public ProductViewPagerAdapter(Context mContext, ArrayList<String>productImage_url)
    {

        this. imageurl=productImage_url;
        this.mContext=mContext;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }


    public int getCount()
    {

        return imageurl.size();

    }

    public boolean isViewFromObject(View view, Object object)
    {
        return view == ((LinearLayout) object);
    }

    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.viewpager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);

        System.out.println("position============" + position);

        Ion.with(imageView)
                .error(ContextCompat.getDrawable(mContext, R.drawable.ic_applogo1))
                .placeholder(ContextCompat.getDrawable(mContext, R.drawable.ic_applogo1))
                .load(imageurl.get(position));
        // imageView.setImageResource(R.drawable.banner_home);

        container.addView(itemView);

        return itemView;

    }


    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
