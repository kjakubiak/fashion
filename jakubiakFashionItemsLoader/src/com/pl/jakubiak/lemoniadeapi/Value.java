package com.pl.jakubiak.lemoniadeapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "value")
@XmlAccessorType (XmlAccessType.FIELD)
public class Value {
	private String code;
	private String id_color;
	private String color;
	private String color_hex;
	private String id_sized;
	private String size;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getId_color() {
		return id_color;
	}
	public void setId_color(String id_color) {
		this.id_color = id_color;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getColor_hex() {
		return color_hex;
	}
	public void setColor_hex(String color_hex) {
		this.color_hex = color_hex;
	}
	public String getId_sized() {
		return id_sized;
	}
	public void setId_sized(String id_sized) {
		this.id_sized = id_sized;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Value [code=");
		builder.append(code);
		builder.append(", id_color=");
		builder.append(id_color);
		builder.append(", color=");
		builder.append(color);
		builder.append(", color_hex=");
		builder.append(color_hex);
		builder.append(", id_sized=");
		builder.append(id_sized);
		builder.append(", size=");
		builder.append(size);
		builder.append("]");
		return builder.toString();
	}
	

}
