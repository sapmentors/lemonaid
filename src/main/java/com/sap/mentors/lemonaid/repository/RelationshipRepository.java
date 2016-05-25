package com.sap.mentors.lemonaid.repository;

import org.springframework.data.repository.CrudRepository;

import com.sap.mentors.lemonaid.entities.RelationshipToSap;

public interface RelationshipRepository extends CrudRepository<RelationshipToSap, String> {

}