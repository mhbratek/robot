package com.java.academy.dao;

import com.java.academy.model.CollectionTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectedDatesDao extends JpaRepository<CollectionTime, Long> {
}
