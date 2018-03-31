package jakubiakFashionItemsLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import numocoProcessing.*;


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
		
		List<Item> listOfItems = new ArrayList<>();

		//	NumocoHelper.processNumocoXML(listOfItems);
		//Form form1 = new Form();
		shopConnection.getProductByCode("dress161010720");
		System.out.println("single processed");
		 Map filterMap = new HashMap();
		 filterMap.put("product_id", 39);
		 	
			ArrayList<LinkedTreeMap> list = shopConnection.getProductsList(filterMap);
			for(LinkedTreeMap<String,Object> entry:list)
			{
				//System.out.println(entry);
				System.out.println("----------------");
				for(Map.Entry<String,Object> mapEntry:entry.entrySet())
				{
				System.out.println(mapEntry.getKey()+" = "+mapEntry.getValue());
				}
				System.out.println("----------------");

			}
			//System.out.println(list.get(2));
		//	System.out.println(products.get("list"));
			//System.out.println(response1);
			
		
	}
	
	

}
