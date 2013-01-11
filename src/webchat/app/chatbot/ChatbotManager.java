package chatbot;

import javax.xml.parsers.ParserConfigurationException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 08.10.12
 * Time: 16:29
 * To change this template use File | Settings | File Templates.
 */
public class ChatbotManager {

    private ArrayList<IPlugin> plugins;
    private IPlugin plugin;

    public ChatbotManager()
    {
        this.plugins = new ArrayList<IPlugin>();
        this.plugin = null;
        loadPlugins();
    }

    private void loadPlugins()
    {
        this.plugins.add(new GoogleSearch());
        this.plugins.add(new YoutubeSearch());
    }

    void selectPlugin(String term)
    {
        this.plugin = null;

        for(IPlugin item : this.plugins)
        {
            if(item.check(term))
            {
                this.plugin = item;
                break;
            }
        }
    }

    public ChatbotResult executePlugin(String term) throws ParserConfigurationException {

        selectPlugin(term);

        if(this.plugin != null)
        {
            return new ChatbotResult(term, this.plugin.execute(term));
        }
        else
        {
            return new ChatbotResult(term);
        }
    }
}
