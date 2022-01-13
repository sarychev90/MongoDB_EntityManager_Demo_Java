package best.project.mongodb.helper;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

public class GenegalOperationHelper {
	
	private static final Logger LOG = Logger.getLogger(GenegalOperationHelper.class.getName());
	
	public static Properties getProperties() {
		Properties properties = new Properties();
		String propertiesFile = "/config.properties";
		try (InputStream inputStream = GenegalOperationHelper.class.getResourceAsStream(propertiesFile)) {
			properties.load(inputStream);
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "getProperties exception {0}", e.getClass().getSimpleName());
		}
		return properties;
	}
	
	public static Map<String, Object> convertObjectToMap(Object obj) {
		Map<String, Object> objAsMap = new HashMap<>();
		if (null != obj) {
			for (Field field : obj.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				try {
					objAsMap.put(field.getName(), field.get(obj));
				} catch (Exception e) {
					LOG.log(Level.SEVERE, "Can't get value from field {0}", field.getName());
				}
			}
		}
		return objAsMap;
	}
	
	public static <T> T convertDocumentToObject(Document document, Class<T> clazz) {
		T object = null;
		try {
			if (null != document) {
				Constructor<T> cons = clazz.getConstructor();
				object = cons.newInstance();
				for (Field field : clazz.getDeclaredFields()) {
					field.setAccessible(true);
					field.set(object, document.get(field.getName()));
				}
			}
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "convertDocumentToObject exception {0}", e.getClass().getSimpleName());
		}
		return object;
	}
	
}
