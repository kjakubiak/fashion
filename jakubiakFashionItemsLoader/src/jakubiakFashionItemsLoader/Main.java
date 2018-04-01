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
		numocoConnection.processProducts(shopConnection);
		
		//LemoniadeHelper lemoniadeConnection = new LemoniadeHelper();
		
		//List<com.pl.jakubiak.lemoniadeapi.Product> listOfProducts = lemoniadeConnection.getListOfProducts();
		//listOfProducts.get(0);
		//System.out.println(listOfProducts.get(0).getCombinations().getCombinations().get(0).getAttributes().getValues().get(0).toString());
	}
}
