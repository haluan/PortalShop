package com.portalshop.adapter;

import java.util.ArrayList;

import com.portalshop.util.ImageDownloader;
import com.portalshop.zendy.R;
import com.portalshop.zendy.R.styleable;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    int mGalleryItemBackground;
    private Context mContext;
    private ArrayList<String> mImageIds;
    private ImageDownloader down=new ImageDownloader();
    public ImageAdapter(Context c,ArrayList<String> list) {
        mContext = c;
        mImageIds=list;
        TypedArray attr = mContext.obtainStyledAttributes(R.styleable.HelloGallery);
        mGalleryItemBackground = attr.getResourceId(
                R.styleable.HelloGallery_android_galleryItemBackground, 0);
        attr.recycle();
    }

    public int getCount() {
        return mImageIds.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        down.download(mImageIds.get(position), imageView);
        //imageView.setImageDrawable(mImageIds.get(position));
        imageView.setLayoutParams(new Gallery.LayoutParams(350, 300));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundResource(mGalleryItemBackground);
        return imageView;
    }
}