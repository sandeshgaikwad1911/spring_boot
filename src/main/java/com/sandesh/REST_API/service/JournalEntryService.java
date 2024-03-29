package com.sandesh.REST_API.service;

import com.sandesh.REST_API.entity.JournalEntry;
import com.sandesh.REST_API.entity.User;
import com.sandesh.REST_API.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
//    we don't need to create instance using new keyword because of [@Autowired].
//    JournalEntryRepository it's an interface and its implementation is put by spring at runtime.

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username) {
        try{

//        we have to save entry at two collections.      [ 1.journalEntries 2.users ]

            // here we get user first based on username sent from controller
            User user = userService.findUserByUsername(username);
//        System.out.println("user=>"+" "+user);

//        save entry in journalEntries collections
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedEntry  = journalEntryRepository.save(journalEntry);

//        we got user, and user has journalEntries field,
//        journalEntries is List, so we can use add() method on it.

//            user.setUsername(null);

            user.getJournalEntries().add(savedEntry);
            userService.saveUser(user);
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("something went wrong while saving entry to database");
        }
    }

    public void updateEntry(JournalEntry journalEntry){

        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    /*public JournalEntry findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }*/
    public Optional<JournalEntry> findJournalById(ObjectId id) {
        return  journalEntryRepository.findById(id);
//        Optional is container object which may or may not contain non-null value;
//        Optional<>    we make it Optional => because we may or may not find data
//        Optional is introduced in java 8
    }


    public void deleteJournalById(String username, ObjectId id) {

        User user = userService.findUserByUsername(username);
//        System.out.println("user"+" "+user);

//        we also have to remove ref of deleted journalEntry from users collection. inside relation Database, it happens automatically
//        in mongoDB we have to do it manually.
//        so here we have user and it has journalEntries field.

        user.getJournalEntries().removeIf(x-> x.getId().equals(id));

//        here we remove deleted entry from users journalEntries field. and save updated user.
        userService.saveUser(user);

//        delete journalEntry
        journalEntryRepository.deleteById(id);
    }

}

/*
    .save(), .findAll(), findById() ... are all methods from MongoDB.
*/

/*
    @Transactional

        inside saveEntry() method we saving entry at two collections, journalEntries and users.
        if entry is saved at journalEntries and then occur exception and entry is not saved in users collection
        then saved entry must be rollback.

        here, we achieve atomicity => multiple operation group into single operation, and if one of them fail
        whole operation is considered as failed operation

        but this is not it. we also have to put @EnableTransactionManagement on main method.

        who does this commit and rollback job ? => its interface called PlatformTransactionManager

        public interface PlatformTransactionManager extends TransactionManager

        it's an interface so, it has implementation also
        public class MongoTransactionManager extends AbstractPlatformTransactionManager implements ResourceTransactionManager, InitializingBean {

        to happen transaction replica is must inside mongoDB
        replica => that means copy of our database is also on other server, if one fails then its useful
        sharding => data is saved in distributed manner.

* */