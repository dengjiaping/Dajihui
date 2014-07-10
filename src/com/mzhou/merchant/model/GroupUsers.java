package com.mzhou.merchant.model;

import java.util.ArrayList;
import java.util.List;

public class GroupUsers {
	 private String id;
	private String name;
	private String number;
	private List<User> users = new ArrayList<User>();
	public GroupUsers() {
		super();
	}
 
 
 
	public GroupUsers(String id, String name, String number, List<User> users) {
		super();
		this.id = id;
		this.name = name;
		this.number = number;
		this.users = users;
	}



	@Override
	public String toString() {
		return "GroupUsers [id=" + id + ", name=" + name + ", number=" + number + ", users=" + users + "]";
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
}
