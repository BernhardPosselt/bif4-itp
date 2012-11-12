package websocket.message;

import java.util.List;

import org.codehaus.jackson.JsonNode;

import websocket.Interfaces.IOutMessage;

public class NotifyInit {
	public static void sendInit(List<Integer> userlist) {
		try {
			WorkRoutine routine = new WorkRoutine();
			routine.outmessage = new websocket.json.out.ActiveUser();
			IOutMessage outmessage = routine.outmessage.genOutMessage(models.User.getbyId(Integer.parseInt(play.mvc.Controller.session("userid"))));
			JsonNode outjson = JsonBinder.bindtoJson(outmessage);
			WebSocketNotifier.sendMessagetoUser(userlist, outjson);

			for (models.Groups mygroup : models.Groups.findAll()) {
				WorkRoutine myroutine = new WorkRoutine();
				myroutine.outmessage = new websocket.json.out.Group();
				IOutMessage outmsg  = routine.outmessage.genOutMessage(mygroup);
				JsonNode outjs  = JsonBinder.bindtoJson(outmsg);
				WebSocketNotifier.sendMessagetoUser(userlist, outjs);
			}

			for (models.Channel mychan : models.Channel.findAll()) {
				WorkRoutine myroutine = new WorkRoutine();
				myroutine.outmessage = new websocket.json.out.Group();
				IOutMessage outmsg  = routine.outmessage.genOutMessage(mychan);
				JsonNode outjs  = JsonBinder.bindtoJson(outmsg);
				WebSocketNotifier.sendMessagetoUser(userlist, outjs);
			}

			for (models.User myuser : models.User.findAll()) {
				WorkRoutine myroutine = new WorkRoutine();
				myroutine.outmessage = new websocket.json.out.Group();
				IOutMessage outmsg  = routine.outmessage.genOutMessage(myuser);
				JsonNode outjs  = JsonBinder.bindtoJson(outmsg);
				WebSocketNotifier.sendMessagetoUser(userlist, outjs);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
}
