package com.pl.jakubiak.lemoniadeapi;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "items")
@XmlAccessorType (XmlAccessType.FIELD)
public class SizeItems {
	@XmlElement(name = "item")
	List<SizeItem> items = null;

	public List<SizeItem> getItems() {
		return items;
	}

	public void setItems(List<SizeItem> items) {
		this.items = items;
	}
	
}
