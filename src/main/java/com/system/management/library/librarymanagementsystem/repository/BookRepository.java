package com.system.management.library.librarymanagementsystem.repository;

import com.system.management.library.librarymanagementsystem.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findAll();
}
