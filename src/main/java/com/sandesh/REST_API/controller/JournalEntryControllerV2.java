package com.sandesh.REST_API.controller;

import com.sandesh.REST_API.entity.JournalEntry;
import com.sandesh.REST_API.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal/v2")
public class JournalEntryControllerV2 {

    //    ---------------------------------------------------------------------------------------------------
    @Autowired
    private JournalEntryService journalEntryService;        // here we are injecting JournalEntryService,


    //    ---------------------------------------------------------------------------------------------------
    @PostMapping("/create-new")     // url will be => localhost:8080/journal/v2/create-new
    public JournalEntry createEntry(@RequestBody JournalEntry newEntry) {
        // @RequestBody => means data from request body[postman]
        // JournalEntry
        newEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(newEntry);
        return newEntry;
    }
//    createEntry method() gets data from RequestBody according to property of POJO class

    //    ---------------------------------------------------------------------------------------------------
    @GetMapping("/all-records")
    public List<JournalEntry> getAll() {
        return journalEntryService.getAll();
    }

//        ---------------------------------------------------------------------------------------------------

    @GetMapping("/id/{myId}")   // {myId} is dynamic value => localhost:8080//journal/v2/id/dynamic_value
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myId) {
        return journalEntryService.findJournalById(myId).orElse(null);
    }

    //      ---------------------------------------------------------------------------------------------------
    @DeleteMapping("/id/{myId}")
    public String deleteById(@PathVariable ObjectId myId) {
        journalEntryService.findJournalById(myId);
        return "record deleted successfully";
    }

    //    ---------------------------------------------------------------------------------------------------
    @PutMapping("/id/{myId}")
    public JournalEntry updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry updateEntry) {
        //find oldEntry first to update;
        JournalEntry oldEntry = journalEntryService.findJournalById(myId).orElse(null);

        if (oldEntry != null) {
            oldEntry.setTitle(updateEntry.getTitle() != null && !updateEntry.getTitle().isEmpty() ? updateEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(updateEntry.getContent() != null && !updateEntry.getContent().isEmpty() ? updateEntry.getTitle() : oldEntry.getTitle());
        }
        journalEntryService.saveEntry(oldEntry);
        return oldEntry;
    }

//    ---------------------------------------------------------------------------------------------------

}
