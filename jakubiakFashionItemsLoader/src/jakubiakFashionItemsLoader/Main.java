package jakubiakFashionItemsLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.pl.jakubiak.numocoapi.*;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import java.net.URI;
import java.net.URLEncoder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

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
		
		RestHelper shopConnection = new RestHelper(baseUrl);
		NumocoHelper numocoConnection = new NumocoHelper();
		
		List<Product> listOfNumocoProducts = numocoConnection.getProducts();
		
		numocoConnection.processProducts(shopConnection);
		//	NumocoHelper.processNumocoXML(listOfItems);
		shopConnection.getProductByCode("183-2");
		
	//	System.out.println("single processed");
		
		Map filterMap = new HashMap();
		filterMap.put("product_id", 156);
	//	filterMap.put("stock.code", "183-2");

		ArrayList<LinkedTreeMap> list = shopConnection.getProductsList(1,filterMap);
		for(LinkedTreeMap<String,Object> entry:list)
		{
			//System.out.println(entry);
		//	System.out.println("----------------");
			for(Map.Entry<String,Object> mapEntry:entry.entrySet())
			{
			//System.out.println(mapEntry.getKey()+" = "+mapEntry.getValue());
			}
			//System.out.println("----------------");
		}		
	}
}
