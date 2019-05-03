package com.example.mongoConnect;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.mongoConnect.models.tickets;
import com.example.mongoConnect.repositories.ticketsRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@RestController
@RequestMapping("/tickets")
public class ticketsController {
	@Autowired
	private ticketsRepository repository;
	String ticketNumber;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Iterable<tickets> getAllTickets(){
		return repository.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Optional<tickets> getTicketById(@PathVariable("id") String id) {
		return repository.findById(id);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public tickets createTicket(@Valid @RequestBody tickets tick) {
		DateFormat dtf = new SimpleDateFormat("dd/MM/yy");
		DateFormat ticketDate = new SimpleDateFormat("dd/MM/yy, hh:mm");
		MongoClient client = new MongoClient("localhost", 27017);
		DB dbs = client.getDB("ayushya");
		DBCollection ticketsCollection = dbs.getCollection("tickets");
		DBCursor lastIns = ticketsCollection.find().sort(new BasicDBObject("_id", -1));
		DBObject lastInsertedTicket = lastIns.next();
		String idOfTicket = lastInsertedTicket.get("_id").toString();
		String dateOfTicket = lastInsertedTicket.get("date_of_post").toString().split(",")[0];
		String tickNum = idOfTicket.substring(idOfTicket.length()-3);
		Date date = new Date();
		String dateStr = dtf.format(date);
		if(dateStr.equals(dateOfTicket)) {
			System.out.println(dateStr);
			System.out.println(dateOfTicket);
			int ticketsCount = Integer.parseInt(tickNum);
			ticketNumber = Long.toString(ticketsCount+1);
			if(ticketNumber.length() == 1)
				ticketNumber = "00" + ticketNumber;
			if(ticketNumber.length() == 2)
				ticketNumber = "0" + ticketNumber;
		}else {
			System.out.println(dateStr);
			System.out.println(dateOfTicket);
			ticketNumber = "000";
		}
		String ticketDateStr = ticketDate.format(date);
		String[] splitDate = dateStr.split("/");
		if(splitDate[0].length() == 1)
			splitDate[0] = "0"+splitDate[0];
		if(splitDate[1].length() == 1)
			splitDate[1] = "0"+splitDate[1];
		String tricketNumber = "ATAS"+splitDate[0]+splitDate[1]+splitDate[2] + ticketNumber;
		tick.set_id(tricketNumber);
		tick.setDate_of_post(ticketDateStr);
		repository.save(tick);
		return tick;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void modifyTicket(@PathVariable("id") String id, @Valid @RequestBody tickets tick) {
		tick.set_id(id);
		repository.save(tick);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String deleteTicket(@PathVariable("id") String id) {
		repository.deleteById(id);
		return "DELETED";
	}
	

	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.PUT)
	public JSONObject mod(@PathVariable("id") String id, @RequestBody JSONObject tick) {
		MongoClient client = new MongoClient("localhost", 27017);
		DB dbs = client.getDB("ayushya");
		DBCollection collection = dbs.getCollection("tickets");
		BasicDBObject updateObject = new BasicDBObject();
		if(tick.get("customerAddress") != null) {
			System.out.println("customerAddress");
			updateObject.append("$set", new BasicDBObject().append("customerAddress", tick.get("customerAddress").toString()));
			BasicDBObject old = new BasicDBObject().append("_id", id);
			collection.update(old, updateObject, false, false);
		}
		
		if(tick.get("customerEmail") != null) {
			System.out.println("customerEmail");
			updateObject.append("$set", new BasicDBObject().append("customerEmail", tick.get("customerEmail").toString()));
			BasicDBObject old = new BasicDBObject().append("_id", id);
			collection.update(old, updateObject, false, false);
		}
			
		
		if(tick.get("customerName") != null) {
			System.out.println("customerName");
			updateObject.append("$set", new BasicDBObject().append("customerName", tick.get("customerName").toString()));
			BasicDBObject old = new BasicDBObject().append("_id", id);
			collection.update(old, updateObject, false, false);
		}
		
		if(tick.get("ticketDate") != null) {
			System.out.println("ticketDate");
			updateObject.append("$set", new BasicDBObject().append("ticketDate", tick.get("ticketDate").toString()));
			BasicDBObject old = new BasicDBObject().append("_id", id);
			collection.update(old, updateObject, false, false);
		}
			
		
		if(tick.get("ticketCategory") != null) {
			System.out.println("ticketCategory");
			updateObject.append("$set", new BasicDBObject().append("ticketCategory", tick.get("ticketCategory").toString()));
			BasicDBObject old = new BasicDBObject().append("_id", id);
			collection.update(old, updateObject, false, false);
		}
			
		
		if(tick.get("serialNumber") != null) {
			System.out.println("serialNumber");
			updateObject.append("$set", new BasicDBObject().append("serialNumber", tick.get("serialNumber").toString()));
			BasicDBObject old = new BasicDBObject().append("_id", id);
			collection.update(old, updateObject, false, false);
		}
			
		
		if(tick.get("product") != null) {
			System.out.println("product");
			updateObject.append("$set", new BasicDBObject().append("product", tick.get("product").toString()));
			BasicDBObject old = new BasicDBObject().append("_id", id);
			collection.update(old, updateObject, false, false);
		}
			
		
		if(tick.get("problemDiscription") != null) {
			System.out.println("problemDiscription");
			updateObject.append("$set", new BasicDBObject().append("problemDiscription", tick.get("problemDiscription").toString()));
			BasicDBObject old = new BasicDBObject().append("_id", id);
			collection.update(old, updateObject, false, false);
		}
			
		
		if(tick.get("warrenty") != null) {
			System.out.println("warrenty");
			updateObject.append("$set", new BasicDBObject().append("warrenty", tick.get("warrenty").toString()));
			BasicDBObject old = new BasicDBObject().append("_id", id);
			collection.update(old, updateObject, false, false);
		}
			
		
		if(tick.get("brand") != null) {
			System.out.println("brand");
			updateObject.append("$set", new BasicDBObject().append("brand", tick.get("brand").toString()));
			BasicDBObject old = new BasicDBObject().append("_id", id);
			collection.update(old, updateObject, false, false);
		}
			
		
		if(tick.get("customerPhone") != null) {
			System.out.println("customerPhone");
			updateObject.append("$set", new BasicDBObject().append("customerPhone", tick.get("customerPhone").toString()));
			BasicDBObject old = new BasicDBObject().append("_id", id);
			collection.update(old, updateObject, false, false);
		}
		
		if(tick.get("locationLat") != null) {
			System.out.println("locationLat");
			updateObject.append("$set", new BasicDBObject().append("locationLat", tick.get("locationLat").toString()));
			BasicDBObject old = new BasicDBObject().append("_id", id);
			collection.update(old, updateObject, false, false);
		}
		
		if(tick.get("locationLon") != null) {
			System.out.println("locationLon");
			updateObject.append("$set", new BasicDBObject().append("locationLon", tick.get("locationLon").toString()));
			BasicDBObject old = new BasicDBObject().append("_id", id);
			collection.update(old, updateObject, false, false);
		}
		
		if(tick.get("ticketStatus") != null) {
			System.out.println("ticketStatus");
			updateObject.append("$set", new BasicDBObject().append("ticketStatus", tick.get("ticketStatus").toString()));
			BasicDBObject old = new BasicDBObject().append("_id", id);
			collection.update(old, updateObject, false, false);
		}
		
		if(tick.get("assignedto") != null) {
			System.out.println("assignedto");
			updateObject.append("$set", new BasicDBObject().append("assignedto", tick.get("assignedto").toString()));
			BasicDBObject old = new BasicDBObject().append("_id", id);
			collection.update(old, updateObject, false, false);
		}
		
		if(tick.get("otherDamages") != null) {
			System.out.println("otherDamages");
			updateObject.append("$set", new BasicDBObject().append("otherDamages", tick.get("otherDamages").toString()));
			BasicDBObject old = new BasicDBObject().append("_id", id);
			collection.update(old, updateObject, false, false);
		}
		
		if(tick.get("model") != null) {
			System.out.println("model");
			updateObject.append("$set", new BasicDBObject().append("model", tick.get("model").toString()));
			BasicDBObject old = new BasicDBObject().append("_id", id);
			collection.update(old, updateObject, false, false);
		}
		
		BasicDBObject old = new BasicDBObject().append("_id", id);
		collection.update(old, updateObject, false, false);
		JSONObject js = new JSONObject();
		js.put("job", "done");
		return js;
	}
}
