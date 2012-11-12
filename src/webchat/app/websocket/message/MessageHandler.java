package websocket.message;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;

public class MessageHandler {
	 public static void handleMessage(JsonNode inmessage, int userid){
		 try{
			 WorkRoutine myroutine = MessageFactory.getMessageFromType(inmessage);
			 myroutine.inmessage = JsonBinder.bindfromJson(inmessage, myroutine.inmessage);
			 Model dbmodel = myroutine.inmessage.savetoDB(myroutine.inmessage);
			 if (myroutine.dbmodel != null){
				  myroutine.outmessage.genOutMessage(dbmodel);
				  myroutine.outmessage.sendMessage(myroutine.outmessage);
			 }
		 }
		 catch (Exception exp){
			 exp.printStackTrace();
		 }
	 }
}
