package com.pl.jakubiak.numocoapi;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "photos")
@XmlAccessorType (XmlAccessType.FIELD)
public class Photos {
	@XmlElement(name = "photo")
	List<String> photos = null;
	
	public List<String>  getPhotos()
	{
		return photos;
	}
	
	
	public void setPhotos(List<String> photos)
	{
		this.photos = photos;
	}
	
}