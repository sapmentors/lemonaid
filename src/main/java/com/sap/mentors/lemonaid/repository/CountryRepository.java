package com.sap.mentors.lemonaid.repository;

import org.springframework.data.repository.CrudRepository;

import com.sap.mentors.lemonaid.entities.Country;

public interface CountryRepository extends CrudRepository<Country, String> {

}