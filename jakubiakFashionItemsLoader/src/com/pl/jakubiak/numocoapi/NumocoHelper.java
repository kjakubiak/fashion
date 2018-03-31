package com.pl.jakubiak.numocoapi;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import jakubiakFashionItemsLoader.Item;

public class NumocoHelper {
	public static void processNumocoXML(List<Item> listOfItems) throws Exception
	{
		File file = new File("C:\\Firma\\pobrane.xml");
		JAXBContext jaxbContext  = JAXBContext.newInstance(com.pl.jakubiak.numocoapi.Root.class);
		int counter=0;
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Root root = (Root) jaxbUnmarshaller.unmarshal(file);
		
		for(Product product:root.getProducts().getProducts())
		{
			counter++;			
			String tempProductName;
			
			// Determinig master item name
			
			if(product.getModel() != null)
			{
				tempProductName = product.getName().substring(product.getName().trim().indexOf(' ')+2).replaceAll(product.getModel(), "").trim();
			}else
			{
				tempProductName = product.getName().substring(product.getName().trim().indexOf(' ')+2).trim();
			}
			if(tempProductName.length() > 50 && tempProductName.lastIndexOf("-")>20)
			{
				tempProductName = tempProductName.substring(0, tempProductName.lastIndexOf("-"));
			}
			
		//	System.out.println(tempProductName);
			
			if(product.getSizes()!=null)
			{
				/// Product processing
			
				
				Sizes sizes = product.getSizes();
				if(sizes.getSizes()!= null && !sizes.getSizes().isEmpty())
				{
					for(Size size:sizes.getSizes())
					{
						if(size != null)
						{
							// Sizes processing
							String sizeTempProductName = tempProductName;
							if(sizeTempProductName.trim().length() < 47)
							{
								sizeTempProductName = sizeTempProductName.trim() + " "+size.getName();
							}
							Item item = new Item();
							item.setName(sizeTempProductName);
							item.setitCostPrice(Double.parseDouble(size.getPrice()));
							item.setSymbol(product.getModel());
							listOfItems.add(item);
						}
						
					}
				}
			}
			
		}
//		System.out.println(Integer.toString(counter));
	}
}
