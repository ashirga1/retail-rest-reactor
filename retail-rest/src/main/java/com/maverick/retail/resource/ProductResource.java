package com.maverick.retail.resource;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.maverick.retail.dao.ProductDao;
import com.maverick.retail.object.Product;
import com.maverick.retail.utils.Convertors;
import com.maverick.retail.utils.PropertiesExtractor;

/**
 * REST resource providing services for {@link ProductResource}.
 * 
 * @author Asad Shirgaonkar
 * 
 * @since 1.0
 */
@Path("product")
public final class ProductResource 
{
	private static Properties entityProperties = PropertiesExtractor.getProperties(ProductResource.class, "maverick.properties");
	private ProductDao productDao;
	
	/**
	 * Creates a new Product Resource of type {@link ProductResource}.
	 */
	public ProductResource()
	{
		productDao = new ProductDao();
	}
	
	/**
	 * Retrieve product details in JSON format based on product identifier.
	 * 
	 * The following statuses may be returned.
	 * 
	 * <dl>
	 * 	<dt>200 OK</dt>
	 * 	<dt>The product details was successfully retrieved.</dt>
	 * </dl>
	 * 
	 * <dl>
	 * 	<dt>400 BAD_REQUEST</dt>
	 * 	<dt>The product identifier passed in the request is null or empty.</dt>
	 * </dl>
	 * <dl>
	 * 	<dt>404 NOT_FOUND</dt>
	 * 	<dt>The product detail cannot be found.</dt>
	 * </dl>
	 * 
	 * @param productId The product ID obtained through the request.
	 * 
	 * @return Non-null response of type {@link com.maverick.retail.object.Product}.
	 */
	@Path("{productId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveProductById(@PathParam("productId") final int productId) 
	{		
		Optional<Product> optionalProduct = productDao.getProductById(productId);
		if(optionalProduct.isPresent())
		{
			 return Response.ok(Convertors.toJson(optionalProduct.get()))
					.build();
		}
		final String message = MessageFormat.format(entityProperties.getProperty("com.maverick.retail.resource.ProductResource.ProductNotFound"), productId);
		return Response.status(Status.NOT_FOUND)
				.entity(message)
				.build();
	}
	
	/**
	 * Updates current details of the product details in JSON format based on product identifier.
	 * 
	 * If the product identifier is not found, it will create a new Product for the retail , based on the Request passed through
	 * the PUT header.
	 * 
	 * The following statuses may be returned.
	 * 
	 * <dl>
	 * 	<dt>200 OK</dt>
	 * 	<dt>The product details was updated or product was successfully inserted..</dt>
	 * </dl>
	 * 
	 * <dl>
	 * 	<dt>400 BAD_REQUEST</dt>
	 * 	<dt>The product identifier passed through the URI header is not matching with the product ID passed through the PUT Header</dt>
	 * </dl>
	 * 
	 * @param productId The product ID obtained through the request.
	 * 
	 * @return Non-null response of type {@link com.maverick.retail.object.Product}.
	 */
	@Path("{productId}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOrUpdateProduct(final Product product, @PathParam("productId") final int productId) 
	{
		final Optional<Product> optionalProduct = productDao.getProductById(productId);
		if(optionalProduct.isPresent())
		{
			Product productToUpdate = optionalProduct.get();
			productDao.updateProduct(productToUpdate, product);
			final String message = MessageFormat.format(entityProperties.getProperty("com.maverick.retail.resource.ProductResource.ProductUpdated"), productId);	
			return Response.ok(message)
						.build();
			
		}
		if(productId != product.getProductId())
		{
			final String message = entityProperties.getProperty("com.maverick.retail.resource.ProductResource.ProductIdNotMatch");					
			return Response.status(Status.BAD_REQUEST).entity(message)
					.build();
		}
		final Optional<Product> createdProduct = productDao.createProduct(product);
		if(createdProduct.isPresent())
		{
			String message = MessageFormat.format(entityProperties.getProperty("com.maverick.retail.resource.ProductResource.ProductCreated")
					, createdProduct.get().getProductId());
			return Response.ok(message)
					.build();
		}
		String message = MessageFormat.format(entityProperties.getProperty("com.maverick.retail.resource.ProductResource.ProductAlreadyExist")
				, product);
		return Response.ok(message)
				.build();
	}
}
