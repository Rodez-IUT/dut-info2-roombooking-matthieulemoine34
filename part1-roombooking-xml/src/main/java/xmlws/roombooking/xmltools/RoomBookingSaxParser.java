package xmlws.roombooking.xmltools;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RoomBookingSaxParser implements RoomBookingParser {

    private RoomBooking roomBoking = new RoomBooking();
    private String chaine;

    @Override
    public RoomBooking parse(InputStream inputStream) {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser saxParser = spf.newSAXParser();
            saxParser.parse(inputStream, new RoomBookingSaxParser.RoomBookingHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roomBoking;
    }

    private class RoomBookingHandler extends DefaultHandler {

        public void startElement(String namespaceURI,
                                 String localName,
                                 String qName,
                                 Attributes atts)
                throws SAXException {
            chaine = localName;
        }

        public void characters(char ch[], int start, int length)
                throws SAXException {
            SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String data = new String(ch, start, length);

            if(data.charAt(0) != '\n'){

                if(chaine.equals("label")){
                    roomBoking.setRoomLabel(data);
                }

                if(chaine.equals("username")){
                    roomBoking.setUsername(data);
                }

                if(chaine.equals("startDate")){
                    try {
                        roomBoking.setStartDate(dateF.parse(data));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                if(chaine.equals("endDate")){
                    try {
                        roomBoking.setEndDate(dateF.parse(data));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}
