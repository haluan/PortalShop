package com.portalshop.zendy;

import com.portalshop.util.TouchImageView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

public class TouchImageViewActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TouchImageView img = new TouchImageView(this);
        Bitmap snoop = DetailProduct.getBitmap();
        //BitmapFactory.decodeResource(getResources(), R.drawable.sample_1);
        img.setImageBitmap(snoop);
        img.setMaxZoom(4f);
        setContentView(img);
    }
}