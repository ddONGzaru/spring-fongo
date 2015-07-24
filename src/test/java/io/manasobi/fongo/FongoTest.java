package io.manasobi.fongo;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestMongoConfig.class, loader=SpringApplicationContextLoader.class)
public class FongoTest {

	@Rule
	public MongoDbRule mongoDbRule = newMongoDbRule().defaultSpringMongoDb("test-db");

	@Autowired
	@SuppressWarnings("unused")
	private ApplicationContext applicationContext;
	
	@Autowired 
	private PersonRepo personRepo;


	@Test
	@UsingDataSet(locations = {"/dataset/two-person.json"}, loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    public void testInsertPersonWithNameJohnathanAndRandomAge(){
		
         this.personRepo.save(35);
         this.personRepo.save(67);
         
         long total = this.personRepo.count();
         
         assertThat(total).isEqualTo(4);
    }
	
	@Test
	@UsingDataSet(locations = {"/dataset/five-person.json"}, loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    public void testCountAllPersons(){
		
         long total = this.personRepo.count();
         
         assertThat(total).isEqualTo(5);
    }
	
	@Test
	@UsingDataSet(locations = {"/dataset/five-person.json"}, loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    public void testCountUnderAge(){
		
         long total = this.personRepo.countUnderAge();
         
         assertThat(total).isEqualTo(3);
    }

}
