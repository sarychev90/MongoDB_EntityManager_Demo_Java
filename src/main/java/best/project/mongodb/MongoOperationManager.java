package best.project.mongodb;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;

import best.project.mongodb.helper.GenegalOperationHelper;
import best.project.mongodb.helper.MongoOperationHelper;

public class MongoOperationManager {
	
	private static final Logger LOG = Logger.getLogger(MongoOperationManager.class.getName());

	private static MongoOperationManager instance;
	private static MongoClient mongoClient;
	
	private MongoOperationManager() {
		try {
			Properties properties = GenegalOperationHelper.getProperties();
			String connectionURL = properties.getProperty("connection.url");
			ConnectionString connectionString = new ConnectionString(connectionURL);
			MongoClientSettings settings = MongoClientSettings.builder().
					applyConnectionString(connectionString).build();
			mongoClient = MongoClients.create(settings);
			LOG.log(Level.INFO, "MongoClient inited!");
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "MongoConnection new instance exception {0}", e.getClass().getSimpleName());
		}
	}
	
	public static MongoOperationManager getConnection() {
		if(null == instance) {
			instance = new MongoOperationManager();
		}
		return instance;
	}
	
	public static void closeConnection() {
		try {
			if (null != mongoClient) {
				mongoClient.close();
				LOG.log(Level.INFO, "MongoClient closed!");
			}
			instance = null;
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "MongoConnection close exception {0}", e.getClass().getSimpleName());
		}
	}
	
	public MongoCollection<Document> getDocumentCollection(String dataBase, String collectionName) {
		return mongoClient.getDatabase(dataBase).getCollection(collectionName);
	}
	
	public void save(String dataBase, String collectionName, Object obj) {
		MongoOperationHelper.insert(obj, mongoClient.getDatabase(dataBase).getCollection(collectionName));
	}
	
	public <T> T get(String dataBase, String collectionName, Bson filter, Class<T> clazz) {
		return MongoOperationHelper.getObject(mongoClient.getDatabase(dataBase).getCollection(collectionName), filter, clazz);
	}
	
	public <T> List<T> getList(String dataBase, String collectionName, Bson filter, Class<T> clazz) {
		return MongoOperationHelper.getObjectList(mongoClient.getDatabase(dataBase).getCollection(collectionName), filter, clazz);
	}
	
	public void delete(String dataBase, String collectionName, Bson filter) {
		mongoClient.getDatabase(dataBase).getCollection(collectionName).deleteMany(filter);
	}
	
	public void update(String dataBase, String collectionName, Bson filter, Object obj) {
		 MongoOperationHelper.update(mongoClient.getDatabase(dataBase).getCollection(collectionName), filter, obj);
	}
	
	public void update(String dataBase, String collectionName, Bson filter, String key, Object value) {
		 MongoOperationHelper.update(mongoClient.getDatabase(dataBase).getCollection(collectionName), filter, key, value);
	}
}
