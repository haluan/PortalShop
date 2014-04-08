package com.portalshop.zendy;

import java.util.ArrayList;

import com.portalshop.model.Rate;
import com.portalshop.zendy.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class RatingAdapter extends ArrayAdapter<Rate>{
	private LayoutInflater mInflater;
    private Context mCon;
    private int mViewResourceId;
    private ArrayList<Rate> mList;
	public RatingAdapter(Context context, int resource,ArrayList<Rate> list) {
		super(context, resource,list);
		mInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mCon=context;
		mList=list;
		this.mViewResourceId=resource;
	}
	@Override
    public int getCount() {
            return mList.size();
    }

    @Override
    public Rate getItem(int position) {
            return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
            return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = mInflater.inflate(mViewResourceId, null);
            TextView title=(TextView)convertView.findViewById(R.id.listTitle);
            TextView comment=(TextView)convertView.findViewById(R.id.listComment);
            TextView date=(TextView)convertView.findViewById(R.id.listDate);
            RatingBar rate=(RatingBar)convertView.findViewById(R.id.listRate);
            title.setText(mList.get(position).getTitile());
            comment.setText(unescape(mList.get(position).getComment()));
            date.setText("By: "+mList.get(position).getUsername().split("@")[0]+" On: "+mList.get(position).getDate().split(" ")[0]);
            rate.setRating(mList.get(position).getRate());
            rate.setEnabled(false);
            return convertView;
    }
    private String unescape(String description) {
        return description.replaceAll("---", "\\\n");
    }

    
}
