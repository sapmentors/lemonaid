package com.sap.mentors.lemonaid.repository;

import org.springframework.data.repository.CrudRepository;

import com.sap.mentors.lemonaid.entities.Gender;

public interface GenderRepository extends CrudRepository<Gender, String> {

}