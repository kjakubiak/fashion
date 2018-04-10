package com.pl.jakubiak.lemoniadeapi;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.pl.jakubiak.shopperapi.RestHelper;



public class LemoniadeHelper {
	List<Product> listOfProducts;
	Boolean processAll;
	Boolean stocksOnly;
	private static List<String> listOfCodesToProcess;

	public LemoniadeHelper(String filePath,String codesFilePath,Boolean processAll,Boolean stocksOnly) throws JAXBException, IOException
	{
		File listOfCodesFile = new File(codesFilePath);
		
		File file = new File(filePath);
		
		JAXBContext jaxbContext  = JAXBContext.newInstance(Root.class);
		int counter=0;
		
		
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Root root = (Root) jaxbUnmarshaller.unmarshal(file);
		this.processAll = processAll;
		this.stocksOnly = stocksOnly;
		this.listOfProducts = root.getProducts().getProducts() ;
		this.listOfCodesToProcess = Files.readAllLines(listOfCodesFile.toPath(),Charset.forName("UTF-8"));
	}
	public List<Product> getListOfProducts() {
		return listOfProducts;
	}
	public void setListOfProducts(List<Product> listOfProducts) {
		this.listOfProducts = listOfProducts;
	}
	
	public void processProducts(RestHelper shoperConnection) throws UnsupportedEncodingException, InterruptedException
	{
		int foundCounter =0;
		int notFoundCounter = 0;
		int processCounter = 0;
		int progressProcent = 0;
		System.out.println("LemoniadeHelper STARTED PRODUCTS PROCESSING");
		System.out.println("LemoniadeHelper Ammount of records in XML file is: "+listOfProducts.size());
		for(Product product:listOfProducts)
		{
			System.out.println("LemoniadeHelper verifying if Code: "+product.getCode()+" shall be processed.");
			System.out.println("LemoniadeHelper process all is: "+processAll);
			System.out.println("LemoniadeHelper list of codes is: "+listOfCodesToProcess.toString());
			if(processAll || (product.getCode() != null && listOfCodesToProcess.contains(product.getCode())))
			{
				System.out.println("LemoniadeHelper processing");

				Thread.sleep(5000);
				if(progressProcent != (processCounter*100)/listOfProducts.size())
				{
					progressProcent = (processCounter*100)/listOfProducts.size();
					System.out.println("LemoniadeHelper Progress... "+(processCounter*100)/listOfProducts.size()+"%");
				}
				System.out.println("LemoniadeHelper Checking product Code number");
				
				if(product.getCode()!=null)
				{
					System.out.println("LemoniadeHelper Code number is: "+product.getCode().trim());
					Map<String,Object> shoperProduct = shoperConnection.getProductByCode(product.getCode().trim());
					
					if(shoperProduct != null)
					{
						System.out.println("LemoniadeHelper product found in Shopper database");
					//	modifyProductStocks(product,shoperConnection);
						// Modify stock quantities
						foundCounter++;
					}else
					{
						System.out.println("LemoniadeHelper product not found in Shopper database");
						// Create new product
					
						//createNewProduct(product,shoperConnection);
						notFoundCounter++;
						
					}
				}
				processCounter++;
				System.out.println(product.getNames().getNames().toString());
			}
		}
	}
}
