package io.manasobi.fongo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepo {

    static final Logger logger = LoggerFactory.getLogger(PersonRepo.class);

    @Autowired
    MongoTemplate mongoTemplate;
    
    public long countUnderAge() {
    	
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria = criteria.and("age").lte(21);

        query.addCriteria(criteria);
        
        long count = mongoTemplate.count(query, Person.class);
        
        logger.info("Total number of under age in database: {}", count);
        
        return count;
    }

    public long count() {
    	
    	long total = mongoTemplate.count(null, Person.class);
    	
        logger.info("Total number in database: {}", total);
        
        return total;
    }

    public void save(double age) {
    	
        Person p = new Person("manasobi", (int) age);

        mongoTemplate.insert(p);
    }
    
}
