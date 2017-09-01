package com.java.academy.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.academy.model.Bookstore;

public interface BookstoreDao extends JpaRepository<Bookstore, Long>{

    Bookstore getBookstoreByName(String name);

}
