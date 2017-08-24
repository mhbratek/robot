package com.java.academy.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bookstores")
public class Bookstore extends BaseEntity {

	private String name;
	private String url;
	
	public Bookstore() {} //for hibernate

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
