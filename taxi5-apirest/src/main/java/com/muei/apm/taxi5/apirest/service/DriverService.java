package com.muei.apm.taxi5.apirest.service;

import com.muei.apm.taxi5.apirest.dao.DriverDAO;
import com.muei.apm.taxi5.apirest.entities.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.util.List;

@Service
public class DriverService {

    @Autowired
    private DriverDAO driverDAO;

    public List<Driver> getAll() {
        return driverDAO.findAll();
    }

    public Driver getOne(Long id) throws InstanceNotFoundException {
        Driver driver = driverDAO.getOne(id);
        if (driver == null)
            throw new InstanceNotFoundException("Driver with id: " + id + "not found");
        return driver;
    }

    public Driver create(Driver driver) {
        return driverDAO.save(driver);
    }

    public void remove(Long id) throws InstanceNotFoundException {
        Driver driver = driverDAO.getOne(id);
        if (driver == null)
            throw new InstanceNotFoundException("Driver with id: " + id + "not found");
        driverDAO.delete(driver);
    }

    public Driver update(Long id, Driver driver) throws InstanceNotFoundException {
        Driver d = driverDAO.getOne(id);
        if (d == null)
            throw new InstanceNotFoundException("Driver with id: " + id + "not found");
        d.setName(driver.getName());
        d.setLastname(driver.getLastname());
        d.setEmail(driver.getEmail());
        d.setActive(driver.isActive());
        d.setImage(driver.getImage());
        d.setLicenseNumber(driver.getLicenseNumber());
        d.setNIF(driver.getNIF());
        d.setPassword(driver.getPassword());
        d.setPhone(driver.getPhone());
        return driverDAO.save(d);
    }

}
