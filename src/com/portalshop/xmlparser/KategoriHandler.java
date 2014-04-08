package com.portalshop.xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class KategoriHandler extends DefaultHandler{
	private boolean inData=false,inKategori=false,inId=false,inNama=false;
	private StringBuffer buff=null;
	private ParsedKategoriDataSet myParsedExampleDataSet;
	 
    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public ParsedKategoriDataSet getParsedData() {
            return this.myParsedExampleDataSet;
    }

    // ===========================================================
    // Methods
    // ===========================================================
    @Override
    public void startDocument() throws SAXException {
            this.myParsedExampleDataSet = new ParsedKategoriDataSet();
    }

    @Override
    public void endDocument() throws SAXException {
            // Nothing to do
    }

    /** Gets be called on opening tags like:
     * <tag>
     * Can provide attribute(s), when xml was like:
     * <tag attribute="attributeValue">*/
    @Override
    public void startElement(String namespaceURI, String localName,
                    String qName, Attributes atts) throws SAXException {
            if (localName.equals("data")) {
                    this.inData=true;
            }else if (localName.equals("kategori")) {
                    this.inKategori=true;
            }else if (localName.equals("id_kategori")) {
                    this.inId = true;
                    buff=new StringBuffer();
            }else if (localName.equals("nama_kategori")) {
            	this.inNama = true;
                buff=new StringBuffer();
            }
            
    }
   
    /** Gets be called on closing tags like:
     * </tag> */
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
                    throws SAXException {
    	if (localName.equals("data")) {
            this.inData=false;
    	}else if (localName.equals("kategori")) {
            this.inKategori=false;
            myParsedExampleDataSet.addKategori();
    	}else if (localName.equals("id_kategori")) {
            this.inId = false;
    	}else if (localName.equals("nama_kategori")) {
    		this.inKategori = false;
    	}
    }
   
    /** Gets be called on the following structure:
     * <tag>characters</tag> */
    @Override	
    public void characters(char ch[], int start, int length) {
            if(this.inId){
                myParsedExampleDataSet.setIdKategori(new String(ch, start, length));
            }else if(this.inNama){
            	myParsedExampleDataSet.setNamaKategori(new String(ch, start, length));
            }
    }
}
