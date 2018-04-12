package com.pl.jakubiak.lemoniadeapi;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "features")
@XmlAccessorType (XmlAccessType.FIELD)
public class Features {
	@XmlElement(name = "feature")
	List<Feature> features = null;
	
	public List<Feature> getNames()
	{
		return features;
	}
	public void setNames(List<Feature> features)
	{
		this.features = features;
	}
	public List<Feature> getFeatures() {
		return features;
	}
	public void setFeatures(List<Feature> features) {
		this.features = features;
	}
	
}
