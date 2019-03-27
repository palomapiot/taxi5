package com.muei.apm.taxi5.apirest.dao;

import com.muei.apm.taxi5.apirest.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverDAO extends JpaRepository<Driver, Long> {
}
