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
@RequestMapping("/master")
public class ItemsMaster {
	
	@RequestMapping(value = "/parts", method = RequestMethod.POST)
	public void masterOfItems(@RequestBody JSONObject itemDetails) {
		MongoClient client = new MongoClient("localhost", 27017);
		DB dbs = client.getDB("ayushya");
		DBCollection masterCollection = dbs.getCollection("inventoryItems");
		BasicDBObject itemsObject = new BasicDBObject();
		try {
			itemsObject.put("_id", itemDetails.get("modelId").toString() + itemDetails.get("partId").toString());
			itemsObject.put("partname", itemDetails.get("partname").toString());
			itemsObject.put("price", itemDetails.get("price").toString());
			itemsObject.put("Desc", itemDetails.get("Desc").toString());
			itemsObject.put("modelId", itemDetails.get("modelId").toString());
			itemsObject.put("partId", itemDetails.get("partId").toString());
			itemsObject.put("vendor", itemDetails.get("vendor").toString());
			itemsObject.put("remaining_quantity", itemDetails.get("remaining_quantity").toString());
			itemsObject.put("procurement_date", itemDetails.get("procurement_date").toString());
			
			masterCollection.insert(itemsObject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Item with this object Id already exists in the database");
		}	
	}

	@RequestMapping(value = "/parts/edit", method = RequestMethod.PUT)
	public JSONObject editItems(@RequestBody JSONObject itemDetails) {
		MongoClient client = new MongoClient("localhost", 27017);
		DB dbs = client.getDB("ayushya");
		DBCollection masterCollection = dbs.getCollection("inventoryItems");
		BasicDBObject itemsObject = new BasicDBObject().append("_id", itemDetails.get("modelId").toString() 
				+ itemDetails.get("partId").toString());
		BasicDBObject updateItemsObject = new BasicDBObject();
		
		if(itemDetails.get("partname") != null) {
			updateItemsObject.append("$set", new BasicDBObject().append("customerAddress", itemDetails.get("partname").toString()));
			masterCollection.update(itemsObject, updateItemsObject, false, false);
		}
		
		if(itemDetails.get("price") != null) {
			updateItemsObject.append("$set", new BasicDBObject().append("price", itemDetails.get("price").toString()));
			masterCollection.update(itemsObject, updateItemsObject, false, false);
		}
		
		if(itemDetails.get("Desc") != null) {
			updateItemsObject.append("$set", new BasicDBObject().append("Desc", itemDetails.get("Desc").toString()));
			masterCollection.update(itemsObject, updateItemsObject, false, false);
		}
	
		if(itemDetails.get("vendor") != null) {
			updateItemsObject.append("$set", new BasicDBObject().append("vendor", itemDetails.get("vendor").toString()));
			masterCollection.update(itemsObject, updateItemsObject, false, false);
		}
		
		if(itemDetails.get("remaining_quantity") != null) {
			updateItemsObject.append("$set", new BasicDBObject().append("remaining_quantity", itemDetails.get("remaining_quantity").toString()));
			masterCollection.update(itemsObject, updateItemsObject, false, false);
		}
		
		if(itemDetails.get("procurement_date") != null) {
			updateItemsObject.append("$set", new BasicDBObject().append("procurement_date", itemDetails.get("procurement_date").toString()));
			masterCollection.update(itemsObject, updateItemsObject, false, false);
		}
		
		JSONObject responseObject = new JSONObject();
		responseObject.put("response", "Item updated");
		return responseObject;
	}
	
	@RequestMapping(value = "/parts/getall", method = RequestMethod.GET)
	public JSONArray getAllInventoryItems() {
		MongoClient client = new MongoClient("localhost", 27017);
		DB dbs = client.getDB("ayushya");
		DBCollection masterCollection = dbs.getCollection("inventoryItems");
		DBCursor cur = masterCollection.find();
		JSONArray arrayOfItems = new JSONArray();
		while(cur.hasNext()) {
			DBObject items = cur.next();
			System.out.println(items);
			arrayOfItems.add(items);
		}
		return arrayOfItems;
	}
	
	@RequestMapping(value = "/parts/getreq/{modelId}", method = RequestMethod.GET)
	public JSONArray getRequiredItems(@PathVariable String modelId) {
		MongoClient client = new MongoClient("localhost", 27017);
		DB dbs = client.getDB("ayushya");
		DBCollection masterCollection = dbs.getCollection("inventoryItems");
		DBCursor cur = masterCollection.find();
		JSONArray arrayOfItems = new JSONArray();
		while(cur.hasNext()) {
			DBObject items = cur.next();
			if(items.get("_id").toString().contains(modelId)) {
				arrayOfItems.add(items);
			}
			System.out.println(items);
		}
		return arrayOfItems;
	}
	
}
