package websocket.message;

import java.util.ArrayList;

public class MessageFactory {

    private static ArrayList<Message> messages = new ArrayList<Message>();

    public static Message getMessageFromType(String type) throws InvalidMessageTypeException {
        for(Message message: messages){
            if(message.canHandle(type)){
                return message.clone();
            }
        }

        String errorMsg = String.format("Can not handle type %s", type);
        throw new InvalidMessageTypeException(errorMsg);
    }

    public void registerMessage(Message message){
        messages.add(message);
    }

}
