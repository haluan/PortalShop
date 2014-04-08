package com.portalshop.zendy;


import java.util.ArrayList;

import com.portalshop.xmlparser.ExecuteStream;
import com.portalshop.xmlparser.ListProductHandler;
import com.portalshop.xmlparser.OutletHandler;
import com.portalshop.xmlparser.ParsingXML;
import com.portalshop.xmlparser.constant;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Denah extends Activity {
	private int selected=0;
	ArrayList<String> Outlet=new ArrayList<String>();
	public void onCreate(Bundle savedBudle){
		super.onCreate(savedBudle);
		setContentView(R.layout.denahall);
		ParsingXML parse=new ParsingXML(this, constant.GET_OUTLET);
		try {
			parse.parse();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Eror");
			alertDialog.setMessage("No Conections!");
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dialog, int which) {
			      // here you can add functions
				   alertDialog.dismiss();
			   }
			});
			alertDialog.show();
		}
		Outlet=((OutletHandler)parse.getMyExampleHandler()).getParsedData().getList();
		final ActionBar bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        bar.addTab(bar.newTab()
                .setText("Lantai G")
                .setTabListener(new TabListener(new TabContentFragment("Lantai G",0))));
        bar.addTab(bar.newTab()
                .setText("Lantai 1")
                .setTabListener(new TabListener(new TabContentFragment("Lantai 1",1))));
        bar.addTab(bar.newTab()
                .setText("Lantai 2")
                .setTabListener(new TabListener(new TabContentFragment("Lantai 2",2))));
        bar.addTab(bar.newTab()
                .setText("Lantai 3")
                .setTabListener(new TabListener(new TabContentFragment("Lantai 3",3))));

        if (savedBudle != null) {
            bar.setSelectedNavigationItem(savedBudle.getInt("tab", 0));
        }
	}
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }

	private class TabListener implements ActionBar.TabListener {
        private TabContentFragment mFragment;

        public TabListener(TabContentFragment fragment) {
            mFragment = fragment;
        }

        public void onTabSelected(Tab tab, FragmentTransaction ft) {
        	ft.add(R.id.container, mFragment, mFragment.getText());
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            ft.remove(mFragment);
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            Toast.makeText(Denah.this, "Reselected!", Toast.LENGTH_SHORT).show();
        }

    }

    private class TabContentFragment extends Fragment {
        private String mText;
        private int mLantai;
        public TabContentFragment(String text,int lantai) {
            this.mText= text;
            mLantai=lantai;
        }

        public String getText() {
            return mText;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	View fragView=null;
        	switch (mLantai) {
			case 0:
				fragView = inflater.inflate(R.layout.lantaig, container, false);
				AbsoluteLayout parent=(AbsoluteLayout)fragView.findViewById(R.id.lantai);
		        int child=parent.getChildCount();
		        for(int i=0;i<child;i++){
		        	View v=parent.getChildAt(i);
		        	boolean aktif=false;
		        	for(String a:Outlet){
		        		if(v.getTag().toString().equals(a)){
			        		aktif=true;
			        		break;
			        	}
		        	}
		        	if((!aktif)&&(!v.getTag().equals("info"))){
		        		v.setBackgroundResource(R.color.supertrans);
		        	}
		        }
				break;
			case 1:
				fragView = inflater.inflate(R.layout.lantai1, container, false);
				parent=(AbsoluteLayout)fragView.findViewById(R.id.lantai);
		        child=parent.getChildCount();
		        for(int i=0;i<child;i++){
		        	View v=parent.getChildAt(i);
		        	boolean aktif=false;
		        	for(String a:Outlet){
		        		if(v.getTag().toString().equals(a)){
			        		aktif=true;
			        		break;
			        	}
		        	}
		        	if((!aktif)&&(!v.getTag().equals("info"))){
		        		v.setBackgroundResource(R.color.supertrans);
		        	}
		        }
				break;
			case 2:
				fragView = inflater.inflate(R.layout.lantai2, container, false);
				parent=(AbsoluteLayout)fragView.findViewById(R.id.lantai);
		        child=parent.getChildCount();
		        for(int i=0;i<child;i++){
		        	View v=parent.getChildAt(i);
		        	boolean aktif=false;
		        	for(String a:Outlet){
		        		if(v.getTag().toString().equals(a)){
			        		aktif=true;
			        		break;
			        	}
		        	}
		        	if((!aktif)&&(!v.getTag().equals("info"))){
		        		v.setBackgroundResource(R.color.supertrans);
		        	}
		        }
				break;
			case 3:
				fragView = inflater.inflate(R.layout.lantai3, container, false);
				parent=(AbsoluteLayout)fragView.findViewById(R.id.lantai);
		        child=parent.getChildCount();
		        for(int i=0;i<child;i++){
		        	View v=parent.getChildAt(i);
		        	boolean aktif=false;
		        	for(String a:Outlet){
		        		if(v.getTag().toString().equals(a)){
			        		aktif=true;
			        		break;
			        	}
		        	}
		        	if((!aktif)&&(!v.getTag().equals("info"))){
		        		v.setBackgroundResource(R.color.supertrans);
		        	}
		        }
				break;
			default:
				break;
			}      
        
            return fragView;
        }

    }
    public void onclick(View v){
    	ParsingXML denah=new ParsingXML(this, constant.GET_LIST_DENAH,v.getTag().toString());
    	try {
			denah.parse();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Eror");
			alertDialog.setMessage("No Conections!");
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dialog, int which) {
			      // here you can add functions
				   alertDialog.dismiss();
			   }
			});
			alertDialog.show();
		}
		ListProduct.list=((ListProductHandler)denah.getMyExampleHandler()).getParsedData().getList();
		if(ListProduct.list!=null){
			String nama=ListProduct.list.get(0).getNamaOutlet();
			Intent i=new Intent(this, ListProduct.class);
			i.putExtra("pesan", nama+" Product		");
			startActivity(i);
		}else{
			final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Information");
			alertDialog.setMessage("Sorry, This Outlet Not Joined Yet");
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dialog, int which) {
			      // here you can add functions
				   alertDialog.dismiss();
			   }
			});
			alertDialog.show();
		}
    }
}
