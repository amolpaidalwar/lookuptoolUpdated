package com.ssl.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

@SuppressWarnings("deprecation")
@Entity
@Table(name="ASIANDATATABLE")
public class AsinModel{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK")
	private int pk;

    
	@Column(name = "SKU",unique = true )
	//@Column(name="SKU", table="ASIANDATATABLE", insertable=false, updatable=true,columnDefinition="NVARCHAR2(100) NULL")
	@Index(name="skuIndex")
	private String sku;

	@Column(name = "ASIN")
	@Index(name="asinIndex")
	private String asin;

	@Column(name = "EAN")
	@Index(name="eanIndex")
	private String ean;

	@Column(name = "MRP")
	private double mrp;

	@Column(name = "HSNCODE")
	private String hsnCode;

	@Column(name = "DEPT")
	private String dept;

	@Column(name = "ISACTIVE")
	private Integer isActive;

	@Column(name = "CREATEDTS",nullable = false)
	private Date createdTime;
	
	@Column(name = "MODIFYTS",nullable = false)
	private Date modifyTime;
	
	@Column(name = "MODIFYBYUSER")
	private String updatedBy;
	
	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public double getMrp() {
		return mrp;
	}

	public void setMrp(double mrp) {
		this.mrp = mrp;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		return "AsinData [pk=" + pk + ", sku=" + sku + ", asin=" + asin + ", ean=" + ean + ", mrp=" + mrp + ", hsnCode="
				+ hsnCode + ", dept=" + dept + ", isActive=" + isActive + "]";
	}
}
