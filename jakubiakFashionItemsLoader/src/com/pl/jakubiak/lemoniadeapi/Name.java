package com.pl.jakubiak.lemoniadeapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "name")
@XmlAccessorType (XmlAccessType.FIELD)
public class Name {
	String code;
	String text;
	String title;
	String title_category;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle_category() {
		return title_category;
	}
	public void setTitle_category(String title_category) {
		this.title_category = title_category;
	}

}
