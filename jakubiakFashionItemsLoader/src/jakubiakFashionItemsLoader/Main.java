package jakubiakFashionItemsLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import java.net.URI;

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
import org.glassfish.jersey.media.sse.SseFeature;

import com.sun.jersey.api.client.WebResource;

import numocoProcessing.Product;
import numocoProcessing.Root;
import numocoProcessing.Size;
import numocoProcessing.Sizes; 
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
public class Main {
	private static void processNumocoXML(List<Item> listOfItems) throws Exception
	{
		File file = new File("C:\\Firma\\pobrane.xml");
		JAXBContext jaxbContext  = JAXBContext.newInstance(numocoProcessing.Root.class);
		int counter=0;
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Root root = (Root) jaxbUnmarshaller.unmarshal(file);
		
		for(Product product:root.getProducts().getProducts())
		{
			counter++;			
			String tempProductName;
			
			// Determinig master item name
			
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
			
		//	System.out.println(tempProductName);
			
			if(product.getSizes()!=null)
			{
				/// Product processing
			
				
				Sizes sizes = product.getSizes();
				if(sizes.getSizes()!= null && !sizes.getSizes().isEmpty())
				{
					for(Size size:sizes.getSizes())
					{
						if(size != null)
						{
							// Sizes processing
							String sizeTempProductName = tempProductName;
							if(sizeTempProductName.trim().length() < 47)
							{
								sizeTempProductName = sizeTempProductName.trim() + " "+size.getName();
							}
							Item item = new Item();
							item.setName(sizeTempProductName);
							item.setitCostPrice(Double.parseDouble(size.getPrice()));
							item.setSymbol(product.getModel());
							listOfItems.add(item);
						}
						
					}
				}
			}
			
		}
//		System.out.println(Integer.toString(counter));
	}
	private static URI getBaseURI() {
        return UriBuilder.fromUri("https://fashion-jakubiak.pl/webapi/rest").build();
    }
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		String baseurl = "http://fashion-jakubiak.pl/webapi/rest";
		List<Item> listOfItems = new ArrayList<>();
		processNumocoXML(listOfItems);

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(baseurl).path("auth");

		Form form = new Form();

		String response=target
				.request(MediaType.APPLICATION_FORM_URLENCODED_TYPE).header("Authorization", "Basic YXBpYWRtaW46QXBpQWRtaW4xMjM=")
				.post(Entity.form(form),String.class);
		
		Gson gson = new GsonBuilder().create();
		HashMap map = gson.fromJson(response, HashMap.class);
		System.out.println(response);
		System.out.println(map.get("access_token"));

		 target = client.target(baseurl).path("Products");

			//Form form1 = new Form();

			String response1=target
					.request(MediaType.APPLICATION_FORM_URLENCODED_TYPE).header("Authorization", "Bearer "+map.get("access_token")).get(String.class);
			Map<String,Object> products = gson.fromJson(response1, Map.class);
			
			for(Map.Entry<String, Object> entry:products.entrySet())
			{
				Syst
			}
			System.out.println(response1);
			
		
	}
	
	

}
