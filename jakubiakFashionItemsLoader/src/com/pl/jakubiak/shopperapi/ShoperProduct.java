package com.pl.jakubiak.shopperapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.pl.jakubiak.numocoapi.Product;
import com.pl.jakubiak.numocoapi.Size;

public class ShoperProduct {
	private Integer producer_id;
	private Integer category_id;
	private Integer group_id;
	private Integer unit_id;
	private Double other_price;
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
	private String stock;
	private Map translations;
	private String attributes;
	private ArrayList<Integer> categories;
	private Map special_offer;
	
	public void generateTranslations(	Map<String,String> translationsList,
										String translationName,
										Product product,
										Boolean productStatus,
										Integer orderNumber)										
	{
		Map<String,Object> polishTranslationMap = new HashMap();
		String productName = product.getName().replaceAll("\\n", "");

		polishTranslationMap.put("name", productName);
		polishTranslationMap.put("short_description", product.getDescription());
		polishTranslationMap.put("description", product.getDescription());
		polishTranslationMap.put("active", productStatus);
		polishTranslationMap.put("seo_title", productName);
		polishTranslationMap.put("seo_keywords", productName);
		polishTranslationMap.put("order", orderNumber);
		polishTranslationMap.put("main_page", true);
		polishTranslationMap.put("main_page_order", orderNumber);
		Gson gson = new Gson();
		translationsList.put(translationName,(String) gson.toJson(polishTranslationMap, HashMap.class));
		
	}
	public Double countPrice(String price)
	{
		return Math.floor(Double.parseDouble(price)*1.23*2);
	}
	public String generateStock(Product product)
	{
		Gson gson = new Gson();
		Map<String,Object> stockMap = new HashMap();
		Double price = countPrice(product.getPrice_netto());
		stockMap.put("price", Math.floor(price));
		stockMap.put("stock", Integer.parseInt(product.getCount()));
		return gson.toJson(stockMap, HashMap.class);
	}
	public List<String> generatePhotos(Product product,int productId)
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
	public Integer getStockNumber(String size)
	{
		Map<String,Integer> mapOfStockSizes = new HashMap();
		mapOfStockSizes.put("XS",60);
		mapOfStockSizes.put("S",61);
		mapOfStockSizes.put("M",62);
		mapOfStockSizes.put("L",63);
		mapOfStockSizes.put("XL",64);
		mapOfStockSizes.put("XXL",65);
		mapOfStockSizes.put("XXXL",66);
		mapOfStockSizes.put("Uniwersalny",67);
		
		return mapOfStockSizes.get(size);
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
			newStock.put("price", countPrice(size.getPrice()));
			newStock.put("ean", size.getEan().replaceAll("\\n", ""));
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
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
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

	
}