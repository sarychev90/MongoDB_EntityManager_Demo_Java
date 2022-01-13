package best.project.mongodb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mongodb.client.model.Filters;

import best.project.mongodb.entity.Person;

public class MongoOperationManagerTest {

	private static final Logger LOG = Logger.getLogger(MongoOperationManagerTest.class.getName());
	private static final String DB_NAME = "test";
	private static final String COLLECTION_NAME = "persons";
	
	@BeforeAll
	public static void setUp() {
		Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
		MongoOperationManager.getConnection();
	}
	
	@AfterAll
	public static void shutDown() {
		MongoOperationManager.closeConnection();
	}
	
	@Test
	public void test_get_documents() throws Exception {
		List<Document> documents = MongoOperationManager.getConnection().getDocumentCollection(DB_NAME, COLLECTION_NAME).
				find(new Document()).into(new ArrayList<>());		
		documents.forEach(document -> LOG.log(Level.INFO, document.toJson()));
	}
	
	@Test
	public void test_save_entity() throws Exception {
		Person person = new Person();
		person.setFirstName("Ivan");
		person.setSurName("Ivanenko");
		person.setPatronymicName("Ivanov");
		person.setAge(45);
		person.setProffesion("lowyer");
		person.setMaritalStatus("married");
		person.setHobbies(Arrays.asList("table tennis","cycling"));
		MongoOperationManager.getConnection().save(DB_NAME, COLLECTION_NAME, person);
	}
	
	@Test
	public void test_get_entity() throws Exception {
		Bson filter = Filters.eq("age", 33);
		Person person = MongoOperationManager.getConnection().
				get(DB_NAME, COLLECTION_NAME, filter, Person.class);
		LOG.log(Level.INFO, "Person data: {0}", person);
	}
	
	@Test
	public void test_get_all_entities() throws Exception {
		Bson filter = new Document();
		List<Person> persons = MongoOperationManager.getConnection().
				getList(DB_NAME, COLLECTION_NAME, filter, Person.class);	
		persons.forEach(person -> LOG.log(Level.INFO, "Person data: {0}", person));	
	}
	
	@Test
	public void test_get_entities() throws Exception {
		Bson filter = Filters.eq("firstName", "Den");
		List<Person> persons = MongoOperationManager.getConnection().
				getList(DB_NAME, COLLECTION_NAME, filter, Person.class);	
		persons.forEach(person -> LOG.log(Level.INFO, "Person data: {0}", person));	
	}
	
	@Test
	public void test_delete_entity() throws Exception {
		Bson filter = Filters.eq("age", 33);
		MongoOperationManager.getConnection().delete(DB_NAME, COLLECTION_NAME, filter);
	}
	
	@Test
	public void test_update_entity() throws Exception {
		Bson filter = Filters.eq("age", 35);
		Person person = new Person();
		person.setFirstName("Petro");
		person.setSurName("Ivanenko");
		person.setPatronymicName("Ivanov");
		person.setAge(66);
		person.setProffesion("model");
		person.setMaritalStatus("married");
		person.setHobbies(Arrays.asList("cycling"));
		MongoOperationManager.getConnection().update(DB_NAME, COLLECTION_NAME, filter, person);
	}
	
	@Test
	public void test_update_Entity_Field() throws Exception {
		Bson filter = Filters.eq("age", 66);
		MongoOperationManager.getConnection().update(DB_NAME, COLLECTION_NAME, filter, "firstName", "Oleg");
	}

}
