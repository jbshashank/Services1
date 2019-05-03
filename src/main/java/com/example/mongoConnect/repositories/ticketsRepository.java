package com.example.mongoConnect.repositories;

import com.example.mongoConnect.models.tickets;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface ticketsRepository extends CrudRepository<tickets, String>{
	tickets findBy_id(ObjectId _id);
}
