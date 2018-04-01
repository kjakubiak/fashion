package com.pl.jakubiak.lemoniadeapi;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "attributes")
@XmlAccessorType (XmlAccessType.FIELD)
public class Values {
	@XmlElement(name = "value")
	List<Value> values = null;
	
	public List<Value> getValues()
	{
		return values;
	}
	public void seValues(List<Value> values)
	{
		this.values = values;
	}

}
