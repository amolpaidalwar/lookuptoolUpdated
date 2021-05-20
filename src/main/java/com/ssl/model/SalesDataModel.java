package com.ssl.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="salesdata")
public class SalesDataModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pk;
	
	private String invoiceNumber;
	
	private Date transactionDate;
	
	private String transactionType;
	
	private String conditionType;
	
	private String orderId;
	
	private String shipmentId;
	
	private String shipmentDate;
	
	private Date orderDate;
	
	private String shipmentItemId;
	
	private int quantity;
	
	private String itemSerialNumber;
	
	private String asin;
	
	@Column(name = "sku", unique = true)
	private String sku;
	
	private String productTaxCode;
	
	private String billingCity;
	
	private String billingState;
	
	private String billingCountry;
	
	private String billingPostalCode;
	
	private String shipFromCity;
	
	private String shipFromState;
	
	private String shipFromCountry;

	private String shipFromPostalCode;

	private String shipToCity;
	
	private String shipToState;
	
	private String shipToCountry;
	
	private String shipToPostalCode;
	
	private String taxType;
	
	private double taxRate;
	
	private double principalAmount;
	
	private double principalTax;
	
	private double shippingAmount;
	
	private double shippingTax;
	
	private double giftWrapAmount;
	
	private double giftWrapTax;
	
	private double itemPromoDiscount;
	
	private double shippingPromoDiscount;
	
	private String warehouseID;
	
	private String deliveryServiceLevel;
	
	private String fulfillmentChannel;
	
	private String paymentMethodCode;
	
	private double sellingPrice;
	
	private double extSellPrice;
	
	private String schemeCode;
	
	private String eventId;
	
	@Column(name = "CREATEDTS",nullable = false)
	private Date createTime;
	
	@Column(name = "MODIFYTS",nullable = false)
	private Date modifyTime;
	
	private String modifiedBy;
	
	
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	public String getConditionType() {
		return conditionType;
	}
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}
	public String getShipmentDate() {
		return shipmentDate;
	}
	public void setShipmentDate(String shipmentDate) {
		this.shipmentDate = shipmentDate;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getShipmentItemId() {
		return shipmentItemId;
	}
	public void setShipmentItemId(String shipmentItemId) {
		this.shipmentItemId = shipmentItemId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getItemSerialNumber() {
		return itemSerialNumber;
	}
	public void setItemSerialNumber(String itemSerialNumber) {
		this.itemSerialNumber = itemSerialNumber;
	}
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getProductTaxCode() {
		return productTaxCode;
	}
	public void setProductTaxCode(String productTaxCode) {
		this.productTaxCode = productTaxCode;
	}
	public String getBillingCity() {
		return billingCity;
	}
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}
	public String getBillingState() {
		return billingState;
	}
	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}
	public String getBillingCountry() {
		return billingCountry;
	}
	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}
	public String getBillingPostalCode() {
		return billingPostalCode;
	}
	public void setBillingPostalCode(String billingPostalCode) {
		this.billingPostalCode = billingPostalCode;
	}
	public String getShipFromCity() {
		return shipFromCity;
	}
	public void setShipFromCity(String shipFromCity) {
		this.shipFromCity = shipFromCity;
	}
	public String getShipFromState() {
		return shipFromState;
	}
	public void setShipFromState(String shipFromState) {
		this.shipFromState = shipFromState;
	}
	public String getShipFromCountry() {
		return shipFromCountry;
	}
	public void setShipFromCountry(String shipFromCountry) {
		this.shipFromCountry = shipFromCountry;
	}
	public String getShipFromPostalCode() {
		return shipFromPostalCode;
	}
	public void setShipFromPostalCode(String shipFromPostalCode) {
		this.shipFromPostalCode = shipFromPostalCode;
	}
	public String getShipToCity() {
		return shipToCity;
	}
	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}
	public String getShipToState() {
		return shipToState;
	}
	public void setShipToState(String shipToState) {
		this.shipToState = shipToState;
	}
	public String getShipToCountry() {
		return shipToCountry;
	}
	public void setShipToCountry(String shipToCountry) {
		this.shipToCountry = shipToCountry;
	}
	public String getShipToPostalCode() {
		return shipToPostalCode;
	}
	public void setShipToPostalCode(String shipToPostalCode) {
		this.shipToPostalCode = shipToPostalCode;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	public double getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}
	public double getPrincipalAmount() {
		return principalAmount;
	}
	public void setPrincipalAmount(double principalAmount) {
		this.principalAmount = principalAmount;
	}
	public double getPrincipalTax() {
		return principalTax;
	}
	public void setPrincipalTax(double principalTax) {
		this.principalTax = principalTax;
	}
	public double getShippingAmount() {
		return shippingAmount;
	}
	public void setShippingAmount(double shippingAmount) {
		this.shippingAmount = shippingAmount;
	}
	public double getShippingTax() {
		return shippingTax;
	}
	public void setShippingTax(double shippingTax) {
		this.shippingTax = shippingTax;
	}
	public double getGiftWrapAmount() {
		return giftWrapAmount;
	}
	public void setGiftWrapAmount(double giftWrapAmount) {
		this.giftWrapAmount = giftWrapAmount;
	}
	public double getGiftWrapTax() {
		return giftWrapTax;
	}
	public void setGiftWrapTax(double giftWrapTax) {
		this.giftWrapTax = giftWrapTax;
	}
	public double getItemPromoDiscount() {
		return itemPromoDiscount;
	}
	public void setItemPromoDiscount(double itemPromoDiscount) {
		this.itemPromoDiscount = itemPromoDiscount;
	}
	public double getShippingPromoDiscount() {
		return shippingPromoDiscount;
	}
	public void setShippingPromoDiscount(double shippingPromoDiscount) {
		this.shippingPromoDiscount = shippingPromoDiscount;
	}
	public String getWarehouseID() {
		return warehouseID;
	}
	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}
	public String getDeliveryServiceLevel() {
		return deliveryServiceLevel;
	}
	public void setDeliveryServiceLevel(String deliveryServiceLevel) {
		this.deliveryServiceLevel = deliveryServiceLevel;
	}
	public String getFulfillmentChannel() {
		return fulfillmentChannel;
	}
	public void setFulfillmentChannel(String fulfillmentChannel) {
		this.fulfillmentChannel = fulfillmentChannel;
	}
	public String getPaymentMethodCode() {
		return paymentMethodCode;
	}
	public void setPaymentMethodCode(String paymentMethodCode) {
		this.paymentMethodCode = paymentMethodCode;
	}
	public double getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public double getExtSellPrice() {
		return extSellPrice;
	}
	public void setExtSellPrice(double extSellPrice) {
		this.extSellPrice = extSellPrice;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
