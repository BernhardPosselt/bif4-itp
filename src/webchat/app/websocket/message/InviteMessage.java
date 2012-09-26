package websocket.message;


public class InviteMessage extends Message {

    @Override
    public boolean canHandle(String type) {
        if(type.equals("invite")){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Message clone() {
        return new InviteMessage();
    }


}
