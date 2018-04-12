package com.pl.jakubiak.numocoapi;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	private static Boolean debug;
	private static List<String> listOfCodesToProcess;
	private static String filePath;
	
	public NumocoHelper(Boolean debug, String fileURL,String filePath,String numocoEansFilePath, Boolean processAll,Boolean stocksOnly) throws Exception
	{

		if(!debug)
		{
			log("Debug Off");
			File fileToDelete = new File(filePath);
			fileToDelete.delete();
			
			URL url = new URL(fileURL);
			try (InputStream in = url.openStream()) {
		    Files.copy(in, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
			}
		}else
		{
			log("Debug ON");
		}
		File file = new File(filePath);

		
		JAXBContext jaxbContext  = JAXBContext.newInstance(Root.class);		
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Root root = (Root) jaxbUnmarshaller.unmarshal(file);
		this.debug = debug;
		this.listOfProducts =  root.getProducts().getProducts();
		this.processAll = processAll;
		this.stocksOnly = stocksOnly;
		this.filePath = filePath;

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

	public void createNewProduct(Product product,RestHelper shopConnection) throws Exception
	{
		log("NumocoHelper PRODUCT CREATION STARTED");
		log("NumocoHelper creating product : "+product.getName().replaceAll("\\n", "").trim()+" EAN: "+product.getEan().replaceAll("\\n", "").trim());

		ShoperProduct productToAdd = new ShoperProduct();
		Map<String,Object> productTranslations = new HashMap();
		int productId;
		Gson gson = new GsonBuilder().disableHtmlEscaping().disableInnerClassSerialization().create();
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
		
		log("NumocoHelper added new translation object: "+gson.toJson(productToAdd.getTranslations()));
		productToAdd.setCategory_id(Integer.parseInt(categoryMapper(product.getCategory_id())));
		productToAdd.setCode(product.getModel().replaceAll("\\n", ""));
		productToAdd.setGroup_id(4);
		productToAdd.setDelivery_id(2);
		productToAdd.setPkwiu("");
		productToAdd.setStock(productToAdd.generateStock(product));
		log(product.getEan());
		if(!product.getEan().replaceAll("\\n", "").replaceAll(" ", "").trim().startsWith("5"))
		{
			productToAdd.setEan("5"+product.getEan().replaceAll("\\n", "").replaceAll(" ", "").trim());
		}else
		{
			productToAdd.setEan(product.getEan().replaceAll("\\n", "").replaceAll(" ", "").trim());

		}
		String jsonToProvision = gson.toJson(productToAdd);
		log("NumocoHelper product generated");
		log("Numoco helper product provisioning json: "+jsonToProvision);
		productId = shopConnection.commitTransaction(jsonToProvision);
		log("NumocoHelper product provisioned");
		
		// Add photo
		log("NumocoHelper generating photos");

		List<String> photosAddString = productToAdd.generatePhotos(product,productId);
		for(String jsonSinglePhoto:photosAddString)
		{
			shopConnection.commitTransaction(jsonSinglePhoto, "product-images");
		}
		log("NumocoHelper photos provisioned");

		// Add variants
		log("NumocoHelper generating product variants");
		
		if(product.getSizes().getSizes() != null)
		{
			List<String> stocksAddString = productToAdd.generateStocks(product,productId);
			
			for(String jsonSingleStock:stocksAddString)
			{
				jsonSingleStock = jsonSingleStock.replaceAll("\\\\","").replaceAll("\\}\"","}").replaceAll("\"\\{", "{");
				log("String to provision: "+jsonSingleStock);
				shopConnection.commitTransaction(jsonSingleStock, "product-stocks");
			}
			log("NumocoHelper product variants provisioned");
		}

	}
	public void modifyProductStocks(Product product,RestHelper shoperConnection) throws Exception
	{
		//Update product
		ShoperProduct productToAdd = new ShoperProduct();
		Map<String,Object> productTranslations = new HashMap();
		Gson gson = new GsonBuilder().disableHtmlEscaping().disableInnerClassSerialization().create();
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
		
		log("NumocoHelper added new translation object: "+gson.toJson(productToAdd.getTranslations()));
		productToAdd.setCategory_id(Integer.parseInt(categoryMapper(product.getCategory_id())));
		productToAdd.setCode(product.getModel().replaceAll("\\n", ""));
		productToAdd.setGroup_id(4);
		productToAdd.setDelivery_id(2);
		productToAdd.setPkwiu("");
		productToAdd.setStock(productToAdd.generateStock(product));
		log(product.getEan());
		if(!product.getEan().replaceAll("\\n", "").replaceAll(" ", "").trim().startsWith("5"))
		{
			productToAdd.setEan("5"+product.getEan().replaceAll("\\n", "").replaceAll(" ", "").trim());
		}else
		{
			productToAdd.setEan(product.getEan().replaceAll("\\n", "").replaceAll(" ", "").trim());

		}
		String jsonToProvisionMainUpdate = gson.toJson(productToAdd);
		log("NumocoHelper product generated");
		log("Numoco helper product provisioning json: "+jsonToProvisionMainUpdate);
		Map<String,Object> mainShoperProduct = shoperConnection.getStockByEan(product.getEan().trim());
		String mainProductPath = "products/"+mainShoperProduct.get("product_id");
		
		//Update stocks
		List<Size> listOfStocksToUpdate = product.getSizes().getSizes();
		shoperConnection.commitTransactionPut(jsonToProvisionMainUpdate, mainProductPath);

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
				log("Returning product object");
				log(shoperProduct.toString());
				log("Shoper stock: "+shoperProduct.get("stock"));
				log(("File product stock: "+size.getCount()));
				
				if(Integer.parseInt((String) shoperProduct.get("stock"))!=Integer.parseInt(size.getCount()))
				{
					String productId = (String) shoperProduct.get("product_id");
					String stockId = (String) shoperProduct.get("stock_id");
					String path = "product-stocks/"+stockId;
					log("Path is: "+path);
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
					log(jsonToProvision);
					shoperConnection.commitTransactionPut(jsonToProvision, path);
				}		
			}
			
		}
		}
	}
	public void processProducts(RestHelper shoperConnection) throws Exception
	{
		int foundCounter =0;
		int notFoundCounter = 0;
		int processCounter = 0;
		int progressProcent = 0;
		log("NumocoHelper STARTED PRODUCTS PROCESSING");
		log("NumocoHelper Ammount of records in XML file is: "+listOfProducts.size());
		
		for(Product product:listOfProducts)
		{
			log("NumocoHelper verifying if Code: "+product.getModel()+" shall be processed.");
			log("NumocoHelper list of codes is: "+listOfCodesToProcess.toString());
			log("NumocoHelper process all is: "+processAll);
			//log("NumocoHelper code to process : "+listOfCodesToProcess.contains(product.getModel().trim()));

			if(processAll || (product.getModel() != null) && listOfCodesToProcess.contains(product.getModel().trim()))
			{
				log("NumocoHelper processing");

				Thread.sleep(5000);
				if(progressProcent != (processCounter*100)/listOfProducts.size())
				{
					progressProcent = (processCounter*100)/listOfProducts.size();
					log("NumocoHelper Progress... "+(processCounter*100)/listOfProducts.size()+"%");
				}
				log("NumocoHelper Checking product EAN number");
	
				if(product.getEan()!=null)
				{
					log("NumocoHelper EAN number is: "+product.getEan().trim());
					Map<String,Object> shoperProduct = shoperConnection.getProductByEan(product.getEan().trim());
					
					if(shoperProduct != null)
					{
						log("NumocoHelper product found in Shopper database");
						modifyProductStocks(product,shoperConnection);
						// Modify stock quantities
						foundCounter++;
					}else
					{
						log("NumocoHelper product not found in Shopper database");
						// Create new product
					
						createNewProduct(product,shoperConnection);
						notFoundCounter++;
						
						}
				}
				processCounter++;
			}else
			{
				log("NumocoHelper skipping");
			}
		}
		log("NumocoHelper found "+Integer.toString(foundCounter)+" products");
		log("NumocoHelper not found "+Integer.toString(notFoundCounter)+" products");
		if(!debug)
		{
			File file = new File(filePath);
			file.delete();
		}
		log("NumocoHelper FINISHED PROCESSING");

	}
	
	public List<Product> getProducts()
	{
		return this.listOfProducts;
	}
	public List<String> getEANStoProcess()
	{
		return this.listOfCodesToProcess;
	}
	public void log(String logMsg) throws Exception
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		
		System.out.println(dtf.format(now)+" com.pl.jakubiak.NumocoHelper: "+logMsg);
	}
}
