package com.pl.jakubiak.lemoniadeapi;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "product")
@XmlAccessorType (XmlAccessType.FIELD)
public class Product {
	String id;
	String code;
    String price;
	String price_suggested;
	String currency;
	String id_category;
	@XmlElement(name = "names")
	Names names;
	@XmlElement(name = "descriptions")

	Descriptions descriptions;
	@XmlElement(name = "features")

	Features features;
	@XmlElement(name = "combinations")

	Combinations combinations;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Names getNames() {
		return names;
	}
	public void setNames(Names names) {
		this.names = names;
	}
	public Descriptions getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(Descriptions descriptions) {
		this.descriptions = descriptions;
	}
	
	public Combinations getCombinations() {
		return combinations;
	}
	public void setCombinations(Combinations combinations) {
		this.combinations = combinations;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product [id=");
		builder.append(id);
		builder.append(", code=");
		builder.append(code);
		builder.append(", names=");
		builder.append(names);
		builder.append(", descriptions=");
		builder.append(descriptions);
		builder.append(", combinations=");
		builder.append(combinations);
		builder.append("]");
		return builder.toString();
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPrice_suggested() {
		return price_suggested;
	}
	public void setPrice_suggested(String price_suggested) {
		this.price_suggested = price_suggested;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getId_category() {
		return id_category;
	}
	public void setId_category(String id_category) {
		this.id_category = id_category;
	}
	public Features getFeatures() {
		return features;
	}
	public void setFeatures(Features features) {
		this.features = features;
	}
	
	
}
