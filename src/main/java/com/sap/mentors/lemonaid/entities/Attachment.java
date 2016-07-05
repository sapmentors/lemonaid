package com.sap.mentors.lemonaid.entities;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="attachments")
public class Attachment {
	
	@Id
	private String id;

    private String fileName;
    private long fileSize;
    private String mimeType;    
    @Temporal(TemporalType.TIMESTAMP) private Calendar lastModified;
    @Lob private byte[] data;

    @JoinColumn(name="mentorId")
    @ManyToOne(optional = false)
    private Mentor mentorId;
    
	@Override
	public String toString() {
		return "Attachment [id=" + id + ", fileName=" + fileName + ", fileSize=" + fileSize + ", mimeType=" + mimeType
				+ ", lastModified=" + lastModified + ", mentorId=" + mentorId + "]";
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
		
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public long getFileSize() {
		return fileSize;
	}
	
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
	public String getMimeType() {
		return mimeType;
	}
	
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public Calendar getLastModified() {
		return lastModified;
	}

	public void setLastModified(Calendar lastModified) {
		this.lastModified = lastModified;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Mentor getMentorId() {
		return mentorId;
	}

	public void setMentorId(Mentor mentorId) {
		this.mentorId = mentorId;
	}
    
}
