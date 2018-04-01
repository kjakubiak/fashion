package com.pl.jakubiak.lemoniadeapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "descriptions")
@XmlAccessorType (XmlAccessType.FIELD)
public class Description {
	@XmlElement(name = "description")
	private String code;
	private String text;
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
		builder.append("Description [code=");
		builder.append(code);
		builder.append(", text=");
		builder.append(text);
		builder.append("]");
		return builder.toString();
	}
	


}
