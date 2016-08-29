package com.sap.mentors.lemonaid.repository;

import org.springframework.data.repository.CrudRepository;

import com.sap.mentors.lemonaid.entities.Event;

public interface EventRepository extends CrudRepository<Event, String> {

}