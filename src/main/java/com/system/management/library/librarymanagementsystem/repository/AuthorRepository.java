package com.system.management.library.librarymanagementsystem.repository;

import com.system.management.library.librarymanagementsystem.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
}
