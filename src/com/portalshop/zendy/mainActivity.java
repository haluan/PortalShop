package com.portalshop.zendy;


import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.portalshop.adapter.BookmarkAdapter;
import com.portalshop.adapter.KategoriAdapter;
import com.portalshop.adapter.ListAdapter;
import com.portalshop.customview.CustomAutoCompleteView;
import com.portalshop.customview.NoDefaultSpinner;
import com.portalshop.model.Barang;
import com.portalshop.model.Iklan;
import com.portalshop.model.Kategori;
import com.portalshop.util.DrawableManager;
import com.portalshop.util.ImageDownloader;
import com.portalshop.xmlparser.DatabaseHandler;
import com.portalshop.xmlparser.HistoryHandler;
import com.portalshop.xmlparser.IklanHandler;
import com.portalshop.xmlparser.KategoriHandler;
import com.portalshop.xmlparser.ListProductHandler;
import com.portalshop.xmlparser.ParsedListProductDataSet;
import com.portalshop.xmlparser.ParsingXML;
import com.portalshop.xmlparser.SearchHandler;
import com.portalshop.xmlparser.constant;

public class mainActivity extends Activity {
    /** Called when the activity is first created. */
	GridView newarival,toprated;
	GridView bestdiscount;
	ListView categorie;
	public static ArrayList<Barang> daftarList;
	private LinearLayout  horizontalOuterLayout;
	private HorizontalScrollView horizontalScrollview;
	private TextView horizontalTextView;
	private ImageView go;
	private ImageView remove;
	private CustomAutoCompleteView auto;
	private ArrayAdapter autoCompleteAdapter;
	private NoDefaultSpinner menu;
	
