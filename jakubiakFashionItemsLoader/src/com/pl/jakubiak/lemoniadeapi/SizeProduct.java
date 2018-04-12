package com.pl.jakubiak.lemoniadeapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "product")
@XmlAccessorType (XmlAccessType.FIELD)
public class SizeProduct {
	String id;
	@XmlElement(name = "items")
	SizeItems items;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public SizeItems getItems() {
		return items;
	}
	public void setItems(SizeItems items) {
		this.items = items;
	}
}
