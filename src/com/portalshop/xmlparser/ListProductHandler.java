package com.portalshop.xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
 
public class ListProductHandler extends DefaultHandler{
 
        // ===========================================================
        // Fields
        // ===========================================================
       
        private boolean in_data = false;
        private boolean in_barang=false;
        private boolean namaOutlet=false,id=false,nama=false,date=false,diskon=false,deskripsi=false,stok=false,pict=false,outlet=false,ketgori=false,harga=false,jml_rate=false,rater=false,lantai=false,lokasi=false,email=false,telp=false;
        private StringBuffer buuff=null;
       
        private ParsedListProductDataSet myParsedExampleDataSet = new ParsedListProductDataSet();
 
        // ===========================================================
        // Getter & Setter
        // ===========================================================
 
        public ParsedListProductDataSet getParsedData() {
                return this.myParsedExampleDataSet;
        }
 
        // ===========================================================
        // Methods
        // ===========================================================
        @Override
        public void startDocument() throws SAXException {
                this.myParsedExampleDataSet = new ParsedListProductDataSet();
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
                        this.setIn_data(true);
                }else if (localName.equals("barang")) {
                        this.setIn_barang(true);
                }else if (localName.equals("id_barang")) {
                        this.id = true;
                        setBuuff(new StringBuffer(""));
                }else if (localName.equals("nama_barang")) {
                		this.nama=true;
                		setBuuff(new StringBuffer(""));
                }else if (localName.equals("date")) {
                		this.date=true;
                		setBuuff(new StringBuffer(""));
                }else if (localName.equals("diskon")) {
            		this.diskon=true;
            		setBuuff(new StringBuffer(""));
                }else if (localName.equals("deskripsi")) {
            		this.deskripsi=true;
            		setBuuff(new StringBuffer(""));
                }else if (localName.equals("stok")) {
            		this.stok=true;
            		setBuuff(new StringBuffer(""));
                }else if (localName.equals("pict")) {
            		this.pict=true;
            		setBuuff(new StringBuffer(""));
                }else if (localName.equals("username")) {
            		this.outlet=true;
            		setBuuff(new StringBuffer(""));
                }else if (localName.equals("id_kategori")) {
            		this.ketgori=true;
            		setBuuff(new StringBuffer(""));
                }else if (localName.equals("harga")) {
            		this.harga=true;
            		setBuuff(new StringBuffer(""));
                }else if (localName.equals("jml_rate")) {
            		this.jml_rate=true;
            		setBuuff(new StringBuffer(""));
                }else if (localName.equals("hasil_rate")) {
            		this.rater=true;
            		setBuuff(new StringBuffer(""));
                }else if (localName.equals("nama_outlet")) {
            		this.namaOutlet=true;
            		setBuuff(new StringBuffer(""));
                }else if (localName.equals("lantai")) {
            		this.lantai=true;
            		setBuuff(new StringBuffer(""));
                }else if (localName.equals("lokasi")) {
            		this.lokasi=true;
            		setBuuff(new StringBuffer(""));
                }else if (localName.equals("email")) {
            		this.email=true;
            		setBuuff(new StringBuffer(""));
                }else if (localName.equals("telp")) {
            		this.telp=true;
            		setBuuff(new StringBuffer(""));
                }    
                
        }
       
        /** Gets be called on closing tags like:
         * </tag> */
        @Override
        public void endElement(String namespaceURI, String localName, String qName)
                        throws SAXException {
        	if (localName.equals("data")) {
                this.setIn_data(false);
        	}else if (localName.equals("barang")) {
                this.setIn_barang(false);
                myParsedExampleDataSet.addBook();
        	}else if (localName.equals("id_barang")) {
                this.id = false;
        	}else if (localName.equals("nama_barang")) {
        		this.nama=false;
        	}else if (localName.equals("harga")) {
        		this.harga=false;
            }else if (localName.equals("date")) {
        		this.date=false;
        	}else if (localName.equals("diskon")) {
        		this.diskon=false;
        	}else if (localName.equals("deskripsi")) {
        		this.deskripsi=false;
        	}else if (localName.equals("stok")) {
        		this.stok=false;
        	}else if (localName.equals("pict")) {
        		this.pict=false;
        	}else if (localName.equals("username")) {
        		this.outlet=false;
        	}else if (localName.equals("id_kategori")) {
        		this.ketgori=false;
        	}else if (localName.equals("jml_rate")) {
        		this.jml_rate=false;
            }else if (localName.equals("hasil_rate")) {
        		this.rater=false;
            }else if (localName.equals("nama_outlet")) {
        		this.namaOutlet=false;
            }else if (localName.equals("lantai")) {
        		this.lantai=false;
            }else if (localName.equals("lokasi")) {
        		this.lokasi=false;
            } else if (localName.equals("email")) {
        		this.email=false;
            } else if (localName.equals("telp")) {
        		this.telp=false;
            }   
        }
       
        /** Gets be called on the following structure:
         * <tag>characters</tag> */
        @Override
    public void characters(char ch[], int start, int length) {
                if(this.id){
                	myParsedExampleDataSet.setId(new String(ch, start, length));
                }
                if(this.date){
                    myParsedExampleDataSet.setDate(new String(ch, start, length));
                }
                if(this.deskripsi){
                    myParsedExampleDataSet.setDeskripsi(new String(ch, start, length));
                }
                if(this.diskon){
                    myParsedExampleDataSet.setDiskon(new String(ch, start, length));
                }
                if(this.ketgori){
                    myParsedExampleDataSet.setKetgori(new String(ch, start, length));
                }
                if(this.nama){
                    myParsedExampleDataSet.setNama(new String(ch, start, length));
                }if(this.harga){
                	myParsedExampleDataSet.setHarga(new String(ch, start, length));
                }
                if(this.outlet){
                    myParsedExampleDataSet.setOutlet(new String(ch, start, length));
                }
                if(this.pict){
                    myParsedExampleDataSet.setPict(new String(ch, start, length));
                }
                if(this.stok){
                    myParsedExampleDataSet.setStok(new String(ch, start, length));
                }                
                if(this.jml_rate){
                	myParsedExampleDataSet.setJml_rate(new String(ch, start, length));
                }
                if(this.rater){
                	myParsedExampleDataSet.setRater(new String(ch, start, length));
                }
                if(this.namaOutlet){
                	myParsedExampleDataSet.setNamaOutlet(new String(ch, start, length));
                }
                if(this.lantai){
                	myParsedExampleDataSet.setLantai(new String(ch, start, length));
                }
                if(this.lokasi){
                	myParsedExampleDataSet.setLokasi(new String(ch, start, length));
                }
                if(this.email){
                	myParsedExampleDataSet.setEmail(new String(ch, start, length));
                }
                if(this.telp){
                	myParsedExampleDataSet.setTelp(new String(ch, start, length));
                }
                
    }
		public void setIn_data(boolean in_data) {
			this.in_data = in_data;
		}

		public boolean isIn_data() {
			return in_data;
		}

		public void setIn_barang(boolean in_barang) {
			this.in_barang = in_barang;
		}

		public boolean isIn_barang() {
			return in_barang;
		}

		public void setBuuff(StringBuffer buuff) {
			this.buuff = buuff;
		}

		public StringBuffer getBuuff() {
			return buuff;
		}
}

