package com.pl.jakubiak.lemoniadeapi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pl.jakubiak.numocoapi.Size;
import com.pl.jakubiak.shopperapi.RestHelper;
import com.pl.jakubiak.shopperapi.ShoperProduct;



public class LemoniadeHelper {
	List<Product> listOfProducts;
	List<SizeProduct> listOfSizes;
	Boolean processAll;
	Boolean stocksOnly;
	private static List<String> listOfCodesToProcess;
	private static String filePath;
	public void log(String logMsg) throws Exception
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		
		System.out.println(dtf.format(now)+" com.pl.jakubiak.LemoniadeHelper: "+logMsg);
	}
	public LemoniadeHelper(Boolean debug, String sizesFilePath, String fileURL,String filePath,String codesFilePath,Boolean processAll,Boolean stocksOnly) throws Exception
	{
	
		int counter=0;
		
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
		File listOfCodesFile = new File(codesFilePath);
		
		JAXBContext jaxbContext  = JAXBContext.newInstance(Root.class);
		
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Root root = (Root) jaxbUnmarshaller.unmarshal(file);
		
		File sizeFile = new File(sizesFilePath);
		
		JAXBContext jaxbSizeContext  = JAXBContext.newInstance(Root.class);
		
		Unmarshaller jaxbSizeUnmarshaller = jaxbSizeContext.createUnmarshaller();
		SizesRoot sizeRoot = (SizesRoot) jaxbUnmarshaller.unmarshal(sizeFile);
		
		this.processAll = processAll;
		this.stocksOnly = stocksOnly;
		this.listOfProducts = root.getProducts().getProducts() ;
		this.listOfSizes = sizeRoot.getProducts().getProducts();
		this.listOfCodesToProcess = Files.readAllLines(listOfCodesFile.toPath(),Charset.forName("UTF-8"));
		this.filePath = filePath;

	}
	public List<Product> getListOfProducts() {
		return listOfProducts;
	}
	public void setListOfProducts(List<Product> listOfProducts) {
		this.listOfProducts = listOfProducts;
	}
	//public boolean getProductStatus(Product product)
	//{
		
	//}
	public String categoryMapper(String categoryID)
	{
		Map<String,String> categoryMap = new HashMap();
		
		categoryMap.put("3", "13"); // Sukienki
		categoryMap.put("1", "15"); // Bluzki
		categoryMap.put("5", "18"); // Spódnice
		categoryMap.put("11", "20"); // Koszule
		categoryMap.put("2", "21"); // Tuniki
		categoryMap.put("6", "14"); // Swetry
		categoryMap.put("10", "14"); // Swetry
		categoryMap.put("4", "17"); // Spodnie

		categoryMap.put("7", "16"); // Okrycia wierzchnie

		if(categoryMap.get(categoryID) != null)
		{
			return categoryMap.get(categoryID);
		}else
		{
			return "29";
		}
	}
	public HashMap<String,Object> processCombinations(Product product) throws Exception
	{
		HashMap<String,Object> colorMap = new HashMap();
		
		for(Combination combination:product.getCombinations().getCombinations())
		{
			Value combinationAttributesPolish = combination.getAttributes().getValues().get(0);
			String combinationColor = combinationAttributesPolish.getColor();
			if(colorMap.get(combinationColor)!= null)
			{
				// Color exists adding size
				HashMap<String,Object> variantMap = (HashMap<String, Object>) colorMap.get(combinationColor);
				
				HashMap<String,String> sizeMap = new HashMap();
				sizeMap.put("ean", combination.getEan());
				sizeMap.put("qty", combination.getQuantity());
				if(!product.getPrice_suggested().equals("0"))
				{
				sizeMap.put("price",product.getPrice_suggested());
				}else
				{
					sizeMap.put("price",Double.toString(Math.floor((Double) Double.parseDouble(product.getPrice())*1.23*2)));
				}
				
				List<String> listOfImages = (List<String>) variantMap.get("photos");
				
				for(String image:combination.getImages().getImages())
				{
					if(!listOfImages.contains(image))
					{
					listOfImages.add(image);
					}
				}
				
				variantMap.put(combinationAttributesPolish.getSize(), sizeMap);
				colorMap.put(combinationAttributesPolish.getColor(), variantMap);

			}else
			{
				// Color does not exist, creating
				HashMap<String,String> sizeMap = new HashMap();
				sizeMap.put("ean", combination.getEan());
				sizeMap.put("qty", combination.getQuantity());
				log("Suggested price is: "+ product.getPrice_suggested());
				if(!product.getPrice_suggested().equals("0"))
				{
				sizeMap.put("price",product.getPrice_suggested());
				}else
				{
					sizeMap.put("price",Double.toString(Math.floor((Double) Double.parseDouble(product.getPrice())*1.23*2)));
				}
				List<String> listOfImages = new ArrayList();
				for(String image:combination.getImages().getImages())
				{
					listOfImages.add(image);
				}
				
				HashMap<String,Object> variantMap = new HashMap();
				variantMap.put(combinationAttributesPolish.getSize(), sizeMap);
				variantMap.put("photos", listOfImages);
				variantMap.put("colorcode", combinationAttributesPolish.getId_color());
				
				colorMap.put(combinationAttributesPolish.getColor(), variantMap);
			}
			
			
		}
		
		return colorMap;
		
	}
	public void createNewProduct(Product product,RestHelper shopConnection, Map<String, Object> variantMap, Entry<String, Object> color) throws Exception
	{
		log("LemoniadeHelper PRODUCT CREATION STARTED");
		log("LemoniadeHelper creating product : "+product.getCode().replaceAll("\\n", "").trim());
		Boolean productStatus;
		ShoperProduct productToAdd = new ShoperProduct();
		Map<String,String> productTranslations = new HashMap();
		
		int productId;
		int overallStock = 0;
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		for(Map.Entry<String, Object> size : variantMap.entrySet())
		{
			if(!size.getKey().equals("photos") && !size.getKey().equals("colorcode"))
			{
				overallStock+=Integer.parseInt(((HashMap<String,String>) size.getValue()).get("qty"));
			}
		}
		
		productStatus = overallStock>0?true:false;
		
		List<Name> listOfNames = product.getNames().getNames();
		for(Name name:listOfNames)
		{			
			productToAdd.generateTranslations(productTranslations, name, product, productStatus,color.getKey());
		}
		productToAdd.setTranslations(productTranslations);
		productToAdd.setProducer_id(25);
		log("LemoniadeHelper added new translation object: "+gson.toJson(productTranslations).replaceAll("\\\\", ""));
		productToAdd.setCategory_id(Integer.parseInt(categoryMapper(product.getId_category())));
		productToAdd.setCode(product.getCode()+"_"+variantMap.get("colorcode"));
		productToAdd.setGroup_id(4);
		productToAdd.setDelivery_id(2);
		productToAdd.setPkwiu("");
		productToAdd.setStock(productToAdd.generateStock(product,overallStock));
		String jsonToProvision = gson.toJson(productToAdd).replaceAll("\\\\","").replaceAll("\\}\"","}").replaceAll("\"\\{", "{");
		log("LemoniadeHelper product generated");
		log("LemoniadeHelper product provisioning json: "+jsonToProvision);
		productId = shopConnection.commitTransaction(jsonToProvision);
		log("LemoniadeHelper product provisioned");
		List<String> listOfImages = (List<String>) variantMap.get("photos");
		
		
		log("LemoniadeHelper generating photos");

		List<String> photosAddString = productToAdd.generatePhotos(listOfImages,productId);
		for(String jsonSinglePhoto:photosAddString)
		{
			shopConnection.commitTransaction(jsonSinglePhoto, "product-images");
		}
		log("LemoniadeHelper photos provisioned");
		
		List<String> stocksAddString = productToAdd.generateStocks(product,productId,variantMap,this);
		
		for(String jsonSingleStock:stocksAddString)
		{
			jsonSingleStock = jsonSingleStock.replaceAll("\\\\","").replaceAll("\\}\"","}").replaceAll("\"\\{", "{");
			log("String to provision: "+jsonSingleStock);
			shopConnection.commitTransaction(jsonSingleStock, "product-stocks");
		}
		log("LemoniadeHelper product variants provisioned");
	}

	public void processProducts(RestHelper shoperConnection) throws Exception
	{
		int foundCounter =0;
		int notFoundCounter = 0;
		int processCounter = 0;
		int progressProcent = 0;
		log("LemoniadeHelper STARTED PRODUCTS PROCESSING");
		log("LemoniadeHelper Ammount of records in XML file is: "+listOfProducts.size());
		log("LemoniadeHelper process all is: "+processAll);
		log("LemoniadeHelper list of codes is: "+listOfCodesToProcess.toString());
		for(Product product:listOfProducts)
		{
			log("Product code:" + product.getCode());
			if(processAll || (product.getCode() != null && listOfCodesToProcess.contains(product.getCode())))
			{
				HashMap<String,Object> mapOfCombinations = processCombinations(product);
				for(Map.Entry<String, Object> color : mapOfCombinations.entrySet())
				{
					log("Returning variant map for colour: "+color.getKey());
					log("LemoniadeHelper processing");

					Thread.sleep(5000);
					if(progressProcent != (processCounter*100)/listOfProducts.size())
					{
						progressProcent = (processCounter*100)/listOfProducts.size();
						log("LemoniadeHelper Progress... "+(processCounter*100)/listOfProducts.size()+"%");
					}
					log("LemoniadeHelper Checking product Code number");
					
					Map<String,Object> variantMap = (Map<String, Object>) color.getValue(); 
					String generatedCode = product.getCode()+"_"+variantMap.get("colorcode");
					log("LemoniadeHelper Code number is: "+generatedCode);
					Map<String,Object> shoperProduct = shoperConnection.getProductByCode(generatedCode);
					
					if(shoperProduct != null)
					{
						log("LemoniadeHelper product found in Shopper database");
						modifyProductStocks(product,shoperConnection,variantMap,color);
						// Modify stock quantities
						foundCounter++;
					}else
					{
						log("LemoniadeHelper product not found in Shopper database");
						// Create new product
						try
						{
						createNewProduct(product,shoperConnection,variantMap,color);
						notFoundCounter++;
						}catch(Exception e)
						{
							e.printStackTrace();
						}
					}
						
				}
			}
		}
		File file = new File(filePath);
		file.delete();
		log("LemoniadeHelper processed");

	}
	private void modifyProductStocks(Product product, RestHelper shoperConnection, Map<String, Object> variantMap,
			Entry<String, Object> color) throws Exception {
		
		Gson gson = new Gson();
		Boolean first = true;
		for(SizeProduct sizeProduct : listOfSizes)
		{
			if(sizeProduct.getId().equals(product.getId()))
			{
				
			}
		}
		for(Map.Entry<String, Object> size : variantMap.entrySet())
		{
			if(!size.getKey().equals("photos") && !size.getKey().equals("colorcode"))
			{
				Map<String,Object> shoperProduct = shoperConnection.getStockByEan(((HashMap<String,String>)size.getValue()).get("ean"));
				if(shoperProduct!=null)
				{
					HashMap<String,String> sizeAttributes = (HashMap<String, String>) size.getValue();
	
					if(first && Integer.parseInt(sizeAttributes.get("qty"))>0)
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
					log(("File product stock: "+sizeAttributes.get("qty")));
					
					if(Integer.parseInt((String) shoperProduct.get("stock"))!=Integer.parseInt(sizeAttributes.get("qty")))
					{
						String productId = (String) shoperProduct.get("product_id");
						String stockId = (String) shoperProduct.get("stock_id");
						String path = "product-stocks/"+stockId;
						log("Path is: "+path);
					
						shoperProduct.put("stock", sizeAttributes.get("qty"));
						if(Integer.parseInt(sizeAttributes.get("qty"))>0)
						{
							shoperProduct.put("active", true);
						}
						if(Integer.parseInt(sizeAttributes.get("qty"))<=0)
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
}
