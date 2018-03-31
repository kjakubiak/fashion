package com.pl.jakubiak.numocoapi;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import jakubiakFashionItemsLoader.Item;

import com.google.gson.Gson;
import com.pl.jakubiak.shopperapi.*;
public class NumocoHelper {
	private static List<Product> listOfProducts;
	private static final Boolean productStatus = false;
	public List<Product> getProducts()
	{
		return this.listOfProducts;
	}
	
	public void createNewProduct(Product product,RestHelper shopConnection) throws UnsupportedEncodingException, InterruptedException
	{
		ShoperProduct productToAdd = new ShoperProduct();
		Map<String,String> productTranslations = new HashMap();
		int productId;
		Gson gson = new Gson();
		//Add main product
		productToAdd.generateTranslations(productTranslations, "pl_PL", product, productStatus, 1);
		productToAdd.setTranslations(productTranslations);
		
		System.out.println(gson.toJson(productTranslations).replaceAll("\\\\", ""));
		productToAdd.setCategory_id(13);
		productToAdd.setCode(product.getModel().replaceAll("\\n", ""));
		productToAdd.setGroup_id(4);
		productToAdd.setPkwiu("");
		productToAdd.setStock(productToAdd.generateStock(product));
		productToAdd.setEan(product.getEan().replaceAll("\\n", "").trim());
		
		String jsonToProvision = gson.toJson(productToAdd).replaceAll("\\\\","").replaceAll("\\}\"","}").replaceAll("\"\\{", "{");
		System.out.println("Generated new product");
		System.out.println(jsonToProvision);
		
		productId = shopConnection.commitTransaction(jsonToProvision);
		
		// Add photo
		
		List<String> photosAddString = productToAdd.generatePhotos(product,productId);
		for(String jsonSinglePhoto:photosAddString)
		{
			shopConnection.commitTransaction(jsonSinglePhoto, "product-images");
		}
		
		// Add variants
		
		List<String> stocksAddString = productToAdd.generateStocks(product,productId);
		for(String jsonSingleStock:stocksAddString)
		{
			jsonSingleStock = jsonSingleStock.replaceAll("\\\\","").replaceAll("\\}\"","}").replaceAll("\"\\{", "{");
			System.out.println(jsonSingleStock);
			shopConnection.commitTransaction(jsonSingleStock, "product-stocks");
		}
		
	}
	
	public void processProducts(RestHelper shoperConnection) throws UnsupportedEncodingException, InterruptedException
	{
		int foundCounter =0;
		int notFoundCounter = 0;
		int processCounter = 0;
		int progressProcent = 0;
		for(Product product:listOfProducts)
		{
			processCounter++;
			if(progressProcent != (processCounter*100)/listOfProducts.size())
				{
				progressProcent = (processCounter*100)/listOfProducts.size();
				System.out.println("Progress... "+(processCounter*100)/listOfProducts.size()+"%");

				}
			//System.out.println(processCounter);
			if(product.getModel()!=null)
			{
				Map<String,Object> shoperProduct = shoperConnection.getProductByCode(product.getModel().trim());
				if(shoperProduct != null )
				{
					foundCounter++;

					System.out.println(shoperProduct);

				}else
				{
				//	System.out.println("Product not found in shop");
					createNewProduct(product,shoperConnection);

					notFoundCounter++;
				}
			}
		}
		System.out.println("Found "+Integer.toString(foundCounter)+" products");
		System.out.println("Not Found "+Integer.toString(notFoundCounter)+" products");

	}
	public NumocoHelper() throws Exception
	{
		File file = new File("C:\\Firma\\123.xml");
		
		JAXBContext jaxbContext  = JAXBContext.newInstance(Root.class);
		int counter=0;
		
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Root root = (Root) jaxbUnmarshaller.unmarshal(file);
		this.listOfProducts =  root.getProducts().getProducts();
//		for(Product product:root.getProducts().getProducts())
	//	{
		//	counter++;			
			//String tempProductName;
			
			// Determinig master item name
			
		//	if(product.getModel() != null)
		//	{
		//		tempProductName = product.getName().substring(product.getName().trim().indexOf(' ')+2).replaceAll(product.getModel(), "").trim();
		//	}else
		//	{
		//		tempProductName = product.getName().substring(product.getName().trim().indexOf(' ')+2).trim();
		//	}
		//	if(tempProductName.length() > 50 && tempProductName.lastIndexOf("-")>20)
		//	{
		//		tempProductName = tempProductName.substring(0, tempProductName.lastIndexOf("-"));
		//	}
			
		//	System.out.println(tempProductName);
			
		//	if(product.getSizes()!=null)
		//	{
				/// Product processing
			
				
	//			Sizes sizes = product.getSizes();
		//		if(sizes.getSizes()!= null && !sizes.getSizes().isEmpty())
			//	{
				//	for(Size size:sizes.getSizes())
					//{
						//if(size != null)
//						{
							// Sizes processing
	//						String sizeTempProductName = tempProductName;
		//					if(sizeTempProductName.trim().length() < 47)
			//				{
				//				sizeTempProductName = sizeTempProductName.trim() + " "+size.getName();
					//		}
						//	Item item = new Item();
							//item.setName(sizeTempProductName);
							//item.setitCostPrice(Double.parseDouble(size.getPrice()));
							//item.setSymbol(product.getModel());
						//	listOfItems.add(item);
					//	}
						
				//	}
			//	}
		//	}
			
		//}
//		System.out.println(Integer.toString(counter));
	}
}
