package com.portalshop.xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class IklanHandler extends DefaultHandler{
	private boolean inData=false,iniklan=false,inId=false,inPict=false,inStatus=false;
	private StringBuffer buff=null;
	private ParsedIklanDataSet myParsedExampleDataSet;
	 
    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public ParsedIklanDataSet getParsedData() {
            return this.myParsedExampleDataSet;
    }

    // ===========================================================
    // Methods
    // ===========================================================
    @Override
    public void startDocument() throws SAXException {
            this.myParsedExampleDataSet = new ParsedIklanDataSet();
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
            }else if (localName.equals("iklan")) {
                    this.iniklan=true;
            }else if (localName.equals("id_barang")) {
                    this.inId = true;
                    buff=new StringBuffer();
            }else if (localName.equals("pict")) {
            	this.inPict = true;
                buff=new StringBuffer();
            }else if (localName.equals("status")) {
            	this.inStatus = true;
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
    	}else if (localName.equals("iklan")) {
            this.iniklan=false;
            myParsedExampleDataSet.addIklan();
    	}else if (localName.equals("id_barang")) {
            this.inId = false;
    	}else if (localName.equals("pict")) {
    		this.inPict = false;
    	}else if (localName.equals("status")) {
    		this.inStatus = false;
    	}
    }
   
    /** Gets be called on the following structure:
     * <tag>characters</tag> */
    @Override	
    public void characters(char ch[], int start, int length) {
            if(this.inId){
                myParsedExampleDataSet.setIdBarang(new String(ch, start, length));
            }else if(this.inPict){
            	myParsedExampleDataSet.setPict(new String(ch, start, length));
            }else if(this.inStatus){
            	myParsedExampleDataSet.setStatus(new String(ch, start, length));
            }
    }

}
