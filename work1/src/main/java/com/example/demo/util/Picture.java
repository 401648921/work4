package com.example.demo.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@NoArgsConstructor
@Table(name="picture")
@Data
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="place")
    private String place;

    @Column(name="user_id")
    private int userId;

    @Column(name="time")
    private Date time;

    @Column(name="package_id")
    private int packageId;

    @Column(name="examine")
    private int examine;

    public Picture(String name, String place, int userId, Date time, int packageId, int examine) {
        this.name = name;
        this.place = place;
        this.userId = userId;
        this.time = time;
        this.packageId = packageId;
        this.examine = examine;
    }
}
