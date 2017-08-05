package com.maverick.retail.dao;

import java.net.UnknownHostException;
import java.util.Optional;
import java.util.Properties;

import com.maverick.retail.object.CurrencyPrice;
import com.maverick.retail.object.Product;
import com.maverick.retail.utils.Convertors;
import com.maverick.retail.utils.PropertiesExtractor;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

/**
 * This class represents method that access and update products from the retail database.
 * 
 * @author Asad Shirgaonkar
 */
public class ProductDao 
{	
	private static Properties databaseProperties = PropertiesExtractor.getProperties(ProductDao.class, "database.properties");
	
	private static final String PRODUCT_ID = "productId";
	private static final String PRODUCT_NAME = "name";
	private static final String CURRENCY_PRICE = "currencyPrice";
	private static final String CURRENCY_VALUE = "value";
	private static final String CURRENCY_NAME = "currency";
	
	private MongoClient mongoClient;
	private DB retailDatabase;
	private DBCollection productsCollection;
	
	/**
	 * Constructor for {@link ProductDao}.
	 */
	public ProductDao() 
	{
		try 
		{
			mongoClient = new MongoClient(databaseProperties.getProperty("database.hostname"), 
				Integer.parseInt(databaseProperties.getProperty("database.portnumber")));
		}
		catch(UnknownHostException exception)
		{
			exception.printStackTrace();
			System.exit(1);
		}
		retailDatabase = mongoClient.getDB(databaseProperties.getProperty("database.name"));
		productsCollection = retailDatabase.getCollection(databaseProperties.getProperty("database.name.collection"));
	}
	
	/**
	 * Retrieves {@link Product} based on the product ID.
	 * 
	 * @param productId The product ID.
	 * @return {@link Product} object.
	 */
	public Optional<Product> getProductById(final int productId)
	{
		DBObject searchQuery = new BasicDBObject();
		searchQuery.put(PRODUCT_ID, productId);
		DBObject productDbObject = productsCollection.findOne(searchQuery);
		if(productDbObject != null)
		{
			Product product = (Product) Convertors.fromDBObject(productDbObject, Product.class);
			return Optional.of(product);
		}
		return Optional.empty();
	}
	
	/**
	 * Updates the old product with the new product object
	 * 
	 * @param oldProduct The {@link Product} that needs to be updated.
	 * @param newProduct The {@link Product} thats is updated.
	 */
	public void updateProduct(final Product oldProduct, final Product newProduct)
	{	
		final DBObject oldDBProductObject = buildDBObject(oldProduct);
		final DBObject newDBProductObject = buildDBObject(newProduct);
		productsCollection.update(oldDBProductObject, newDBProductObject);
	}
	
	/**
	 * Create a {@link DBObject} of type {@link Product}
	 * 
	 * @param product The {@link Product} 
	 * @return a {@link DBObject} of type {@link Product}.
	 */
	private DBObject buildDBObject(Product product) 
	{
		final DBObject productObject = new BasicDBObject();
		productObject.put(PRODUCT_ID, product.getProductId());
		productObject.put(PRODUCT_NAME, product.getName());
		final DBObject currencyPrice = new BasicDBObject();
		CurrencyPrice currencyPriceObject = product.getCurrencyPrice();
		currencyPrice.put(CURRENCY_NAME, currencyPriceObject.getCurrency());
		currencyPrice.put(CURRENCY_VALUE, currencyPriceObject.getValue());
		productObject.put(CURRENCY_PRICE, currencyPrice);			
		return productObject;
	}

	/**
	 * Insert a product into the retail.
	 * 
	 * @param product The {@link Product} to be inserted.
	 * @return The {@link Product}
	 */
	public Optional<Product> createProduct(final Product product)
	{
		try 
		{
			DBObject productObject = buildDBObject(product);
			productsCollection.insert(productObject);
			return Optional.of(product);
		}
		catch(MongoException.DuplicateKey exception)
		{
			return Optional.empty();
		}
	}
}
