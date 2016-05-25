package com.sap.mentors.lemonaid.repository;

import org.springframework.data.repository.CrudRepository;

import com.sap.mentors.lemonaid.entities.Topic;

public interface TopicRepository extends CrudRepository<Topic, String> {

}