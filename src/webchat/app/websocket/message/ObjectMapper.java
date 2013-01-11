package websocket.message;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.validator.util.privilegedactions.NewInstance;

import models.User;

import play.db.ebean.Model;
import websocket.Interfaces.IInMessage;
import websocket.Interfaces.IMessageData;
import websocket.Interfaces.IOutMessage;
import websocket.json.in.InChannelClose;
import websocket.json.in.InChannelCloseData;
import websocket.json.in.InMessageData;


public class ObjectMapper {
	public static Model maptoDB (WorkRoutine myroutine, int owner_id) {
		/*Model mymodel = (Model)myroutine.model;
		try{	
			IInMessage inmessage = myroutine.inmessage;
			Field field = inmessage.getClass().getField("data");
			IMessageData indata = (IMessageData)field.get(inmessage);
			Method m =null;
			if (!myroutine.dbaction.equals("create")){	
				m = mymodel.getClass().getMethod("getbyId", int.class);
				Field f = indata.getClass().getField("id");
				int id = (Integer)f.get(indata);
				mymodel= (Model) m.invoke(mymodel, id);
			}			
			for (Field infield:indata.getClass().getFields()){
				for (Field mfield:mymodel.getClass().getFields()){
					List<Model> modellist;
					if (infield.getName().equals(mfield.getName())){
						if (infield.getName().equals("users") || infield.getName().equals("groups")){
							Model arraymodel = ListMapper.getmapModel(indata.getClass().getField(infield.getName()).getName());			
							List<Model> helplist = (List<Model>)mfield.get(mymodel);
							modellist = helplist;
							m = arraymodel.getClass().getMethod("getbyId", int.class);
							Model helpmodel =(Model)m.invoke(arraymodel,infield.getInt(indata));
							if (!helplist.contains(helpmodel)){
								modellist.add(helpmodel);	
							}
							else if (!indata.getClass().getField("value").getBoolean(indata)){
								modellist.remove(helpmodel);
							}	
							mfield.set(mymodel,modellist);				
						}
						else if (mfield.getType().getName().contains("models.")){
							Class c = Class.forName(mfield.getType().getName());
							Model helpmodel = (Model)c.newInstance();
							m = helpmodel.getClass().getMethod("getbyId", int.class);
							mfield.set(mymodel, m.invoke(indata, infield.getInt(indata)));
						}
						else
							mfield.set(mymodel,infield.get(indata));	
					}
					else if (mfield.getName().equals("owner_id"))
						mfield.set(mymodel, models.User.getbyId(owner_id));
				}
			}
			if (myroutine.dbaction.equals("create")){
				mymodel.save();
				try {
					if (mymodel.getClass().getField("is_public") != null){
						if ((Boolean)mymodel.getClass().getField("is_public").get(mymodel)){
							
							mymodel.getClass().getField("users").set(mymodel, models.User.findAll());
						}
						else
						{	
							List<models.User> ulist = new ArrayList<models.User>();
							ulist.add(User.find.byId(owner_id));
							mymodel.getClass().getField("users").set(mymodel, ulist);
						}
						mymodel.saveManyToManyAssociations("users");
					}
				}
				catch(NoSuchFieldException exp){
				    	
				}	
			}
			else if (myroutine.dbaction.equals("delete"))
				mymodel.delete();
			else 
				mymodel.update();
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
		return mymodel;*/
		return null;
	}

	public static IOutMessage mapfromDB(WorkRoutine myroutine){
		/*IOutMessage outMessage = (IOutMessage)myroutine.outmessage;
		try{
			Model model = (Model)myroutine.model;
			Field field = outMessage.getClass().getField("data");
			IMessageData outdata = (IMessageData)field.get(outMessage);
			Method m = null;
			for(Field outfield :outdata.getClass().getFields()){
				for (Field modelfield :model.getClass().getFields()){
					if (outfield.getName().equals(modelfield.getName())){
						if (outfield.getType().getName().equals("java.util.List")){
							List<Model> modellist = (List<Model>)modelfield.get(model);	
							ArrayList<Integer> mylist= new ArrayList<Integer>();
							//Iteratate through all Listmembers
							for (Iterator<Model> iter = modellist.iterator(); iter.hasNext();){
								Model mid = (Model)iter.next();
								Field myfield = mid.getClass().getField("id");
								mylist.add(myfield.getInt(mid));
							}
							outfield.set(outdata, mylist);
						}
						else if (modelfield.getType().getName().contains("models.")){
							Model mid = (Model) modelfield.get(model);
							Field myfield = mid.getClass().getField("id");
							outfield.set(outdata, myfield.getInt(mid));
						}
						else{
							outfield.set(outdata, modelfield.get(model));
						}
					}
				}
			}
			outMessage.action = myroutine.dbaction;
			outMessage.data = outdata;
			
		}catch (Exception exp){
			exp.printStackTrace();
		}
		return outMessage;*/
		return null;
	}
}
