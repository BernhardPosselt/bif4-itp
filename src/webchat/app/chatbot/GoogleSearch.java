package chatbot;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 07.01.13
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */

public class GoogleSearch implements IPlugin {
    DocumentBuilderFactory dbf = null;
    DocumentBuilder db = null;
    Document doc = null;

    String key = "AIzaSyDCeMebhsnixd7lpmPIhK0ZLg4NPGpeEzI";
    String cx = "006255338146876143651:2vobgacijh0";

    @Override
    public boolean check(String term) {
        if(term.startsWith("google"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public String execute(String term) throws ParserConfigurationException {

        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();

        URL url = null;
        String q;

        try
        {
            q = term.substring(7);
            q = q.replaceAll(" ", "+");
            url = new URL("https://www.googleapis.com/customsearch/v1?key=" + key + "&cx=" + cx + "&q=" + q + "&alt=atom");
            StringBuilder sb = new StringBuilder();
            doc = db.parse(url.openStream());
            Element root = doc.getDocumentElement();

            sb.append("Search results for " + q + "\n\n");
            NodeList nl1 = root.getElementsByTagName("entry");
            for(int i = 0; i < nl1.getLength(); i++)
            {
                NodeList entry = nl1.item(i).getChildNodes();
                sb.append(entry.item(5).getFirstChild().getNodeValue());
                sb.append("");
                sb.append(entry.item(9).getFirstChild().getNodeValue());
                sb.append("\n");
                sb.append(entry.item(7).getAttributes().item(0).getNodeValue());
                sb.append("\n");
                sb.append("\n");
            }
            return sb.toString();
        }
        catch (MalformedURLException e)
        {
            return "No search results found!";
        }
        catch (IOException e)
        {
            return "No search results found!";
        }
        catch (SAXException e)
        {
            return "No search results found!";
        }
        catch(Exception e)
        {
            return "No search results found!";
        }
    }
}
