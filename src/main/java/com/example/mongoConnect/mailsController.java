package com.example.mongoConnect;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.mail.Address;
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
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

@RestController
@RequestMapping("/mail")
public class mailsController {
	
	private String toEmail;
	private InternetAddress[] toEmailAddress;
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public JSONObject sendMail(@RequestBody JSONObject mailAttributes) {
		String emailPort = "587";
		Properties emailProperties = System.getProperties();
		emailProperties.put("mail.smtp.port", emailPort);
		emailProperties.put("mail.smtp.auth", "true");
		emailProperties.put("mail.smtp.starttls.enable", "true");
		//--------------TEST--------------------
		String email = null;
		org.json.JSONArray arrayOfEmails = new supportEmails().getEmails();
		for(int i = 0;i < arrayOfEmails.length();i++) {
			BasicDBObject obj = (BasicDBObject) arrayOfEmails.get(i);
			String ids = obj.get("_id").toString();
			if(ids.equalsIgnoreCase(mailAttributes.get("company").toString())) {
				email = obj.get("email_id").toString();
				break;
			}
		}
		System.out.println(email);
		
		if(mailAttributes.get("toEmail") != null) {
			toEmail = mailAttributes.get("toEmail").toString();
		}else {
			toEmail = "shreyas.shivajirao@gmail.com";
		}
		
		String addresses = email + " , " + toEmail + " , " + "shreyassgujar89@gmail.com";
		System.out.println(addresses);
		try {
			toEmailAddress = InternetAddress.parse(addresses);
		} catch (AddressException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//--------------END---------------------
		
		String emailSubject = mailAttributes.get("Subject").toString();
		String emailBody = mailAttributes.get("Body").toString();
		Session mailSession = Session.getDefaultInstance(emailProperties, null);
		MimeMessage message = new MimeMessage(mailSession);
		 Map<String, String> input = new HashMap<String, String>();
         input.put("Author", "java2db.com");
         input.put("Topic", "HTML Template for Email");
         input.put("Content In", "English");
		try {
			String htmlText = readEmailFromHtml(input);
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(htmlText, "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(addresses));
			message.setSubject(emailSubject);
//			message.setText(emailBody);
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
//			transport.send(message);
			transport.close();
			System.out.println("Email Sent successfully");
			JSONObject mailStatus = new JSONObject();
			mailStatus.put("status", "sent");
			return mailStatus;
		} catch (AddressException e) {
			System.out.println(e.getMessage());
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
		}
		
		//Response of the mail status
		JSONObject mailStatus = new JSONObject();
		mailStatus.put("status", "sent");
		//----------------------------
		return mailStatus;
	}
	
	protected String readEmailFromHtml(Map<String, String> input)
	{
	    String msg = ""
	    		+ "<html>\n" + 
	    		"<head>\n" + 
	    		"</head>\n" + 
	    		"<body>\n" + 
	    		"<table bgcolor=\"#008000\" width=\"100%\">\n" + 
	    		"<tr>\n" + 
	    		"<td>\n" + 
	    		"<h2><font color=\"#ffffff\" ><b>Test HTML email</b></font></h2>\n" + 
	    		"</td>\n" + 
	    		"</tr>\n" + 
	    		"</table>\n" + 
	    		"<br>\n" + 
	    		"<table bgcolor=\"#d4d4d4\" width=\"100%\">\n" + 
	    		"<tr>\n" + 
	    		"<td>\n" + 
	    		"<b>This mail from  Author  , for the topic of \" \n" + 
	    		"<font color=\"#ff6600\">Topic </font>\" in \n" + 
	    		"<font color=\"#800080\">Content In </font> .</b> \n" + 
	    		"</td>\n" + 
	    		"</tr>\n" + 
	    		"</table>\n" + 
	    		"</body>\n" + 
	    		"</html>";
	    try
	    {
	    Set<Entry<String, String>> entries = input.entrySet();
	    for(Map.Entry<String, String> entry : entries) {
	        msg = msg.replace(entry.getKey().trim(), entry.getValue().trim());
	    }
	    }
	    catch(Exception exception)
	    {
	        exception.printStackTrace();
	    }
	    return msg;
	}
	
	private String readContentFromFile(String fileName)
	{
	    StringBuffer contents = new StringBuffer();
	    
	    try {
	      //use buffering, reading one line at a time
	      BufferedReader reader =  new BufferedReader(new FileReader(fileName));
	      try {
	        String line = null; 
	        while (( line = reader.readLine()) != null){
	          contents.append(line);
	          contents.append(System.getProperty("line.separator"));
	        }
	      }
	      finally {
	          reader.close();
	      }
	    }
	    catch (IOException ex){
	      ex.printStackTrace();
	    }
	    return contents.toString();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public void addEmails(@RequestBody JSONObject mailDetails) {
		MongoClient client = new MongoClient("localhost", 27017);
		DB dbs = client.getDB("ayushya");
		try {
			DBCollection collection = dbs.createCollection("Emails", new BasicDBObject());
		}catch(Exception e){
			System.out.println("collection exists");
		}
		DBCollection logsCollection = dbs.getCollection("Emails");
		BasicDBObject mailsObject = new BasicDBObject();
		mailsObject.put("email_id", mailDetails.get("email_id").toString());
		mailsObject.put("_id", mailDetails.get("company").toString());
		logsCollection.insert(mailsObject);
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public JSONArray getAllEmails(){
		MongoClient client = new MongoClient("localhost", 27017);
		DB dbs = client.getDB("ayushya");
		DBCollection MailsCollection = dbs.getCollection("Emails");
		DBCursor cur = MailsCollection.find();
		JSONArray arrayOfEmails = new JSONArray();
		while(cur.hasNext()) {
			DBObject emailDetails = cur.next();
			arrayOfEmails.add(emailDetails);
		}
		return arrayOfEmails;
	}
	
	@RequestMapping(value = "/edit/{company}", method = RequestMethod.PUT)
	public void editEmails(@PathVariable String company,@RequestBody JSONObject email_id_details) {
		MongoClient client = new MongoClient("localhost", 27017);
		DB dbs = client.getDB("ayushya");
		DBCollection MailsCollection = dbs.getCollection("Emails");
		BasicDBObject newMailDetails = new BasicDBObject();
		newMailDetails.append("$set", new BasicDBObject().append("email_id", email_id_details.get("email_id").toString()));
		System.out.println(email_id_details.get("email_id").toString());
		BasicDBObject oldMailDetails = new BasicDBObject().append("_id", company);
		MailsCollection.update(oldMailDetails, newMailDetails, false, false);
	}
	
}
