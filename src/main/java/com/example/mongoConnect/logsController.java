package com.example.mongoConnect;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;


@RestController
@RequestMapping("/logs")
public class logsController {

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public JSONObject logs(@PathVariable("id") String id, @RequestBody JSONObject logDetails) {
		MongoClient client = new MongoClient("localhost", 27017);
		DB dbs = client.getDB("ayushya");
		try {
			DBCollection collection = dbs.createCollection("EngineerLogs", new BasicDBObject());
		}catch(Exception e){
			System.out.println("collection exists");
		}
		DateFormat dtf = new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
		Date date = new Date();
		String dateStr = dtf.format(date);
		DBCollection logsCollection = dbs.getCollection("EngineerLogs");
		BasicDBObject queryObject = new BasicDBObject();
		queryObject.put("_id", id);
		if(logsCollection.find(queryObject).count()==0) {
			BasicDBObject newObject = new BasicDBObject();
			long logsCount = logsCollection.count();
			newObject.put("_id", id);
			BasicDBObject logsObject = new BasicDBObject();
			logsObject.put("log_no", "Log_" + Long.toString(logsCount++));
			logsObject.put("type", "Logging Module");
			logsObject.put("updated", dateStr);
			BasicDBObject detailsObject = new BasicDBObject();
			detailsObject.put("Action", logDetails.get("Action"));
			detailsObject.put("Result", logDetails.get("Result"));
			detailsObject.put("Initiator", logDetails.get("Initiator"));
			detailsObject.put("Responder", logDetails.get("Responder"));
			logsObject.put("Details", detailsObject);
			newObject.put("Log_1",logsObject);
			logsCollection.insert(newObject);
		}else {
			BasicDBObject oldObject = new BasicDBObject().append("_id", id);
			BasicDBObject newObject = new BasicDBObject();
			org.json.JSONObject coll = new org.json.JSONObject(logsCollection.findOne((oldObject)).toString());
			long logsCount = coll.length()-1;
			BasicDBObject logsObject = new BasicDBObject();
			newObject.put("log_no", "Log_" + Long.toString(++logsCount));
			newObject.put("type", "Logging Module");
			newObject.put("updated", dateStr);
			BasicDBObject detailsObject = new BasicDBObject();
			detailsObject.put("Action", logDetails.get("Action"));
			detailsObject.put("Result", logDetails.get("Result"));
			detailsObject.put("Initiator", logDetails.get("Initiator"));
			detailsObject.put("Responder", logDetails.get("Responder"));
			newObject.put("Details", detailsObject);
			logsObject.append("$set",new BasicDBObject().append("Log_"+Long.toString(logsCount), newObject));
			logsCollection.update(oldObject,logsObject, false, false);
		}
		
		//Json Response
		JSONObject respObj = new JSONObject();
		respObj.put("response", "done");
		
		return respObj;
	}

}
