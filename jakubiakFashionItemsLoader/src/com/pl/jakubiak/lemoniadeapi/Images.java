package com.pl.jakubiak.lemoniadeapi;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "images")
@XmlAccessorType (XmlAccessType.FIELD)
public class Images {
	@XmlElement(name = "image")
	List<String> images = null;
	
	public List<String> getImages()
	{
		return images;
	}
	public void setImages(List<String> image)
	{
		this.images = image;
	}
}
