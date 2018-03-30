package numocoProcessing;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "root")
@XmlAccessorType (XmlAccessType.FIELD)
public class Root {
	@XmlElement(name = "products")
	Products products = null;
	String date = null;
	
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public Products getProducts()
	{
		return this.products;
	}

	public void setProducts(Products products)
	{
		this.products = products;
	}
}
