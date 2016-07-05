package com.sap.mentors.lemonaid.rest;

public class User {

	private final String name;
	private final String firstName;
	private final String lastName;
	private final String email;
	private final String displayName;

	public User() {
		this.name = null;
		this.firstName = null;
		this.lastName = null;
		this.email = null;
		this.displayName = null;
	}
	
	public User(String name) {
		this.name = name;
		this.firstName = null;
		this.lastName = null;
		this.email = null;
		this.displayName = null;
	}

	public User(String name, String firstName, String lastName, String email, String displayName) {
		this.name = name;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.displayName = displayName;
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
	
}