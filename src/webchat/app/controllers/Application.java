package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.io.Files;

import flexjson.JSONSerializer;

import org.h2.util.IOUtils;
import org.joda.time.DateTime;
import play.Logger;

import play.libs.Json;
import play.mvc.*;
import views.html.*;
import websocket.WebsocketManager;

import play.mvc.Controller;

import org.codehaus.jackson.JsonNode;

import models.*;
import java.util.UUID;

import static com.google.common.io.Files.copy;

public class Application extends Controller {
  
	 static int userid;

    /**
     * Displays the index page
     * @return
     */
	 public static Result index() {

         if(session("userid") != null) //User logged in
         {
             userid = Integer.parseInt(session("userid"));
             return ok(index.render(User.getUsername(userid), User.find.byId(userid).admin));
         }
         else //User doesn't logged in
         {
             return redirect(routes.LoginController.login());
         }
	 }

    public static Result upload_form()
    {
        session("channelid", request().uri().substring(19));
        return ok(upload.render(form(models.File.class)));
    }

    public static Result download_file(Integer file_id, String name)
    {

        models.File tmp = models.File.find.byId(Integer.valueOf(file_id));

        File download = new File(play.Play.application().path().toString() + "/files/" + tmp.filename);

        return ok(download);
    }


    public static Result upload() throws IOException {
        Http.MultipartFormData body =  request().body().asMultipartFormData();

        Http.MultipartFormData.FilePart uploaded_file= body.getFile("uploaded_file");

        if(uploaded_file != null)
        {
            String filename = uploaded_file.getFilename();
            String contentType = uploaded_file.getContentType();
            

            String unqName = UUID.randomUUID().toString() + "_" + filename;
            File file = uploaded_file.getFile();
            File dest = new File(play.Play.application().path().toString() + "/files/" + unqName);
            

            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(dest);

            IOUtils.copy(fis, fos);
            
            fis.close();
            fos.close();
            
            //Save file to database
            int channelid = Integer.valueOf(session("channelid"));
            models.File new_file = new models.File();
            new_file.name = filename;
            new_file.filename = unqName;
            new_file.type = contentType;
            new_file.date = DateTime.now().toDate();
            new_file.uid = User.find.byId(Integer.valueOf(session("userid")));
            new_file.size = file.length();
            new_file.channels.add(models.Channel.find.byId(channelid));
            new_file.save();
            new_file.saveManyToManyAssociations("channels");
            websocket.WebsocketManager.notifyAllMembers(websocket.json.out.File.gennewFile(new_file));
            websocket.json.in.InMessage inmessage = new websocket.json.in.InMessage();
            websocket.json.in.InMessageData indata = new websocket.json.in.InMessageData();
            
            System.out.println(contentType);
            List<String> validoctettypes = new ArrayList<String>();
            validoctettypes.add("java");
            validoctettypes.add("php");
            validoctettypes.add("coffee");
            validoctettypes.add("js");
            validoctettypes.add("php");
            
            List<String> validplaintypes = new ArrayList<String>();
            validplaintypes.add("vb");
            validplaintypes.add("cs");
            validplaintypes.add("cpp");
            String filetype = filename.substring(filename.indexOf(".")+1);
            System.out.println("endung: "+ filetype);
            if (contentType.equals("application/octet-stream") && validoctettypes.contains(filetype)){
            
             	indata.channel.add(channelid);
                FileInputStream filestream = new FileInputStream(file);
	        	indata.message =  org.apache.commons.io.IOUtils.toString(filestream);
	        	filestream.close();
	        	if (filetype.equals("coffee"))
	        		filetype = "js";
	            indata.type = filetype;
	            inmessage.data = indata;
            	
            }
            else if (contentType.equals("text/plain") && validplaintypes.contains(filetype)){
            	indata.channel.add(channelid);
                FileInputStream filestream = new FileInputStream(file);
	        	indata.message =  org.apache.commons.io.IOUtils.toString(filestream);
	        	filestream.close();
	        	if (filetype.equals("cs"))
	        		filetype = "csharp";
	            indata.type = filetype;
	            inmessage.data = indata;
            }
            else if (contentType.equals("text/css")){
            	indata.channel.add(channelid);
                FileInputStream filestream = new FileInputStream(file);
	        	indata.message =  org.apache.commons.io.IOUtils.toString(filestream);
	        	filestream.close();
	            indata.type = filetype;
	            inmessage.data = indata;
            }
            else{
 
	        	indata.channel.add(channelid);
	        	indata.message =  "download/" + new_file.id + "/" + filename;
	            indata.type = "text";
	            inmessage.data = indata;
            }
           
    		JSONSerializer aser = new JSONSerializer().include("*");
			String json = aser.exclude("*.class").serialize(inmessage);
            websocket.WebsocketManager.notifyAllMembers(websocket.json.out.Message.genMessage(Json.parse(json), Integer.valueOf(session("userid"))));
            websocket.WebsocketManager.notifyAllMembers(websocket.json.out.Channel.genChannel("update", channelid));
            return ok(upload.render(form(models.File.class)));
        }
        else
        {
            flash("error", "Missing file");
            return badRequest(upload.render(form(models.File.class)));
        }
    }
	 
	 public static Result filltestdata()
	 {
            User user = new User();
	        user.username = "Glembo";
            user.setPassword("test");
	        user.email = "a.b@aon.at";
	        user.lastname = "Huber";
	        user.firstname = "Ernst";
	        user.admin = true;
	        user.online = false;
	        user.active = true;
	        user.save();
	        
	        User user1 = new User();
	        user1.username = "MasterLindi";
            user1.setPassword("test");
	        user1.email = "christoph.lindmaier@gmx.at";
	        user1.lastname = "Lindmaier";
	        user1.firstname = "Christoph";
	        user1.admin = false;
	        user1.online = false;
	        user1.active = true;
	        user1.save();
	         
	        Channel channel = new Channel();
	  	    channel.name = "Channel 1";
	  	    channel.topic = "Webengineering";
	  	    channel.is_public = false;
	  	    channel.archived = false;
	  	    channel.save();
	  	   
	  	    Channel channel1 = new Channel();
	  	    channel1.name = "Channel2";
	  	    channel1.topic = "Softwareengineering";
	  	    channel1.archived = false;
	  	    channel1.is_public= true;
	  	  
	  	    channel1.save();
	  	    
	  	    channel1.setUsers(user);
	  	    channel1.setUsers(user1);
	  	    channel1.saveManyToManyAssociations("users");
	  	   
	  	    channel.setUsers(user); 
	  	    channel.setUsers(user1);
	  	    channel.saveManyToManyAssociations("users");
	  	   
	  	    Groups group = new Groups();
	  	    group.modified = new Date();
	  	    group.name = "Group1";
	  	    group.users.add(user);
	  	    group.channels.add(channel);
	  	    group.save();
	  	    group.saveManyToManyAssociations("channels");
	  	    group.saveManyToManyAssociations("users");
	  	    
	  	    Groups group1 = new Groups();
	  	    group1.modified = new Date();
	  	    group1.name = "Group2";
	  	    group1.users.add(user);
	  	    group1.users.add(user1);
	  	    group1.save();
	  	    group1.saveManyToManyAssociations("channels");
	  	    group1.saveManyToManyAssociations("users");
	  	    return ok(index.render("testdata", false));
	 }
	 
	 
	 public static WebSocket<JsonNode> websocket() {
         int userId;
         if(session("userid") != null){
             userId = Integer.parseInt(session("userid"));
            return WebsocketManager.getWebsocket(userId);
	    }
	    return null;
     }
	 
}