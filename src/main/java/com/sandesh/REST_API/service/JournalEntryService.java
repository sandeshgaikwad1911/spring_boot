package com.sandesh.REST_API.service;

import com.sandesh.REST_API.entity.JournalEntry;
import com.sandesh.REST_API.entity.User;
import com.sandesh.REST_API.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
//    here we are injecting JournalEntryRepository in this class. we don't need to create instance using new keyword because of [@Autowired].
//    JournalEntryRepository it's an interface and its implementation is put by spring at runtime.

    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry journalEntry, String username) {

        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved  = journalEntryRepository.save(journalEntry);

        // here we find user first
        User user = userService.findByUsername(username);
        System.out.println("user=>"+" "+user);

//        journalEntries is List, so we can use add() method on it.
        user.getJournalEntries().add(saved);

        userService.saveUser(user);
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
    }

    public void deleteJournalById(ObjectId id) {
        journalEntryRepository.deleteById(id);
    }

}

/*
    .save(), .findAll(), findById() ... are all methods from MongoDB.
* */