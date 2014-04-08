    package com.portalshop.xmlparser;
     
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
     
public class ParsingXML {
           	//private Context ctx;
	private int status;
	private String raw_url;
	Context mctx;
	public DefaultHandler getMyExampleHandler() {
		return myExampleHandler;
	}
	DefaultHandler myExampleHandler=null;
			
	public ParsingXML(Context ctx , int status){
           	//this.ctx=ctx;
			this.status=status;
			this.mctx=ctx;
			if(status==constant.GET_LIST_PRODUCT){
				raw_url=constant.URL+"listBarang.php";
				myExampleHandler = new ListProductHandler();
            }else if(status==constant.GET_KATEGORI){
				raw_url=constant.URL+"listkategori.php";
				myExampleHandler = new KategoriHandler();
            }else if(status==constant.GET_NEW_ARIVAL){
    			raw_url=constant.URL+"newArival.php";
    			Log.d("url",raw_url);
    			myExampleHandler = new ListProductHandler();
            }else if(status==constant.GET_BEST_DISKON){
    			raw_url=constant.URL+"bestDiskon.php";
    			myExampleHandler = new ListProductHandler();
            }else if(status==constant.GET_IKLAN){
    			raw_url=constant.URL+"iklan.php";
    			myExampleHandler = new IklanHandler();
            }else if(status==constant.GET_TOP_RATED){
    			raw_url=constant.URL+"listRated.php";
    			myExampleHandler = new ListProductHandler();
            }else if(status==constant.GET_OUTLET){
    			raw_url=constant.URL+"listOutlet.php";
    			myExampleHandler = new OutletHandler();
            }
			
	}
	public ParsingXML(Context ctx,int status,String id){
		this.status=status;
		this.mctx=ctx;
		if(status==constant.GET_DETAIL_PRODUCT){
			raw_url=constant.URL+"detailBarang.php?id="+id;
			Log.d("url",raw_url);
			myExampleHandler = new DetailProductHandler();
        }
		else if(status==constant.GET_PRODUCT_RATE){
			raw_url=constant.URL+"listRate.php?id="+id;
			myExampleHandler=new ListRateHandler();
		}else if(status==constant.GET_RELATED_PRODUCT){
			String a[]=id.split("-");
			raw_url=constant.URL+"relatedProduct.php?outlet="+a[0]+"&nama="+a[1];
			
			myExampleHandler = new ListProductHandler();
		}else if(status==constant.GET_NEW_ARIVAL){
			raw_url=constant.URL+"newArival.php?id="+id;
			
			myExampleHandler = new ListProductHandler();
        }else if(status==constant.GET_BEST_DISKON){
			raw_url=constant.URL+"bestDiskon.php?id="+id;
			myExampleHandler = new ListProductHandler();
        }else if(status==constant.GET_SEARCH){
        	raw_url=constant.URL+"autocomplete.php?st="+id;
			myExampleHandler = new SearchHandler();
        }else if(status==constant.GET_SEARCH_RESULT){
        	raw_url=constant.URL+"searching.php?id="+id;
        	Log.d("url",raw_url);
			myExampleHandler = new ListProductHandler();
        }else if(status==constant.GET_LIST_PRODUCT){
			raw_url=constant.URL+"listBarang.php?id="+id;
			Log.d("url",raw_url);
			myExampleHandler = new ListProductHandler();	
        }else if(status==constant.GET_LIST_DENAH){
			raw_url=constant.URL+"listdenah.php?id="+id;
			Log.d("url",raw_url);
			myExampleHandler = new ListProductHandler();
        }else if(status==constant.GET_IKLAN){
			raw_url=constant.URL+"iklan.php?level="+id;
			Log.d("url",raw_url);
			myExampleHandler = new IklanHandler();
        }else if(status==constant.GET_TOP_RATED){
			raw_url=constant.URL+"listRated.php?id="+id;
			Log.d("url",raw_url);
			myExampleHandler = new ListProductHandler();
        }
	}
	
	public void parse() throws Exception{	
    		StrictMode.ThreadPolicy policy = new StrictMode.
            ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
    		URL url = new URL(raw_url);
            	
        	SAXParserFactory spf = SAXParserFactory.newInstance();
    		SAXParser sp = spf.newSAXParser();
    	    XMLReader xr = sp.getXMLReader();
            xr.setContentHandler(myExampleHandler);
                   
            xr.parse(new InputSource(url.openStream()));
                   
	}
}