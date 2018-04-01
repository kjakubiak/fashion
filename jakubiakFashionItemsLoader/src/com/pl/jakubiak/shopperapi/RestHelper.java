package com.pl.jakubiak.shopperapi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

public class RestHelper {
	private static String authToken;
	private static String baseUrl;
	private static Map filterMap = new HashMap();

	
	public RestHelper(String baseUrl)
	{
		System.out.println("RestFull Api establishing connection");
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(baseUrl).path("auth");

		Form form = new Form();
		System.out.println("RestFull Api negotiating security token");

		String response=target
						.request(MediaType.APPLICATION_FORM_URLENCODED_TYPE).header("Authorization", "Basic YXBpYWRtaW46QXBpQWRtaW4xMjM=")
						.post(Entity.form(form),String.class);
		
		Gson gson = new GsonBuilder().create();
		HashMap map = gson.fromJson(response, HashMap.class);
		System.out.println("RestFull Api security token returned: "+map.get("access_token"));

		this.baseUrl = baseUrl;
		this.authToken = (String) map.get("access_token");
	}
	
	public Map<String,Object> getProductByEan(String ean) throws UnsupportedEncodingException, InterruptedException
	{
		System.out.println("RestFull API getProductByEan method executed");
		Map<String, Object> filtersMap = new HashMap();
		filtersMap.put("stock.ean", ean);
		System.out.println("RestFull API executing getProductsList with limit 1 and filtersMap");

		ArrayList<LinkedTreeMap> list = getProductsList(1,filtersMap);
		System.out.println("RestFull API getProductsList executed, returning list of products");

		if(list != null && list.size()>0)
		{
			System.out.println("RestFull API returned list is not empty");

			return list.get(0);
		}else
		{
			System.out.println("RestFull API returned list is empty");

			return null;
		}
	}
	public Map<String,Object> getStockByEan(String ean) throws UnsupportedEncodingException, InterruptedException
	{
		System.out.println("RestFull API getProductByEan method executed");
		Map<String, Object> filtersMap = new HashMap();
		filtersMap.put("ean", ean);
		System.out.println("RestFull API executing getProductsList with limit 1 and filtersMap");

		ArrayList<LinkedTreeMap> list = getProductsList(1,filtersMap,"product-stocks");
		System.out.println("RestFull API getProductsList executed, returning list of products");

		if(list != null && list.size()>0)
		{
			System.out.println("RestFull API returned list is not empty");

			return list.get(0);
		}else
		{
			System.out.println("RestFull API returned list is empty");

			return null;
		}
	}
	public ArrayList<LinkedTreeMap> getProductsList() throws UnsupportedEncodingException, InterruptedException
	{
		return getProductsList(50,filterMap);
	}
	public ArrayList<LinkedTreeMap> getProductsList(Map filterMap) throws UnsupportedEncodingException, InterruptedException
	{
		return getProductsList(50,filterMap);
	}
	public ArrayList<LinkedTreeMap> getProductsList(int limit) throws UnsupportedEncodingException, InterruptedException
	{
		return getProductsList(limit,filterMap);
	}
	public int commitTransaction(String jsonToCommit) throws UnsupportedEncodingException, InterruptedException
	{
		return commitTransaction(jsonToCommit,"Products");
	}
	public int commitTransaction(String jsonToCommit,String path) throws UnsupportedEncodingException, InterruptedException
	{
		Client client = ClientBuilder.newClient();
		Gson gson = new GsonBuilder().create();
		String filterString = gson.toJson(filterMap);
		WebTarget target = client.target(baseUrl).path(path);
		String restResponse = target
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer "+authToken)
				.post(Entity.json(jsonToCommit),String.class);
		
		return Integer.parseInt(restResponse);
	}
	public ArrayList<LinkedTreeMap> getProductsList(int limit,Map filterMap) throws UnsupportedEncodingException, InterruptedException
	{
		return getProductsList(limit,filterMap,"Products");
	}
	public ArrayList<LinkedTreeMap> getProductsList(int limit,Map filterMap,String path) throws UnsupportedEncodingException, InterruptedException
	{
		Client client = ClientBuilder.newClient();
		Gson gson = new GsonBuilder().create();
		String filterString = gson.toJson(filterMap);
		System.out.println("RestFull API received filterString is: "+URLEncoder.encode(filterString, "UTF-8"));
		System.out.println("RestFull API received baseUrl is: "+baseUrl);
		
		WebTarget target = client.target(baseUrl).path(path);
		
		Builder restResponse=target
				.queryParam("limit", limit)
				.queryParam("filters", URLEncoder.encode(filterString, "UTF-8"))
				.request(MediaType.APPLICATION_FORM_URLENCODED_TYPE).header("Authorization", "Bearer "+authToken);
		
		if((Integer.parseInt((String) restResponse.head().getHeaders().get("X-SHOP-API-CALLS").get(0)))>=(Integer.parseInt((String) restResponse.head().getHeaders().get("X-SHOP-API-LIMIT").get(0))-2))
		{
			Thread.sleep(2000);
		}
		String stringRespornse=restResponse.get(String.class);
		Map<String,Object> products = (Map<String,Object>)gson.fromJson(stringRespornse, Map.class);
		
		if(products!= null && products.containsKey("list"))
		{
			return (ArrayList) products.get("list");
		}else
		{
			return null;
		}
		}
}
