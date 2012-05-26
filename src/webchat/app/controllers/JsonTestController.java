package controllers;

import java.util.*;

import models.Groups;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import flexjson.*;
import play.libs.Json;
import play.mvc.*;
import websocket.json.out.*;
import websocket.json.in.*;

public class JsonTestController extends Controller {

	public static Result genAuth() {
		JsonNode json = null;
		try {
			json = Message.genjoinMessage(1);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ok(json);
	}

	public static Result genMessage() {
		JsonNode json = null;
		try {
			json = File.genjoinFile(2, "create", true, 2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ok(json);
	}

	public static Result genGroup() {
		int userid = 1;
		String json = "";
		ObjectNode jnode = Json.newObject(); // initalize Objectnodes
		ObjectNode data = jnode.objectNode();
		try {
			for (Iterator<Groups> iterator = Groups.find.all()
					.iterator(); iterator.hasNext();) {
				Groups group = new Groups();
				group = iterator.next();

				JsonNode gdata = data.objectNode();
				GroupData gd = new GroupData();
				gd.name = group.name;
				gd.modified = group.modified;
				JSONSerializer gser = new JSONSerializer().include();
				json = gser.exclude("*.class").serialize(gd);
				gdata = Json.parse(json);
				data.putObject(String.valueOf(group.id)).putAll(
						Json.fromJson(gdata, ObjectNode.class));

			}
			jnode.put("type", "group");
			jnode.putObject("data").putAll(data); // put the DataObject Node to
													// the jnode
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ok(jnode);
	}

	public static JsonNode buildinmessage() {
		ObjectNode injson = Json.newObject();
		ObjectNode data = injson.objectNode();
		ObjectNode dat = Json.newObject();
		ArrayNode channel = data.arrayNode();
		channel.add(1);
		channel.add(2);
		injson.put("type", "message");
		dat.put("message", "Hello Chris!");
		dat.put("type", "text");
		dat.putArray("channel").addAll(channel);
		data.putAll(dat);
		injson.putObject("data").putAll(data);
		return injson;
	}

	public static JsonNode buildinvite() {
		String json = "";
		InInvite invite = new InInvite();
		invite.type = "invite";
		InInviteData idata = new InInviteData();
		idata.groups.add(2);
		idata.groups.add(4);
		idata.users.add(2);
		invite.data.put(2, idata);
		idata = new InInviteData();
		idata.groups.add(5);
		idata.users.add(4);
		invite.data.put(3, idata);
		
		JSONSerializer gser = new JSONSerializer().include("*.actions", "*.data","*.users", "*.groups");
		json = gser.exclude("*.class").serialize(invite);
		return Json.parse(json);
	}

	public static JsonNode buildjoin(){
		String json= "";
		InJoin inj = new InJoin();
		inj.type = "join";
		InJoinData jdata = new InJoinData();
		jdata.channel = 2;
		inj.data = jdata;
		JSONSerializer jser = new JSONSerializer().include("*.data");
		json = jser.exclude("*.class").serialize(inj);
		return Json.parse(json);
	}
}
