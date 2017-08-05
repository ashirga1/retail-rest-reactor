package com.maverick.retail;

import java.util.Set;
import java.util.HashSet;

import javax.ws.rs.core.Application;

import com.maverick.retail.resource.ProductResource;

/**
 * <p> JAX-RS {@link Application} that contributes the components necessary to expose 
 * RESTful services for Retail application.
 * </p>
 * 
 * @author Asad Shirgaonkar
 */
public final class RetailApplication extends Application
{
	/**
	 * {@inheritDoc}
	 */
	public Set<Class<?>> getClasses()
	{
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(ProductResource.class);
		return classes;
	}
}
