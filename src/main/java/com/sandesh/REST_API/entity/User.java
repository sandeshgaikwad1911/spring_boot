package com.sandesh.REST_API.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
public class User {

    @Id  // it's map with _id: ObjectId(".....") inside MongoDB.
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    private String username;

    @NonNull
    private String password;

    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();
}

/*
    @DBRef => means we are creating ref of JournalEntry class inside [users] collections.
    so, User will have ref of @Document(collection = "journalEntries") collections.

    @Indexed(unique = true)     // indexing will not happen automatically we have to do it manually inside application.properties.
    [spring.data.mongodb.auto-index-creation=true]

 */
