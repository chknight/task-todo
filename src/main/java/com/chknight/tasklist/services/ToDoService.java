package com.chknight.tasklist.services;

import com.chknight.tasklist.repositories.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToDoService {

    private ToDoRepository repository;

    @Autowired
    ToDoService(
            ToDoRepository repository
    ) {
        this.repository = repository;
    }
}
