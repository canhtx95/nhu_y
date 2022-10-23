package com.myapp.canhvm.repository;

import com.myapp.canhvm.entity.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ItemRepository extends CrudRepository<Item, Integer> {

    Optional<Item> findById(Integer id);

    List<Item> findAll();

    Optional<Item> findByItemCode(String itemName);

    @Query(value = "SELECT id FROM item WHERE level = :lv", nativeQuery = true)
    List<Integer> findItemByLevel(@Param("lv") Integer level);


//    Item save(Item item);

//    @Query("SELECT * FROM item WHERE owner_id = :user_id")
//    List<Item> getItemsByUser(@Param("user_id") Long userId);

}
