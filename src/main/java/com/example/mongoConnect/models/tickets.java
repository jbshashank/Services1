package com.example.mongoConnect.models;

import org.springframework.data.annotation.Id;

public class tickets {
	
	@Id
	public String _id;
	public String call_type;
	public String name;
	public String address_1;
	public String address_2;
	public String street;
	public String city;
	public String state;
	public String pin_code;
	public String tech_name;
	public String mobile_number_1;
	public String mobile_number_2;
	public String email_id;
	public String brand;
	public String product_category;
	public String model_name;
	public String serial_number;
	public String iw;
	public String visit_date;
	public String time_of_visit;
	public String remarks;
	public String date_of_post;
	public String invoice_number;
	public String dealer_name;
	
	public tickets() {
		
	}

	public tickets(String _id, String call_type, String name, String address_1, String address_2, String street,
			String city, String state, String pin_code, String tech_name, String mobile_number_1,
			String mobile_number_2, String email_id, String brand, String product_category, String model_name,
			String serial_number, String iw, String visit_date, String time_of_visit, String remarks,
			String date_of_post, String invoice_number, String dealer_name) {
		super();
		this._id = _id;
		this.call_type = call_type;
		this.name = name;
		this.address_1 = address_1;
		this.address_2 = address_2;
		this.street = street;
		this.city = city;
		this.state = state;
		this.pin_code = pin_code;
		this.tech_name = tech_name;
		this.mobile_number_1 = mobile_number_1;
		this.mobile_number_2 = mobile_number_2;
		this.email_id = email_id;
		this.brand = brand;
		this.product_category = product_category;
		this.model_name = model_name;
		this.serial_number = serial_number;
		this.iw = iw;
		this.visit_date = visit_date;
		this.time_of_visit = time_of_visit;
		this.remarks = remarks;
		this.date_of_post = date_of_post;
		this.invoice_number = invoice_number;
		this.dealer_name = dealer_name;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getCall_type() {
		return call_type;
	}

	public void setCall_type(String call_type) {
		this.call_type = call_type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress_1() {
		return address_1;
	}

	public void setAddress_1(String address_1) {
		this.address_1 = address_1;
	}

	public String getAddress_2() {
		return address_2;
	}

	public void setAddress_2(String address_2) {
		this.address_2 = address_2;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPin_code() {
		return pin_code;
	}

	public void setPin_code(String pin_code) {
		this.pin_code = pin_code;
	}

	public String getTech_name() {
		return tech_name;
	}

	public void setTech_name(String tech_name) {
		this.tech_name = tech_name;
	}

	public String getMobile_number_1() {
		return mobile_number_1;
	}

	public void setMobile_number_1(String mobile_number_1) {
		this.mobile_number_1 = mobile_number_1;
	}

	public String getMobile_number_2() {
		return mobile_number_2;
	}

	public void setMobile_number_2(String mobile_number_2) {
		this.mobile_number_2 = mobile_number_2;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getProduct_category() {
		return product_category;
	}

	public void setProduct_category(String product_category) {
		this.product_category = product_category;
	}

	public String getModel_name() {
		return model_name;
	}

	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}

	public String getSerial_number() {
		return serial_number;
	}

	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}

	public String getIw() {
		return iw;
	}

	public void setIw(String iw) {
		this.iw = iw;
	}

	public String getVisit_date() {
		return visit_date;
	}

	public void setVisit_date(String visit_date) {
		this.visit_date = visit_date;
	}

	public String getTime_of_visit() {
		return time_of_visit;
	}

	public void setTime_of_visit(String time_of_visit) {
		this.time_of_visit = time_of_visit;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDate_of_post() {
		return date_of_post;
	}

	public void setDate_of_post(String date_of_post) {
		this.date_of_post = date_of_post;
	}

	public String getInvoice_number() {
		return invoice_number;
	}

	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}

	public String getDealer_name() {
		return dealer_name;
	}

	public void setDealer_name(String dealer_name) {
		this.dealer_name = dealer_name;
	}

	
}
