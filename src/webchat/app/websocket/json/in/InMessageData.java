package websocket.json.in;

import java.util.ArrayList;
import java.util.List;

public class InMessageData {
	public String message;
	public String type;
	public List<Integer> channels = new ArrayList<Integer>();
}
