package com.sap.mentors.lemonaid.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sap.mentors.lemonaid.entities.Country;

public interface CountryRepository extends CrudRepository<Country, String> {

	List<Country> findByName(String name);
	
}