package com.sap.mentors.lemonaid.repository;

import org.springframework.data.repository.CrudRepository;

import com.sap.mentors.lemonaid.entities.Attachment;

public interface AttachmentRepository extends CrudRepository<Attachment, String> {

}