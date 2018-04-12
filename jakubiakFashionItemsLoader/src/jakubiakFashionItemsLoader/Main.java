package jakubiakFashionItemsLoader;
import java.net.*; 
import java.io.*; 
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
		Boolean debug = true;
		String baseUrl = "http://fashion-jakubiak.pl/webapi/rest";
		String numocoFilePath;
		String numocoFileURL = "https://www.numoco.com/myadmin/service/json/mcenniki/ynspe/key=8c74fd6f47bded101ba3192f93ba48ff//profileId=1/do=xml/";

		String numocoCodesFilePath;
		String lemoniadeFilePath;
		String lemoniadeFileURL = "https://wspolpraca.lemoniade.pl/export/export.xml";
		String lemoniadeSizesFilePath ;
		String lemoniadeCodesFilePath;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		LocalDateTime now = LocalDateTime.now();
		
		PrintStream fileStream = new PrintStream("C:\\\\temp\\\\integracjaHurtowni"+dtf.format(now)+".out");
		if(!debug)
		{
			System.setOut(fileStream);
			System.setErr(fileStream);
			numocoCodesFilePath = "numocoListOfCodes.txt";
			lemoniadeFilePath = "lemoniade.xml";
			lemoniadeSizesFilePath = "lemoniadeListOfSizes.txt";
			lemoniadeCodesFilePath = "lemoniadeListOfCodes.txt";
			numocoFilePath = "numoco.xml";
		}else
		{
			numocoCodesFilePath = "c:\\temp\\numocoListOfCodes.txt";
			lemoniadeFilePath = "c:\\\\temp\\\\lemoniade.xml";
			lemoniadeSizesFilePath = "c:\\\\temp\\\\listOfLemoniadeSizes.txt";
			lemoniadeCodesFilePath = "c:\\\\temp\\\\lemoniadeListOfCodes.txt";
			numocoFilePath = "c:\\\\temp\\\\numoco.xml";
		}
		Boolean processAll = false;
		Boolean processNumoco = true;
		Boolean processLemoniade = false;
		Boolean stocksOnly = false;
		
		RestHelper shopConnection = new RestHelper(baseUrl);
		if(processNumoco)
		{
			System.out.println("Processing Numoco");
			NumocoHelper numocoConnection = new NumocoHelper(debug,numocoFileURL,numocoFilePath,numocoCodesFilePath, processAll, stocksOnly);
			numocoConnection.processProducts(shopConnection);
			System.out.println("Numoco processed");
		}
		
		if(processLemoniade)
		{
			System.out.println("Processing Lemoniade");
			LemoniadeHelper lemoniadeConnection = new LemoniadeHelper(debug,lemoniadeSizesFilePath,lemoniadeFileURL,lemoniadeFilePath,lemoniadeCodesFilePath, processAll, stocksOnly);
			lemoniadeConnection.processProducts(shopConnection);
			System.out.println("Lemoniade processed");

		}
		}
}
