package io.manasobi.fongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;

@Configuration
@ComponentScan("io.manasobi")
@EnableMongoRepositories("io.manasobi.fongo")
public class TestMongoConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "test-db";
    }
	
    @Override
    protected String getMappingBasePackage() {
        return "io.manasobi";
    }

    @Bean
    public Mongo mongo() {
    	return new Fongo("test-db").getMongo();
    }
}