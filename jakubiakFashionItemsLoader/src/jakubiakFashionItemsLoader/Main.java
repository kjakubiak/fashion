package jakubiakFashionItemsLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.pl.jakubiak.lemoniadeapi.*;
import com.pl.jakubiak.numocoapi.*;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.internal.guava.Maps;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.pl.jakubiak.numocoapi.Product;
import com.pl.jakubiak.numocoapi.Root;
import com.pl.jakubiak.numocoapi.Size;
import com.pl.jakubiak.numocoapi.Sizes;
import com.pl.jakubiak.shopperapi.RestHelper;
public class Main {

	public static void main(String[] args) throws Exception  {
		String baseUrl = "http://fashion-jakubiak.pl/webapi/rest";
		String numocoFilePath = "\\\\\\\\192.168.1.1\\\\123\\\\Firma\\\\01042018numoco.xml";
		String numocoCodesFilePath = "\\\\192.168.1.1\\123\\Firma\\numocoListOfCodes.txt";
		String lemoniadeFilePath = "\\\\192.168.1.1\\123\\Firma\\01042018lemoniade.xml";
		String lemoniadeCodesFilePath = "\\\\192.168.1.1\\123\\Firma\\lemoniadeListOfCodes.txt";
	
		Boolean processAll = true;
		Boolean processNumoco = false;
		Boolean processLemoniade = true;
		Boolean stocksOnly = true;
		
		RestHelper shopConnection = new RestHelper(baseUrl);
		System.out.println(shopConnection.getProductsList(1));
		if(processNumoco)
		{
			NumocoHelper numocoConnection = new NumocoHelper(numocoFilePath,numocoCodesFilePath, processAll, stocksOnly);
			numocoConnection.processProducts(shopConnection);
		}
		
		if(processLemoniade)
		{
			LemoniadeHelper lemoniadeConnection = new LemoniadeHelper(lemoniadeFilePath,lemoniadeCodesFilePath, processAll, stocksOnly);
			lemoniadeConnection.processProducts(shopConnection);
		}
		//List<com.pl.jakubiak.lemoniadeapi.Product> listOfProducts = lemoniadeConnection.getListOfProducts();
		//listOfProducts.get(0);
		//System.out.println(listOfProducts.get(0).getCombinations().getCombinations().get(0).getAttributes().getValues().get(0).toString());
	}
}
