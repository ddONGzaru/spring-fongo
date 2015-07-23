package io.manasobi.mongodb.repository;

import static com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;

import javax.annotation.Resource;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.fakemongo.junit.FongoRule;
import com.lordofthejars.nosqlunit.annotation.ShouldMatchDataSet;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;


/**
 * Date:   6/28/13 / 10:41 AM
 * Author: Johnathan Mark Smith
 * Email:  john@johnathanmarksmith.com
 * <p/>
 * Comments:
 *
 *  This is a demo on how to use Fongo and nosqlunit-mongodb
 */


@RunWith(SpringJUnit4ClassRunner.class)
//@Import(value = {TestMongoConfig.class})
//@ContextConfiguration(classes = TestMongoConfig.class, loader=AnnotationConfigContextLoader.class)
@ContextConfiguration(classes = TestMongoConfig.class, loader=SpringApplicationContextLoader.class)
public class PersonRepositoryTest2 {

	@Rule
	public MongoDbRule mongoDbRule = newMongoDbRule().defaultSpringMongoDb("test-db");

    /**
     *
     *   nosql-unit requirement
     *
     */
	@Autowired private ApplicationContext applicationContext;
	
	@Resource(name = "personRepository") 
	private PersonRepository personRepository;

	/**
	 * Expected results are in "one-person.json" file
	 */
	@Test
	@ShouldMatchDataSet(location = "/two-person.json")
    public void testInsertPersonWithNameJohnathanAndRandomAge(){
		
         this.personRepository.insertPersonWithNameJohnathan(35);
         this.personRepository.insertPersonWithNameJohnathan(67);
    }
	
	/**
	 * Insert data from "five-person.json" and test countAllPersons method
	 */
	@Test
	@UsingDataSet(locations = {"/five-person.json"}, loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    public void testCountAllPersons(){
		
		/*try {
			fongoRule.insertFile(fongoRule.newCollection("person"), "/two-person2.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
         long total = this.personRepository.countAllPersons();
         
         assertThat(total).isEqualTo(5);
    }
	
	/**
	 * Insert data from "five-person.json" and test countUnderAge method
	 */
	@Test
	@UsingDataSet(locations = {"/five-person.json"}, loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    public void testCountUnderAge(){
         long total = this.personRepository.countUnderAge();
         
         assertThat(total).isEqualTo(3);
    }
	
	/*@Configuration
	@EnableMongoRepositories("io.manasobi")
	@ComponentScan(basePackages = {"io.manasobi"})  // modified to not load configs from com.johnathanmarksmith.mongodb.example.MongoConfiguration
	@PropertySource("classpath:application.properties")
	class PersonRepositoryTestConfiguration extends AbstractMongoConfiguration {

	    @Override
	    protected String getDatabaseName() {
	        return "demo-test";
	    }
		
	    @Override
		public Mongo mongo() {
	    	// uses fongo for in-memory tests
			return new Fongo("mongo-test").getMongo();
		}
		
	    @Override
	    protected String getMappingBasePackage() {
	        return "io.manasobi";
	    }

	}*/
}
