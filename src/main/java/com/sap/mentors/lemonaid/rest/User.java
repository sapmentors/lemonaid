package com.sap.mentors.lemonaid.rest;

public class User {

	private final String name;
	private final String firstName;
	private final String lastName;
	private final String email;
	private final String displayName;
	private final boolean isMentor;
	private final boolean isProjectMember;

	public User(String name) {
		this.name = name;
		this.firstName = null;
		this.lastName = null;
		this.email = null;
		this.displayName = null;
		this.isMentor = false;
		this.isProjectMember = false;
	}

	public User(String name, String firstName, String lastName, String email, String displayName, boolean isMentor, boolean isProjectMember) {
		this.name = name;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.displayName = displayName;
		this.isMentor = isMentor;
		this.isProjectMember = isProjectMember;
	}

	public String getName() {
		return name;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getDisplayName() {
		return displayName;
	}

	public boolean isMentor() {
		return isMentor;
	}

	public boolean isProjectMember() {
		return isProjectMember;
	}
	
}