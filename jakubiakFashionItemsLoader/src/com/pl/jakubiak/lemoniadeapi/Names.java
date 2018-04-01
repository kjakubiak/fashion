package com.pl.jakubiak.lemoniadeapi;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "names")
@XmlAccessorType (XmlAccessType.FIELD)
public class Names {
	@XmlElement(name = "name")
	List<Name> names = null;
	
	public List<Name> getNames()
	{
		return names;
	}
	public void setNames(List<Name> names)
	{
		this.names = names;
	}
	
}
