package com.pl.jakubiak.numocoapi;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import jakubiakFashionItemsLoader.Item;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pl.jakubiak.shopperapi.*;
public class NumocoHelper {
	private static List<Product> listOfProducts;
	private static  Boolean productStatus = false;
	private static Boolean processAll;
	private static Boolean processNumoco;
	private static Boolean stocksOnly;
	private static List<String> listOfCodesToProcess;
	
	public NumocoHelper(String filePath,String numocoEansFilePath, Boolean processAll,Boolean stocksOnly) throws Exception
	{
		File numocoXMLFile = new File(filePath);
		
		JAXBContext jaxbContext  = JAXBContext.newInstance(Root.class);		
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Root root = (Root) jaxbUnmarshaller.unmarshal(numocoXMLFile);
		
		this.listOfProducts =  root.getProducts().getProducts();
		this.processAll = processAll;
		this.stocksOnly = stocksOnly;

		File numocoListOfEansFile = new File(numocoEansFilePath);
		
		this.listOfCodesToProcess = Files.readAllLines(numocoListOfEansFile.toPath(),Charset.forName("UTF-8"));
	}

	public String categoryMapper(String categoryID)
	{
		Map<String,String> categoryMap = new HashMap();
		
		categoryMap.put("2", "13"); // Sukienki
		categoryMap.put("167", "15"); // Bluzki
		categoryMap.put("366", "18"); // Spódnice
		categoryMap.put("236", "20"); // Koszule
		categoryMap.put("167", "21"); // Tuniki
		
		if(categoryMap.get(categoryID) != null)
		{
			return categoryMap.get(categoryID);
		}else
		{
			return "13";
		}
	}

