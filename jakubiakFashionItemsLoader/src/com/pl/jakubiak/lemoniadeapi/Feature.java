package com.pl.jakubiak.lemoniadeapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "feature")
@XmlAccessorType (XmlAccessType.FIELD)
public class Feature {
	String code;
	@XmlElement(name = "names")
	Names names;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Names getNames() {
		return names;
	}
	public void setText(Names names) {
		this.names = names;
	}
	public void setNames(Names names) {
		this.names = names;
	}
	

}
