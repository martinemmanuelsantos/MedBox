package com.martinemmanuelsantos.medbox.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.martinemmanuelsantos.medbox.R;


/**
 * Created by nutel on 1/17/2017.
 */

public class IconSelectorSwipeAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context context;
    private int[] resources;

    public IconSelectorSwipeAdapter(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TypedArray typedArray = context.getResources().obtainTypedArray(R.array.icons);
        int count = typedArray.length();
        resources = new int[count];
        for (int i = 0; i < resources.length; i++) {
            resources[i] = typedArray.getResourceId(i, 0);
        }
    }

    @Override
    public int getCount() {
        return resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_icon_selector, container, false);
        ImageView image = (ImageView) view.findViewById(R.id.image_view_icon_selector_icon);
        image.setImageResource(resources[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }

}
