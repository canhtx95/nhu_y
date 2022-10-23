package com.myapp.canhvm.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "item")
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "created_date")
    private LocalDateTime createdDated;

    @Column(name = "level")
    private Integer level;

    @Column(name = "status")
    private Integer status;

    @Column(name = "description")
    private String description;
}
