package com.pl.jakubiak.numocoapi;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sizes")
@XmlAccessorType (XmlAccessType.FIELD)
public class Sizes {
	@XmlElement(name = "size")
	List<Size> sizes = null;
	
	public List<Size> getSizes()
	{
		return this.sizes;
	}
	public void setSizes(List<Size> sizes)
	{
		this.sizes = sizes;
	}
	

}
