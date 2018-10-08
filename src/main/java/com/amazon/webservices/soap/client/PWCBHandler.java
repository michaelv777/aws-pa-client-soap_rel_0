package com.amazon.webservices.soap.client;

import org.apache.ws.security.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import java.io.IOException;

public class PWCBHandler implements CallbackHandler
{
	public void handle(Callback[] callbacks) throws IOException,
    UnsupportedCallbackException {
	
	for (int i = 0; i < callbacks.length; i++) 
	{
	    WSPasswordCallback pwcb = (WSPasswordCallback)callbacks[i];
	    String id = pwcb.getIdentifier();
	    if("michaelv777".equals(id)) {
	        pwcb.setPassword("michaelv01secret");
    }
}
}
}
