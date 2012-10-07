package websocket.message;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;

public class MessageHandler {
	 public static void handleMessage(JsonNode inmessage, int userid){
		 try{
			 WorkRoutine myroutine = MessageFactory.getMessageFromType(inmessage.findPath("type").asText());
			 myroutine.inmessage = JsonBinder.bindfromJson(inmessage, myroutine);
			 ObjectMapper.maptoDB(myroutine);
			 myroutine.outmessage = ObjectMapper.mapfromDB(myroutine);
			 JsonNode outmessage = JsonBinder.bindtoJson(myroutine);
			 myroutine.sender.sendMessage(outmessage);
		 }
		 catch (Exception exp){
			 exp.printStackTrace();
		 }
	 }
}
