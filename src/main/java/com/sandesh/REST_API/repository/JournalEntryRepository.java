package com.sandesh.REST_API.repository;

import com.sandesh.REST_API.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {

}


/*
    <JournalEntry, ObjectId>  => <> its called Generics;

    JournalEntry => entity || POJO || collection, whatever u call.
    ObjectId => because id in pojo has ObjectId datatype. (JournalEntry.java)

    MongoRepository is from dependency we installed in pom.xml [spring-boot-starter-data-mongodb]
* */