	private int scrollMax;
	private int scrollPos =	0;
	private TimerTask clickSchedule;
	private TimerTask scrollerSchedule;
	private TimerTask faceAnimationSchedule;
	private ImageView clickedButton	=	null;
	private Timer scrollTimer		=	null;
	private Timer clickTimer		=	null;
	private Timer faceTimer         =   null;
	boolean balik=false,delay=false;
	static boolean back=false;
	private Boolean isFaceDown      =   true;
	private String[] nameArray = {"Apple", "Banana", "Grapes", "Orange", "Strawberry","Apple", "Banana"};
	private String[] imageNameArray = {"apple", "banana", "grapes", "orange", "strawberry","apple", "banana"};
    private DrawableManager manager=new DrawableManager();
	private ArrayList<Barang> listArival,listDiskon,listTopRated;
	private ArrayList<Kategori> listKategori;
	private ArrayList<Iklan> listIklan;
	private ArrayList<Iklan> listIklanGold;
	private ArrayList<Iklan> listIklanSilver;
	private ArrayList<Iklan> listIklanBroze;
	private HistoryHandler historyDb=new HistoryHandler(this); 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new GetData().execute();
    }
	
	
	/* Class AsyncTAsk to get Data from server
	*
	**/
    public class GetData extends AsyncTask<Void, Void, Void>{
    		Bundle b;
			@Override
			protected void onPreExecute() {
				setContentView(R.layout.loadinglayout);
				((LinearLayout)findViewById(R.id.loading)).setVisibility(View.VISIBLE);
				((LinearLayout)findViewById(R.id.eror)).setVisibility(View.GONE);
			}
			
			@Override
			protected Void doInBackground(Void... params) {
				b=getIntent().getExtras();
				try{
				if(b==null){
					ParsingXML parseArival=new ParsingXML(mainActivity.this, constant.GET_NEW_ARIVAL);
			        parseArival.parse();
			        ParsedListProductDataSet data=((ListProductHandler)parseArival.getMyExampleHandler()).getParsedData();
			        listArival=data.getList();
			        //get best diskon
			        ParsingXML parseDiskon=new ParsingXML(mainActivity.this, constant.GET_BEST_DISKON);
			        parseDiskon.parse();
			        listDiskon=((ListProductHandler)parseDiskon.getMyExampleHandler()).getParsedData().getList();
			        //get toprated
			        ParsingXML parseRated=new ParsingXML(mainActivity.this, constant.GET_TOP_RATED);
			        parseRated.parse();
			        listTopRated=((ListProductHandler)parseRated.getMyExampleHandler()).getParsedData().getList();
					
				}
				else{
					ParsingXML parseArival=new ParsingXML(mainActivity.this, constant.GET_NEW_ARIVAL,b.getString("id"));
			        parseArival.parse();
			        listArival=((ListProductHandler)parseArival.getMyExampleHandler()).getParsedData().getList();
			        //get best diskon
			        ParsingXML parseDiskon=new ParsingXML(mainActivity.this, constant.GET_BEST_DISKON,b.getString("id"));
			        parseDiskon.parse();
			        listDiskon=((ListProductHandler)parseDiskon.getMyExampleHandler()).getParsedData().getList();
			        //get toprated
			        ParsingXML parseRated=new ParsingXML(mainActivity.this, constant.GET_TOP_RATED,b.getString("id"));
			        parseRated.parse();
			        listTopRated=((ListProductHandler)parseRated.getMyExampleHandler()).getParsedData().getList();
					
				}
				//getKategori
		        ParsingXML parseKategori=new ParsingXML(mainActivity.this, constant.GET_KATEGORI);
		        parseKategori.parse();
		        listKategori=((KategoriHandler)parseKategori.getMyExampleHandler()).getParsedData().getList();
				
		        //get Iklan
		        listIklan=new ArrayList<Iklan>();
		        ParsingXML gold=new ParsingXML(mainActivity.this, constant.GET_IKLAN,"Gold");
		        gold.parse();
		        listIklanGold=((IklanHandler)gold.getMyExampleHandler()).getParsedData().getList();
		        ArrayList<Integer> genrandom;
		        Random generator=new Random();;
		        if(listIklanGold.size()>5){
			        genrandom=new ArrayList<Integer>();
					
			        for(int i=0;i<5;i++){
			        	while (true) {
			        		Integer r=generator.nextInt(listIklanGold.size());
			        		
				        	if(!genrandom.contains(r)){
				        		genrandom.add(r);
				        		listIklan.add(listIklanGold.get(r));
				        		Log.d("url", r+"Gold");
				        		break;
				        	}
						}
			        }
		        }else{
		        	for(Iklan i:listIklanGold){
		        		listIklan.add(i);
		        	}
		        }
		        
		        ParsingXML silver=new ParsingXML(mainActivity.this, constant.GET_IKLAN,"Silver");
		        silver.parse();
		        listIklanSilver=((IklanHandler)silver.getMyExampleHandler()).getParsedData().getList();
		        if(listIklanSilver.size()>3){
			        genrandom=new ArrayList<Integer>();
			        for(int i=0;i<3;i++){
			        	while (true) {
			        		Integer r=generator.nextInt(listIklanSilver.size());
			        		
				        	if(!genrandom.contains(r)){
				        		genrandom.add(r);
				        		listIklan.add(listIklanSilver.get(r));
				        		Log.d("url", r+"Silver");
				        		break;
				        	}
						}
			        }
		        }else{
		        	for(Iklan i:listIklanSilver){
		        		listIklan.add(i);
		        	}
		        }
		        
		        ParsingXML bronze=new ParsingXML(mainActivity.this, constant.GET_IKLAN,"Bronze");
		        bronze.parse();
		        listIklanBroze=((IklanHandler)bronze.getMyExampleHandler()).getParsedData().getList();
		        if(listIklanBroze.size()>2){
			        genrandom=new ArrayList<Integer>();
			        for(int i=0;i<2;i++){
			        	while (true) {
			        		Integer r=generator.nextInt(listIklanBroze.size());
			        		
				        	if(!genrandom.contains(r)){
				        		genrandom.add(r);
				        		listIklan.add(listIklanBroze.get(r));
				        		Log.d("url", r+"bronze");
				        		break;
				        	}
						}
			        }
		        }else{
		        	for(Iklan i:listIklanBroze){
		        		listIklan.add(i);
		        	}
		        }
				}catch (Exception e) {
					e.printStackTrace();
					((LinearLayout)findViewById(R.id.loading)).setVisibility(View.GONE);
					((LinearLayout)findViewById(R.id.eror)).setVisibility(View.VISIBLE);
					((Button)findViewById(R.id.retry)).setOnClickListener(listener);
					this.cancel(true);
				}
		        return null;
			}

			@Override
			protected void onPostExecute(Void unused) {
				setContentView(R.layout.home);
				//INIT MENU
				initMenu();
				//INIT HEADER
				if(b!=null){
					TextView header=(TextView)findViewById(R.id.Header);
					char head=b.getString("header").toUpperCase().charAt(0);
					String tail=b.getString("header").substring(1).toLowerCase();
					header.setText(head+tail);
				}
				if(back){
					((ImageView)findViewById(R.id.back_image)).setVisibility(View.VISIBLE);
					((LinearLayout)findViewById(R.id.back)).setOnClickListener(listener);
				}
				//INIT SEARCH KOMPONENT
				initAutoComplete();
		        //INIT LIST
				initListHome();
				//INIT ADVERTISING
				initAdvertising();
			}

		  
	  }
    
    
    
    
    /* All onClick event listener
	*
	**/
    private OnClickListener listener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.textNewComer:
				ListProduct.list=listArival;
				Intent i=new Intent(mainActivity.this, ListProduct.class);
				i.putExtra("pesan", ((TextView)v).getText());
				startActivity(i);
				break;
			case R.id.textDiskon:
				ListProduct.list=listDiskon;
				i=new Intent(mainActivity.this, ListProduct.class);
				i.putExtra("pesan", ((TextView)v).getText());
				startActivity(i);
				break;
			case R.id.TextToprated:
				ListProduct.list=listTopRated;
				i=new Intent(mainActivity.this, ListProduct.class);
				i.putExtra("pesan", ((TextView)v).getText());
				startActivity(i);
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
					ParsingXML parseArival=new ParsingXML(mainActivity.this, constant.GET_SEARCH_RESULT,auto.getText().toString());
					try {
						parseArival.parse();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						final AlertDialog alertDialog = new AlertDialog.Builder(mainActivity.this).create();
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
					i=new Intent(mainActivity.this, ListProduct.class);
					i.putExtra("pesan", "Search Result		");
					startActivity(i);
				}
				break;
			case R.id.back:
				i=new Intent(mainActivity.this, mainActivity.class);
				mainActivity.back=false;
				startActivity(i);
				break;
			case R.id.retry:
				mainActivity.this.finish();
				startActivity(new Intent(mainActivity.this, mainActivity.class));
				break;
			default:
				break;
			}
		}
	};
	
	
	
	/* Method to initialize List of Product
	*
	**/
    public void initListHome(){
    	
    	newarival=(GridView)findViewById(R.id.gridNewComers);
    	toprated=(GridView)findViewById(R.id.GridTopRated);
    	bestdiscount=(GridView)findViewById(R.id.gridDiscount);
		categorie=(ListView)findViewById(R.id.listKategori);
		if(listArival==null){
    		newarival.setVisibility(View.GONE);
    		(findViewById(R.id.noitem)).setVisibility(View.VISIBLE);
    	}else{
    		newarival.setVisibility(View.VISIBLE);
    		(findViewById(R.id.noitem)).setVisibility(View.GONE);
    	}
		
		ArrayList<Barang> temp=new ArrayList<Barang>();
		int i=0;
		if(listArival!=null){
			for(Barang brg:listArival){
				if(i<6){
					temp.add(brg);
				}else{
					break;
				}
				i++;
			}
		}
		ListAdapter arivalAdapter=new ListAdapter(mainActivity.this, R.layout.adapterlist, temp,manager);
		temp=new ArrayList<Barang>();
		i=0;
		if(listDiskon!=null){
			for(Barang brg:listDiskon){
				if(i<6){
					temp.add(brg);
				}else{
					break;
				}
				i++;
			}
		}
		ListAdapter diskonAdapter=new ListAdapter(mainActivity.this, R.layout.adapterlist, temp,manager);
		temp=new ArrayList<Barang>();
		i=0;
		if(listTopRated!=null){
			for(Barang brg:listTopRated){
				if(i<6){
					temp.add(brg);
				}else{
					break;
				}
				i++;
			}
		}
		ListAdapter ratedAdapter=new ListAdapter(mainActivity.this, R.layout.adapterlist, temp,manager);
		
		KategoriAdapter kategoriAdapater=new KategoriAdapter(mainActivity.this, R.layout.kategoriadapter,listKategori);
		
		
		newarival.setAdapter(arivalAdapter);
		bestdiscount.setAdapter(diskonAdapter);
		toprated.setAdapter(ratedAdapter);
		categorie.setAdapter(kategoriAdapater);
		categorie.setItemsCanFocus(false);
		categorie.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Intent i=new Intent(mainActivity.this, mainActivity.class);
				i.putExtra("id",listKategori.get(arg2).getIdKategori());
				i.putExtra("header", listKategori.get(arg2).getNamaKategori());
				mainActivity.back=true;
				startActivity(i);
			}
		});
		((TextView)findViewById(R.id.textNewComer)).setOnClickListener(listener);
		((TextView)findViewById(R.id.textDiskon)).setOnClickListener(listener);
		((TextView)findViewById(R.id.TextToprated)).setOnClickListener(listener);
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
		
		autoCompleteAdapter = new ArrayAdapter(mainActivity.this, android.R.layout.simple_dropdown_item_1line);
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
						ParsingXML parseArival=new ParsingXML(mainActivity.this, constant.GET_SEARCH_RESULT,auto.getText().toString());
						try {
							parseArival.parse();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							final AlertDialog alertDialog = new AlertDialog.Builder(mainActivity.this).create();
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
						Intent i=new Intent(mainActivity.this, ListProduct.class);
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
			final AlertDialog alertDialog = new AlertDialog.Builder(mainActivity.this).create();
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
		final ArrayAdapter m=ArrayAdapter.createFromResource(mainActivity.this,R.array.menu, android.R.layout.simple_spinner_item);
		m.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		menu.setAdapter(m);
		menu.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(menu.getSelectedItemId()==1){
					final Dialog book=new Dialog(mainActivity.this);
					book.setContentView(R.layout.bookmarkdialog);
					DatabaseHandler db=new DatabaseHandler(mainActivity.this);
					final ListView bookmark=(ListView)book.findViewById(R.id.listbookmark);
					bookmark.setItemsCanFocus(true);
					bookmark.setFocusable(false);
					BookmarkAdapter adapter=new BookmarkAdapter(mainActivity.this, R.layout.bookmarkadapter, db.getAllContacts(),book);
					bookmark.setAdapter(adapter);
					book.setTitle("Bookmarked Item");
					book.show();
				}
				else {
					Intent i=new Intent(mainActivity.this, Denah.class);
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
	
	
	
	/* Method to initialize Advertising Image
	*  AutoScroll, Adding Image etc
	**/
    public void initAdvertising(){
    	horizontalScrollview  = (HorizontalScrollView) findViewById(R.id.horiztonal_scrollview_id);
        horizontalOuterLayout =	(LinearLayout)findViewById(R.id.horiztonal_outer_layout_id);
        
        horizontalScrollview.setHorizontalScrollBarEnabled(false);
        addImagesToView();
        
		ViewTreeObserver vto 		=	horizontalOuterLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
            	horizontalOuterLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            	getScrollMaxAmount();
            	startAutoScrolling();            	          	
            }
        });
    }
	public void getScrollMaxAmount(){
    	scrollMax   = horizontalScrollview.getChildAt(0).getMeasuredWidth()-getWindowManager().getDefaultDisplay().getWidth();
    	
    }    
    public void startAutoScrolling(){
		if (scrollTimer == null) {
			scrollTimer					=	new Timer();
			final Runnable Timer_Tick 	= 	new Runnable() {
			    public void run() {
			    	moveScrollView();
			    }
			};
			if(scrollerSchedule != null){
				scrollerSchedule.cancel();
				scrollerSchedule = null;
			}
			scrollerSchedule = new TimerTask(){
				@Override
				public void run(){
					runOnUiThread(Timer_Tick);
				}
			};
			
			scrollTimer.schedule(scrollerSchedule,5000, 1);
			
		}
	}
    
    public void moveScrollView(){
    	Display display = getWindowManager().getDefaultDisplay();
    	Point size = new Point();
    	display.getSize(size);
    	int width = size.x;
    	if(horizontalScrollview.getScrollX()==scrollMax/2||horizontalScrollview.getScrollX()==0||horizontalScrollview.getScrollX()>=scrollMax){
    		scrollTimer=null;
    		if(horizontalScrollview.getScrollX()==0){
        		balik=false;
        	}
        	else if(horizontalScrollview.getScrollX()>=scrollMax){
        		balik=true;
        	}
    		startAutoScrolling();
    	}
    	if(balik==false){
    		scrollPos							= 	(int) (horizontalScrollview.getScrollX() + 1.0);
    	}
    	if(balik){
    		scrollPos							= 	(int) (horizontalScrollview.getScrollX() - 1.0);
		}
		horizontalScrollview.scrollTo(scrollPos, 0);
				
	}
    
    /** Adds the images to view. */
    public void addImagesToView(){
    	int pos=0;
		for (final Iklan i:listIklan){
			ImageView imageButton =	new ImageView(this);
		    ImageDownloader down=new ImageDownloader();
		    down.download(i.getPict(), imageButton);
			imageButton.setTag(i);
		    imageButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					String[] asd=i.getId_barang().split("-");
					if(asd.length==2){
						ParsingXML p=new ParsingXML(mainActivity.this, constant.GET_LIST_PRODUCT, i.getId_barang());
						try {
							p.parse();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							final AlertDialog alertDialog = new AlertDialog.Builder(mainActivity.this).create();
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
						Barang b=((ListProductHandler)p.getMyExampleHandler()).getParsedData().getList().get(0);
						Intent i=new Intent(mainActivity.this, DetailProduct.class);
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
						startActivity(i);
					}else{
						ParsingXML denah=new ParsingXML(mainActivity.this, constant.GET_LIST_DENAH,i.getId_barang());
				    	try {
							denah.parse();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							final AlertDialog alertDialog = new AlertDialog.Builder(mainActivity.this).create();
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
						//if(ListProduct.list!=null){
							String nama=ListProduct.list.get(0).getNamaOutlet();
							Intent i=new Intent(mainActivity.this, ListProduct.class);
							i.putExtra("pesan", nama+" Product		");
							startActivity(i);
						//}
					}
				}
			});
			
			LinearLayout.LayoutParams params 	=	new LinearLayout.LayoutParams(320,250);
			params.setMargins(0, 0, 0, 0);
			imageButton.setLayoutParams(params);
			horizontalOuterLayout.addView(imageButton);
			pos++;
		}
	}
    
    public void stopAutoScrolling(){
		if (scrollTimer != null) {
			scrollTimer.cancel();
			scrollTimer	=	null;
		}
	}
	
	
	@Override
	public void onDestroy(){
		clearTimerTaks(clickSchedule);
		clearTimerTaks(scrollerSchedule);
		clearTimerTaks(faceAnimationSchedule);		
		clearTimers(scrollTimer);
		clearTimers(clickTimer);
		clearTimers(faceTimer);
		
		clickSchedule         = null;
		scrollerSchedule      = null;
		faceAnimationSchedule = null;
		scrollTimer           = null;
		clickTimer            = null;
		faceTimer             = null;
		super.onDestroy();
	}
	
	private void clearTimers(Timer timer){
	    if(timer != null) {
		    timer.cancel();
	        timer = null;
	    }
	}
	
	private void clearTimerTaks(TimerTask timerTask){
		if(timerTask != null) {
			timerTask.cancel();
			timerTask = null;
		}
	}
}