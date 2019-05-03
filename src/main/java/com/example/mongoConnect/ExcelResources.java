package com.example.mongoConnect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.mongoConnect.models.tickets;
import com.example.mongoConnect.services.ExcelService;
import com.example.mongoConnect.services.TicketService;

import java.io.IOException;
import java.util.List;

@Description(value = "Resource layer for handling REST requests.")
@RestController
@RequestMapping("/")
public class ExcelResources {
    private ExcelService excelService;
    @Autowired
    TicketService ticketService;

    /**
     * Constructor / dependency injector
     * @param excelService - service layer dependency.
     */
    public ExcelResources(ExcelService excelService) {
        this.excelService = excelService;
    }
    
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ping() throws IOException
    {
        return new ResponseEntity<>("Connected",HttpStatus.OK);
    }

    @PostMapping(value = "/excel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<tickets>> mapExcelRowsToObject(@RequestParam("file") MultipartFile multipartFile) throws IOException
    {
        List<tickets> ticketDTOList = this.excelService.readFromExcelWithKnownObject(multipartFile);
        ticketService.createTicket(ticketDTOList);
        return new ResponseEntity<>(ticketDTOList, HttpStatus.OK);
    }
}
