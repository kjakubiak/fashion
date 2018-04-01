package com.pl.jakubiak.lemoniadeapi;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;



public class LemoniadeHelper {
	List<Product> listOfProducts;
	public LemoniadeHelper() throws JAXBException
	{
		File file = new File("C:\\Firma\\01042018lemoniade.xml");
		
		JAXBContext jaxbContext  = JAXBContext.newInstance(Root.class);
		int counter=0;
		
		
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Root root = (Root) jaxbUnmarshaller.unmarshal(file);
		this.listOfProducts = root.getProducts().getProducts() ;
	}
	public List<Product> getListOfProducts() {
		return listOfProducts;
	}
	public void setListOfProducts(List<Product> listOfProducts) {
		this.listOfProducts = listOfProducts;
	}
}
