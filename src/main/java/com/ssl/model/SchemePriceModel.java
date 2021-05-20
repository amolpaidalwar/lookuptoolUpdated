package com.ssl.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

@SuppressWarnings("deprecation")
@Entity
@Table(name="SCHEMEPRICE")
public class SchemePriceModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3618620819057138723L;
	
	
	@Id
	@Column(name = "SKU")
	@Index(name="skuIndex")
	private String sku;
	
	@Id
	@Column(name = "EFECTIVEDATE")
	@Temporal(TemporalType.DATE)
	@Index(name="effectiveDateIndex")
	private Date effectiveDate;
	
	@Column(name = "MRP")
	private double mrp;
	
	@Column(name = "DISCTYPE")
	private String discType;
	
	@Column(name = "DISCVALUE")
	private double discValue;
	
	@Column(name = "SELLPRICE")
	private double sellPrice;
	
	@Column(name = "EXTDISCTYPE")
	private String extDiscType;
	
	@Column(name = "EXTDISCVALUE")
	private double extDiscValue;
	
	@Column(name = "EXTSELLPRICE")
	private double extSellPrice;
	
	@Column(name = "SSLLIABILITY")
	private String sslLiability;
	
	@Column(name = "SCHEMECODE")
	@Index(name="schemeCodeIndex")
	private String schemeCode;
	
	@Column(name = "EVENTID")
	@Index(name="eventIdIndex")
	private String eventId;
	
	@Column(name = "PROMODESC")
	private String promoDesc;
	
	@Column(name = "CREATEDTS",nullable = false)
	private Date  createdTime;
	
	@Column(name = "MODIFYTS",nullable = false)
	private Date modifiedTme;
	
	@Column(name = "MODIFYBYUSER")
	private String modifiedBy;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public double getMrp() {
		return mrp;
	}
	public void setMrp(double mrp) {
		this.mrp = mrp;
	}
	public String getDiscType() {
		return discType;
	}
	public void setDiscType(String discType) {
		this.discType = discType;
	}
	public double getDiscValue() {
		return discValue;
	}
	public void setDiscValue(double discValue) {
		this.discValue = discValue;
	}
	
	public double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getExtDiscType() {
		return extDiscType;
	}
	public void setExtDiscType(String extDiscType) {
		this.extDiscType = extDiscType;
	}
	public double getExtDiscValue() {
		return extDiscValue;
	}
	public void setExtDiscValue(double extDiscValue) {
		this.extDiscValue = extDiscValue;
	}
	public double getExtSellPrice() {
		return extSellPrice;
	}
	public void setExtSellPrice(double extSellPrice) {
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
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getModifiedTme() {
		return modifiedTme;
	}
	public void setModifiedTme(Date modifiedTme) {
		this.modifiedTme = modifiedTme;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	@Override
	public String toString() {
		return "SchemePrice [sku=" + sku + ", effectiveDate=" + effectiveDate + ", mrp=" + mrp
				+ ", discType=" + discType + ", discValue=" + discValue + ", SELLPRICE=" + sellPrice + ", extDiscType="
				+ extDiscType + ", extDiscValue=" + extDiscValue + ", extSellPrice=" + extSellPrice + ", sslLiability="
				+ sslLiability + ", schemeCode=" + schemeCode + ", eventId=" + eventId + ", promoDesc=" + promoDesc
				+ ", createdTime=" + createdTime + ", modifiedTme=" + modifiedTme + ", modifiedBy=" + modifiedBy + "]";
	}
	
}
