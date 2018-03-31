package com.pl.jakubiak.numocoapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "product")
@XmlAccessorType (XmlAccessType.FIELD)

public class Product {
	String ean;
	String name;
	String code;
	String price_netto;
	String sale;
	String fillup;
	String description;
	String count;
	@XmlElement(name = "photos")
	Photos photos;
	String vat;
	String producer_code;
	String producer;
	String category_id;
	String category_id2;
	String category_id3;
	String category_id4;
	String add_date;
	@XmlElement(name = "sizes")
	Sizes sizes;
	String model;
	String cloth;
	String color;
	
	public Sizes getSizes()
	{
		return this.sizes;
	}
	public void setSizes(Sizes sizes)
	{
		this.sizes=sizes;
	}
	
	public String getName()
	{
		return this.name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getEan()
	{
		return this.ean;
	}
	public void setEan(String ean)
	{
		this.ean = ean;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPrice_netto() {
		return price_netto;
	}
	public void setPrice_netto(String price_netto) {
		this.price_netto = price_netto;
	}
	public String getSale() {
		return sale;
	}
	public void setSale(String sale) {
		this.sale = sale;
	}
	public String getFillup() {
		return fillup;
	}
	public void setFillup(String fillup) {
		this.fillup = fillup;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public Photos getPhotos() {
		return photos;
	}
	public void setPhotos(Photos photos) {
		this.photos = photos;
	}
	public String getVat() {
		return vat;
	}
	public void setVat(String vat) {
		this.vat = vat;
	}
	public String getProducer_code() {
		return producer_code;
	}
	public void setProducer_code(String producer_code) {
		this.producer_code = producer_code;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getCategory_id2() {
		return category_id2;
	}
	public void setCategory_id2(String category_id2) {
		this.category_id2 = category_id2;
	}
	public String getCategory_id3() {
		return category_id3;
	}
	public void setCategory_id3(String category_id3) {
		this.category_id3 = category_id3;
	}
	public String getCategory_id4() {
		return category_id4;
	}
	public void setCategory_id4(String category_id4) {
		this.category_id4 = category_id4;
	}
	public String getAdd_date() {
		return add_date;
	}
	public void setAdd_date(String add_date) {
		this.add_date = add_date;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getCloth() {
		return cloth;
	}
	public void setCloth(String cloth) {
		this.cloth = cloth;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