	public void createNewProduct(Product product,RestHelper shopConnection) throws UnsupportedEncodingException, InterruptedException
	{
		System.out.println("NumocoHelper PRODUCT CREATION STARTED");
		System.out.println("NumocoHelper creating product : "+product.getName().replaceAll("\\n", "").trim()+" EAN: "+product.getEan().replaceAll("\\n", "").trim());

		ShoperProduct productToAdd = new ShoperProduct();
		Map<String,String> productTranslations = new HashMap();
		int productId;
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		if(Integer.parseInt(product.getCount())>0)
		{
			productStatus=true;
		}else
		{
			productStatus=false;
		}
		
		//Add main product
		productToAdd.generateTranslations(productTranslations, "pl_PL", product, productStatus, 1);
		productToAdd.setTranslations(productTranslations);
		productToAdd.setProducer_id(26);
		
		System.out.println("NumocoHelper added new translation object: "+gson.toJson(productTranslations).replaceAll("\\\\", ""));
		productToAdd.setCategory_id(Integer.parseInt(categoryMapper(product.getCategory_id())));
		productToAdd.setCode(product.getModel().replaceAll("\\n", ""));
		productToAdd.setGroup_id(4);
		productToAdd.setDelivery_id(2);
		productToAdd.setPkwiu("");
		productToAdd.setStock(productToAdd.generateStock(product));
		System.out.println(product.getEan());
		if(!product.getEan().replaceAll("\\n", "").replaceAll(" ", "").trim().startsWith("5"))
		{
			productToAdd.setEan("5"+product.getEan().replaceAll("\\n", "").replaceAll(" ", "").trim());
		}else
		{
			productToAdd.setEan(product.getEan().replaceAll("\\n", "").replaceAll(" ", "").trim());

		}
		String jsonToProvision = gson.toJson(productToAdd).replaceAll("\\\\","").replaceAll("\\}\"","}").replaceAll("\"\\{", "{");
		System.out.println("NumocoHelper product generated");
		System.out.println("Numoco helper product provisioning json: "+jsonToProvision);
		productId = shopConnection.commitTransaction(jsonToProvision);
		System.out.println("NumocoHelper product provisioned");
		
		// Add photo
		System.out.println("NumocoHelper generating photos");

		List<String> photosAddString = productToAdd.generatePhotos(product,productId);
		for(String jsonSinglePhoto:photosAddString)
		{
			shopConnection.commitTransaction(jsonSinglePhoto, "product-images");
		}
		System.out.println("NumocoHelper photos provisioned");

		// Add variants
		System.out.println("NumocoHelper generating product variants");
		
		if(product.getSizes().getSizes() != null)
		{
			List<String> stocksAddString = productToAdd.generateStocks(product,productId);
			
			for(String jsonSingleStock:stocksAddString)
			{
				jsonSingleStock = jsonSingleStock.replaceAll("\\\\","").replaceAll("\\}\"","}").replaceAll("\"\\{", "{");
				System.out.println("String to provision: "+jsonSingleStock);
				shopConnection.commitTransaction(jsonSingleStock, "product-stocks");
			}
			System.out.println("NumocoHelper product variants provisioned");
		}

	}
	public void modifyProductStocks(Product product,RestHelper shoperConnection) throws UnsupportedEncodingException, InterruptedException
	{
		List<Size> listOfStocksToUpdate = product.getSizes().getSizes();
		Gson gson = new Gson();
		Boolean first = true;
		if(listOfStocksToUpdate!=null)
		{
		for(Size size : listOfStocksToUpdate)
		{
			Map<String,Object> shoperProduct = shoperConnection.getStockByEan(size.getEan().trim());
			if(shoperProduct!=null)
			{
				if(first && Integer.parseInt(size.getCount())>0)
				{
					shoperProduct.put("default", true);
					first=false;
				}else
				{
					shoperProduct.put("default", false);
				}
				System.out.print("Returning product object");
				System.out.println(shoperProduct);
				System.out.println("Shoper stock: "+shoperProduct.get("stock"));
				System.out.println(("File product stock: "+size.getCount()));
				
				if(Integer.parseInt((String) shoperProduct.get("stock"))!=Integer.parseInt(size.getCount()))
				{
					String productId = (String) shoperProduct.get("product_id");
					String stockId = (String) shoperProduct.get("stock_id");
					String path = "product-stocks/"+stockId;
					System.out.println("Path is: "+path);
				//	shoperProduct.put(key, value)
					//shoperProduct.put("product_id", productId);
					shoperProduct.put("stock", size.getCount());
					if(Integer.parseInt(size.getCount())>0)
					{
						shoperProduct.put("active", true);
					}
					if(Integer.parseInt(size.getCount())<=0)
					{
						shoperProduct.put("active", false);
					}
					
					String jsonToProvision = gson.toJson(shoperProduct);
					System.out.println(jsonToProvision);
					shoperConnection.commitTransactionPut(jsonToProvision, path);
				}		
			}
			
		}
		}
	}
	public void processProducts(RestHelper shoperConnection) throws UnsupportedEncodingException, InterruptedException
	{
		int foundCounter =0;
		int notFoundCounter = 0;
		int processCounter = 0;
		int progressProcent = 0;
		System.out.println("NumocoHelper STARTED PRODUCTS PROCESSING");
		System.out.println("NumocoHelper Ammount of records in XML file is: "+listOfProducts.size());
		
		for(Product product:listOfProducts)
		{
			System.out.println("NumocoHelper verifying if Code: "+product.getModel()+" shall be processed.");
			System.out.println("NumocoHelper list of codes is: "+listOfCodesToProcess.toString());
			System.out.println("NumocoHelper process all is: "+processAll);
			//System.out.println("NumocoHelper code to process : "+listOfCodesToProcess.contains(product.getModel().trim()));

			if(processAll || (product.getModel() != null) && listOfCodesToProcess.contains(product.getModel().trim()))
			{
				System.out.println("NumocoHelper processing");

				Thread.sleep(5000);
				if(progressProcent != (processCounter*100)/listOfProducts.size())
				{
					progressProcent = (processCounter*100)/listOfProducts.size();
					System.out.println("NumocoHelper Progress... "+(processCounter*100)/listOfProducts.size()+"%");
				}
				System.out.println("NumocoHelper Checking product EAN number");
	
				if(product.getEan()!=null)
				{
					System.out.println("NumocoHelper EAN number is: "+product.getEan().trim());
					Map<String,Object> shoperProduct = shoperConnection.getProductByEan(product.getEan().trim());
					
					if(shoperProduct != null)
					{
						System.out.println("NumocoHelper product found in Shopper database");
						modifyProductStocks(product,shoperConnection);
						// Modify stock quantities
						foundCounter++;
					}else
					{
						System.out.println("NumocoHelper product not found in Shopper database");
						// Create new product
					
						createNewProduct(product,shoperConnection);
						notFoundCounter++;
						
						}
				}
				processCounter++;
			}else
			{
				System.out.println("NumocoHelper skipping");
			}
		}
		System.out.println("NumocoHelper found "+Integer.toString(foundCounter)+" products");
		System.out.println("NumocoHelper not found "+Integer.toString(notFoundCounter)+" products");
		System.out.println("NumocoHelper FINISHED PROCESSING");

	}
	
	public List<Product> getProducts()
	{
		return this.listOfProducts;
	}
	public List<String> getEANStoProcess()
	{
		return this.listOfCodesToProcess;
	}
}
