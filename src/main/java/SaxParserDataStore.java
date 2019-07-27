/*********


 http://www.saxproject.org/

 SAX is the Simple API for XML, originally a Java-only API.
 SAX was the first widely adopted API for XML in Java, and is a �de facto� standard.
 The current version is SAX 2.0.1, and there are versions for several programming language environments other than Java.

 The following URL from Oracle is the JAVA documentation for the API

 https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html
 *********/

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;


////////////////////////////////////////////////////////////

/**************

 SAX parser use callback function  to notify client object of the XML document structure.
 You should extend DefaultHandler and override the method when parsin the XML document
 ***************/

////////////////////////////////////////////////////////////

public class SaxParserDataStore extends DefaultHandler {
    static HashMap<String, Product> products;

    Product product;
    String dataFileName;
    String elementValueRead;

    public SaxParserDataStore() {
    }

    public SaxParserDataStore(String dataFileName) throws ServletException {
        this.dataFileName = dataFileName;
        products = new HashMap<>();
        parseDocument();
    }

    //call the constructor to parse the xml and get product details
    public static void initHashMap(final ServletContext servletContext) throws ServletException {
        final String filePath = servletContext.getRealPath("ProductCatalog.xml");
        new SaxParserDataStore(filePath);
    }

    //parse the xml using sax parser to get the data
    private void parseDocument() throws ServletException {
        try {
            final SAXParserFactory factory = SAXParserFactory.newInstance();
            final SAXParser parser = factory.newSAXParser();
            parser.parse(dataFileName, this);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /*************

     There are a number of methods to override in SAX handler  when parsing your XML document :

     Group 1. startDocument() and endDocument() :  Methods that are called at the start and end of an XML document.
     Group 2. startElement() and endElement() :  Methods that are called  at the start and end of a document element.
     Group 3. characters() : Method that is called with the text content in between the start and end tags of an XML document element.


     There are few other methods that you could use for notification for different purposes, check the API at the following URL:

     https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html
     ***************/

    // when xml start element is parsed store the id into respective hashmap for different products etc
    @Override
    public void startElement(String str1, String str2, String elementName, Attributes attributes) {
        if (elementName.equalsIgnoreCase("Product")) {
            product = new Product();
        }
    }

    // when xml end element is parsed store the data into respective hashmap for console,games etc respectively
    @Override
    public void endElement(String str1, String str2, String element) {

        if (element.equals("Product")) {
            products.put(product.getId(), product);
            return;
        }

        switch (element) {
            case "id":
                product.setId(elementValueRead);
                break;
            case "name":
                product.setName(elementValueRead);
                break;
            case "price":
                product.setPrice(Double.parseDouble(elementValueRead));
                break;
            case "image":
                product.setImage(elementValueRead);
                break;
            case "category":
                product.setCategory(elementValueRead);
                break;
            case "subcategory":
                product.setSubcategory(elementValueRead);
                break;
            case "condition":
                product.setCondition(elementValueRead);
                break;
            case "discount":
                product.setDiscount(Double.parseDouble(elementValueRead));
                break;
            case "manufacturer":
                product.setManufacturer(elementValueRead);
                break;
            case "manufacturerRebate":
                product.setManufacturerRebate(Double.parseDouble(elementValueRead));
                break;
            case "inventoryCount":
                product.setInventoryCount(Integer.parseInt(elementValueRead));
                break;
            case "accessoryIds":
                String[] ids = elementValueRead.split(",");
                product.setAccessoryIds(new HashSet<>(Arrays.asList(ids)));
                break;
            default:
                System.out.println("Skipping element " + element);

        }
    }


    /////////////////////////////////////////
    // 	     Kick-Start SAX in main       //
    ////////////////////////////////////////

    //get each element in xml tag
    @Override
    public void characters(char[] content, int begin, int end) {
        elementValueRead = new String(content, begin, end);
    }
}
