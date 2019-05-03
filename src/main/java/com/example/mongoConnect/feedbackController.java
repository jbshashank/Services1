package com.example.mongoConnect;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@RestController
@RequestMapping("/feedback")
public class feedbackController {
	
	@RequestMapping(value = "/{tickNum}", method = RequestMethod.POST)
	public JSONObject saveFeedback(@PathVariable String tickNum, @RequestBody JSONObject userDetails) {
		MongoClient client = new MongoClient("localhost", 27017);
		DB dbs = client.getDB("ayushya");
		try {
			DBCollection FeedbackCollection = dbs.createCollection("Feedback", new BasicDBObject());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		DBCollection FeedbackCollection = dbs.getCollection("Feedback");
		BasicDBObject queryObject = new BasicDBObject();
		queryObject.put("_id", userDetails.get("U_ID").toString());
		if(FeedbackCollection.find(queryObject).count() == 0) {
			BasicDBObject feedbackObject = new BasicDBObject();
			feedbackObject.put("_id", userDetails.get("U_ID").toString());
			feedbackObject.put("avgRating", userDetails.get("rating").toString());
			BasicDBObject feedback = new BasicDBObject();
			feedback.put("ticketNumber", tickNum);
			feedback.put("rating", userDetails.get("rating").toString());
			if(userDetails.get("comments") != null) {
				feedback.put("comments", userDetails.get("comments").toString());
			}
			feedbackObject.put("feedback_1", feedback);
			FeedbackCollection.insert(feedbackObject);
		}else {
			BasicDBObject prevUser = new BasicDBObject().append("_id", userDetails.get("U_ID").toString());
			org.json.JSONObject data = new org.json.JSONObject(FeedbackCollection.findOne(prevUser).toString());
			String avgrat = data.get("avgRating").toString();
			float avgRating = Float.parseFloat(avgrat);
			long feedbackCount = data.length()-1;
			float baseRating = avgRating*(feedbackCount-1);
			float newRating = Float.parseFloat(userDetails.get("rating").toString());
			avgRating = (baseRating+newRating)/feedbackCount;
			System.out.println(avgRating);
			BasicDBObject feedbackObject = new BasicDBObject();
			BasicDBObject logsObject = new BasicDBObject();
			feedbackObject.put("rating", userDetails.get("rating").toString());
			BasicDBObject feedback = new BasicDBObject();
			feedback.put("ticketNumber", tickNum);
			feedback.put("rating", userDetails.get("rating").toString());
			if(userDetails.get("comments") != null) {
				feedback.put("comments", userDetails.get("comments").toString());
			}
			logsObject.append("$set", new BasicDBObject().append("avgRating", Float.toString(avgRating)));
			FeedbackCollection.update(prevUser,logsObject, false, false);
			logsObject.append("$set",new BasicDBObject().append("feedback_"+Long.toString(feedbackCount), feedback));
			FeedbackCollection.update(prevUser,logsObject, false, false);
		}
		
		JSONObject responseObject = new JSONObject();
		responseObject.put("response", "done");
		return responseObject;
		
	}

}
