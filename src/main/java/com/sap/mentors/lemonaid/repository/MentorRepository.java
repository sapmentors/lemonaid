package com.sap.mentors.lemonaid.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sap.mentors.lemonaid.entities.Mentor;

public interface MentorRepository extends CrudRepository<Mentor, String> {

    List<Mentor> findByFullName(String fullName);

}