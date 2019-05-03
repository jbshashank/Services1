package com.example.mongoConnect;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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
@RequestMapping("/reqitems")
public class newItemsController {

	@RequestMapping(value = "/{Brand}/{Product}/{Model}/Parts", method = RequestMethod.POST)
	public JSONObject requestNewItems(@PathVariable String Brand, 
			@PathVariable String Product, 
			@PathVariable String Model, @RequestBody JSONObject productDetails) {
		System.out.println(Product);
		MongoClient client = new MongoClient("localhost", 27017);
		DB dbs = client.getDB("ayushya");
		try {
			DBCollection newItemsCollection = dbs.createCollection("New_Items", new BasicDBObject());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		DBCollection newItemsCollection = dbs.getCollection("New_Items");
		BasicDBObject itemDetails = new BasicDBObject();
		//---------------SENDING MAILS TO THE SUPPLIER----------------------
		
		String emailPort = "587";
		Properties emailProperties = System.getProperties();
		emailProperties.put("mail.smtp.port", emailPort);
		emailProperties.put("mail.smtp.auth", "true");
		emailProperties.put("mail.smtp.starttls.enable", "true");
		
		String email = null;
		org.json.JSONArray arrayOfEmails = new supportEmails().getEmails();
		for(int i = 0;i < arrayOfEmails.length();i++) {
			BasicDBObject obj = (BasicDBObject) arrayOfEmails.get(i);
			String ids = obj.get("_id").toString();
			if(ids.equalsIgnoreCase(Brand)) {
				email = obj.get("email_id").toString();
				break;
			}
		}
		
		String emailSubject = productDetails.get("Subject").toString();
		String emailBody = productDetails.get("Body").toString() + "\n\n" + 
		productDetails.get("partname") + "\t" + "Qty: 1";
		Session mailSession = Session.getDefaultInstance(emailProperties, null);
		MimeMessage message = new MimeMessage(mailSession);
		
		try {
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject(emailSubject);
			message.setText(emailBody);
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
		}
		
		String emailHost = "smtp.gmail.com";
		String fromUser = "trupthin.murthy";
		String fromUserPass = "Cheeku@98";
		try {
			Transport transport = mailSession.getTransport("smtp");
			transport.connect(emailHost, fromUser, fromUserPass);
			System.out.println("connected successfuly");
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("Email Sent successfully");
		} catch (AddressException e) {
			System.out.println(e.getMessage());
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
		}
		
		//---------------SENDING MAILS TO THE SUPPLIER----------------------
		
		long count = newItemsCollection.count();
		itemDetails.put("_id", "Request_"+ count++);
		itemDetails.put("Brand", Brand);
		itemDetails.put("Product", Product);
		itemDetails.put("Model", Model);
		itemDetails.put("partname", productDetails.get("partname").toString());
		itemDetails.put("Desc", productDetails.get("Desc").toString());
		itemDetails.put("vendor", productDetails.get("vendor").toString());
		newItemsCollection.insert(itemDetails);
		JSONObject responseObject = new JSONObject();
		responseObject.put("response", "Email_Sent");
		return responseObject;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public JSONArray getAllNewRequests() {
		MongoClient client = new MongoClient("localhost", 27017);
		DB dbs = client.getDB("ayushya");
		DBCollection newItemsCollection = dbs.getCollection("New_Items");
		DBCursor cur = newItemsCollection.find();
		JSONArray arrayOfRequests = new JSONArray();
		while(cur.hasNext()) {
			DBObject RequestDetails = cur.next();
			arrayOfRequests.add(RequestDetails);
		}
		return arrayOfRequests;
	}
}
