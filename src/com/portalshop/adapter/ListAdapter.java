package com.portalshop.adapter;

import java.util.ArrayList;

import com.portalshop.model.Barang;
import com.portalshop.util.DrawableManager;
import com.portalshop.zendy.DetailProduct;
import com.portalshop.zendy.R;
import com.portalshop.zendy.R.id;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<Barang>{
	private LayoutInflater mInflater;
	private ArrayList<Barang> mList;
    private Context mCon;
    private int mViewResourceId;
    static Barang currentList;
    private DrawableManager manager;
	public ListAdapter(Context ctx,int resource,ArrayList<Barang> list,DrawableManager manager){
		super(ctx,resource,list);
		mInflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mList=list;
		mViewResourceId=resource;
		mCon=ctx;
		this.manager=manager;
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
            
            ImageView thum=(ImageView)convertView.findViewById(R.id.thumb);
            manager.fetchDrawableOnThread(mList.get(position).getPict(), thum);
            //down.download(mList.get(position).getPict(), thum);
            
            TextView namaPro=(TextView)convertView.findViewById(R.id.namaPro);
            namaPro.setText(mList.get(position).getNama());
            
            TextView namaOut=(TextView)convertView.findViewById(R.id.outletText);
            namaOut.setText(mList.get(position).getNamaOutlet());
            
            RatingBar rate=(RatingBar)convertView.findViewById(R.id.ratingBarList);
            setRate(mList.get(position).getRater(), rate);
            
            Button view=(Button)convertView.findViewById(R.id.viewlist);
            view.setId(position);
            
            view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//Toast.makeText(mCon, mList.get(position).getPict(), 15).show();
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
    public static Barang getlist(){
    	return currentList;
    }
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
	    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
	        bitmap.getHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);

	    final int color = 0xff424242;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(),bitmap.getHeight());
	    final RectF rectF = new RectF(rect);
	    final float roundPx = 12;

	    paint.setAntiAlias(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);

	    return output;
	  }
    public void setRate(float rater,RatingBar r){
		float result=rater;
		RatingBar rate=r;
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
