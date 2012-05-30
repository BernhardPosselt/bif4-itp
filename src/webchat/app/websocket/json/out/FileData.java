package websocket.json.out;

import java.util.*;

public class FileData {
	public String name;
	public String type;
	public double size;
	public int owner_id;
	public Date modified;
	public List<Integer> channels = new ArrayList<Integer>();
}
