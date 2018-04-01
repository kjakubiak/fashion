package com.pl.jakubiak.lemoniadeapi;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "descriptions")
@XmlAccessorType (XmlAccessType.FIELD)
public class Descriptions {
	@XmlElement(name = "description")
	List<Description> description = null;
	
	public List<Description> getDescriptions()
	{
		return description;
	}
	public void setDescriptions(List<Description> description)
	{
		this.description = description;
	}
}
