package websocket.message;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;

public class MessageHandler {
	
	 public static void handleMessage(JsonNode inmessage, int userid){
		 try{
			 WorkRoutine myroutine = MessageFactory.getMessageFromType(inmessage);
			 if (myroutine.inmessage != null)
			 {
				 myroutine.inmessage = JsonBinder.bindfromJson(inmessage, myroutine.inmessage);
				 myroutine.dbmodel = myroutine.inmessage.savetoDB(myroutine.inmessage, userid);
				 if (myroutine.dbmodel != null){
					  myroutine.outmessage = myroutine.outmessage.genOutMessage(myroutine.dbmodel, userid);
					  myroutine.outmessage.sendMessage(myroutine.outmessage);
				 }
			 }
		 }
		 catch (Exception exp){
			 exp.printStackTrace();
		 }
	 }
}
