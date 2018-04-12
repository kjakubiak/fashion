package com.pl.jakubiak.lemoniadeapi;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "products")
@XmlAccessorType (XmlAccessType.FIELD)
public class SizeProducts {
	@XmlElement(name = "product")
	List<SizeProduct> products = null;
	
	public List<SizeProduct> getProducts()
	{
		return products;
	}
	public void setProducts(List<SizeProduct> products)
	{
		this.products = products;
	}

}
