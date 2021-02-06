package com.chknight.tasklist.repositories;

import com.chknight.tasklist.entities.ToDoItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

/**
 * Repository to handle CRUD operation of the todo item
 * Please notice that, the data is only stored in memory
 */
@Repository
public interface ToDoRepository extends CrudRepository<ToDoItem, Long> {

    Map<Long, ToDoItem> todoItems = new HashMap<>();

}
