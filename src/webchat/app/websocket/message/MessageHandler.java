package websocket.message;

import models.Message;
import models.User;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;
import websocket.json.in.InMessage;

public class MessageHandler {
	 public static void handleMessage(JsonNode inmessage, int userid){
		 try{
			 WorkRoutine myroutine = MessageFactory.getMessageFromType(inmessage.findPath("type").asText());
			 myroutine.inmessage = JsonBinder.bindfromJson(inmessage, myroutine);
			 if (myroutine.model != null){
				 myroutine.model = ObjectMapper.maptoDB(myroutine);
				 models.Message message= new Message();
				 message = (models.Message)myroutine.model;
				 message.user_id = User.find.byId(1);
				 message.update();
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
