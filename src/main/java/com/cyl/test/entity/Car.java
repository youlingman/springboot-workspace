package com.cyl.test.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    String model;

    String engine;

    @ManyToOne(cascade = CascadeType.PERSIST)	//表示多方
    @JoinColumn(name ="user_id")	//维护一个外键，外键在Users一侧
    private User user;
}
