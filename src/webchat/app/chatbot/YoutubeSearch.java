package chatbot;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 09.01.13
 * Time: 15:48
 * To change this template use File | Settings | File Templates.
 */
public class YoutubeSearch implements IPlugin {

    DocumentBuilderFactory dbf = null;
    DocumentBuilder db = null;
    Document doc = null;

    @Override
    public boolean check(String term) {
        if(term.startsWith("youtube"))
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
            q = term.substring(8);
            q = q.replaceAll(" ", "+");
            url = new URL("http://gdata.youtube.com/feeds/api/videos?q=" + q + "&max-results=10&v=2");


            StringBuilder sb = new StringBuilder();
            doc = db.parse(url.openStream());
            Element root = doc.getDocumentElement();

            sb.append("Search results for " + q + "\n\n");
            NodeList nl1 = root.getElementsByTagName("entry");
            for(int i = 0; i < nl1.getLength(); i++)
            {
                NodeList entry = nl1.item(i).getChildNodes();

                if(entry.item(5).getNodeName().equals("title"))
                {
                    sb.append(entry.item(5).getFirstChild().getNodeValue());
                }
                else
                {
                    sb.append(entry.item(6).getFirstChild().getNodeValue());
                }
                sb.append("\n");

                if(entry.item(7).getNodeName().equals("content"))
                {
                    sb.append(entry.item(7).getAttributes().item(0).getNodeValue());
                }
                else
                {
                    sb.append(entry.item(8).getAttributes().item(0).getNodeValue());
                }
                sb.append("\n");
                sb.append("\n");
            }
            return sb.toString();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            return "No search results found!";
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "No search results found!";
        }
        catch (SAXException e)
        {
            e.printStackTrace();
            return "No search results found!";
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "No search results found!";
        }
    }
}
