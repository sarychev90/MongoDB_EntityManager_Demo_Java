package best.project.mongodb.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;

public class MongoOperationHelper {
	
	private static final Logger LOG = Logger.getLogger(MongoOperationHelper.class.getName());
	
	public static void insert(Object obj, MongoCollection<Document> collection) {
		try {
			Map<String, Object> objDataMap = GenegalOperationHelper.convertObjectToMap(obj);
			Document document = new Document(objDataMap);
			collection.insertOne(document);			
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "insert exception {0}", e.getClass().getSimpleName());
		}
	}
	
	public static <T> T getObject(MongoCollection<Document> collection, Bson filter, Class<T> clazz) {
		T obj = null;
		try {
			Document document = getDocument(collection, filter);
			obj = GenegalOperationHelper.convertDocumentToObject(document, clazz);
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "getObject exception {0}", e.getClass().getSimpleName());
		}
		return obj;
	}
	
	public static <T> List<T> getObjectList(MongoCollection<Document> collection, Bson filter, Class<T> clazz) {
		List<T> objects = new ArrayList<>();
		try {
			List<Document> documents = getDocumentList(collection, filter);
			documents.forEach(document -> objects.add(GenegalOperationHelper.convertDocumentToObject(document, clazz)));
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "getObjectList exception {0}", e.getClass().getSimpleName());
		}
		return objects;
	}
	
	public static Document getDocument(MongoCollection<Document> collection, Bson filter) {
		Document document = null;
		try {
			document = collection.find(filter).first();
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "getDocument exception {0}", e.getClass().getSimpleName());
		}
		return document;
	}
	
	public static List<Document> getDocumentList(MongoCollection<Document> collection, Bson filter) {
		List<Document> documents = new ArrayList<>();
		try {
			documents = collection.find(filter).into(new ArrayList<>());
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "getDocumentList exception {0}", e.getClass().getSimpleName());
		}
		return documents;
	}
	
	public static void update(MongoCollection<Document> collection, Bson filter, Object obj) {
		try {
			Map<String, Object> objDataMap = GenegalOperationHelper.convertObjectToMap(obj);
			Document document = new Document("$set", objDataMap);
			collection.updateMany(filter, document);
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "update exception {0}", e.getClass().getSimpleName());
		}
	}
	
	public static void update(MongoCollection<Document> collection, Bson filter, String key, Object value) {
		try {
			collection.updateMany(filter, Updates.set(key, value));
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "update by key exception {0}", e.getClass().getSimpleName());
		}
	}
}
