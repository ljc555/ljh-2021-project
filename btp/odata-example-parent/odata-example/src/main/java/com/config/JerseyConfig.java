package com.config;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.ApplicationPath;

import org.apache.olingo.odata2.core.rest.ODataRootLocator;
import org.apache.olingo.odata2.core.rest.app.ODataApplication;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.locator.CarsRootLocator;
import com.service.CarsODataJPAServiceFactory;
import com.service.EntityManagerFilter;

@Component
@ApplicationPath("/odata")
public class JerseyConfig extends ResourceConfig {
	
    public JerseyConfig(CarsODataJPAServiceFactory serviceFactory, EntityManagerFactory emf) {        
        ODataApplication app = new ODataApplication();        
        app
          .getClasses()
          .forEach( c -> {
              if ( !ODataRootLocator.class.isAssignableFrom(c)) {
                  register(c);
              }
          });
         
        register(new CarsRootLocator(serviceFactory)); 
        register(new EntityManagerFilter(emf));
    }
}