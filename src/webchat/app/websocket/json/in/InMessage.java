package websocket.json.in;

public class InMessage {
	public final String type;
	public InMessageData data;
	
	public InMessage(){
		this.type = "message";
	}
}
