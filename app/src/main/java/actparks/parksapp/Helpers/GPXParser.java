package actparks.parksapp.Helpers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class GPXParser {

    public List<List> ConvertGPX(File file){
        List<List> list = new ArrayList<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        String ele = null;
        int ord = 0;
        try{
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            FileInputStream fileInputStream = new FileInputStream(file);
            Document document = documentBuilder.parse(fileInputStream);
            Element elementRoot = document.getDocumentElement();

            NodeList nodelist_trkpt = elementRoot.getElementsByTagName("trkpt");

            for(int i  = 0; i < nodelist_trkpt.getLength(); i++){

                ArrayList single_list = new ArrayList();


                Node node = nodelist_trkpt.item(i);

                NamedNodeMap attributes = node.getAttributes();

                String newLatitude = attributes.getNamedItem("lat").getTextContent();

                String newLongitude = attributes.getNamedItem("lon").getTextContent();

                String newLocationName = newLatitude + ":" + newLongitude;


                NodeList nList = node.getChildNodes();
                for(int j=0; j<nList.getLength(); j++) {
                    Node el = nList.item(j);
                    if(el.getNodeName().equals("ele")) {
                        ele = el.getTextContent();
                    }
                }

                ord = ord + 1;
                single_list.add(newLocationName);
                single_list.add(ele);
                single_list.add(ord);

                list.add(single_list);
            }
            fileInputStream.close();

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}

