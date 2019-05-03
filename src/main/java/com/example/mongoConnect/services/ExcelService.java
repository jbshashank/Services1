package com.example.mongoConnect.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.mongoConnect.models.tickets;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Description(value = "Service layer responsible for processing data.")
@Service
public class ExcelService {
	public String ticketNumber;
//    /**
//     * Method for reading from specific excel file when we know types and number of cells.
//     *
//     * @return list of mapped objects
//     * @throws IOException - throws IO exception.
//     */
//    public List<TicketDTO> readFromExcelWithKnownObject() throws IOException
//    {
//        // get file that needs to be mapped into object.
//        Resource resource = new ClassPathResource("documents/sample.xlsx");
//        FileInputStream inputStream = new FileInputStream(resource.getFile());
//
//        // get workbook and sheet
//        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//        Sheet sheet = workbook.getSheetAt(0);
//
//        List<TicketDTO> ticketDTOList = new ArrayList<>();
//
//        // iterate through rows
//        Iterator<Row> iterator = sheet.iterator();
//        while (iterator.hasNext())
//        {
//            Row currentRow = iterator.next();
//
//            // skip heading row.
//            if (currentRow.getRowNum() == 0) {
//                continue;
//            }
//
//            // mapped to example object.
//            TicketDTO ticketDTO = new TicketDTO();
//            ticketDTO.setTicketId(currentRow.getCell(0).getStringCellValue());
//            ticketDTO.setCustomer(currentRow.getCell(1).getStringCellValue());
//            ticketDTO.setBrand(currentRow.getCell(2).getDateCellValue().toString());
//            ticketDTO.setCategory(currentRow.getCell(3).getStringCellValue());
//            ticketDTO.setModel(currentRow.getCell(4).getStringCellValue());
//
//            ticketDTOList.add(ticketDTO);
//        }
//        return ticketDTOList;
//    }
    
    /**
     * Method for reading from specific excel file when we know types and number of cells.
     *
     * @return list of mapped objects
     * @throws IOException - throws IO exception.
     */
    public List<tickets> readFromExcelWithKnownObject(MultipartFile multipartFile) throws IOException
    {
        // get file that needs to be mapped into object.
    	    InputStream inputStream = new ByteArrayInputStream(multipartFile.getBytes());

        // get workbook and sheet
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        List<tickets> ticketDTOList = new ArrayList<>();

        // iterate through rows
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext())
        {
            Row currentRow = iterator.next();  
            System.out.println(currentRow.getCell(0).getStringCellValue());
            System.out.println(currentRow.getCell(7).getStringCellValue());
            // skip heading row.
            if (currentRow.getRowNum() == 0) {
                continue;
            }

    // mapped to example object.
    tickets ticketDTO = new tickets();
//    ticketDTO.setTicketId(currentRow.getCell(0).getStringCellValue());
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
	ticketDTO.set_id(tricketNumber);
	ticketDTO.setCall_type(currentRow.getCell(0).getStringCellValue());
	ticketDTO.setName(currentRow.getCell(1).getStringCellValue());
	ticketDTO.setAddress_1(currentRow.getCell(2).getStringCellValue());
	ticketDTO.setAddress_2(currentRow.getCell(3).getStringCellValue());
	ticketDTO.setStreet(currentRow.getCell(4).getStringCellValue());
	ticketDTO.setCity(currentRow.getCell(5).getStringCellValue());
	ticketDTO.setState(currentRow.getCell(6).getStringCellValue());
	ticketDTO.setPin_code(currentRow.getCell(7).getStringCellValue());
	ticketDTO.setTech_name(currentRow.getCell(8).getStringCellValue());
	ticketDTO.setMobile_number_1(currentRow.getCell(9).getStringCellValue());
	ticketDTO.setMobile_number_2(currentRow.getCell(10).getStringCellValue());
	ticketDTO.setEmail_id(currentRow.getCell(11).getStringCellValue());
	ticketDTO.setBrand(currentRow.getCell(12).getStringCellValue());
	ticketDTO.setProduct_category(currentRow.getCell(13).getStringCellValue());
	ticketDTO.setModel_name(currentRow.getCell(14).getStringCellValue());
	ticketDTO.setSerial_number(currentRow.getCell(15).getStringCellValue());
	ticketDTO.setIw(currentRow.getCell(16).getStringCellValue());
	ticketDTO.setVisit_date(dateStr);
	ticketDTO.setTime_of_visit(ticketDateStr);
	ticketDTO.setRemarks(currentRow.getCell(19).getStringCellValue());
	ticketDTO.setDate_of_post(dateStr);
	ticketDTO.setDealer_name(currentRow.getCell(22).getStringCellValue());

            ticketDTOList.add(ticketDTO);
        }
        return ticketDTOList;
    }


}
