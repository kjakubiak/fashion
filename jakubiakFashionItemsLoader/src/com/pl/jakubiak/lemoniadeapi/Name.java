package com.pl.jakubiak.lemoniadeapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "name")
@XmlAccessorType (XmlAccessType.FIELD)
public class Name {
	String code;
	String text;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Name [code=");
		builder.append(code);
		builder.append(", text=");
		builder.append(text);
		builder.append("]");
		return builder.toString();
	}

}
