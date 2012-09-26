package websocket.message;

public class MessageFactory {

    private static Message[] messages = {
        new InviteMessage(),
    };

    public static Message getMessageFromType(String type) throws InvalidMessageTypeException {
        for(Message message: messages){
            if(message.canHandle(type)){
                return message.clone();
            }
        }

        String errorMsg = String.format("Can not handle type %s", type);
        throw new InvalidMessageTypeException(errorMsg);
    }

}
