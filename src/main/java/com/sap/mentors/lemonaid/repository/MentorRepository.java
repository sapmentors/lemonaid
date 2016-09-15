package com.sap.mentors.lemonaid.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.sap.mentors.lemonaid.entities.Mentor;

public interface MentorRepository extends CrudRepository<Mentor, String> {

    List<Mentor> findByFullName(String fullName);
    
    @Query("SELECT m FROM Mentor m WHERE LOWER(m.userId) = LOWER(:userId)")
    public List<Mentor> findByUserId(@Param("userId") String userId);

    @Query("SELECT m FROM Mentor m WHERE LOWER(m.email1) = LOWER(:email) OR LOWER(m.email2) = LOWER(:email)")
    public List<Mentor> findByEmail(@Param("email") String email);

}