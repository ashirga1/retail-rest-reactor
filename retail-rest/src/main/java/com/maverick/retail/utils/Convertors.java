package com.maverick.retail.utils;

import com.google.gson.Gson;
import com.maverick.retail.object.Product;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * Utility class for converting Json or DBobject to Java POJO and vice versa.
 * 
 * @author Asad Shirgaonkar
 */
public final class Convertors 
{
	/**
	 * Helper method for converting {@link Product} to a JSON string.
	 * 
	 * @param product The {@link Product}
	 * 
	 * @return JSON string.
	 */
	public static String toJson(Product product)
	{
		final Gson gson = new Gson();
		return gson.toJson(product);
	}

	/**
	 * Helper method for converting {@link DBObject} to a expected object.
	 * 
	 * @param dbObject The {@link DBObject}.
	 * @param clazz The destination class object
	 * 
	 * @return The expected Class Object.
	 */
	public static Object fromDBObject(DBObject dbObject, Class<?> clazz) 
	{
		String json = dbObject.toString();
	    return new Gson().fromJson(json, clazz);
	}

	/**
	 * Helper method for converting {@link Product} to {@link DBObject}.
	 * 
	 * @param product The {@link Product} object.
	 * 
	 * @return The {@link DBObject}.
	 */
	public static DBObject toDBObject(Product product) 
	{
		Gson gson  = new Gson();
		return (DBObject) JSON.parse(gson.toJson(product));		
	}
	
    private Convertors()
    {
        throw new UnsupportedOperationException("Convertors should never be instantiated");
    }
}


