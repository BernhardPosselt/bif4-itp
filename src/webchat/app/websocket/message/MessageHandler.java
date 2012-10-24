package websocket.message;

import models.Message;
import models.User;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;
import websocket.json.in.InMessage;

public class MessageHandler {
	 public static void handleMessage(JsonNode inmessage, int userid){
		 try{
			 WorkRoutine myroutine = MessageFactory.getMessageFromType(inmessage);
			 myroutine.inmessage = JsonBinder.bindfromJson(inmessage, myroutine);
			 if (myroutine.model != null){
				 myroutine.model = ObjectMapper.maptoDB(myroutine, userid);
			 }
			 if (myroutine.outmessage != null){
				  myroutine.outmessage = ObjectMapper.mapfromDB(myroutine);
				  JsonNode outmessage = JsonBinder.bindtoJson(myroutine);
				  myroutine.sender.sendMessage(outmessage);
			 }
		 }
		 catch (Exception exp){
			 exp.printStackTrace();
		 }
	 }
}
