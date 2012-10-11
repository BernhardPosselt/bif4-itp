package unittests;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Date;

import junit.framework.Assert;

import org.codehaus.jackson.JsonNode;
import org.junit.Test;

import play.libs.Json;

import flexjson.JSONSerializer;

import websocket.json.in.*;
import websocket.json.out.*;
import websocket.message.IMessageData;
import websocket.message.JsonBinder;
import websocket.message.WorkRoutine;

public class TestJsonBinder {
	
	@Test
	public void testbindtoJson_ActiveUser(){
		WorkRoutine myroutine = new WorkRoutine();
		ActiveUser outmessage = new ActiveUser();
		ActiveUserData data = new ActiveUserData();
		outmessage.action = "create";
		data.id = 1;
		outmessage.data = data;
		myroutine.outmessage = outmessage;
		JsonNode json = JsonBinder.bindtoJson(myroutine);
		assertEquals(json.findPath("type").asText(), "activeuser");
		assertEquals(json.findPath("id").asText(), "1");
	}
	
	@Test
	public void testbindtoJson_Channel(){
		WorkRoutine myroutine = new WorkRoutine();
		Channel outmessage = new Channel();
		ChannelData data = new ChannelData();
		outmessage.action = "create";
		outmessage.data = new ChannelData();
		data.name = "rudi";
		data.topic = "karl";
		data.files.add(0);
		data.files.add(1);
		data.groups.add(2);
		data.users.add(4);
		data.users.add(2);
		outmessage.data = data;
		myroutine.outmessage = outmessage;
		myroutine.outmessage.type = "channel";
		myroutine.model = new models.Channel();
		JsonNode json = JsonBinder.bindtoJson(myroutine);
		assertEquals(json.findPath("type").asText(), "channel");
		assertEquals(json.findPath("name").asText(), "rudi");
		assertEquals(json.findPath("topic").asText(), "karl");
		assertEquals(json.findPath("action").asText(), "create");
		assertEquals(json.findValues("users").toString(), "[[4,2]]");
	}

	@Test
	public void testbindtoJson_File(){
		WorkRoutine myroutine = new WorkRoutine();
		File outmessage = new File();
		FileData data = new FileData();
		outmessage.action = "create";
		data.id = 1;
		data.mimetype = "text";
		data.modified = new Date();
		data.name = "myfile";
		data.size = 55.55;
		data.owner_id = 23;
		outmessage.data = data;
		myroutine.outmessage = outmessage;
		JsonNode json = JsonBinder.bindtoJson(myroutine);
		assertEquals(json.findPath("type").asText(), "file");
		assertEquals(json.findPath("name").asText(), "myfile");
		assertEquals(json.findPath("mimetype").asText(), "text");
		assertEquals(json.findPath("size").asDouble(), 55.55,0);
		assertEquals(json.findPath("owner_id").asInt(),23);
	}
	@Test
	public void testbindtoJson_Group(){
		WorkRoutine myroutine = new WorkRoutine();
		Group outmessage = new Group();
		GroupData data = new GroupData();
		data.id = 2;
		data.name = "mygroup";
		data.modified = new Date();
		outmessage.data = data;
		myroutine.outmessage = outmessage;
		JsonNode json = JsonBinder.bindtoJson(myroutine);
		assertEquals(json.findPath("type").asText(), "group");
		assertEquals(json.findPath("name").asText(), "mygroup");
	}
	@Test
	public void testbindtoJson_Message(){
		WorkRoutine myroutine = new WorkRoutine();
		Message outmessage = new Message();
		MessageData data = new MessageData();
		data.message = "mymessagecontent";
		data.date = new Date();
		data.id = 22;
		data.type = "java";
		data.owner_id = 2;
		data.modified = new Date();
		outmessage.data = data;
		myroutine.outmessage = outmessage;
		JsonNode json = JsonBinder.bindtoJson(myroutine);
		assertEquals(json.findPath("message").asText(), "mymessagecontent");
		assertEquals(json.findPath("id").asInt(), 22);
		assertEquals(json.findPath("owner_id").asInt(), 2);
	}
	@Test
	public void testbindtoJson_Status(){
		WorkRoutine myroutine = new WorkRoutine();
		Status outmessage = new Status();
		StatusData data = new StatusData();
		data.level = "error";
		data.msg = "Server crashed!";
		outmessage.data = data;
		myroutine.outmessage = outmessage;
		JsonNode json = JsonBinder.bindtoJson(myroutine);
		assertEquals(json.findPath("type").asText(), "status");
		assertEquals(json.findPath("level").asText(), "error");
		assertEquals(json.findPath("msg").asText(), "Server crashed!");
	}
	@Test
	public void testbindtoJson_User(){
		WorkRoutine myroutine = new WorkRoutine();
		User outmessage = new User();
		UserData data = new UserData();
		data.email = "a.b@aon.at";
		data.firstname = "Karl";
		data.lastname = "Maier";
		data.username = "Bond";
		data.id = 2;
		data.status = "online";
		data.modified = new Date();
		data.groups.add(2);
		data.groups.add(8);
		outmessage.data = data;
		myroutine.outmessage = outmessage;
		JsonNode json = JsonBinder.bindtoJson(myroutine);
		assertEquals(json.findPath("type").asText(), "user");
		assertEquals(json.findPath("firstname").asText(), "Karl");
		assertEquals(json.findPath("lastname").asText(), "Maier");
		assertEquals(json.findPath("username").asText(), "Bond");
		assertEquals(json.findPath("status").asText(), "online");
		assertEquals(json.findPath("email").asText(), "a.b@aon.at");
		assertEquals(json.findValues("groups").toString(), "[[2,8]]");
	}

