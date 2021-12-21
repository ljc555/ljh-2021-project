package com.locator;

import javax.ws.rs.Path;

import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.core.rest.ODataRootLocator;

import com.service.CarsODataJPAServiceFactory;

@Path("/")
public class CarsRootLocator extends ODataRootLocator {
    
	private CarsODataJPAServiceFactory serviceFactory;
    
	public CarsRootLocator(CarsODataJPAServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }
 
    @Override
    public ODataServiceFactory getServiceFactory() {
       return this.serviceFactory;
    } 
}