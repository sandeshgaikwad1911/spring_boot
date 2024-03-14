package com.sandesh.REST_API.controller;

import com.sandesh.REST_API.entity.JournalEntry;
import com.sandesh.REST_API.entity.User;
import com.sandesh.REST_API.service.JournalEntryService;
import com.sandesh.REST_API.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@RestController => is special type of @Component
@RequestMapping("/journal/v2")
public class JournalEntryControllerV2 {

    //    ---------------------------------------------------------------------------------------------------
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

//    ---------------------------------------------------------------------------------------------------

    @GetMapping("/{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username) {
        try {

            User user = userService.findByUsername(username);

            if(user == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<JournalEntry> allUserEntries = user.getJournalEntries();

            if(!allUserEntries.isEmpty()) {
                return new ResponseEntity<>(allUserEntries, HttpStatus.OK);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return null;
    }


    //    ---------------------------------------------------------------------------------------------------
    @PostMapping("/{username}")
    public ResponseEntity<JournalEntry> createEntry(@PathVariable String username, @RequestBody JournalEntry newEntry) {
        try {
            User user = userService.findByUsername(username);
            System.out.println("user"+" "+user);

            if(user == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            journalEntryService.saveEntry(newEntry, username);
            return new ResponseEntity<>(newEntry, HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
//    createEntry method() gets data from RequestBody according to property of POJO class

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
//    @PutMapping("/id/{myId}")
//    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry updateEntry) {
//        //find oldEntry first to update;
//        try {
//            JournalEntry oldEntry = journalEntryService.findJournalById(myId).orElse(null);
//
//            if (oldEntry != null) {
//                oldEntry.setTitle(!updateEntry.getTitle().isEmpty() ? updateEntry.getTitle() : oldEntry.getTitle());
//                oldEntry.setContent(updateEntry.getContent() != null && !updateEntry.getContent().isEmpty() ? updateEntry.getTitle() : oldEntry.getTitle());
//            }
//            journalEntryService.saveEntry(oldEntry, username);
//            return new ResponseEntity<>(oldEntry, HttpStatus.CREATED);
//        }catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

//    ---------------------------------------------------------------------------------------------------

}

/*
   Controller  =>  service   => repository
*/
