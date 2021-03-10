package com.example.demo.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Table(name="package")
@Data
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="in_package_id")
    private int inPackageId;

    @Column(name="user_id")
    private int userId;

    public Package(String name, int inPackageId, int userId) {
        this.name = name;
        this.inPackageId = inPackageId;
        this.userId = userId;
    }
}
