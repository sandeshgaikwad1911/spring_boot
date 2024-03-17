package com.sandesh.REST_API.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

//@Document(collection = "journalEntries")
//// journalEntries is name of our collection inside journalDB database;
//// journalDB created inside resources => application.properties file
//public class JournalEntry {
//    // this is pojo class => plain old java object
//    @Id     // to make this field primary_Key
//    private ObjectId id;
//    private String title;
//    private String content;
//    private LocalDateTime date;
//
//    public LocalDateTime getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDateTime date) {
//        this.date = date;
//    }
//
//    public void setId(ObjectId id) {
//        this.id = id;
//    }
//
//    public ObjectId getId() {
//        return id;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//}

@Document(collection = "journalEntries")
@Data
@NoArgsConstructor
public class JournalEntry {

    @Id
    private ObjectId id;

    @NonNull
    private String title;

    @NonNull
    private String content;

    private LocalDateTime date;

}


//   This is simple pojo class => we have to map this with collection. @Document

//     @Id   =>   to make this field primary_Key inside collection

//     @Data => its equivalent to @Getter @Setter and many more. at compile time Lombok library generate code for us according to annotations.


/*
    journalEntries is name of our collection inside journalDB database;
    journalDB created inside resources => application.properties file
*/
