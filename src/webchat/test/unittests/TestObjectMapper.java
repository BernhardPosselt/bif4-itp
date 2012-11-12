package unittests;
import static org.junit.Assert.*;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import models.Channel;
import models.File;
import models.Groups;
import models.Message;
import models.User;


import org.codehaus.jackson.JsonNode;
import org.junit.Test;

import controllers.Application;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import play.libs.Json;
import play.mvc.WebSocket;
import websocket.Interfaces.IInMessage;
import websocket.Interfaces.IMessageData;
import websocket.json.in.InChannelClose;
import websocket.json.in.InChannelCloseData;
import websocket.json.in.InChannelDelete;
import websocket.json.in.InChannelDeleteData;
import websocket.json.in.InChannelName;
import websocket.json.in.InChannelNameData;
import websocket.json.in.InChannelTopic;
import websocket.json.in.InChannelTopicData;
import websocket.json.in.InFileDelete;
import websocket.json.in.InFileDeleteData;
import websocket.json.in.InInviteGroup;
import websocket.json.in.InInviteGroupData;
import websocket.json.in.InInviteUser;
import websocket.json.in.InInviteUserData;
import websocket.json.in.InMessage;
import websocket.json.in.InMessageData;
import websocket.json.in.InNewChannel;
import websocket.json.in.InNewChannelData;
import websocket.json.in.InProfileUpdate;
import websocket.json.in.InProfileUpdateData;
import websocket.json.out.ActiveUserData;
import websocket.json.out.ChannelData;
import websocket.json.out.FileData;
import websocket.json.out.GroupData;
import websocket.json.out.MessageData;
import websocket.json.out.UserData;
import websocket.message.JsonBinder;
import websocket.message.ListMapper;
import websocket.message.ObjectMapper;
import websocket.message.WorkRoutine;
import static play.test.Helpers.*; 

public class TestObjectMapper {
	
