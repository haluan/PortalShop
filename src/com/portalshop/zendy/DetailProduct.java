package com.portalshop.zendy;
import java.util.ArrayList;

import com.portalshop.adapter.BookmarkAdapter;
import com.portalshop.adapter.ImageAdapter;
import com.portalshop.adapter.RelatedProductAdapter;
import com.portalshop.customview.CustomAutoCompleteView;
import com.portalshop.customview.NoDefaultSpinner;
import com.portalshop.model.Bookmark;
import com.portalshop.model.Rate;
import com.portalshop.model.Barang;
import com.portalshop.util.DrawableManager;
import com.portalshop.util.Utility;
import com.portalshop.xmlparser.DatabaseHandler;
import com.portalshop.xmlparser.DetailProductHandler;
import com.portalshop.xmlparser.ExecuteStream;
import com.portalshop.xmlparser.HistoryHandler;
import com.portalshop.xmlparser.ListProductHandler;
import com.portalshop.xmlparser.ListRateHandler;
import com.portalshop.xmlparser.ParsingXML;
import com.portalshop.xmlparser.SearchHandler;
import com.portalshop.xmlparser.constant;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class DetailProduct extends Activity{
	private static Drawable dr;
	private String id,nama,date,diskon,deskripsi,stok,pict,outlet,ketgori,namaOutlet,acount,lantai,lokasi,email,telp;
	private double harga;
	private float jmlRate;
	private float rater;
	private final DetailProduct this_class=this;
	private ArrayList<String> listUrl;
	private ArrayList<String> drwList;
	private ArrayList<Rate> listRate;
	private ArrayList<Barang> listRelated,listRelatedAll;
	private HistoryHandler historyDb=new HistoryHandler(this);

	private RatingAdapter rateAdapter;
	private DrawableManager drwManager;
	private ImageAdapter galeryAdapter;
	private RelatedProductAdapter relatedAdapter;
	private ImageView go;
	private ImageView remove;
	private CustomAutoCompleteView auto;
	private ArrayAdapter autoCompleteAdapter;
	private NoDefaultSpinner menu;
	Dialog rateDialog;
	LinearLayout rateButton,rateResult;
	public static Barang currentBarang;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Account[] accounts=AccountManager.get(DetailProduct.this).getAccountsByType("com.google");
        acount=accounts[0].name;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.loadinglayout);
		((LinearLayout)findViewById(R.id.loading)).setVisibility(View.VISIBLE);
		((LinearLayout)findViewById(R.id.eror)).setVisibility(View.GONE);
        Bundle b=getIntent().getExtras();
        pict=b.getString("pict");
		outlet=b.getString("outlet");
		nama=b.getString("nama");
		ketgori=b.getString("kategori");
		id=b.getString("id");
		diskon=b.getString("diskon");
		deskripsi=b.getString("deskripsi");
		stok=b.getString("stok");
		harga=b.getDouble("harga");
		jmlRate=b.getFloat("jmlRate");
		rater=b.getFloat("rater");
		namaOutlet=b.getString("namaOutlet");
		lantai=b.getString("lantai");
		lokasi=b.getString("lokasi");
		email=b.getString("email");
		telp=b.getString("telp");
		drwManager=new DrawableManager();
		new GetData().execute();
		
	}
	public void initView(){
		setContentView(R.layout.detailproduct);
		Button Go=(Button)findViewById(R.id.buttonGoto);
		Go.setOnClickListener(listener);
		galeryAdapter=new ImageAdapter(this_class, drwList);
		Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        gallery.setAdapter(galeryAdapter);
        gallery.setSelection(drwList.size()/2);
        gallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	ImageView img=(ImageView)v;
            	dr=img.getDrawable();
            	Intent i = new Intent();
            	i.setClass(DetailProduct.this, TouchImageViewActivity.class);
            	startActivity(i);
            }
        });
        
        rateButton=(LinearLayout)findViewById(R.id.ratebutton);
		rateButton.setVisibility(View.GONE);
		rateResult=(LinearLayout)findViewById(R.id.linearLayout5);
		rateResult.setVisibility(View.GONE);

        rateAdapter=new RatingAdapter(getApplicationContext(), R.layout.ratelistadapter,listRate);
        ListView listRating=(ListView)findViewById(R.id.listViewRate);
        listRating.setAdapter(rateAdapter);
        Utility.setListViewHeightBasedOnChildren(listRating);
        boolean has=false;
        for(Rate a:listRate){
        	if(a.getUsername().equals(acount)){
        		has=true;
        		((RatingBar)findViewById(R.id.myrate)).setEnabled(false);
        		((RatingBar)findViewById(R.id.myrate)).setRating(a.getRate());
        		break;
        	}
        }				
		if(has){
			rateResult.setVisibility(View.VISIBLE);
		}
		if(!has){
			rateButton.setVisibility(View.VISIBLE);
			rateButton.setOnClickListener(listener);
			((RatingBar)findViewById(R.id.emptyRate)).setEnabled(false);
		}
		
		relatedAdapter=new RelatedProductAdapter(getApplicationContext(), R.layout.relatedproductadapter, listRelated, drwManager);
		ListView listRelated=(ListView)findViewById(R.id.listViewrelated);
		listRelated.setAdapter(relatedAdapter);
		listRelated.setItemsCanFocus(false);
		listRelated.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(DetailProduct.this,"sdacvsd", 5).show();
			}
		});
		
		ImageView thum=(ImageView)findViewById(R.id.thumbnail);
        drwManager.fetchDrawableOnThread(pict, thum);
		TextView ProductName=(TextView)findViewById(R.id.namaProduct);
		ProductName.setText(nama);
		((TextView)findViewById(R.id.namaOutlet)).setText(namaOutlet);
		((TextView)findViewById(R.id.infoOutlet)).setText(lokasi+"\nPhone : "+telp+"\nEmail : "+email);
		((TextView)findViewById(R.id.harga)).setText("Rp."+harga+"");
		((TextView)findViewById(R.id.jumrate)).setText(diskon+"%");
		((TextView)findViewById(R.id.moreby)).setText("More By "+namaOutlet);
		setRate(rater);
		if(this.listRelated.size()==0){
			((Button)findViewById(R.id.moreButton)).setVisibility(View.GONE);
			((TextView)findViewById(R.id.moreby)).setText("");
		}
        ((Button)findViewById(R.id.moreButton)).setOnClickListener(listener);
        ((TextView)findViewById(R.id.textDeskripsi)).setText(unescape(deskripsi));
        Button bookmark=(Button)findViewById(R.id.bookmark);
        bookmark.setOnClickListener(listener);
        ((ImageView)findViewById(R.id.home)).setOnClickListener(listener);
        initAutoComplete();
        initMenu();
	}
	private String unescape(String description) {
		if(description==null){
			return "";
		}else{
			return description.replaceAll("---", "\\\n");
		}
    }
	public void setRate(float rater){
		float result=rater;
		Log.d("rate",rater+" "+jmlRate);
		RatingBar rate=(RatingBar)findViewById(R.id.ProductRate);
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
	public static Bitmap getBitmap(){
		return ((BitmapDrawable)dr).getBitmap();
	}
	
	public class GetData extends AsyncTask<Void, Void, Void>{
		  ProgressDialog dialog;
		  boolean exceptio=false;
			//@Override
			protected void onPreExecute() {
				
			}
			
			//@Override
			protected Void doInBackground(Void... params) {
				//get  screenshot detail product
				try{
				ParsingXML parser=new ParsingXML(this_class, constant.GET_DETAIL_PRODUCT,id);
				parser.parse();
				DetailProductHandler handler=(DetailProductHandler)parser.getMyExampleHandler();
				listUrl=handler.getParsedData().getList();
				drwList=new ArrayList<String>();
				for(String url:listUrl){
					drwList.add(url);
				}
				
				//get rate and review
				parser=new ParsingXML(this_class,constant.GET_PRODUCT_RATE,id);
				parser.parse();
				ListRateHandler handlerreview=(ListRateHandler)parser.getMyExampleHandler();
				listRate=handlerreview.getParsedData().getList();
				
				//get related product
				parser=new ParsingXML(this_class,constant.GET_RELATED_PRODUCT,outlet+"-"+nama);
				parser.parse();
				ListProductHandler handlerrelated=(ListProductHandler)parser.getMyExampleHandler();
				listRelatedAll=handlerrelated.getParsedData().getList();
				listRelated=new ArrayList<Barang>();
				int i=0;
				if(listRelatedAll!=null){
				for(Barang b:listRelatedAll){
					if(i<2){
						listRelated.add(b);
					}else{
						break;
					}
					i++;
				}}
				}catch (Exception e) {
					exceptio=true;
					e.printStackTrace();
					//this.cancel(true);
				}
				return null;
			}

			//@Override
			protected void onPostExecute(Void unused) {
				if(exceptio){
					((LinearLayout)findViewById(R.id.loading)).setVisibility(View.GONE);
					((LinearLayout)findViewById(R.id.eror)).setVisibility(View.VISIBLE);
					((Button)findViewById(R.id.retry)).setOnClickListener(listener);
				}else{
					initView();
				}
			}		  
	}
	
	
	
	
	/* Method to initialize AutoComplete Search TextView
	*
	**/
	public void initAutoComplete(){
    	go=(ImageView)findViewById(R.id.goButton);
		remove=(ImageView)findViewById(R.id.removeButton);
		remove.setOnClickListener(listener);
		go.setOnClickListener(listener);
		go.setVisibility(View.GONE);
		remove.setVisibility(View.GONE);
		
		autoCompleteAdapter = new ArrayAdapter(DetailProduct.this, android.R.layout.simple_dropdown_item_1line);
    	autoCompleteAdapter.setNotifyOnChange(true); // This is so I don't have to manually sync whenever changed
    	auto = (CustomAutoCompleteView) findViewById(R.id.autoCompleteSearch);
    	auto.setThreshold(3);
    	auto.setAdapter(autoCompleteAdapter);
    	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(auto.getWindowToken(), 0);
		auto.setCursorVisible(false);
        auto.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				go.setVisibility(View.VISIBLE);
				remove.setVisibility(View.VISIBLE);
				auto.setCursorVisible(true);
				return false;
			}
		});
        auto.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode==KeyEvent.KEYCODE_ENTER){
					if(!auto.getText().toString().isEmpty()){
						ParsingXML parseArival=new ParsingXML(DetailProduct.this, constant.GET_SEARCH_RESULT,auto.getText().toString());
						try {
							parseArival.parse();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							final AlertDialog alertDialog = new AlertDialog.Builder(DetailProduct.this).create();
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
						ListProduct.list=((ListProductHandler)parseArival.getMyExampleHandler()).getParsedData().getList();
						Intent i=new Intent(DetailProduct.this, ListProduct.class);
						i.putExtra("pesan", "Search Result		");
						startActivity(i);
					}
				}
				return false;
			}
		});
        auto.addTextChangedListener(textChecker);
    }
	/* Class TextWatcher to cek text change on AutoComplete TextView
	*
	**/
	final TextWatcher textChecker = new TextWatcher() {
    	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    	public void onTextChanged(CharSequence s, int start, int before, int count){
			autoCompleteAdapter.clear();
			callPHP();
		}
    	@Override
		public void afterTextChanged(Editable s) {}
	};
	/* Method to get autocomplete data from server
	*
	**/
	private void callPHP(){
		ParsingXML p=new ParsingXML(this, constant.GET_SEARCH,auto.getText().toString());
		try {
			p.parse();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			final AlertDialog alertDialog = new AlertDialog.Builder(DetailProduct.this).create();
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
		ArrayList<String> listHistory=historyDb.getHIstoryLike(auto.getText().toString());
		for(String h:listHistory){
			autoCompleteAdapter.add(h);
		}
		ArrayList<String> list=((SearchHandler)p.getMyExampleHandler()).getParsedData().getList();
		for(String a:list){
			autoCompleteAdapter.add(a);
		}
	}
	
	
	
	/* Method to Initialize Menu Component
	*
	**/
	public void initMenu(){
    	menu=(NoDefaultSpinner)findViewById(R.id.spinerMenu);
		final ArrayAdapter m=ArrayAdapter.createFromResource(DetailProduct.this,R.array.menu, android.R.layout.simple_spinner_item);
		m.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		menu.setAdapter(m);
		menu.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(menu.getSelectedItemId()==1){
					final Dialog book=new Dialog(DetailProduct.this);
					book.setContentView(R.layout.bookmarkdialog);
					DatabaseHandler db=new DatabaseHandler(DetailProduct.this);
					final ListView bookmark=(ListView)book.findViewById(R.id.listbookmark);
					BookmarkAdapter adapter=new BookmarkAdapter(DetailProduct.this, R.layout.bookmarkadapter, db.getAllContacts(),book);
					bookmark.setAdapter(adapter);
					book.setTitle("Bookmarked Item");
					book.show();
				}else {
					Intent i=new Intent(DetailProduct.this, Denah.class);
					startActivity(i);
				}
				menu.setAdapter(m);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    }
	
	
	
	/* All onClick event listener
	*
	**/
	OnClickListener listener=new OnClickListener() {
		private String unescape(String description) {
	        return description.replaceAll("\n", "---");
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {	
			case R.id.ratebutton:
				rateDialog=new Dialog(DetailProduct.this);
				rateDialog.setContentView(R.layout.dialog);
				rateDialog.setTitle("			Rate and Review");
				Button cancel=(Button)rateDialog.findViewById(R.id.rateCancel);
				Button ok=(Button)rateDialog.findViewById(R.id.rateOK);
				cancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						rateDialog.dismiss();
					}
				});
				ok.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						RatingBar rateBar=(RatingBar)rateDialog.findViewById(R.id.ratingBar);
						EditText title=(EditText)rateDialog.findViewById(R.id.rateTitle);
						EditText review=(EditText)rateDialog.findViewById(R.id.rateComment);
						float rate=rateBar.getRating();	
						String a=unescape(review.getText().toString());
						InputRate inpt=new InputRate(rate,acount, title.getText().toString(), unescape(review.getText().toString()));
						rateDialog.dismiss();
						inpt.execute();
					}
				});
				final TextView text=(TextView)rateDialog.findViewById(R.id.rateTitle);
				text.setOnKeyListener(new OnKeyListener() {
					
					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						// TODO Auto-generated method stub
						if(keyCode==KeyEvent.KEYCODE_ENTER){return true;}else return false;
						
					}
				});
				rateDialog.show();
				break;
			case R.id.moreButton:
				ListProduct.list=listRelatedAll;
				Intent i=new Intent(DetailProduct.this, ListProduct.class);
				i.putExtra("pesan", ((TextView)findViewById(R.id.moreby)).getText());
				startActivity(i);
				break;
			case R.id.bookmark:
				DatabaseHandler db=new DatabaseHandler(this_class);
				db.addContact(new Bookmark(id, nama, namaOutlet));
				Toast.makeText(DetailProduct.this,"Bokmarked",7).show();
				break;
			case R.id.retry:
				Intent i1=new Intent(DetailProduct.this, DetailProduct.class);
				i1.putExtra("pict", pict);
				i1.putExtra("outlet", outlet);
				i1.putExtra("nama", nama);
				i1.putExtra("kategori", ketgori);
				i1.putExtra("id", id);
				i1.putExtra("diskon", diskon);
				i1.putExtra("deskripsi", deskripsi);
				i1.putExtra("harga", harga);
				i1.putExtra("jmlRate", jmlRate);
				i1.putExtra("rater", rater);
				i1.putExtra("namaOutlet", namaOutlet);
				i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				DetailProduct.currentBarang=currentBarang;
				startActivity(i1);
				DetailProduct.this.finish();
				break;
			case R.id.removeButton:
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(auto.getWindowToken(), 0);
				auto.setCursorVisible(false);
				auto.setText("");
				go.setVisibility(View.GONE);
				remove.setVisibility(View.GONE);
				break;
			case R.id.goButton:
				if(!auto.getText().toString().isEmpty()){
					ParsingXML parseArival=new ParsingXML(DetailProduct.this, constant.GET_SEARCH_RESULT,auto.getText().toString());
					try {
						parseArival.parse();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						final AlertDialog alertDialog = new AlertDialog.Builder(DetailProduct.this).create();
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
					historyDb.addHistory(auto.getText().toString());
					ListProduct.list=((ListProductHandler)parseArival.getMyExampleHandler()).getParsedData().getList();
					i=new Intent(DetailProduct.this, ListProduct.class);
					i.putExtra("pesan", "Search Result		");
					startActivity(i);
				}
				break;
			case R.id.buttonGoto:
				
				Intent go=new Intent(this_class, LokasiActivity.class);
				go.putExtra("lantai",lantai);
				go.putExtra("outlet",outlet);
				startActivity(go);
				break;
			case R.id.home:
				Intent a=new Intent(DetailProduct.this, mainActivity.class);
				mainActivity.back=false;
				startActivity(a);
				break;
			default:
				break;
			}
		}
	};
	  
	public class InputRate extends AsyncTask<Void, Void, Void>{
		Toast a;
		float rate;
		String user,title,review,hasil;
		public InputRate(float rate,String user,String title,String review){
			this.rate=rate;
			this.user=user;
			this.title=title;
			this.review=review;
		}
		@Override
		protected void onPreExecute() {
				a=Toast.makeText(DetailProduct.this, "Uploading Review...",130);
				a.show();
		}
		@Override
		protected Void doInBackground(Void... arg0) {
			String url=constant.URL+"insertRate.php?id="+id+"&rate="+this.rate+"&user="+user+"&title="+title+"&review="+review;
			hasil=ExecuteStream.execute(url);
			return null;
		}
		@Override
		protected void onPostExecute(Void unused) {
			a.cancel();
			if(hasil.equals("sukses")){
				a=Toast.makeText(DetailProduct.this, "Review Uploaded ",10);
			}
			else{
				a=Toast.makeText(DetailProduct.this, "Review Upload Eror",10);
			}
			a.show();
		}
	  }
}
