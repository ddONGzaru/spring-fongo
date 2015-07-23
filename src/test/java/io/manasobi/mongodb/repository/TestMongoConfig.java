package io.manasobi.mongodb.repository;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.github.fakemongo.Fongo;
import com.mongodb.DB;
import com.mongodb.Mongo;

@Configuration
//@EnableAutoConfiguration
@ComponentScan("io.manasobi")
@EnableMongoRepositories("io.manasobi.mongodb.repository")
public class TestMongoConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "test-db";
    }
	
    @Bean
	public Mongo mongo() {
    	// uses fongo for in-memory tests
		Mongo mongo = new Fongo("test-db").getMongo();
		
		return mongo;
	}
	
    @Override
    protected String getMappingBasePackage() {
        return "io.manasobi";
    }
    
    @Bean
    public Jongo jongo() {
    	
    	DB db = new Fongo("test").getDB("test-db");
        return new Jongo(db);
    }
    
    @Bean
    public MongoCollection users() {
        return jongo().getCollection("person");
    }

}