	@Test
	public void testfromJson_InChannelClose(){
		try{
			InChannelClose chanclose =new InChannelClose();
			chanclose.type = "channelclose";
			chanclose.data = new InChannelCloseData();
			InChannelCloseData data=new InChannelCloseData();
			data.id = 1;
			data.archived = true;
			chanclose.data = data;
			JSONSerializer myser = new JSONSerializer().include("*.data");
			String json =  myser.exclude("*.class", "*.workRoutine").serialize(chanclose);
			JsonNode inmessage = Json.parse(json);
			WorkRoutine myroutine = new WorkRoutine();
			myroutine.inmessage = new InChannelClose();
			myroutine.outmessage = new Channel();
			myroutine.model = new models.Channel();
			myroutine.inmessage = JsonBinder.bindfromJson(inmessage, myroutine);
			Field f = chanclose.getClass().getField("data");
			InChannelCloseData indata = (InChannelCloseData)f.get(chanclose);
			assertEquals(myroutine.inmessage.type, "channelclose");
			assertEquals(indata.id, 1);
			assertEquals(indata.archived,true);
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	@Test
	public void testfromJson_InChannelDelete(){
		try{
			InChannelDelete chandel =new InChannelDelete();
			chandel.type = "channeldelete";
			chandel.data = new InChannelDeleteData();
			InChannelDeleteData data=new InChannelDeleteData();
			data.id = 3;
			chandel.data = data;
			JSONSerializer myser = new JSONSerializer().include("*.data");
			String json =  myser.exclude("*.class", "*.workRoutine").serialize(chandel);
			JsonNode inmessage = Json.parse(json);
			WorkRoutine myroutine = new WorkRoutine();
			myroutine.inmessage = new InChannelClose();
			myroutine.outmessage = new Channel();
			myroutine.model = new models.Channel();
			myroutine.inmessage = JsonBinder.bindfromJson(inmessage, myroutine);
			Field f = chandel.getClass().getField("data");
			InChannelDeleteData indata = (InChannelDeleteData)f.get(chandel);
			assertEquals(myroutine.inmessage.type, "channeldelete");
			assertEquals(indata.id, 3);
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	@Test
	public void testfromJson_InChannelName(){
		try{
			InChannelName chann =new InChannelName();
			chann.type = "channelname";
			chann.data = new InChannelNameData();
			InChannelNameData data=new InChannelNameData();
			data.id=4;
			data.name = "karli";
			chann.data = data;
			JSONSerializer myser = new JSONSerializer().include("*.data");
			String json =  myser.exclude("*.class", "*.workRoutine").serialize(chann);
			JsonNode inmessage = Json.parse(json);
			WorkRoutine myroutine = new WorkRoutine();
			myroutine.inmessage = new InChannelDelete();
			myroutine.outmessage = new Channel();
			myroutine.model = new models.Channel();
			myroutine.inmessage = JsonBinder.bindfromJson(inmessage, myroutine);
			Field f = chann.getClass().getField("data");
			InChannelNameData indata = (InChannelNameData)f.get(chann);
			assertEquals(myroutine.inmessage.type, "channelname");
			assertEquals(indata.name, "karli");
			assertEquals(indata.id, 4);
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	@Test
	public void testfromJson_InChannelTopic(){
		try{
			InChannelTopic chantop =new InChannelTopic();
			chantop.type = "channeltopic";
			chantop.data = new InChannelTopicData();
			InChannelTopicData data=new InChannelTopicData();
			data.id = 7;
			data.topic = "supi";
			chantop.data = data;
			JSONSerializer myser = new JSONSerializer().include("*.data");
			String json =  myser.exclude("*.class", "*.workRoutine").serialize(chantop);
			JsonNode inmessage = Json.parse(json);
			WorkRoutine myroutine = new WorkRoutine();
			myroutine.inmessage = new InChannelTopic();
			myroutine.outmessage = new Channel();
			myroutine.model = new models.Channel();
			myroutine.inmessage = JsonBinder.bindfromJson(inmessage, myroutine);
			Field f = chantop.getClass().getField("data");
			InChannelTopicData indata = (InChannelTopicData)f.get(chantop);
			assertEquals(myroutine.inmessage.type, "channeltopic");
			assertEquals(indata.topic, "supi");
			assertEquals(indata.id, 7);
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	@Test
	public void testfromJson_InFileDelete(){
		try{
			InFileDelete filedel =new InFileDelete();
			filedel.type = "filedelete";
			filedel.data = new InFileDeleteData();
			InFileDeleteData data=new InFileDeleteData();
			data.id = 3;
			filedel.data = data;
			JSONSerializer myser = new JSONSerializer().include("*.data");
			String json =  myser.exclude("*.class", "*.workRoutine").serialize(filedel);
			JsonNode inmessage = Json.parse(json);
			WorkRoutine myroutine = new WorkRoutine();
			myroutine.inmessage = new InFileDelete();
			myroutine.inmessage = JsonBinder.bindfromJson(inmessage, myroutine);
			Field f = filedel.getClass().getField("data");
			InFileDeleteData indata = (InFileDeleteData)f.get(filedel);
			assertEquals(myroutine.inmessage.type, "filedelete");
			assertEquals(indata.id, 3);
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	@Test
	public void testfromJson_InInviteUser(){
		try{
			InInviteUser inv =new InInviteUser();
			inv.type = "inviteuser";
			inv.data = new InInviteUserData();
			InInviteUserData data=new InInviteUserData();
			data.id = 6;
			data.users = 2;
			inv.data = data;
			JSONSerializer myser = new JSONSerializer().include("*.data", "*.users", "*.groups");
			String json =  myser.exclude("*.class", "*.workRoutine").serialize(inv);
			JsonNode inmessage = Json.parse(json);
			WorkRoutine myroutine = new WorkRoutine();
			myroutine.inmessage = new InInviteUser();
			myroutine.inmessage = JsonBinder.bindfromJson(inmessage, myroutine);
			Field f = inv.getClass().getField("data");
			InInviteUserData indata = (InInviteUserData)f.get(inv);
			assertEquals(myroutine.inmessage.type, "inviteuser");
			assertEquals(indata.users, 2);
			assertEquals(indata.id, 6);
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	
	@Test
	public void testfromJson_InInviteGroup(){
		try{
			InInviteGroup inv =new InInviteGroup();
			inv.data = new InInviteGroupData();
			inv.type = "invitegroup";
			InInviteGroupData data=new InInviteGroupData();
			data.id = 6;
			data.groups = 2;
			inv.data = data;
			JSONSerializer myser = new JSONSerializer().include("*.data", "*.users", "*.groups");
			String json =  myser.exclude("*.class", "*.workRoutine").serialize(inv);
			JsonNode inmessage = Json.parse(json);
			WorkRoutine myroutine = new WorkRoutine();
			myroutine.inmessage = new InInviteGroup();
			myroutine.inmessage = JsonBinder.bindfromJson(inmessage, myroutine);
			Field f = inv.getClass().getField("data");
			InInviteGroupData indata = (InInviteGroupData)f.get(inv);
			assertEquals(myroutine.inmessage.type, "invitegroup");
			assertEquals(indata.groups, 2);
			assertEquals(indata.id, 6);
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	@Test
	public void testfromJson_InJoin(){
		try{
			InJoin join =new InJoin();
			join.type = "join";
			join.data = new InJoinData();
			InJoinData data=new InJoinData();
			data.id = 3;
			join.data = data;
			JSONSerializer myser = new JSONSerializer().include("*.data");
			String json =  myser.exclude("*.class", "*.workRoutine").serialize(join);
			JsonNode inmessage = Json.parse(json);
			WorkRoutine myroutine = new WorkRoutine();
			myroutine.inmessage = new InJoin();
			myroutine.outmessage = new Channel();
			myroutine.model = new models.Channel();
			myroutine.inmessage = JsonBinder.bindfromJson(inmessage, myroutine);
			Field f = join.getClass().getField("data");
			InJoinData indata = (InJoinData)f.get(join);
			assertEquals(myroutine.inmessage.type, "join");
			assertEquals(indata.id, 3);
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	
		
	@Test
	public void testfromJson_InMessage(){
		try{
			InMessage mess =new InMessage();
			mess.type = "message";
			mess.data = new InMessageData();
			InMessageData data=new InMessageData();
			data.channel_id = 3;
			data.message = "Hallo ich bins.";
			data.type = "text";
			mess.data = data;
			JSONSerializer myser = new JSONSerializer().include("*.data");
			String json =  myser.exclude("*.class", "*.workRoutine").serialize(mess);
			JsonNode inmessage = Json.parse(json);
			WorkRoutine myroutine = new WorkRoutine();
			myroutine.inmessage = new InMessage();
			myroutine.inmessage = JsonBinder.bindfromJson(inmessage, myroutine);
			Field f = mess.getClass().getField("data");
			InMessageData indata = (InMessageData)f.get(mess);
			assertEquals(myroutine.inmessage.type, "message");
			assertEquals(indata.channel_id, 3);
			assertEquals(indata.message.equals("Hallo ich bins."), true);
			assertEquals(indata.type.equals("text"), true);
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	@Test
	public void testfromJson_InNewChannel(){
		try{
			InNewChannel channew =new InNewChannel();
			channew.type = "newchannel";
			channew.data = new InNewChannelData();
			InNewChannelData data=new InNewChannelData();
			data.is_public = true;
			data.name = "myname";
			data.topic = "mytopic";
			channew.data = data;
			JSONSerializer myser = new JSONSerializer().include("*.data");
			String json =  myser.exclude("*.class", "*.workRoutine").serialize(channew);
			JsonNode inmessage = Json.parse(json);
			WorkRoutine myroutine = new WorkRoutine();
			myroutine.inmessage = new InNewChannel();
			myroutine.inmessage = JsonBinder.bindfromJson(inmessage, myroutine);
			Field f = channew.getClass().getField("data");
			InNewChannelData indata = (InNewChannelData)f.get(channew);
			assertEquals(myroutine.inmessage.type, "newchannel");;
			assertEquals(indata.name.equals("myname"), true);
			assertEquals(indata.topic.equals("mytopic"), true);
			assertEquals(indata.is_public, true);
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	@Test
	public void testfromJson_InProfileUpdate(){
		try{
			InProfileUpdate prof =new InProfileUpdate();
			prof.type = "profileupdate";
			prof.data = new InProfileUpdateData();
			InProfileUpdateData data=new InProfileUpdateData();
			data.email = "mymail@prov.at";
			data.firstname = "myfirstname";
			data.lastname = "mylastname";
			data.password = "mypassword";
			data.username = "myusername";
			prof.data = data;
			JSONSerializer myser = new JSONSerializer().include("*.data");
			String json =  myser.exclude("*.class", "*.workRoutine").serialize(prof);
			JsonNode inmessage = Json.parse(json);
			WorkRoutine myroutine = new WorkRoutine();
			myroutine.inmessage = new InProfileUpdate();
			myroutine.inmessage = JsonBinder.bindfromJson(inmessage, myroutine);
			Field f = prof.getClass().getField("data");
			InProfileUpdateData indata = (InProfileUpdateData)f.get(prof);
			assertEquals(myroutine.inmessage.type, "profileupdate");
			assertEquals(indata.username, "myusername");
			assertEquals(indata.email , "mymail@prov.at");
			assertEquals(indata.firstname, "myfirstname");
			assertEquals(indata.lastname, "mylastname");
			assertEquals(indata.password,"mypassword");
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
}
