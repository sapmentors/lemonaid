package com.sap.mentors.lemonaid.repository;

import org.springframework.data.repository.CrudRepository;

import com.sap.mentors.lemonaid.entities.MentorStatus;

public interface MentorStatusRepository extends CrudRepository<MentorStatus, String> {

}