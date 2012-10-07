package websocket.message;


public abstract class IInMessage extends IMessage {
	public abstract boolean canHandle(String type);
	public abstract WorkRoutine getWorkRoutine();
}
