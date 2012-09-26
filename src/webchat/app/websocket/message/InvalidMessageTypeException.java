package websocket.message;

public class InvalidMessageTypeException extends Exception {

    public InvalidMessageTypeException(String msg){
        super(msg);
    }

}
