package com.sap.mentors.lemonaid.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sap.mentors.lemonaid.entities.Language;

public interface LanguageRepository extends CrudRepository<Language, String> {

	List<Language> findByName(String name);
	
}