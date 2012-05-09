package json_models;

import java.util.ArrayList;
import java.util.List;

import models.Channel;

public class MessageData{
	
	public String message;
	public String type;
	public List<Integer> channels = new ArrayList<Integer>();
	
	public List<Integer> getChannels() {
		return channels;
	}
	public void setChannels(int value) {
		this.channels.add(value);
	}
	
}

