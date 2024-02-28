package com.example.appyugioh.interfacegraphique;
import android.content.Context;
import android.support.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.example.appyugioh.R;

public class ImagePagerAdapter extends PagerAdapter {
    private Context mContext;
    private int[] mImages;

    public ImagePagerAdapter(Context context, int[] images) {
        mContext = context;
        mImages = images;
    }

    @Override
    public int getCount() {
        return mImages.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.mainactivity, container, false);

        ImageView imageView = itemView.findViewById(R.id.imageView);
        imageView.setImageResource(mImages[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
