/**
 * 
 */
package com.amazon.webservices.soap.client.test;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazon.webservices.soap.client.AmazonClientWorker;
import com.amazon.webservices.soap.types.Item;

/**
 * @author MVEKSLER
 *
 */
public class AmazonClientWorkerTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.AmazonClientWorker.amazon.AmazonProductSearch#getAmazonInfo(java.lang.String)}.
	 * @throws Exception
	 */
	@Test
	public void testGetAmazonInfo() throws Exception 
	{
		
		AmazonClientWorker amazonClientWorker = new AmazonClientWorker("AKIAIYJHVBEP5LF72RGA");
		Item amazonInfo = amazonClientWorker.itemLookup("B001AVOJ50");
		assertNotNull(amazonInfo);
		
		//String price = amazonProductSearch.getCartPrice(amazonInfo);
		//assertNotNull(price);
	}
}
