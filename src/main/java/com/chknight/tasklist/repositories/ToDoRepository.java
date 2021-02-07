package com.chknight.tasklist.repositories;

import com.chknight.tasklist.entities.ToDoItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

/**
 * Repository to handle CRUD operation of the to-do item
 * Please notice that, the data is only stored in memory
 */
@Repository
public interface ToDoRepository extends CrudRepository<ToDoItemEntity, Long> {

    Map<Long, ToDoItemEntity> todoItems = new HashMap<>();

}
