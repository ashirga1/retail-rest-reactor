package com.maverick.retail.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Helper utility class for extracting the value through property files.
 * 
 * @author Asad Shirgaonkar
 */
public final class PropertiesExtractor 
{
	/**
	 * Helper method that extracts the value through property files.
	 * 
	 * @param clazz The class object.
	 * @param propertyFileName The property file name
	 * 
	 * @return The properties from the file.
	 */
	public static Properties getProperties(Class<?> clazz, String propertyFileName)
	{
		Properties properties = new Properties();
		try 
		{
			properties.load(clazz.getClassLoader().getResourceAsStream(propertyFileName));
			return properties;
		} 
		catch (IOException exception) 
		{
			exception.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
    private PropertiesExtractor()
    {
        throw new UnsupportedOperationException("PropertiesExtractor should never be instantiated");
    }
}


