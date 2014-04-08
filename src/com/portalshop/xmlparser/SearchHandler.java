package com.portalshop.xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SearchHandler extends DefaultHandler{
	private boolean inData=false,inUrl=false,inBarang=false;
	private StringBuffer buff=null;
	private ParsedSearchDataSet myParsedExampleDataSet = new ParsedSearchDataSet();
	 
    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public ParsedSearchDataSet getParsedData() {
            return this.myParsedExampleDataSet;
    }

    // ===========================================================
    // Methods
    // ===========================================================
    @Override
    public void startDocument() throws SAXException {
            this.myParsedExampleDataSet = new ParsedSearchDataSet();
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
                    this.setInData(true);
            }else if (localName.equals("barang")) {
                    this.setInBarang(true);
            }else if (localName.equals("nama_barang")) {
                    this.inUrl = true;
                    setBuff(new StringBuffer());
            }
            
    }
   
    /** Gets be called on closing tags like:
     * </tag> */
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
                    throws SAXException {
    	if (localName.equals("data")) {
            this.setInData(false);
    	}else if (localName.equals("barang")) {
            this.setInBarang(false);
            myParsedExampleDataSet.adduNama();
    	}else if (localName.equals("nama_barang")) {
            this.inUrl = false;
    	}
    }
   
    /** Gets be called on the following structure:
     * <tag>characters</tag> */
    @Override	
    public void characters(char ch[], int start, int length) {
            if(this.inUrl){
                myParsedExampleDataSet.setUrl(new String(ch, start, length));
            }
    }

	public void setInBarang(boolean inBarang) {
		this.inBarang = inBarang;
	}

	public boolean isInBarang() {
		return inBarang;
	}

	public void setBuff(StringBuffer buff) {
		this.buff = buff;
	}

	public StringBuffer getBuff() {
		return buff;
	}

	public void setInData(boolean inData) {
		this.inData = inData;
	}

	public boolean isInData() {
		return inData;
	}

	
}
