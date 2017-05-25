package grace.assignment3.RoadMap;

import android.util.Xml;

import com.google.android.gms.maps.model.LatLng;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Helper class for parsing the kml files
 * Using SAX parser
 * Source: Linnaues university, Android for Javaprogrammers course
 */
public class RoadMapHandler extends DefaultHandler {

    private static final String COORDINATES = "coordinates";
    private static final String NAME = "name";
    private StringBuilder builder = new StringBuilder();
    private String longString;
    private RouteReport routeReport = null;
    private RouteCoordinate routeCoordinate = null;
    boolean currentElement = false;
    private ArrayList<LatLng> route = new ArrayList<LatLng>();

    public RouteReport getRouteReport() { return routeReport; }

    public static RouteReport getRoadMapObj (URL url) {
        RoadMapHandler handler = new RoadMapHandler();
        try {
            URLConnection urlConnection = url.openConnection();
            InputStream input = urlConnection.getInputStream() ;
            //print_input(input);
            Xml.parse(input, Xml.Encoding.UTF_8, handler);
            input.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return handler.getRouteReport();
    }

    /**
     * method is called when the parser starts parsing the Document
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        System.out.println("--- StartDocument ---");
        // start new route object
        routeReport = new RouteReport();
    }

    /**
     * method is called when the parser ends parsing the Document
     * @throws SAXException
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        System.out.println("\n--- EndDocument ---");
    }

    /**
     * Beautifying the coordinates string and splitting it to the parts Array.
     * Later adding the parts Array to LatLng objects and adding the LatLng objects to the RouteCoordinates Object
     * Then saving the RouteCoordinates objects in the RouteReport object.
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        //System.out.print("</"+qName+">");

        if (qName.equalsIgnoreCase(NAME)) {
            // there are 3 name tags!!
            routeReport.setName(builder.toString().trim());
        } else if (qName.equalsIgnoreCase(COORDINATES)) {
            String coord = builder.toString().trim();
            String pattern = "0.0 ";
            coord = coord.replaceAll(pattern, "");
            System.out.println("coord in builder: " + coord);

            final String[] parts = coord.split(",");
            for (int i = 0; i<parts.length-1; i=i+2) {
                route.add(new LatLng(Double.parseDouble(parts[i+1]),Double.parseDouble(parts[i])));
            }
            routeCoordinate.setRoute(route);
            System.out.println("route in builder: " + route.toString());

            //routeCoordinate.setCoordinate(coord);
            routeReport.addRouteCoordinate(routeCoordinate);
        }
        builder.setLength(0);
    }

    /**
     * called every time the parsers gets an open tag '<'
     * it assigns an open flag
     * in <student rollno="393"> attribute.getValue("rollno") would return 393
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase(COORDINATES)) {
            routeCoordinate = new RouteCoordinate();
        }
    }

    /**
     * print data is stored between the '<' and '>' tags
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        builder.append(ch, start, length);
    }

    private static void print_input(InputStream input) throws IOException {
        BufferedReader in = new BufferedReader( new InputStreamReader(input) );
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
    }
}
