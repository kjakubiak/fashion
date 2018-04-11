package com.pl.jakubiak.shopperapi;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pl.jakubiak.lemoniadeapi.Combination;
import com.pl.jakubiak.lemoniadeapi.Image;
import com.pl.jakubiak.lemoniadeapi.LemoniadeHelper;
import com.pl.jakubiak.numocoapi.Product;
import com.pl.jakubiak.numocoapi.Size;
import com.pl.jakubiak.shopperapi.RestHelper;


public class ShoperProduct {
	private Integer producer_id;
	private Integer category_id;
	private Integer group_id;
	private Integer unit_id;
	private Double other_price;
	private Integer delivery_id;
	private String code;
	private Double dimension_w;
	private Double dimension_h;
	private Double dimension_l;
	private String ean;
	private String pkwiu;
	private Boolean is_product_of_day;
	private Double vol_weight;
	private Integer gauge_id;
	private Integer currency_id;
	private String additional_isbn;
	private String additional_kgo;
	private Integer additional_bloz7;
	private Integer additional_bloz12;
	private String additional_producer;
	private ArrayList<Integer> related;
	private Map options;
	private Map<String, Object> stock;
	private Map translations;
	private String attributes;
	private ArrayList<Integer> categories;
	private Map special_offer;
	
	public void log(String logMsg) throws Exception
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		
		System.out.println(dtf.format(now)+" com.pl.jakubiak.ShoperProduct: "+logMsg);
	}
	public void generateTranslations( Map<String,String> translationsList,
										com.pl.jakubiak.lemoniadeapi.Name name,
										com.pl.jakubiak.lemoniadeapi.Product product,
										Boolean productStatus, String color)
	{
		if(name.getCode().equals("PL_pl"))
		{
		Map<String,Object> languageTranslationMap = new HashMap();
		String tempProductName = name.getText();
		if(product.getCode()!=null)
		{
			tempProductName= tempProductName.replaceAll(product.getCode(),"");
		}
		
		String productName = tempProductName.replaceAll("\\n", "").trim();
		
		languageTranslationMap.put("name", productName+" "+color);
		//polishTranslationMap.put("short_description", product.getDescription());
		//polishTranslationMap.put("description", product.getDescription().replaceAll("\\<[^>]*>",""));
		languageTranslationMap.put("active", productStatus);
		languageTranslationMap.put("seo_title", productName);
		languageTranslationMap.put("delivery_id", 2);
		languageTranslationMap.put("seo_keywords", productName);
		languageTranslationMap.put("order", 1);
		languageTranslationMap.put("main_page", true);
		languageTranslationMap.put("main_page_order", 1);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		translationsList.put(name.getCode(),(String) gson.toJson(languageTranslationMap, HashMap.class));
		}
	}
										
	public void generateTranslations(	Map<String,Object> translationsList,
										String translationName,
										Product product,
										Boolean productStatus,
										Integer orderNumber)										
	{
		Map<String,Object> polishTranslationMap = new HashMap();
		String tempProductName = product.getName();
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
		
		String productName = tempProductName.replaceAll("\\n", "");
		//Gson descriptionGson = sonBuilder().disableHtmlEscaping().create();
		//log(product.getDescription());
		polishTranslationMap.put("name", productName);
		String productDescription = product.getDescription().replaceAll(" valign=\"top\"","");
		productDescription = productDescription.replaceAll("\\n","");
		//System.out.println(productDescription);
		//URLEncoder encoder = new URLEncoder();
		//encoder.encode(productDescription)
		
		polishTranslationMap.put("short_description", productDescription);
		polishTranslationMap.put("description", productDescription);
		polishTranslationMap.put("active", productStatus);
		polishTranslationMap.put("seo_title", productName);
		polishTranslationMap.put("delivery_id", 2);
		polishTranslationMap.put("seo_keywords", productName);
		polishTranslationMap.put("order", orderNumber);
		polishTranslationMap.put("main_page", true);
		polishTranslationMap.put("main_page_order", orderNumber);
		Gson gson = new GsonBuilder().disableHtmlEscaping().disableInnerClassSerialization().create();
		//Gson gson = new Gson();
		System.out.println(gson.toJson(polishTranslationMap, HashMap.class));
		translationsList.put(translationName,polishTranslationMap);
		
	}
	public Double countPrice(String price)
	{
		return Math.floor(Double.parseDouble(price)*1.23);
	}
	public Map<String,Object> generateStock(Product product)
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Map<String,Object> stockMap = new HashMap();
		Double price = countPrice(product.getPrice_netto());
		stockMap.put("price", Math.floor(price));
		stockMap.put("stock", Integer.parseInt(product.getCount()));
		return stockMap;
	}
	public Map<String,Object> generateStock(com.pl.jakubiak.lemoniadeapi.Product product,int overallStock)
	{
		Gson gson = new Gson();
		Double price;
		Map<String,Object> stockMap = new HashMap();
		if(!product.getPrice_suggested().equals("0"))
		{
			price = Double.parseDouble(product.getPrice_suggested());
		}else
		{
			price = Double.parseDouble(Double.toString(Math.floor((Double) Double.parseDouble(product.getPrice())*1.23*2)));
		}
		stockMap.put("price", Math.floor(price));
		stockMap.put("stock", overallStock);
		return stockMap;
	}
	public List<String> generatePhotos(com.pl.jakubiak.numocoapi.Product product,int productId)
	{
		List<String> listOfURLs = product.getPhotos().getPhotos();
		List<String> listToReturn = new ArrayList();
		Gson gson = new Gson();
		for(String photoUrl:listOfURLs)
		{
			Map<String,String> mapToJson = new HashMap();
			mapToJson.put("product_id", Integer.toString(productId));
			mapToJson.put("url",photoUrl.replace("\\n", "").trim());
			listToReturn.add(gson.toJson(mapToJson));
		}
		return listToReturn;
	}
	public List<String> generatePhotos(com.pl.jakubiak.lemoniadeapi.Product product,int productId) throws Exception
	{
		log("1");
		List<Combination> listOfCombinations = product.getCombinations().getCombinations();
		log("2");
		List<String> listOfURLs = new ArrayList();

	
		
	
		log("4");

		List<String> listToReturn = new ArrayList();
		Gson gson = new Gson();
		for(String photoUrl:listOfURLs)
		{
			Map<String,String> mapToJson = new HashMap();
			mapToJson.put("product_id", Integer.toString(productId));
			mapToJson.put("url",photoUrl.replace("\\n", "").trim());
			listToReturn.add(gson.toJson(mapToJson));
		}
		return listToReturn;
	}
	public Integer getStockNumber(String size)
	{
		Map<String,Integer> mapOfStockSizes = new HashMap();
		mapOfStockSizes.put("XS",60);
		mapOfStockSizes.put("S",61);
		mapOfStockSizes.put("M",62);
		mapOfStockSizes.put("L",63);
		mapOfStockSizes.put("XL",64);
		mapOfStockSizes.put("XXL",65);
		mapOfStockSizes.put("2XL",65);
		mapOfStockSizes.put("3XL",66);
		mapOfStockSizes.put("4XL",72);

		mapOfStockSizes.put("XXXL",66);
		mapOfStockSizes.put("Uniwersalny",67);
		mapOfStockSizes.put("UNI",67);
		mapOfStockSizes.put("S/M", 68);
		mapOfStockSizes.put("M/L", 69);
		mapOfStockSizes.put("L/XL", 70);
		mapOfStockSizes.put("XL/XXL", 71);
		mapOfStockSizes.put("XXXXL", 72);
		
		Integer intToReturn = mapOfStockSizes.get(size) != null ? mapOfStockSizes.get(size) : 67;
		return intToReturn;
	}
	public List<String> generateStocks(Product product,int productId)
	{
		List<String> listToReturn = new ArrayList();
		Gson gson = new Gson();
		Boolean first = true;
		List<Size> sizesList = product.getSizes().getSizes();
		for(Size size:sizesList)
		{
			Map<String, Object> newStock = new HashMap();

			if(first && Integer.parseInt(size.getCount())>0)
			{
				newStock.put("default",true);
				first=false;
			}
			newStock.put("product_id", productId);
			newStock.put("group_id", 4);
			newStock.put("stock", size.getCount());
			newStock.put("price_type", 1);
			newStock.put("category_id", 13);
			if(Integer.parseInt(size.getCount())>0)
			{
				newStock.put("active", true);
			}else
			{
				newStock.put("active", false);
			}
			newStock.put("price", Math.floor(Double.parseDouble(size.getPrice())));
			newStock.put("ean", size.getEan().replaceAll("\\n", "").trim());
			Map<String,String> optionMap = new HashMap();
			optionMap.put("7", getStockNumber(size.getName()).toString());
			
			newStock.put("options", gson.toJson(optionMap));
			listToReturn.add(gson.toJson(newStock));
		}
		
		return listToReturn;
	}
	public Integer getProducer_id() {
		return producer_id;
	}
	public void setProducer_id(Integer producer_id) {
		this.producer_id = producer_id;
	}
	public Integer getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}
	public Integer getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(Integer unit_id) {
		this.unit_id = unit_id;
	}
	public Double getOther_price() {
		return other_price;
	}
	public void setOther_price(Double other_price) {
		this.other_price = other_price;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Double getDimension_w() {
		return dimension_w;
	}
	public void setDimension_w(Double dimension_w) {
		this.dimension_w = dimension_w;
	}
	public Double getDimension_h() {
		return dimension_h;
	}
	public void setDimension_h(Double dimension_h) {
		this.dimension_h = dimension_h;
	}
	public Double getDimension_l() {
		return dimension_l;
	}
	public void setDimension_l(Double dimension_l) {
		this.dimension_l = dimension_l;
	}
	public String getEan() {
		return ean;
	}
	public void setEan(String ean) {
		this.ean = ean;
	}
	public String getPkwiu() {
		return pkwiu;
	}
	public void setPkwiu(String pkwiu) {
		this.pkwiu = pkwiu;
	}
	public Boolean getIs_product_of_day() {
		return is_product_of_day;
	}
	public void setIs_product_of_day(Boolean is_product_of_day) {
		this.is_product_of_day = is_product_of_day;
	}
	public Double getVol_weight() {
		return vol_weight;
	}
	public void setVol_weight(Double vol_weight) {
		this.vol_weight = vol_weight;
	}
	public Integer getGauge_id() {
		return gauge_id;
	}
	public void setGauge_id(Integer gauge_id) {
		this.gauge_id = gauge_id;
	}
	public Integer getCurrency_id() {
		return currency_id;
	}
	public void setCurrency_id(Integer currency_id) {
		this.currency_id = currency_id;
	}
	public String getAdditional_isbn() {
		return additional_isbn;
	}
	public void setAdditional_isbn(String additional_isbn) {
		this.additional_isbn = additional_isbn;
	}
	public String getAdditional_kgo() {
		return additional_kgo;
	}
	public void setAdditional_kgo(String additional_kgo) {
		this.additional_kgo = additional_kgo;
	}
	public Integer getAdditional_bloz7() {
		return additional_bloz7;
	}
	public void setAdditional_bloz7(Integer additional_bloz7) {
		this.additional_bloz7 = additional_bloz7;
	}
	public Integer getAdditional_bloz12() {
		return additional_bloz12;
	}
	public void setAdditional_bloz12(Integer additional_bloz12) {
		this.additional_bloz12 = additional_bloz12;
	}
	public String getAdditional_producer() {
		return additional_producer;
	}
	public void setAdditional_producer(String additional_producer) {
		this.additional_producer = additional_producer;
	}
	public ArrayList<Integer> getRelated() {
		return related;
	}
	public void setRelated(ArrayList<Integer> related) {
		this.related = related;
	}
	public Map getOptions() {
		return options;
	}
	public void setOptions(Map options) {
		this.options = options;
	}
	public Map<String, Object> getStock() {
		return stock;
	}
	public void setStock(Map<String, Object> map) {
		this.stock = map;
	}
	public Map getTranslations() {
		return translations;
	}
	public void setTranslations(Map translations) {
		this.translations = translations;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public ArrayList<Integer> getCategories() {
		return categories;
	}
	public void setCategories(ArrayList<Integer> categories) {
		this.categories = categories;
	}
	public Map getSpecial_offer() {
		return special_offer;
	}
	public void setSpecial_offer(Map special_offer) {
		this.special_offer = special_offer;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ShoperProduct [producer_id=");
		builder.append(producer_id);
		builder.append(", category_id=");
		builder.append(category_id);
		builder.append(", unit_id=");
		builder.append(unit_id);
		builder.append(", other_price=");
		builder.append(other_price);
		builder.append(", code=");
		builder.append(code);
		builder.append(", dimension_w=");
		builder.append(dimension_w);
		builder.append(", dimension_h=");
		builder.append(dimension_h);
		builder.append(", dimension_l=");
		builder.append(dimension_l);
		builder.append(", ean=");
		builder.append(ean);
		builder.append(", pkwiu=");
		builder.append(pkwiu);
		builder.append(", is_product_of_day=");
		builder.append(is_product_of_day);
		builder.append(", vol_weight=");
		builder.append(vol_weight);
		builder.append(", gauge_id=");
		builder.append(gauge_id);
		builder.append(", currency_id=");
		builder.append(currency_id);
		builder.append(", additional_isbn=");
		builder.append(additional_isbn);
		builder.append(", additional_kgo=");
		builder.append(additional_kgo);
		builder.append(", additional_bloz7=");
		builder.append(additional_bloz7);
		builder.append(", additional_bloz12=");
		builder.append(additional_bloz12);
		builder.append(", additional_producer=");
		builder.append(additional_producer);
		builder.append(", related=");
		builder.append(related);
		builder.append(", options=");
		builder.append(options);
		builder.append(", stock=");
		builder.append(stock);
		builder.append(", translations=");
		builder.append(translations);
		builder.append(", attributes=");
		builder.append(attributes);
		builder.append(", categories=");
		builder.append(categories);
		builder.append(", special_offer=");
		builder.append(special_offer);
		builder.append("]");
		return builder.toString();
	}
	public Integer getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}
	public Integer getDelivery_id() {
		return delivery_id;
	}
	public void setDelivery_id(Integer delivery_id) {
		this.delivery_id = delivery_id;
	}

	public List<String> generatePhotos(List<String> listOfImages, int productId) {
		List<String> listToReturn = new ArrayList();
		Gson gson = new Gson();
		for(String photoUrl:listOfImages)
		{
			Map<String,String> mapToJson = new HashMap();
			mapToJson.put("product_id", Integer.toString(productId));
			mapToJson.put("url",photoUrl.replace("\\n", "").trim());
			listToReturn.add(gson.toJson(mapToJson));
		}
		return listToReturn;
	}

	public List<String> generateStocks(com.pl.jakubiak.lemoniadeapi.Product product, int productId,
			Map<String, Object> variantMap, LemoniadeHelper helper) throws Exception {
		
		List<String> listToReturn = new ArrayList();
		Gson gson = new Gson();
		Boolean first = true;
		log("Before for");
		for(Map.Entry<String, Object> size : variantMap.entrySet())
		{
			log("in for");

			if(!size.getKey().equals("photos") && !size.getKey().equals("colorcode"))
			{
				log("in if for");

				HashMap<String,String> sizeAttributes = (HashMap<String, String>) size.getValue();
				
				Map<String, Object> newStock = new HashMap();
				log("Before second if");

				if(first && Integer.parseInt(sizeAttributes.get("qty"))>0)
				{
					log("in second if");

					newStock.put("default",true);
					first=false;
				}
				newStock.put("product_id", productId);
				newStock.put("group_id", 4);
				newStock.put("stock", sizeAttributes.get("qty"));
				newStock.put("price_type", 1);
				newStock.put("category_id", helper.categoryMapper(product.getId_category()));
				if(Integer.parseInt(sizeAttributes.get("qty"))>0)
				{
					newStock.put("active", true);
				}else
				{
					newStock.put("active", false);
				}
				Double price;
				if(!product.getPrice_suggested().equals("0"))
				{
					price = Double.parseDouble(product.getPrice_suggested());
				}else
				{
					price = Double.parseDouble(Double.toString(Math.floor((Double) Double.parseDouble(product.getPrice())*1.23*2)));
				}
				newStock.put("price", Math.floor(price));
				newStock.put("ean", sizeAttributes.get("ean"));
				Map<String,String> optionMap = new HashMap();
				log("Before stockNumber");

				optionMap.put("7", getStockNumber(size.getKey()).toString());
				log("After stock number");

				newStock.put("options", gson.toJson(optionMap));
				listToReturn.add(gson.toJson(newStock));
				
			}
		}
			return listToReturn;
		}

	
}
