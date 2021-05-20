package com.ssl.data;

import java.io.Serializable;

public class PriceFeed implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8058415323686635964L;
	private String item_sku;
	private String standard_price;
	private String sale_price;
	private String start_date;
	private String end_date;
	public String getItem_sku() {
		return item_sku;
	}
	public void setItem_sku(String item_sku) {
		this.item_sku = item_sku;
	}
	public String getStandard_price() {
		return standard_price;
	}
	public void setStandard_price(String standard_price) {
		this.standard_price = standard_price;
	}
	public String getSale_price() {
		return sale_price;
	}
	public void setSale_price(String sale_price) {
		this.sale_price = sale_price;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
}
