package com.example.mongoConnect.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mongoConnect.models.tickets;
import com.example.mongoConnect.repositories.ticketsRepository;

@Service
@Transactional
public class TicketServiceImp implements TicketService {
	@Autowired
	ticketsRepository ticketRepository;

	@Override
	public void createTicket(List<tickets> ticket) {
		// TODO Auto-generated method stub
		ticketRepository.saveAll(ticket);
	}
}
