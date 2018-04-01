package com.pl.jakubiak.lemoniadeapi;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "combinations")
@XmlAccessorType (XmlAccessType.FIELD)
public class Combinations {
	@XmlElement(name = "combination")
	List<Combination> combinations = null;
	
	public List<Combination> getCombinations()
	{
		return combinations;
	}
	public void setCombinations(List<Combination> combinations)
	{
		this.combinations = combinations;
	}
	
}
