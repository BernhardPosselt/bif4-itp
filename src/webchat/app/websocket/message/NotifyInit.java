package websocket.message;

import java.util.List;

import org.codehaus.jackson.JsonNode;

import play.db.ebean.Model;

import websocket.WebsocketNotifier;

public class NotifyInit{

	public static void sendInit(List<Integer> userlist) {
		try{
			for (models.Groups mygroup : models.Groups.findAll()){
				WorkRoutine myroutine = new WorkRoutine();
				myroutine.outmessage = new websocket.json.out.Group();
				myroutine.model = mygroup;
				myroutine.dbaction = "create";
				myroutine.outmessage = ObjectMapper.mapfromDB(myroutine);
				JsonNode outmessage = JsonBinder.bindtoJson(myroutine);
				WebsocketNotifier.sendMessagetoUser(userlist, outmessage);
			}
			
			for (models.Channel mychan : models.Channel.findAll()){
				WorkRoutine myroutine = new WorkRoutine();
				myroutine.outmessage = new websocket.json.out.Channel();
				myroutine.dbaction = "create";
				myroutine.model = mychan;
				myroutine.outmessage = ObjectMapper.mapfromDB(myroutine);	
				JsonNode outmessage = JsonBinder.bindtoJson(myroutine);
				myroutine.dbaction = "create";
				WebsocketNotifier.sendMessagetoUser(userlist, outmessage);
			}
			
			for (models.User myuser : models.User.findAll()){
				WorkRoutine myroutine = new WorkRoutine();
				myroutine.outmessage = new websocket.json.out.User();
				myroutine.model = myuser;
				myroutine.dbaction = "create";
				myroutine.outmessage = ObjectMapper.mapfromDB(myroutine);
				JsonNode outmessage = JsonBinder.bindtoJson(myroutine);
				WebsocketNotifier.sendMessagetoUser(userlist, outmessage);
			}
			
			
			WorkRoutine myroutine = new WorkRoutine();
			myroutine.model = models.User.getbyId(userlist.get(0));
			myroutine.dbaction = "create";
			myroutine.outmessage = new websocket.json.out.ActiveUser();
			myroutine.outmessage = ObjectMapper.mapfromDB(myroutine);
			JsonNode outmessage = JsonBinder.bindtoJson(myroutine);	
			WebsocketNotifier.sendMessagetoUser(userlist, outmessage);
			
	  	 	//WebsocketNotifier.(User.genUserchanged(userid, "update"));
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
	}

}
