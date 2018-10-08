/**
 * 
 */
package com.amazon.webservices.soap.client;

/**
 * @author MVEKSLER
 *
 */

/*
 * Use one of the following end-points, according to the region you are interested in.
 * 
 * 	US:	soap.amazon.com
 * 	CA:	soap.amazon.ca
 * 	UK:	soap.amazon.co.uk
 * 	DE:	soap.amazon.de
 * 	FR:	soap.amazon.fr
 * 	JP:	soap.amazon.co.jp
 * 
 * If you want to use tcpmon[1] to capture the outgoing and incoming
 * SOAP envelopes, set it up to listen on port 8080 and
 * forward to one of the end-points above and set END_POINT below
 * to "localhost:8080"
 * 
 * [1] https://tcpmon.dev.java.net/
 * 
 */

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.addressing.AddressingConstants;
import org.apache.axis2.client.Options;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.databinding.types.PositiveInteger;
import org.apache.axis2.engine.Handler;
import org.apache.axis2.engine.Phase;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyEngine;
import org.apache.rampart.RampartMessageData;
import org.apache.rampart.handler.PostDispatchVerificationHandler;
import org.apache.rampart.handler.RampartReceiver;

import com.amazon.webservices.soap.AWSECommerceServiceAWSECommerceServicePortCAStub;
import com.amazon.webservices.soap.types.CartCreate;
import com.amazon.webservices.soap.types.CartCreateRequest;
import com.amazon.webservices.soap.types.CartCreateResponse;
import com.amazon.webservices.soap.types.Errors;
import com.amazon.webservices.soap.types.Item;
import com.amazon.webservices.soap.types.ItemLookup;
import com.amazon.webservices.soap.types.ItemLookupRequest;
import com.amazon.webservices.soap.types.ItemLookupResponse;
import com.amazon.webservices.soap.types.ItemSearch;
import com.amazon.webservices.soap.types.ItemSearchRequest;
import com.amazon.webservices.soap.types.ItemSearchResponse;
import com.amazon.webservices.soap.types.Items;
import com.amazon.webservices.soap.types.Price;
import com.amazon.webservices.soap.types.Request;

public class AmazonClientWorker 
{
	/*
     * The AWS Access Key ID that corresponds to the identity you wish to use.
     */
    private static final String AWS_ACCESS_KEY_ID = "";
    
    /*
     * The AWS Secret Key ID.
     */
    @SuppressWarnings("unused")
	private static final String AWS_SECRET_KEY    = "";
    
    /*
     * The AWS Assosiate Tag.
     */
    private static final String AWS_ASSOCIATE_TAG = "";
    
    /*
     * Which ASIN to lookup up.
     */
    @SuppressWarnings("unused")
	private static final String MY_ITEM_ID = "";
    
	protected String amazonWsAccessKey = AWS_ACCESS_KEY_ID;
	
	protected  AWSECommerceServiceAWSECommerceServicePortCAStub client = null;
    
	//---------------------------------------------------------------------
	public AmazonClientWorker(String amazonWsKey) throws Exception 
	{
		super();
		
		try
		{
			this.amazonWsAccessKey = amazonWsKey;
	 
	        ClassLoader loader = AmazonClientWorker.class.getClassLoader();
	 
	        ConfigurationContext ctx = ConfigurationContextFactory.createConfigurationContextFromURIs(
	        		loader.getResource("client.axis2.xml"), null);
	   
	        this.client = new AWSECommerceServiceAWSECommerceServicePortCAStub(ctx);
	        
	        Options options = this.client._getServiceClient().getOptions();
	        
	        this.client._getServiceClient().getOptions().setProperty(
	        		AddressingConstants.INCLUDE_OPTIONAL_HEADERS,Boolean.TRUE);
	 
	        InputStream resource = loader.getResourceAsStream("policy.xml");
	        Policy policy = PolicyEngine.getPolicy(resource);
	        options.setProperty(RampartMessageData.KEY_RAMPART_POLICY, policy);
	        
	        // work around Rampart? bug which expects SOAP headers in the response
	        for(Phase phase : ctx.getAxisConfiguration().getInFlowPhases())
	        {
	        	if(phase.getName().equals("Security")) {
			        for(Handler handler : phase.getHandlers()) {
			        	if(handler instanceof RampartReceiver) {
			        		phase.removeHandler(handler.getHandlerDesc());
			        	}
			        }
	        	}
	        	if(phase.getName().equals("Dispatch")) {
			        for(Handler handler : phase.getHandlers()) {
			        	if(handler instanceof PostDispatchVerificationHandler) {
			        		phase.removeHandler(handler.getHandlerDesc());
			        	}
			        }        		
	        	}
	        }
		}
		catch( Exception e)
		{
			
		}
	}
	
