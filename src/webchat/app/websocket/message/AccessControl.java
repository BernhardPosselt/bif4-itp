package websocket.message;

public class AccessControl {
	
	public static boolean IsAdmin(int userid){
		if (models.User.getbyId(userid).admin == true)
			return true;
		else
			return false;
	}
	
	public static boolean IsMod(int userid, int channelid){
		if (models.Channel.getbyId(channelid).mods.contains(userid))
			return true;
		else
			return false;
	}
	
	public static boolean IsReadOnly(int userid, int channelid){
		if (models.Channel.getbyId(channelid).readonly.contains(userid))
			return true;
		else
			return false;
	}
}
