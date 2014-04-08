package com.portalshop.zendy;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.portalshop.adapter.BookmarkAdapter;
import com.portalshop.adapter.ListAdapter;
import com.portalshop.customview.CustomAutoCompleteView;
import com.portalshop.customview.NoDefaultSpinner;
import com.portalshop.model.Barang;
import com.portalshop.util.DrawableManager;
import com.portalshop.xmlparser.DatabaseHandler;
import com.portalshop.xmlparser.HistoryHandler;
import com.portalshop.xmlparser.ListProductHandler;
import com.portalshop.xmlparser.ParsingXML;
import com.portalshop.xmlparser.SearchHandler;
import com.portalshop.xmlparser.constant;

public class ListProduct extends Activity{
	static ArrayList<Barang> list=new ArrayList<Barang>();
	ArrayList<Barang> temp=new ArrayList<Barang>();
	DrawableManager manager=new DrawableManager();
	ParsingXML parse;
	ListProduct this_c=this;
	onScroll listener=new onScroll();
	ListAdapter adapter;
	int itemsPerPage = 20;
	int itemLoaded=0;
	boolean loadingMore = false;
	boolean full=false;
	GridView grid;
	Thread thread ;
	private ImageView go;
	private ImageView remove;
	private CustomAutoCompleteView auto;
	private ArrayAdapter autoCompleteAdapter;
	private NoDefaultSpinner menu;
	private HistoryHandler historyDb=new HistoryHandler(this);
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.adapter);

        ((ImageView)findViewById(R.id.home)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent a=new Intent(ListProduct.this, mainActivity.class);
				mainActivity.back=false;
				startActivity(a);
			}
		});
        StrictMode.ThreadPolicy policy = new StrictMode.
        ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        new GetData().execute();
    }
	//Runnable untuk load the items 
    private Runnable loadMoreListItems = new Runnable() {			
		@Override
		public void run() {
			loadingMore = true;
	        runOnUiThread(returnRes);
	        
		}
	};	
	  
    private Runnable returnRes = new Runnable() {
        @Override
        public void run() {  
        	if(itemLoaded+itemsPerPage>=list.size()){
				Log.d("list", itemLoaded+" "+itemsPerPage);
				itemsPerPage=list.size()-itemLoaded;
				full=true;
			}
        	for (int i = 0; i < itemsPerPage; i++) {		
        		adapter.add(list.get(itemLoaded));
        		itemLoaded++;
			}
            adapter.notifyDataSetChanged();
        }
    };
    class onScroll implements OnScrollListener{
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			if(!full){					
					thread =  new Thread(null, loadMoreListItems);
			        thread.start();
			}else{
				((LinearLayout)findViewById(R.id.layoutloading)).setVisibility(View.GONE);
			}
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			
		}
    	
    }
	public class GetData extends AsyncTask<Void, Void, Void>{
		  ProgressDialog dialog;

			@Override
			protected void onPreExecute() {
			}
			
			@Override
			protected Void doInBackground(Void... params) {
				Bundle b=getIntent().getExtras();
		        ((TextView)findViewById(R.id.textMassege)).setText(b.getString("pesan"));
		        //list=mainActivity.daftarList;
		        if(list==null){
		        	((TextView)findViewById(R.id.textResult)).setText("0 Result");
		        }else
		        ((TextView)findViewById(R.id.textResult)).setText(list.size()+" Result");
				return null;
			}

			@Override
			protected void onPostExecute(Void unused) {
				//setContentView(R.layout.adapter);
				if(list!=null){
					ArrayList<Barang> temp=new ArrayList<Barang>();
					adapter=new ListAdapter(getApplicationContext(), R.layout.adapterlist,temp,manager);
					grid=(GridView)findViewById(R.id.gridView1);        
					grid.setAdapter(adapter);
					grid.setOnScrollListener(listener);
				}else{
					((LinearLayout)findViewById(R.id.layoutloading)).setVisibility(View.GONE);
				}
				//dialog.dismiss();
		        initAutoComplete();
		        initMenu();
		       
			}

		  
	  }
	/* Method to initialize AutoComplete Search TextView
	*
	**/
	public void initAutoComplete(){
    	go=(ImageView)findViewById(R.id.goButton);
		remove=(ImageView)findViewById(R.id.removeButton);
		remove.setOnClickListener(listenerClick);
		go.setOnClickListener(listenerClick);
		go.setVisibility(View.GONE);
		remove.setVisibility(View.GONE);
		
		autoCompleteAdapter = new ArrayAdapter(ListProduct.this, android.R.layout.simple_dropdown_item_1line);
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
						ParsingXML parseArival=new ParsingXML(ListProduct.this, constant.GET_SEARCH_RESULT,auto.getText().toString());
						try {
							parseArival.parse();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							final AlertDialog alertDialog = new AlertDialog.Builder(ListProduct.this).create();
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
						Intent i=new Intent(ListProduct.this, ListProduct.class);
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
			final AlertDialog alertDialog = new AlertDialog.Builder(ListProduct.this).create();
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
		final ArrayAdapter m=ArrayAdapter.createFromResource(ListProduct.this,R.array.menu, android.R.layout.simple_spinner_item);
		m.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		menu.setAdapter(m);
		menu.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(menu.getSelectedItemId()==1){
					final Dialog book=new Dialog(ListProduct.this);
					book.setContentView(R.layout.bookmarkdialog);
					DatabaseHandler db=new DatabaseHandler(ListProduct.this);
					final ListView bookmark=(ListView)book.findViewById(R.id.listbookmark);
					BookmarkAdapter adapter=new BookmarkAdapter(ListProduct.this, R.layout.bookmarkadapter, db.getAllContacts(),book);
					bookmark.setAdapter(adapter);
					
					book.setTitle("Bookmarked Item");
					book.show();
				}
				else {
					Intent i=new Intent(ListProduct.this, Denah.class);
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
	private OnClickListener listenerClick=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		switch (v.getId()) {
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
				ParsingXML parseArival=new ParsingXML(ListProduct.this, constant.GET_SEARCH_RESULT,auto.getText().toString());
				try {
					parseArival.parse();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					final AlertDialog alertDialog = new AlertDialog.Builder(ListProduct.this).create();
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
				Intent i=new Intent(ListProduct.this, ListProduct.class);
				i.putExtra("pesan", "Search Result		");
				startActivity(i);
			}
			break;
		
		}
		}
		
	};
}
