package com.muei.apm.taxi5.apirest.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.time.Instant;

@Entity
@Getter
@Setter
public class Driver implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private String lastname;

    private String phone;

    private String email;

    private String password;

    @Lob
    private Blob image;

    private String NIF;

    private String licenseNumber;

    private boolean active;

    private Instant created;

    public Driver(String name, String lastname, String phone, String email, String password, Blob image, String nif, String licenseNumber, boolean active, Instant created) {
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.image = image;
        NIF = nif;
        this.licenseNumber = licenseNumber;
        this.active = active;
        this.created = created;
    }
}
