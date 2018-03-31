package com.pl.jakubiak.numocoapi;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "size")
@XmlAccessorType (XmlAccessType.PROPERTY)
public class Size {
	String name;
	String count;
	String price;
	String ean;
	@XmlAttribute	public String getName()
	{
		return this.name;
	}
	@XmlAttribute
	public String getCount()
	{
		return this.count;
	}
	@XmlAttribute
	public String getPrice()
	{
		return this.price;
	}
	@XmlAttribute
	public String getEan()
	{
		return this.ean;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	public void setCount(String count)
	{
		this.count=count;
	}
	public void setPrice(String price)
	{
		this.price=price;
	}
	public void setEan(String ean)
	{
		this.ean=ean;
	}
}
