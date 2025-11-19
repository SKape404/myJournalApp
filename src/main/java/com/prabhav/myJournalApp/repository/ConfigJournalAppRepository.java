package com.prabhav.myJournalApp.repository;

import com.prabhav.myJournalApp.entity.ConfigJournalAppEntity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {
}