	//---------------------------------------------------------------------
	public String getCartPrice(Item item) throws Exception 
	{
		try
		{
			PositiveInteger qty = new PositiveInteger("1");
			CartCreateRequest cartCreateRequest = new CartCreateRequest();
			
			CartCreateRequest.Items.Item cartItem = new CartCreateRequest.Items.Item();
			cartItem.setASIN(item.getASIN());
			cartItem.setQuantity(qty);
			
			CartCreateRequest.Items items = new CartCreateRequest.Items();
			items.getItem().add(cartItem);
			cartCreateRequest.setItemsCustom(items);
			
			//---
			CartCreate cart = new CartCreate();
			cart.setAssociateTag(AWS_ASSOCIATE_TAG);
			cart.setAWSAccessKeyId(amazonWsAccessKey);
			cart.setShared(cartCreateRequest);
			cart.getRequest().add(cartCreateRequest);
			
			//---
			CartCreateResponse resp = client.cartCreate(cart);
			
			Price cartPrice = resp.getCart().get(0).getCartItems().getCartItem().get(0).getPrice();
			
			return cartPrice.getFormattedPrice();
		}
		catch( Exception e)
		{
			return "M.V. Custom::ERROR:" + e.getMessage();
		}
	}
	
	//---------------------------------------------------------------------
	public Item itemLookup(String asin) throws RemoteException, Exception
	{
		try
		{
			ItemLookupRequest itemLookup = new ItemLookupRequest();
			itemLookup.getItemId().add(asin);
			itemLookup.setMerchantId("All");
		
			//---
			ItemLookup lookup = new ItemLookup();
			lookup.setAWSAccessKeyId(amazonWsAccessKey);
			lookup.setAssociateTag(AWS_ASSOCIATE_TAG);
			lookup.setShared(itemLookup);
			lookup.getRequest().add(itemLookup);
	 
			//List<String> responseGroups = new ArrayList<String>();
			//responseGroups.add("ItemAttributes");
			//responseGroups.add("OfferFull");
			//responseGroups.add("Images");
			//responseGroups.add("Reviews");
			//responseGroups.add("Medium");
			itemLookup.getResponseGroup().add("ItemAttributes");
	 
			// build Amazon Client
			ItemLookupResponse response = client.itemLookup(lookup);
			
			//for(Items_type3 items : response.getItemLookupResponse().getItems())
			for(Items items : response.getItems())
			{
				Request request = items.getRequest();
				
				if(request.getErrors() != null)
				{
					for(Errors.Error error : request.getErrors().getError())
					{
						System.out.println("Error: " + error.getMessage());
					}
				}
	 
				if(items.getItem() != null && items.getItem().size() > 0)
				{
					Item cItem = items.getItem().get(0);
					
					System.out.println("Item ASIN: " +cItem.getASIN());
					System.out.println("Item Parent ASIN: " +cItem.getParentASIN());
					
					return items.getItem().get(0);
				}
			}
			return null;
		}
		catch( Exception e)
		{
			Item errorItem = new Item();
			
			errorItem.setASIN("M.V. Custom::ERROR:" + e.getMessage());
			
			return errorItem ;
		}
	}
	
	//---------------------------------------------------------------------
	public List<Items> itemsSearch(String asin) throws RemoteException, Exception
	{
		List<Items> retItems = new ArrayList<Items>();
		
		try
		{
			ItemSearchRequest request = new ItemSearchRequest();                      
		        
	        request.setSearchIndex("Books");
	        
	        request.setKeywords("Austen");
	        
	        ItemSearch itemSearch= new ItemSearch();                                  
	        
	        itemSearch.setAWSAccessKeyId(amazonWsAccessKey);
	       
	        itemSearch.setAssociateTag(AWS_ASSOCIATE_TAG);
	       
	        itemSearch.getRequest().add(request);
	        
	        ItemSearchResponse response = client.itemSearch(itemSearch);                
	        
	        List<Items> itemsList = response.getItems();                              
	        
	        int i = 1;
	        
	        for (Items next : itemsList)
	        {
	           for (Item item : next.getItem())
	           {
	               System.out.println(String.format("%2d: ", i++) +
	            		   
	               item.getItemAttributes().getTitle());
	           }
	        }
		        
			return retItems;
		}
		catch( Exception e)
		{
			retItems = new ArrayList<Items>();
			
			return retItems ;
		}
	}
}
