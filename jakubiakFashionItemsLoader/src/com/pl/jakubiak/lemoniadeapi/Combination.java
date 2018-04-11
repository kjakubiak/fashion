package com.pl.jakubiak.lemoniadeapi;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "combination")
@XmlAccessorType (XmlAccessType.FIELD)
public class Combination {
	String id;
	String ean;
	String quantity;
	Values attributes;
	Images images;
//	Images images;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Images getImages() {
		return images;
	}
	public void setImages(Images images) {
		this.images = images;
	}
	public String getEan() {
		return ean;
	}
	public void setEan(String ean) {
		this.ean = ean;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Combination [id=");
		builder.append(id);
		builder.append(", ean=");
		builder.append(ean);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", attributes=");
		builder.append(attributes);
		builder.append("]");
		return builder.toString();
	}
	public Values getAttributes() {
		return attributes;
	}
	public void setAttributes(Values attributes) {
		this.attributes = attributes;
	}
	

}
