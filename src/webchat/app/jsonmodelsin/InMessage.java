package jsonmodelsin;

public class InMessage {
	public final String type;
	public InMessageData data;
	
	public InMessage(){
		this.type = "message";
	}
}
