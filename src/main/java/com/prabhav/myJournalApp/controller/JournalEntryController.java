package com.prabhav.myJournalApp.controller;

import com.prabhav.myJournalApp.entity.JournalEntry;
import com.prabhav.myJournalApp.entity.User;
import com.prabhav.myJournalApp.service.JournalEntryService;
import com.prabhav.myJournalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        User user = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<JournalEntry> all = user.getJournalEntries();

        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> creteEntry(@RequestBody JournalEntry myEntry) {
        try {
            journalEntryService.saveEntry(myEntry, SecurityContextHolder.getContext().getAuthentication().getName());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {
        User user = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<JournalEntry> myIdEntry = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).toList();
        if (!myIdEntry.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
        boolean removed = journalEntryService.deleteById(myId, SecurityContextHolder.getContext().getAuthentication().getName());

        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> updateJournalById(
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry newEntry
    ) {
        User user = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<JournalEntry> myIdEntry = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).toList();
        if (!myIdEntry.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if (journalEntry.isPresent()) {
                JournalEntry old = journalEntry.get();
                old.setTitle(!newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
