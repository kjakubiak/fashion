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
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(baseUrl).path("auth");

		Form form = new Form();

		String response=target
						.request(MediaType.APPLICATION_FORM_URLENCODED_TYPE).header("Authorization", "Basic YXBpYWRtaW46QXBpQWRtaW4xMjM=")
						.post(Entity.form(form),String.class);
		
		Gson gson = new GsonBuilder().create();
		HashMap map = gson.fromJson(response, HashMap.class);
		
		this.baseUrl = baseUrl;
		this.authToken = (String) map.get("access_token");
	}
	
	public Map<String,Object> getProductByCode(String code) throws UnsupportedEncodingException, InterruptedException
	{
		Map<String, Object> filtersMap = new HashMap();
		filtersMap.put("stock.code", code);
		
		ArrayList<LinkedTreeMap> list = getProductsList(1,filtersMap);
		//System.out.println(list);
		if(list != null && list.size()>0)
		{
			return list.get(0);
		}else
		{
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
		Client client = ClientBuilder.newClient();
		Gson gson = new GsonBuilder().create();
		String filterString = gson.toJson(filterMap);
		WebTarget target = client.target(baseUrl).path("Products");
		
		Builder restResponse=target
				.queryParam("limit", limit)
				.queryParam("filters", URLEncoder.encode(filterString, "UTF-8"))
				.request(MediaType.APPLICATION_FORM_URLENCODED_TYPE).header("Authorization", "Bearer "+authToken);
		//System.out.println(restResponse.head().getHeaders().get("X-SHOP-API-LIMIT"));
		//System.out.println(restResponse.head().getHeaders().get("X-SHOP-API-CALLS").get(0));
		if((Integer.parseInt((String) restResponse.head().getHeaders().get("X-SHOP-API-CALLS").get(0)))>=(Integer.parseInt((String) restResponse.head().getHeaders().get("X-SHOP-API-LIMIT").get(0))-1))
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
