package com.portalshop.xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class OutletHandler extends DefaultHandler{
	private boolean inData=false,inOutlet=false,inUsername=false;
	private StringBuffer buff=null;
	private ParsedOutletDataSet myParsedExampleDataSet;
	 
    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public ParsedOutletDataSet getParsedData() {
            return this.myParsedExampleDataSet;
    }

    // ===========================================================
    // Methods
    // ===========================================================
    @Override
    public void startDocument() throws SAXException {
            this.myParsedExampleDataSet = new ParsedOutletDataSet();
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
            }else if (localName.equals("outlet")) {
                    this.inOutlet=true;
            }else if (localName.equals("username")) {
                    this.inUsername = true;
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
    	}else if (localName.equals("outlet")) {
            this.inOutlet=false;
            myParsedExampleDataSet.addString();
    	}else if (localName.equals("username")) {
            this.inUsername = false;
    	}
    }
   
    /** Gets be called on the following structure:
     * <tag>characters</tag> */
    @Override	
    public void characters(char ch[], int start, int length) {
            if(this.inUsername){
                myParsedExampleDataSet.setUsername(new String(ch, start, length));
            }
    }
}
