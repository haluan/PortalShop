package com.portalshop.adapter;

import java.util.ArrayList;

import com.portalshop.model.Barang;
import com.portalshop.model.Bookmark;
import com.portalshop.xmlparser.DatabaseHandler;
import com.portalshop.xmlparser.ListProductHandler;
import com.portalshop.xmlparser.ParsingXML;
import com.portalshop.xmlparser.constant;
import com.portalshop.zendy.DetailProduct;
import com.portalshop.zendy.R;
import com.portalshop.zendy.R.id;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BookmarkAdapter extends ArrayAdapter<Bookmark>{
	private LayoutInflater mInflater;
	private ArrayList<Bookmark> mList;
    private Context mCon;
    private int mViewResourceId;
    private Dialog book;
	public BookmarkAdapter(Context context, int textViewResourceId,
			ArrayList<Bookmark> objects,Dialog book) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		mCon=context;
		mViewResourceId=textViewResourceId;
		mList=objects;
		this.book=book;
		mInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
    public int getCount() {
            return mList.size();
    }

    @Override
    public Bookmark getItem(int position) {
            return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
            return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
    	convertView = mInflater.inflate(mViewResourceId, null);
    	TextView t=(TextView)convertView.findViewById(R.id.textbookmark);
    	t.setText(mList.get(position).get_nama_barang()+" by "+mList.get(position).getOutlet());
    	ImageView go=(ImageView)convertView.findViewById(R.id.imageGo);
    	go.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ParsingXML p=new ParsingXML(mCon, constant.GET_LIST_PRODUCT, mList.get(position).get_id_barang());
				try {
					p.parse();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					final AlertDialog alertDialog = new AlertDialog.Builder(mCon).create();
					alertDialog.setTitle("Eror");
					alertDialog.setMessage("No Conections!");
					alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
					   public void onClick(DialogInterface dialog, int which) {
					      // here you can add functions
						   alertDialog.dismiss();
					   }
					});
					book.dismiss();
					alertDialog.show();
				}
				ArrayList<Barang> list=((ListProductHandler)p.getMyExampleHandler()).getParsedData().getList();
				
				if(list==null){
					book.dismiss();
					Toast.makeText(mCon, "Product Not Found", 3).show();
				}else{
					Barang b=list.get(0);
					Intent i=new Intent(mCon, DetailProduct.class);
					i.putExtra("pict", b.getPict());
					i.putExtra("outlet",b.getOutlet());
					i.putExtra("nama", b.getNama());
					i.putExtra("kategori", b.getKetgori());
					i.putExtra("id", b.getId());
					i.putExtra("diskon", b.getDiskon());
					i.putExtra("deskripsi", b.getDeskripsi());
					i.putExtra("harga", b.getHarga());
					i.putExtra("jmlRate", b.getJml_rate());
					i.putExtra("rater", b.getRater());
					i.putExtra("namaOutlet", b.getNamaOutlet());
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					DetailProduct.currentBarang=b;
					mCon.startActivity(i);
				}
				book.dismiss();
			}
			});
    	ImageView del=(ImageView)convertView.findViewById(R.id.imageDelete);
    	del.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatabaseHandler d=new DatabaseHandler(mCon);
				d.deleteContact(mList.get(position));
				mList.remove(mList.get(position));
				BookmarkAdapter.this.notifyDataSetChanged();
				book.dismiss();
			}
		});
    	return convertView;
    }
}
