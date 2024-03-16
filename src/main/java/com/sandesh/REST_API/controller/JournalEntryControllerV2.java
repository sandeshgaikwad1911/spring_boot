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

    @GetMapping("/all-entries")
    public ResponseEntity<?> getAllJournalEntries() {
        try {
            List<JournalEntry> allJournalEntries = journalEntryService.getAll();

            if(allJournalEntries.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(allJournalEntries, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    ---------------------------------------------------------------------------------------------------

//            getting JournalEntries of particular user
    @GetMapping("/{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username) {
        try {

            User user = userService.findUserByUsername(username);
//            System.out.println("user"+" "+user);

//            if user is not found
            if(user == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<JournalEntry> allUserEntries = user.getJournalEntries();

//            if user found but [user.getJournalEntries();] are empty
            if(allUserEntries.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
//            finally return entries
            return new ResponseEntity<>(allUserEntries, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    //    ---------------------------------------------------------------------------------------------------
    @PostMapping("/{username}")
    public ResponseEntity<JournalEntry> createEntry(@PathVariable String username, @RequestBody JournalEntry newEntry) {
        try {

//            here we find user first, if user not found we don't save new entry.

            User user = userService.findUserByUsername(username);
//            System.out.println("user"+" "+user);

            if(user == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
//              if user found we call saveEntry() method from JournalEntryService.
            journalEntryService.saveEntry(newEntry, user.getUsername());

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
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //      ---------------------------------------------------------------------------------------------------
    @DeleteMapping("/{username}/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable String username, @PathVariable ObjectId myId) {
        try {
            journalEntryService.deleteJournalById(username, myId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//    ? means wild card entry, we can return any other instance also

//    if we delete particular entry from this url, its get deleted from journalEntries collection,

//    but users collection have ref of this deleted id, it is called cascading delete.
//    in mongoDB it's not deleted automatically from ref collection, we have to do it manually.
//    in relational database it's get deleted automatically

    //    ---------------------------------------------------------------------------------------------------
    @PutMapping("/{username}/update/{myId}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId myId, @PathVariable String username, @RequestBody JournalEntry updateEntry) {

        //find oldEntry first to update;
        try {
            JournalEntry existingEntry = journalEntryService.findJournalById(myId).orElse(null);

            if(existingEntry == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            existingEntry.setTitle(!updateEntry.getTitle().isEmpty() ? updateEntry.getTitle() : existingEntry.getTitle());
            existingEntry.setContent(!updateEntry.getContent().isEmpty() ? updateEntry.getContent() : existingEntry.getContent());

//            here saving updated existing JournalEntry.
            journalEntryService.updateEntry(existingEntry);

            return new ResponseEntity<>(existingEntry, HttpStatus.CREATED);

        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    ---------------------------------------------------------------------------------------------------

}

/*
   Controller  =>  service   => repository

   *    controller calls method defined in service.
   *    method defined in service perform or call Repository [database related methods]
*/
