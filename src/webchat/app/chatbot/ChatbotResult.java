package chatbot;

/**
 * Created with IntelliJ IDEA.
 * User: daniel
 * Date: 09.01.13
 * Time: 15:57
 * To change this template use File | Settings | File Templates.
 */
public class ChatbotResult {

    String in;
    private String out;
    private boolean success;

    public ChatbotResult(String in)
    {
        this.in = in;
        this.out = null;
        this.success = false;
    }

    public ChatbotResult(String in, String out)
    {
        this.in = in;
        this.out = out;
        this.success = true;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getOut() {
        return out;
    }
}
