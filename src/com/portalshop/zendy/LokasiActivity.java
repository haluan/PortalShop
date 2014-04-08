package com.portalshop.zendy;

import com.portalshop.zendy.mainActivity.GetData;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class LokasiActivity extends Activity{
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b=getIntent().getExtras();
        final String outlet=b.getString("outlet");
        String[] lantai=b.getString("lantai").split(",");
        switch (lantai.length) {
		case 1:
			if(lantai[0].equals("1")){
				setContentView(R.layout.lantai1);
			}else if(lantai[0].equals("2")){
				setContentView(R.layout.lantai2);
			}else if(lantai[0].equalsIgnoreCase("g")){
				setContentView(R.layout.lantaig);
			}else if(lantai[0].equals("3")){
				setContentView(R.layout.lantai3);
			}
			View text = findViewById(R.id.textView1);
            Animation blinktext = AnimationUtils.loadAnimation(this, R.anim.blink);
            text.startAnimation(blinktext);
			AbsoluteLayout parent=(AbsoluteLayout)findViewById(R.id.lantai);
	        int child=parent.getChildCount();
	        for(int i=0;i<child;i++){
	        	View v=parent.getChildAt(i);
	        	v.setEnabled(false);
	        	if(v.getTag().toString().equals(outlet)){
	        		ImageView myImageView = (ImageView)v;
	        		myImageView.setBackgroundResource(R.color.green);
	                Animation blink = AnimationUtils.loadAnimation(this, R.anim.blink);
	                 myImageView.startAnimation(blink);
	        	}
	        }
			break;
		case 2:
			final Dialog pilih=new Dialog(this);
			final ListView pilihan=new ListView(this);
			pilih.setTitle("Please Choose Which Floor");
			ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
			for(String a:lantai){
				adapter.add("L "+a.toUpperCase());
			}
			pilihan.setAdapter(adapter);
			pilih.setContentView(pilihan);
			pilihan.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					String l=pilihan.getItemAtPosition(arg2).toString().toLowerCase().split(" ")[1];
					if(l.equalsIgnoreCase("1")){
						setContentView(R.layout.lantai1);
					}else if(l.equalsIgnoreCase("2")){
						setContentView(R.layout.lantai2);
					}else if(l.equalsIgnoreCase("g")){
						setContentView(R.layout.lantaig);
					}else if(l.equalsIgnoreCase("3")){
						setContentView(R.layout.lantai3);
					}
					pilih.dismiss();
					View text = findViewById(R.id.textView1);
		            Animation blinktext2 = AnimationUtils.loadAnimation(LokasiActivity.this, R.anim.blink);
		            text.startAnimation(blinktext2);
					AbsoluteLayout parent=(AbsoluteLayout)findViewById(R.id.lantai);
			        int child=parent.getChildCount();
			        for(int i=0;i<child;i++){
			        	View v=parent.getChildAt(i);
			        	v.setEnabled(false);
			        	if(v.getTag().toString().equals(outlet)){
			        		ImageView myImageView = (ImageView)v;
			        		myImageView.setBackgroundResource(R.color.green);
			                Animation blink = AnimationUtils.loadAnimation(LokasiActivity.this, R.anim.blink);
			                 myImageView.startAnimation(blink);
			        	}
			        }
				}
			});
			pilih.show();
			break;
		default:
			break;
		}
        //Toast.makeText(this,outlet,10).show();
        

    }
}
