package com.example.mongoConnect;

import org.json.JSONArray;
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
@RequestMapping("/items")
public class purchasedItems {

	@RequestMapping(value = "/{tickNum}", method = RequestMethod.POST)
	public JSONObject saveitems(@PathVariable String tickNum, @RequestBody JSONObject itemDetails) {
		MongoClient client = new MongoClient("localhost", 27017);
		DB dbs = client.getDB("ayushya");
		try {
			DBCollection itemsCollection = dbs.createCollection("Items", new BasicDBObject());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		DBCollection itemsCollection = dbs.getCollection("Items");
		BasicDBObject queryObject = new BasicDBObject();
		queryObject.put("_id", tickNum);
		if(itemsCollection.find(queryObject).count() == 0) {
			BasicDBObject itemsObject = new BasicDBObject();
			itemsObject.put("_id", tickNum);
			float priceForEach = Float.parseFloat(itemDetails.get("price").toString());
			int quantityOfEach = Integer.parseInt(itemDetails.get("quantity").toString());
			float totPriceForEach = priceForEach * quantityOfEach;
			itemsObject.put("totalamount", totPriceForEach);
			BasicDBObject items = new BasicDBObject();
			items.put("partname", itemDetails.get("partname").toString());
			items.put("quantity", itemDetails.get("quantity").toString());
			items.put("price", itemDetails.get("price").toString());
			itemsObject.put("items_1", items);
			itemsCollection.insert(itemsObject);
		}else {
			BasicDBObject prevUser = new BasicDBObject().append("_id", tickNum);
			org.json.JSONObject data = new org.json.JSONObject(itemsCollection.findOne(prevUser).toString());
			String totalamount = data.get("totalamount").toString();
			float totamtflt = Float.parseFloat(totalamount);
			float priceForEach = Float.parseFloat(itemDetails.get("price").toString());
			int quantityOfEach = Integer.parseInt(itemDetails.get("quantity").toString());
			float totPriceForEach = priceForEach * quantityOfEach;
			totamtflt = totamtflt + totPriceForEach;
			long itemsCount = data.length()-1;
			BasicDBObject logsObject = new BasicDBObject();
			BasicDBObject items = new BasicDBObject();
			items.put("partname", itemDetails.get("partname").toString());
			items.put("quantity", itemDetails.get("quantity").toString());
			items.put("price", itemDetails.get("price").toString());
			logsObject.append("$set", new BasicDBObject().append("totalamount", Float.toString(totamtflt)));
			itemsCollection.update(prevUser,logsObject, false, false);
			logsObject.append("$set",new BasicDBObject().append("items_"+Long.toString(itemsCount), items));
			itemsCollection.update(prevUser,logsObject, false, false);
		}
		
		JSONObject response = new JSONObject();
		response.put("response", "Done");
		return response;
		
	}
	
	public JSONArray getAllItems(String tickNum) {
		MongoClient client = new MongoClient("localhost", 27017);
		DB dbs = client.getDB("ayushya");
		DBCollection MailsCollection = dbs.getCollection("Items");
		BasicDBObject queryObject = new BasicDBObject();
		queryObject.put("_id", tickNum);
		DBCursor cur = MailsCollection.find(queryObject);
		JSONArray arrayOfEmails = new JSONArray();
		System.out.println(arrayOfEmails.toString());
		while(cur.hasNext()) {
			DBObject emailDetails = cur.next();
			arrayOfEmails.put(emailDetails);
		}
		return arrayOfEmails;
	}
	
}
