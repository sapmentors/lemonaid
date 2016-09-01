package com.sap.mentors.lemonaid.repository;

import org.springframework.data.repository.CrudRepository;

import com.sap.mentors.lemonaid.entities.Config;

public interface ConfigRepository extends CrudRepository<Config, String> {

}