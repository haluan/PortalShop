package com.portalshop.xmlparser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;

public class ExecuteStream {
	private static InputStream OpenHttpConection(String urlS) throws IOException{
		InputStream in = null;
        int response = -1;
               
        URL url = new URL(urlS); 
        URLConnection conn = url.openConnection();
    	//Toast.makeText(getBaseContext(), url.toString(), Toast.LENGTH_SHORT).show();
    	  
        if (!(conn instanceof HttpURLConnection))                     
            throw new IOException("Not an HTTP connection");
        
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect(); 

            response = httpConn.getResponseCode();                 
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();                                 
            }                     
        }
        catch (Exception ex)
        {
            throw new IOException("Error connecting");            
        }
        return in;
	}
	public static String execute(String url){
		int buff=10;
		Log.d("url", url);
		InputStream in=null;
		String str="";
		int charRead;
		try{
			in=OpenHttpConection(url);
		}catch(IOException e1){
			// TODO: handle exception
			e1.printStackTrace();
			return "";
		}
		InputStreamReader isr=new InputStreamReader(in);
		char[] inBuffer=new char[buff];
		try{
			while((charRead=isr.read(inBuffer))>0){ 
				String read=String.copyValueOf(inBuffer,0,charRead);	
				str+=read;
				inBuffer=new char[buff];
			}
			in.close();
		}catch (Exception e1) {
			// TODO: handle exception
		}
		return str;
	}
}
