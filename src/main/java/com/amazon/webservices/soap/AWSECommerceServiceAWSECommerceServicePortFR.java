/**
 * AWSECommerceServiceAWSECommerceServicePortFR.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.3  Built on : May 30, 2016 (04:08:57 BST)
 */
package com.amazon.webservices.soap;


/*
 *  AWSECommerceServiceAWSECommerceServicePortFR java interface
 */
public interface AWSECommerceServiceAWSECommerceServicePortFR {
    /**
     * Auto generated method signature
     *
     * @param cartModify
     */
    public com.amazon.webservices.soap.types.CartModifyResponse cartModify(
        com.amazon.webservices.soap.types.CartModify cartModify)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param itemLookup
     */
    public com.amazon.webservices.soap.types.ItemLookupResponse itemLookup(
        com.amazon.webservices.soap.types.ItemLookup itemLookup)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param itemSearch
     */
    public com.amazon.webservices.soap.types.ItemSearchResponse itemSearch(
        com.amazon.webservices.soap.types.ItemSearch itemSearch)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param cartCreate
     */
    public com.amazon.webservices.soap.types.CartCreateResponse cartCreate(
        com.amazon.webservices.soap.types.CartCreate cartCreate)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param browseNodeLookup
     */
    public com.amazon.webservices.soap.types.BrowseNodeLookupResponse browseNodeLookup(
        com.amazon.webservices.soap.types.BrowseNodeLookup browseNodeLookup)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param cartGet
     */
    public com.amazon.webservices.soap.types.CartGetResponse cartGet(
        com.amazon.webservices.soap.types.CartGet cartGet)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param cartClear
     */
    public com.amazon.webservices.soap.types.CartClearResponse cartClear(
        com.amazon.webservices.soap.types.CartClear cartClear)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param cartAdd
     */
    public com.amazon.webservices.soap.types.CartAddResponse cartAdd(
        com.amazon.webservices.soap.types.CartAdd cartAdd)
        throws java.rmi.RemoteException;

    /**
     * Auto generated method signature
     *
     * @param similarityLookup
     */
    public com.amazon.webservices.soap.types.SimilarityLookupResponse similarityLookup(
        com.amazon.webservices.soap.types.SimilarityLookup similarityLookup)
        throws java.rmi.RemoteException;

    //
}
