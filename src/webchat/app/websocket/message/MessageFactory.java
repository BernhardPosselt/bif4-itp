package websocket.message;

import java.util.ArrayList;

import websocket.json.in.*;

public class MessageFactory {

    private static ArrayList<IInMessage> messages = new ArrayList<IInMessage>();
    
    public static WorkRoutine getMessageFromType(String type) throws InvalidMessageTypeException {
        for(IInMessage message: messages){
            if(message.canHandle(type)){
                return message.getWorkRoutine();
            }
        }
        String errorMsg = String.format("Can not handle type %s", type);
        throw new InvalidMessageTypeException(errorMsg);
    }

   
    public static void registerMessage(){
    	messages.add(new InChannelClose());
    	messages.add(new InChannelDelete());
    	messages.add(new InChannelName());
    	messages.add(new InChannelTopic());
    	messages.add(new InFileDelete());
    	messages.add(new InInviteGroup());
    	messages.add(new InInviteUser());
    	messages.add(new InJoin());
    	messages.add(new InMessage());
    	messages.add(new InNewChannel());
    	messages.add(new InProfileUpdate());
    }
}
