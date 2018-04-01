package com.pl.jakubiak.lemoniadeapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "file")
@XmlAccessorType (XmlAccessType.FIELD)
public class Root {
	@XmlElement(name = "products")
	Products products = null;
	@XmlElement(name = "header")
	Header header = null;
	//@XmlElement(name = "categories")
	//Products products = null;
	public Products getProducts() {
		return products;
	}
	public void setProducts(Products products) {
		this.products = products;
	}
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}

}
