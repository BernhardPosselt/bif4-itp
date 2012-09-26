package websocket.message;


public abstract class Message implements Cloneable {
    public abstract boolean canHandle(String type);
    public abstract Message clone();
}
