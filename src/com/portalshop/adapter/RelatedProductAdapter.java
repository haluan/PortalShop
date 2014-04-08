package com.portalshop.adapter;

import java.util.ArrayList;

import com.portalshop.model.Barang;
import com.portalshop.util.DrawableManager;
import com.portalshop.zendy.DetailProduct;
import com.portalshop.zendy.R;
import com.portalshop.zendy.R.id;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class RelatedProductAdapter extends ArrayAdapter<Barang>{
	private LayoutInflater mInflater;
	private ArrayList<Barang> mList;
    private Context mCon;
    private int mViewResourceId;
    private DrawableManager manager;
	public RelatedProductAdapter(Context context, int textViewResourceId,ArrayList<Barang> list,DrawableManager manager) {
		super(context, textViewResourceId);
		mCon=context;
		mViewResourceId=textViewResourceId;
		mList=list;
		this.manager=manager;
		mInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
    public int getCount() {
            return mList.size();
    }

    @Override
    public Barang getItem(int position) {
            return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
            return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
    	convertView = mInflater.inflate(mViewResourceId, null);
		ImageView image=(ImageView)convertView.findViewById(R.id.relatedimage);
		RatingBar rate=(RatingBar)convertView.findViewById(R.id.relatedrating);
		TextView nama=(TextView)convertView.findViewById(R.id.relatedname);
		TextView jumlah=(TextView)convertView.findViewById(R.id.relatedjumrate);
		
		manager.fetchDrawableOnThread(getItem(position).getPict(), image);
		setRate(getItem(position).getRater(),  rate);
		nama.setText(getItem(position).getNama());
		jumlah.setText(getItem(position).getJml_rate()+"");
		LinearLayout parent1=(LinearLayout)convertView.findViewById(R.id.relatedParent);
		parent1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(mCon, DetailProduct.class);
				i.putExtra("pict", mList.get(position).getPict());
				i.putExtra("outlet", mList.get(position).getOutlet());
				i.putExtra("nama", mList.get(position).getNama());
				i.putExtra("kategori", mList.get(position).getKetgori());
				i.putExtra("id", mList.get(position).getId());
				i.putExtra("diskon", mList.get(position).getDiskon());
				i.putExtra("deskripsi", mList.get(position).getDeskripsi());
				i.putExtra("harga", mList.get(position).getHarga());
				i.putExtra("jmlRate", mList.get(position).getJml_rate());
				i.putExtra("rater", mList.get(position).getRater());
				i.putExtra("namaOutlet", mList.get(position).getNamaOutlet());
				i.putExtra("lantai", mList.get(position).getLantai());
				i.putExtra("lokasi", mList.get(position).getLokasi());
				i.putExtra("email", mList.get(position).getEmail());
				i.putExtra("telp", mList.get(position).getTelp());
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				DetailProduct.currentBarang=mList.get(position);
				mCon.startActivity(i);
			}
		});
    	return convertView;
    }
    public void setRate(float rater,RatingBar rate){
		float result=rater;
		rate.setEnabled(false);
		if(result>4.7){
			rate.setRating(5);
		}else if(result>=4.3){
			rate.setRating(4.5f);
		}else if(result>3.7){
			rate.setRating(4);
		}else if(result>=3.3){
			rate.setRating(3.5f);
		}else if(result>2.7){
			rate.setRating(3);
		}else if(result>=2.3){
			rate.setRating(2.5f);
		}else if(result>1.7){
			rate.setRating(2);
		}else if(result>=1.3){
			rate.setRating(1.5f);
		}else if(result>0.7){
			rate.setRating(1);
		}else if(result>0.2){
			rate.setRating(0.5f);
		}else{
			rate.setRating(0);
		}
	}
}
