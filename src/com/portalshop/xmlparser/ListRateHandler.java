package com.portalshop.xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ListRateHandler extends DefaultHandler{
	private boolean inData=false,inBarang=false,in_id=false,in_rate=false,in_username=false,in_title=false,in_review=false,in_tanggl=false;
	private StringBuffer buff=null;
	private ParsedListRateDataSet myParsedExampleDataSet = new ParsedListRateDataSet();
	 
    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public ParsedListRateDataSet getParsedData() {
            return this.myParsedExampleDataSet;
    }

    // ===========================================================
    // Methods
    // ===========================================================
    @Override
    public void startDocument() throws SAXException {
            this.myParsedExampleDataSet = new ParsedListRateDataSet();
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
            }else if (localName.equals("id_barang")) {
                    this.in_id = true;
                    setBuff(new StringBuffer());
            }else if (localName.equals("rate")) {
                this.in_rate = true;
                setBuff(new StringBuffer());
            }else if (localName.equals("username")) {
                this.in_username = true;
                setBuff(new StringBuffer());
            }else if (localName.equals("title")) {
                this.in_title = true;
                setBuff(new StringBuffer());
            }else if (localName.equals("review")) {
                this.in_review = true;
                setBuff(new StringBuffer());
            }else if (localName.equals("tanggal")) {
                this.in_tanggl = true;
                setBuff(new StringBuffer());
            }
            
    }
   
    /** Gets be called on closing tags like:
     * </tag> */
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
                    throws SAXException {
    	if (localName.equals("data")) {
            this.inData=false;
    	}else if (localName.equals("barang")) {
            this.inBarang=false;
            myParsedExampleDataSet.addRate();
    	}else if (localName.equals("id_barang")) {
            this.in_id = false;
    	}else if (localName.equals("rate")) {
    		this.in_rate = false;
    	}else if (localName.equals("username")) {
    		this.in_username = false;
    	}else if (localName.equals("title")) {
    		this.in_title = false;
    	}else if (localName.equals("review")) {
    		this.in_review = false;
    	}else if (localName.equals("tanggal")) {
    		this.in_tanggl = false;
    	}
    }
   
    /** Gets be called on the following structure:
     * <tag>characters</tag> */
    @Override	
    public void characters(char ch[], int start, int length) {
            if(this.in_id){
                myParsedExampleDataSet.setId(new String(ch, start, length));
            }else if(this.in_rate){
            	myParsedExampleDataSet.setRate(new String(ch, start, length));
            }else if(this.in_username){
            	myParsedExampleDataSet.setUsername(new String(ch, start, length));
            }else if(this.in_title){
            	myParsedExampleDataSet.setTitile(new String(ch, start, length));
            }else if(this.in_review){
            	myParsedExampleDataSet.setComment(new String(ch, start, length));
            }else if(this.in_tanggl){
            	myParsedExampleDataSet.setDate(new String(ch, start, length));
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
