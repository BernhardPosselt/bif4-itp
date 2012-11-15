package websocket.message;

import java.util.List;

import org.codehaus.jackson.JsonNode;

import controllers.LoginController;

import websocket.Interfaces.IOutMessage;

public class NotifyInit {
	public static void sendInit(List<Integer> userlist, int userid) {
		try {
			WorkRoutine routine = new WorkRoutine();
			routine.outmessage = new websocket.json.out.ActiveUser();
			IOutMessage outmessage = routine.outmessage.genOutMessage(models.User.getbyId(userid), userid);
			JsonNode outjson = JsonBinder.bindtoJson(outmessage);
			WebSocketNotifier.sendMessagetoUser(userlist, outjson);

			for (models.Groups mygroup : models.Groups.findAll()) {
				WorkRoutine myroutine = new WorkRoutine();
				myroutine.outmessage = new websocket.json.out.Group();
				IOutMessage outmsg  = myroutine.outmessage.genOutMessage(mygroup, userid);
				JsonNode outjs  = JsonBinder.bindtoJson(outmsg);
				WebSocketNotifier.sendMessagetoUser(userlist, outjs);
			}

			for (models.Channel mychan : models.Channel.findAll()) {
				WorkRoutine myroutine = new WorkRoutine();
				myroutine.outmessage = new websocket.json.out.Channel();
				IOutMessage outmsg  = myroutine.outmessage.genOutMessage(mychan, userid);
				JsonNode outjs  = JsonBinder.bindtoJson(outmsg);
				WebSocketNotifier.sendMessagetoUser(userlist, outjs);
			}

			for (models.User myuser : models.User.findAll()) {
				WorkRoutine myroutine = new WorkRoutine();
				myroutine.outmessage = new websocket.json.out.User();
				IOutMessage outmsg  = myroutine.outmessage.genOutMessage(myuser, userid);
				JsonNode outjs  = JsonBinder.bindtoJson(outmsg);
				WebSocketNotifier.sendMessagetoUser(userlist, outjs);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
}