	/*@Test
	public void testmapfromdb_Message(){
		try{
			running(fakeApplication(inMemoryDatabase()), new Runnable() {
		    	//DB fertig generiert
		    	public void run()
		    	{
		    		Application.filltestdata();
			        models.Message message = new Message();
			        message.message = "Hallo du!";
			        message.date = new Date();
			        message.modified = new Date();
			        message.type = "text";
			        message.owner_id = User.find.byId(1);
			        message.channel_id = Channel.find.byId(1);
			        message.save();
			        WorkRoutine myroutine = new WorkRoutine();
			        myroutine.model = message;
			        myroutine.outmessage = new websocket.json.out.Message();
			        myroutine.dbaction = "update";
					websocket.json.out.Message mymessage = (websocket.json.out.Message)ObjectMapper.mapfromDB(myroutine);
					websocket.json.out.MessageData data = new MessageData();
					data = mymessage.data;
					assertEquals(data.message, "Hallo du!");
					assertEquals(data.owner_id, 1);
					assertEquals(data.type, "text");
		    	}		
			});
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	@Test
	public void testmapfromdb_Group(){
		try{
			running(fakeApplication(inMemoryDatabase()), new Runnable() {
		    	//DB fertig generiert
		    	public void run()
		    	{
		    		Application.filltestdata();
			        WorkRoutine myroutine = new WorkRoutine();
			        myroutine.model = Groups.find.byId(1);
			        myroutine.outmessage = new websocket.json.out.Group();
			        myroutine.dbaction = "update";
					websocket.json.out.Group mymessage = (websocket.json.out.Group)ObjectMapper.mapfromDB(myroutine);
					websocket.json.out.GroupData data = new GroupData();
					data = mymessage.data;
					assertEquals(mymessage.type, "group");
					assertEquals(data.id, 1);
					assertEquals(data.name, "Group1");
					assertNotNull(data.modified);
		    	}		
			});
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	@Test
	public void testmapfromdb_User(){
		try{
			running(fakeApplication(inMemoryDatabase()), new Runnable() {
		    	//DB fertig generiert
		    	public void run()
		    	{
		    		Application.filltestdata();
			       
			        WorkRoutine myroutine = new WorkRoutine();
			        myroutine.model = User.find.byId(1);
			        myroutine.outmessage = new websocket.json.out.User();
			        myroutine.dbaction = "update";
					websocket.json.out.User mymessage = (websocket.json.out.User)ObjectMapper.mapfromDB(myroutine);
					websocket.json.out.UserData data = new UserData();
					data = mymessage.data;
					assertEquals(mymessage.type,"user");
					assertEquals(data.id, 1);
					assertEquals(data.firstname, "Ernst");
					assertEquals(data.lastname, "Huber");
					assertEquals(data.username, "Glembo");
					assertEquals(data.groups.size(), 2);
					assertEquals(data.status, "offline");
					assertEquals(data.email, "a.b@aon.at");
					assertNotNull(data.modified);
		    	}		
			});
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	@Test
	public void testmapfromdb_ActiveUser(){
		try{
			running(fakeApplication(inMemoryDatabase()), new Runnable() {
		    	//DB fertig generiert
		    	public void run()
		    	{
		    		Application.filltestdata();     
			        WorkRoutine myroutine = new WorkRoutine();
			        myroutine.model = User.find.byId(1);
			        myroutine.outmessage = new websocket.json.out.ActiveUser();
			        myroutine.dbaction = "update";
					websocket.json.out.ActiveUser mymessage = (websocket.json.out.ActiveUser)ObjectMapper.mapfromDB(myroutine);
					websocket.json.out.ActiveUserData data = new  ActiveUserData();
					data = mymessage.data;
					assertEquals(data.id, 1);
					assertEquals(mymessage.type, "activeuser");
		    	}		
			});
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	@Test
	public void testmapfromdb_File(){
		try{
			running(fakeApplication(inMemoryDatabase()), new Runnable() {
		    	//DB fertig generiert
		    	public void run()
		    	{
		    		Application.filltestdata(); 
		    		models.File myfile = new File();
		    		myfile.date = new Date();
		    		myfile.name = "myfile";
		    		myfile.mimetype = ".pdf";
		    		myfile.owner_id = User.find.byId(1);
		    		myfile.size = 233.4345;
		    		myfile.save();
			        WorkRoutine myroutine = new WorkRoutine();
			        myroutine.model = myfile;
			        myroutine.outmessage = new websocket.json.out.File();
			        myroutine.dbaction = "update";
					websocket.json.out.File mymessage = (websocket.json.out.File)ObjectMapper.mapfromDB(myroutine);
					websocket.json.out.FileData data = new  FileData();
					data = mymessage.data;
					assertEquals(data.id, myfile.id);
					assertEquals(data.mimetype, ".pdf");
					assertEquals(data.name, "myfile");
					assertEquals(data.size, 233.4345,0);
					assertEquals(data.owner_id, 1);
					assertEquals(mymessage.type, "file");
		    	}		
			});
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	@Test
	public void testmapfromdb_Channel(){
		try{
			running(fakeApplication(inMemoryDatabase()), new Runnable() {
		    	//DB fertig generiert
		    	public void run()
		    	{
		    		Application.filltestdata(); 		    		
			        WorkRoutine myroutine = new WorkRoutine();
			        myroutine.model = models.Channel.find.byId(1);
			        myroutine.outmessage = new websocket.json.out.Channel();
			        myroutine.dbaction = "update";
					websocket.json.out.Channel mymessage = (websocket.json.out.Channel)ObjectMapper.mapfromDB(myroutine);
					websocket.json.out.ChannelData data = new  ChannelData();
					data = mymessage.data;
					assertEquals(data.id, 1);
					assertEquals(data.name, "Channel 1");
					assertEquals(mymessage.type, "channel");
					JsonNode json = JsonBinder.bindtoJson(myroutine);
					//System.out.println(json.toString());
		    	}		
			});
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	@Test
	public void testmaptodb_Message() {
		try{
			running(fakeApplication(inMemoryDatabase()), new Runnable() {
		    	//DB fertig generiert
		    	public void run()
		    	{
		    		Application.filltestdata();
			        
			        WorkRoutine myroutine = new WorkRoutine();
			        InMessage inmessage = new InMessage();
			        InMessageData indata = new InMessageData();
			        indata.channel_id = 1;
			        indata.message = "Servus";
			        indata.type = "text";
			        inmessage.data = indata;
			        myroutine.inmessage = inmessage;
			        myroutine.model = new models.Message();
			        myroutine.dbaction = "create";
					Model mymodel = (Model)ObjectMapper.maptoDB(myroutine, 2);
					models.Message mess = new models.Message();
					mess = (models.Message)mymodel;
					assertEquals(mess.channel_id,models.Channel.find.byId(1));
					assertEquals(mess.message, "Servus");
					assertEquals(mess.owner_id,models.User.getbyId(2));
					assertEquals(mess.type, "text");
		    	}		
			});
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	@Test
	public void testmaptodb_InChannelClose() {
		try{
			running(fakeApplication(inMemoryDatabase()), new Runnable() {
		    	//DB fertig generiert
		    	public void run()
		    	{
		    		Application.filltestdata();
			        
			        WorkRoutine myroutine = new WorkRoutine();
			        InChannelClose inmessage = new InChannelClose();
			        InChannelCloseData indata = new InChannelCloseData();
			        indata.id = 1;
			        indata.archived = true;
			        inmessage.data = indata;
			        myroutine.inmessage = inmessage;
			        myroutine.model = new models.Channel();
			        myroutine.outmessage = new websocket.json.out.Channel();
			        myroutine.dbaction = "update";
					Model mymodel = (Model)ObjectMapper.maptoDB(myroutine,2);
					models.Channel chan = new models.Channel();
					chan = (models.Channel)mymodel;
				 	assertEquals(true, chan.archived);	
				 	assertEquals(3, models.Channel.findAll().size());
		    	}		
			});
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	@Test
	public void testmaptodb_InInviteUser() {
		try{
			running(fakeApplication(inMemoryDatabase()), new Runnable() {
		    	//DB fertig generiert
		    	public void run()
		    	{
		    		Application.filltestdata();
		    		ListMapper.registerMap("users", new models.User());
		    		InInviteUser inmessage =new InInviteUser();
					inmessage.data = new InInviteUserData();
					InInviteUserData data=new InInviteUserData();
					data.id = 3;
					data.users = 2;
					data.value = true;
					inmessage.data = data;
					WorkRoutine myroutine = new WorkRoutine();
			        myroutine.inmessage = inmessage;
			        myroutine.model = new models.Channel();
			        myroutine.dbaction = "update";
					Model mymodel = (Model)ObjectMapper.maptoDB(myroutine,2);
					models.Channel chan = new models.Channel();
					chan = (models.Channel)mymodel;
					assertEquals(chan.id, 3);
					assertEquals(chan.users.contains(models.User.find.byId(2)), true);
					assertEquals(chan.users.size(), 1);
		    	}		
			});
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	@Test
	public void testmaptodb_InInviteGroup() {
		try{
			running(fakeApplication(inMemoryDatabase()), new Runnable() {
		    	//DB fertig generiert
		    	public void run()
		    	{
		    		Application.filltestdata();
		    		ListMapper.registerMap("groups", new models.Groups());
		    		InInviteGroup inmessage =new InInviteGroup();
					inmessage.data = new InInviteGroupData();
					InInviteGroupData data=new InInviteGroupData();
					data.id = 3;
					data.groups = 1;
					data.value = true;
					inmessage.data = data;
					WorkRoutine myroutine = new WorkRoutine();
			        myroutine.inmessage = inmessage;
			        myroutine.model = new models.Channel();
			        myroutine.dbaction = "update";
					Model mymodel = (Model)ObjectMapper.maptoDB(myroutine,2);
					models.Channel chan = new models.Channel();
					chan = (models.Channel)mymodel;
					assertEquals(chan.id, 3);
					assertEquals(chan.groups.contains(models.Groups.find.byId(1)), true);
					assertEquals(chan.groups.size(), 1);					
		    	}		
			});
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	@Test
	public void testmaptodb_InChannelTopic() {
		try{
			running(fakeApplication(inMemoryDatabase()), new Runnable() {
		    	//DB fertig generiert
		    	public void run()
		    	{
		    		Application.filltestdata();
		    		InChannelTopic inmessage =new InChannelTopic();
					inmessage.data = new InChannelTopicData();
					InChannelTopicData data=new InChannelTopicData();
					data.id = 2;
					data.topic = "newtopic";
					inmessage.data = data;
					WorkRoutine myroutine = new WorkRoutine();
			        myroutine.inmessage = inmessage;
			        myroutine.model = new models.Channel();
			        myroutine.dbaction = "update";
					Model mymodel = (Model)ObjectMapper.maptoDB(myroutine,2);
					models.Channel chan = new models.Channel();
					chan = (models.Channel)mymodel;
					assertEquals(chan.id, 2);
					assertEquals(chan.topic, "newtopic");					
		    	}		
			});
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	@Test
	public void testmaptodb_InNewChannel() {
		try{
			running(fakeApplication(inMemoryDatabase()), new Runnable() {
		    	//DB fertig generiert
		    	public void run()
		    	{
		    		Application.filltestdata();
		    		InNewChannel inmessage =new InNewChannel();

					inmessage.data = new InNewChannelData();
					InNewChannelData data=new InNewChannelData();
					data.name = "newchannel";
					data.topic = "newtopic";
					data.is_public = false;
					inmessage.data = data;
					WorkRoutine myroutine = new WorkRoutine();
			        myroutine.inmessage = inmessage;
			        myroutine.model = new models.Channel();
			        myroutine.dbaction = "create";
					Model mymodel = (Model)ObjectMapper.maptoDB(myroutine,2);
					models.Channel chan = new models.Channel();
					chan = (models.Channel)mymodel;
					assertEquals(chan.name, "newchannel");
					assertEquals(chan.topic, "newtopic");
					assertEquals(chan.is_public, false);
		    	}		
			});
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	@Test
	public void testmaptodb_InChannelName() {
		try{
			running(fakeApplication(inMemoryDatabase()), new Runnable() {
		    	//DB fertig generiert
		    	public void run()
		    	{
		    		Application.filltestdata();
		    		InChannelName inmessage =new InChannelName();
					inmessage.data = new InChannelNameData();
					InChannelNameData data=new InChannelNameData();
					data.id = 2;
					data.name = "new name";
					inmessage.data = data;
					WorkRoutine myroutine = new WorkRoutine();
			        myroutine.inmessage = inmessage;
			        myroutine.model = new models.Channel();
			        myroutine.dbaction = "update";
					Model mymodel = (Model)ObjectMapper.maptoDB(myroutine,2);
					models.Channel chan = new models.Channel();
					chan = (models.Channel)mymodel;
					assertEquals(chan.name, "new name");
					assertEquals(chan.id, 2);
		    	}		
			});
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	@Test
	public void testmaptodb_InChannelDelete() {
		try{
			running(fakeApplication(inMemoryDatabase()), new Runnable() {
		    	//DB fertig generiert
		    	public void run()
		    	{
		    		Application.filltestdata();
		    		InChannelDelete inmessage =new InChannelDelete();
					inmessage.data = new InChannelDeleteData();
					InChannelDeleteData data=new InChannelDeleteData();
					data.id = 3;
					inmessage.data = data;
					WorkRoutine myroutine = new WorkRoutine();
			        myroutine.inmessage = inmessage;
			        myroutine.model = new models.Channel();
			        myroutine.dbaction = "delete";
					Model mymodel = (Model)ObjectMapper.maptoDB(myroutine,2);
					models.Channel chan = new models.Channel();
					chan = (models.Channel)mymodel;
					assertEquals(models.Channel.findAll().size(), 2);
		    	}		
			});
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	
	@Test
	public void testmaptodb_InFileDelete() {
		try{
			running(fakeApplication(inMemoryDatabase()), new Runnable() {
		    	//DB fertig generiert
		    	public void run()
		    	{
		    		Application.filltestdata();
		    		models.File myfile = new File();
		    		myfile.filename = "myfile";
		    		myfile.date = new Date();
		    		myfile.owner_id = User.find.byId(2);
		    		myfile.channels = new ArrayList<Channel>();
		    		myfile.channels.add(Channel.find.byId(2));
		    		myfile.save();
		    		InFileDelete inmessage =new InFileDelete();
					inmessage.type = "join";
					inmessage.data = new InFileDeleteData();
					InFileDeleteData data=new InFileDeleteData();
					data.id = myfile.id;
					inmessage.data = data;
					WorkRoutine myroutine = new WorkRoutine();
			        myroutine.inmessage = inmessage;
			        myroutine.model = new models.File();
			        myroutine.dbaction = "delete";
					Model mymodel = (Model)ObjectMapper.maptoDB(myroutine,2);
					models.File file = new models.File();
					file = (models.File)mymodel;
					assertEquals(File.findAll().size(), 0);
		    	}		
			});
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}
	@Test
	public void testmaptodb_InProfileUpdate() {
		try{
			running(fakeApplication(inMemoryDatabase()), new Runnable() {
		    	//DB fertig generiert
		    	public void run()
		    	{
		    		Application.filltestdata();
		    		InProfileUpdate inmessage =new InProfileUpdate();
					inmessage.data = new InProfileUpdateData();
					InProfileUpdateData data=new InProfileUpdateData();
					data.id = 1;
					data.username = "newname";
					data.email = "newmail@mail.at";
					data.firstname = "karl";
					data.lastname = "maier";
					inmessage.data = data;
					WorkRoutine myroutine = new WorkRoutine();
			        myroutine.inmessage = inmessage;
			        myroutine.model = new models.User();
			        myroutine.dbaction = "update";
					Model mymodel = (Model)ObjectMapper.maptoDB(myroutine,2);
					models.User usr = new models.User();
					usr = (models.User)mymodel;
					assertEquals(usr.username, "newname");
					assertEquals(usr.firstname, "karl");
					assertEquals(usr.lastname, "maier");
					assertEquals(usr.email, "newmail@mail.at");
		    	}		
			});
		}catch (Exception exp){
			exp.printStackTrace();
		}
	}*/
}
