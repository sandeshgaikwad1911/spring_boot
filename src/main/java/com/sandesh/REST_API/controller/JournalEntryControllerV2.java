package com.sandesh.REST_API.controller;

import com.sandesh.REST_API.entity.JournalEntry;
import com.sandesh.REST_API.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
//@RestController => is special type of @Component
@RequestMapping("/journal/v2")
public class JournalEntryControllerV2 {

    //    ---------------------------------------------------------------------------------------------------
    @Autowired
    private JournalEntryService journalEntryService;
    // here we are injecting JournalEntryService,


    //    ---------------------------------------------------------------------------------------------------
    @PostMapping("/create-new")
    // url will be => localhost:8080/journal/v2/create-new
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry newEntry) {
        // @RequestBody => means data from request body[postman]
        try {
            newEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(newEntry);
            return new ResponseEntity<>(newEntry, HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
//    createEntry method() gets data from RequestBody according to property of POJO class

    //    ---------------------------------------------------------------------------------------------------
    @GetMapping("/all-records")
    public ResponseEntity<?> getAll() {
       try {
           List<JournalEntry> all = journalEntryService.getAll();
           if(all != null && !all.isEmpty()) {
               return new ResponseEntity<>(all, HttpStatus.OK);
           }
       }catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }

        return null;
    }

//        ---------------------------------------------------------------------------------------------------

    @GetMapping("/id/{myId}")   // {myId} is dynamic value => localhost:8080//journal/v2/id/dynamic_value
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        try {
            Optional<JournalEntry> journalEntry = journalEntryService.findJournalById(myId);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return null;
    }

    //      ---------------------------------------------------------------------------------------------------
    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId) {
        try {
            journalEntryService.findJournalById(myId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//    ? means wild card entry, we can return any other instance also

    //    ---------------------------------------------------------------------------------------------------
    @PutMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry updateEntry) {
        //find oldEntry first to update;
        try {
            JournalEntry oldEntry = journalEntryService.findJournalById(myId).orElse(null);

            if (oldEntry != null) {
                oldEntry.setTitle(updateEntry.getTitle() != null && !updateEntry.getTitle().isEmpty() ? updateEntry.getTitle() : oldEntry.getTitle());
                oldEntry.setContent(updateEntry.getContent() != null && !updateEntry.getContent().isEmpty() ? updateEntry.getTitle() : oldEntry.getTitle());
            }
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    ---------------------------------------------------------------------------------------------------

}

/*
   Controller  =>  service   => repository
*/
