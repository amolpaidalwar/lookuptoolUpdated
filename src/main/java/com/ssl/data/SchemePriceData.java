package com.ssl.data;

import java.io.Serializable;

public class SchemePriceData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1600317031638747563L;

	private String sku; 
	
	private String effectiveDate;
	
	private String mrp;
	
	private String discType;
	
	private String discValue;
	
	private String sellPrice;
	
	private String extDiscType;
	
	private String extDiscValue;
	
	private String extSellPrice;
	
	private String sslLiability;
	
	private String schemeCode;
	
	private String eventId;
	
	private String promoDesc;
	
	
	private String modifiedBy;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getMrp() {
		return mrp;
	}

	public void setMrp(String mrp) {
		this.mrp = mrp;
	}

	public String getDiscType() {
		return discType;
	}

	public void setDiscType(String discType) {
		this.discType = discType;
	}

	public String getDiscValue() {
		return discValue;
	}

	public void setDiscValue(String discValue) {
		this.discValue = discValue;
	}

	public String getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getExtDiscType() {
		return extDiscType;
	}

	public void setExtDiscType(String extDiscType) {
		this.extDiscType = extDiscType;
	}

	public String getExtDiscValue() {
		return extDiscValue;
	}

	public void setExtDiscValue(String extDiscValue) {
		this.extDiscValue = extDiscValue;
	}

	public String getExtSellPrice() {
		return extSellPrice;
	}

	public void setExtSellPrice(String extSellPrice) {
		this.extSellPrice = extSellPrice;
	}

	public String getSslLiability() {
		return sslLiability;
	}

	public void setSslLiability(String sslLiability) {
		this.sslLiability = sslLiability;
	}

	public String getSchemeCode() {
		return schemeCode;
	}

	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getPromoDesc() {
		return promoDesc;
	}

	public void setPromoDesc(String promoDesc) {
		this.promoDesc = promoDesc;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}
