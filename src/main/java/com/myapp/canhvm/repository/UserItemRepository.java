package com.myapp.canhvm.repository;

import com.myapp.canhvm.entity.User;
import com.myapp.canhvm.entity.UserItem;
import com.myapp.canhvm.request.AssignItemsForUserRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserItemRepository extends CrudRepository<UserItem, Integer> {

    Optional<UserItem> findById(Long id);

    @Query(value = "SELECT item_id FROM user_item WHERE user_id = :userId", nativeQuery = true)
    Set<Long> findItemsIdByUserId(@Param("userId") Integer userId);

    List<UserItem> findAll();

    Optional<UserItem> findByUserIdAndItemId(Integer userId, Integer itemId);

    @Query(value = "SELECT * FROM user_item WHERE user_id = :userId AND item_id IN (:itemIds)", nativeQuery = true)
    List<UserItem> findByUserIdAndItemIds(@Param("userId") Integer userId, @Param("itemIds") List<Integer> itemIds);

//    User save(User user);

}
