package com.portalshop.adapter;

import java.util.ArrayList;
import com.portalshop.model.Kategori;
import com.portalshop.zendy.R;
import com.portalshop.zendy.R.id;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class KategoriAdapter extends ArrayAdapter<Kategori>{
	private ArrayList<Kategori> mList;
	private LayoutInflater mInflater;
	private int mResource;
	Context mcon;
	public KategoriAdapter(Context context, int textViewResourceId, ArrayList<Kategori> objects) {
		super(context, textViewResourceId, objects);
		this.mList=objects;
		mcon=context;
		this.mInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mResource=textViewResourceId;
	}
	@Override
    public int getCount() {
            return mList.size();
    }
	@Override
	public boolean areAllItemsEnabled() {
	    return true;
	}

	@Override
	public boolean isEnabled(int position) {
	    return true;
	}

    @Override
    public Kategori getItem(int position) {
            return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
            return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
    	convertView = mInflater.inflate(mResource, null);
    	TextView text=(TextView)convertView.findViewById(R.id.kategori);
    	text.setText(mList.get(position).getNamaKategori().toUpperCase());
//    	((LinearLayout)convertView.findViewById(R.id.asd)).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Toast.makeText(mcon, "afcaf", 5).show();
//			}
//		});
    	return convertView;
    }
}
