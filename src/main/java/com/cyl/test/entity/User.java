package com.cyl.test.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    String name;

//    String[] booking;
}
