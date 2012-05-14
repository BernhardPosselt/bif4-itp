package websocket.json.in;

public class InStatus {
	public final String type;
	public InStatusData data;
	
	public InStatus(){
		this.type = "status";
	}

}
