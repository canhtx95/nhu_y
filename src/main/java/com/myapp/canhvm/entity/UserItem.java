package com.myapp.canhvm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myapp.canhvm.dto.ItemAssignDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_item")
@Data
public class UserItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "created_date")
    private LocalDateTime createdDated;

    @Column(name = "quantity_in_inventory")
    private Integer quantityInInventory;

    @Column(name = "quantity_on_market")
    private Integer quantityOnMarket = 0;

    @Column(name = "price_on_market")
    private Long priceOnMarket;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name="user_id",insertable = false,updatable = false, nullable = false)
    private User owner;

    public UserItem(){
        this.createdDated = LocalDateTime.now();
    }
    public UserItem(Integer userId, Integer itemId, Integer quantityInInventory) {
        this();
        this.itemId = itemId;
        this.userId = userId;
        this.quantityInInventory = quantityInInventory;
        this.priceOnMarket = 0L;
    }
}
