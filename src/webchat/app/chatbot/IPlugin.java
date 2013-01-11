package chatbot;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 08.10.12
 * Time: 16:31
 * To change this template use File | Settings | File Templates.
 */
public interface IPlugin {
    boolean check(String term);
    String execute(String term) throws ParserConfigurationException;
}
