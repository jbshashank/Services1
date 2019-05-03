package com.example.mongoConnect;

import java.io.File;
import java.io.IOException;

import org.json.JSONArray;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

public class supportEmails {
	
	public JSONArray getEmails() {
		MongoClient client = new MongoClient("localhost", 27017);
		DB dbs = client.getDB("ayushya");
		DBCollection MailsCollection = dbs.getCollection("Emails");
		DBCursor cur = MailsCollection.find();
		JSONArray arrayOfEmails = new JSONArray();
		while(cur.hasNext()) {
			DBObject emailDetails = cur.next();
			arrayOfEmails.put(emailDetails);
		}
		return arrayOfEmails;
	}
	
	public void setImage() {
		MongoClient client = new MongoClient("localhost", 27017);
		DB db = client.getDB("ayushya");
		DBCollection imageCollection = db.createCollection("image", new BasicDBObject());
		String newFileName = "first Image";
		GridFS gfspic = new GridFS(db, "image");
		File imageFile = new File("/Users/ShreyasGS/Desktop/mongodb.png");
		try {
			GridFSInputFile gfsFile = gfspic.createFile(imageFile);
			gfsFile.setFilename(newFileName);
			gfsFile.save();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
